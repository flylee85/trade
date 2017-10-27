package cc.oceanz.learn.rocketmq.pay.service.impl;

import cc.oceanz.learn.rocketmq.constants.MQEnums;
import cc.oceanz.learn.rocketmq.enums.YesNoEnum;
import cc.oceanz.learn.rocketmq.exception.TradeException;
import cc.oceanz.learn.rocketmq.pay.mapper.TradeMqProduceTempMapper;
import cc.oceanz.learn.rocketmq.pay.mapper.TradePayMapper;
import cc.oceanz.learn.rocketmq.pay.model.TradeMqProduceTemp;
import cc.oceanz.learn.rocketmq.pay.model.TradePay;
import cc.oceanz.learn.rocketmq.pay.service.IPayService;
import cc.oceanz.learn.rocketmq.protocol.CallbackPaymentReq;
import cc.oceanz.learn.rocketmq.protocol.CreatePaymentReq;
import cc.oceanz.learn.rocketmq.uitl.mq.MqProducer;
import cc.oceanz.learn.rocketmq.uitl.mq.protocol.PaidMQ;
import cc.oceanz.learn.rocketmq.uitl.util.IDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.oceanz.learn.rocketmq.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lwz on 2017/10/18 11:44.
 */
@Service
public class PayServiceImpl extends AbstractBaseService<TradePay> implements IPayService {

    private static final Logger      LOGGER          = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private TradePayMapper           tradePayMapper;
    @Autowired
    private TradeMqProduceTempMapper tradeMqProduceTempMapper;
    @Autowired
    private MqProducer               mqProducer;

    private ExecutorService          executorService = Executors.newFixedThreadPool(10);

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createPayment(CreatePaymentReq createPaymentReq) {
        Example example = new Example(TradePay.class);
        Example.Criteria criteria = example.createCriteria();
        criteria //
            .andEqualTo("orderId", createPaymentReq.getOrderId()) //
            .andEqualTo("isPaid", YesNoEnum.YES.getCode());
        int i = tradePayMapper.selectCountByExample(example);
        if (i > 0) {
            throw new TradeException("该订单已支付");
        }

        String payId = IDGenerator.generateUUID();

        TradePay tradePay = new TradePay();
        tradePay.setPayId(payId);
        tradePay.setOrderId(createPaymentReq.getOrderId());
        tradePay.setPayAmount(createPaymentReq.getPayAmount());
        tradePay.setIsPaid(YesNoEnum.NO.getCode());

        tradePayMapper.insert(tradePay);
        LOGGER.info("创建支付订单成功, paiId : {}", payId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void callbackPayment(CallbackPaymentReq callbackPaymentReq) {

        final TradePay tradePay = tradePayMapper.selectByPrimaryKey(callbackPaymentReq.getOrderId());
        if (tradePay == null) {
            throw new TradeException("订单不存在");
        }

        if (StringUtils.equals(YesNoEnum.YES.getCode(), tradePay.getIsPaid())) {
            throw new TradeException("该订单已支付");
        }

        if (tradePay.getPayAmount().compareTo(callbackPaymentReq.getPayAmount()) != 0) {
            LOGGER.error("===============支付金额异常, callbackPaymentReq : {}.===============", JSON.toJSONString(callbackPaymentReq));
            throw new TradeException("支付金额异常");
        }

        tradePay.setIsPaid(YesNoEnum.YES.getCode());
        int i = tradePayMapper.updateByPrimaryKeySelective(tradePay);
        // 发送可靠消息
        if (i > 0) {
            final PaidMQ paidMQ = new PaidMQ();
            paidMQ.setPayId(tradePay.getPayId());
            paidMQ.setOrderId(tradePay.getOrderId());
            paidMQ.setPayAmount(tradePay.getPayAmount());

            // 记录日志
            final TradeMqProduceTemp tradeMqProduceTemp = new TradeMqProduceTemp();
            tradeMqProduceTemp.setGroupName("trade_pay_topic_group");
            tradeMqProduceTemp.setTopic(MQEnums.TopicEnum.PAY_PAID.getTopic());
            tradeMqProduceTemp.setMsgKeys(tradePay.getOrderId());
            tradeMqProduceTemp.setMsgTag(MQEnums.TopicEnum.PAY_PAID.getTag());
            tradeMqProduceTemp.setMsgBody(JSON.toJSONString(paidMQ));
            tradeMqProduceTemp.setCreateTime(new Date());
            tradeMqProduceTempMapper.insert(tradeMqProduceTemp);

            // 异步发送mq，发送成功清空发送表
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        SendResult sendResult = mqProducer.sendMsg(MQEnums.TopicEnum.PAY_PAID, paidMQ.getPayId(), JSON.toJSONString(paidMQ));
                        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                            tradeMqProduceTempMapper.deleteByPrimaryKey(tradeMqProduceTemp.getId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            throw new TradeException("该订单已支付");
        }
    }
}