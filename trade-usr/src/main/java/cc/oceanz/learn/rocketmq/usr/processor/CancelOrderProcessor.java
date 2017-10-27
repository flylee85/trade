package cc.oceanz.learn.rocketmq.usr.processor;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;

import cc.oceanz.learn.rocketmq.enums.MoneyLogTypeEnum;
import cc.oceanz.learn.rocketmq.protocol.TradeUsrReq;
import cc.oceanz.learn.rocketmq.uitl.mq.MessageProcessor;
import cc.oceanz.learn.rocketmq.uitl.mq.protocol.CancelOrderMq;
import cc.oceanz.learn.rocketmq.usr.service.IUsrService;

/**
 * Created by lwz on 2017/10/19 20:51.
 */
public class CancelOrderProcessor implements MessageProcessor {

    @Autowired
    private IUsrService         usrService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Override
    public boolean handleMessage(MessageExt messageExt) {

        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();

            LOGGER.info("usr cancelOrderProcessor received msg: {}.", body);

            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);

            if (cancelOrderMq != null && cancelOrderMq.getUsrMoney() != null && BigDecimal.ZERO.compareTo(cancelOrderMq.getUsrMoney()) == -1) {
                TradeUsrReq tradeUsrReq = new TradeUsrReq();
                tradeUsrReq.setUsrId(cancelOrderMq.getUsrId());
                tradeUsrReq.setOrderId(cancelOrderMq.getOrderId());
                tradeUsrReq.setUsrMoney(cancelOrderMq.getUsrMoney());
                tradeUsrReq.setMoneyLogType(MoneyLogTypeEnum.REFUND.getCode());
                usrService.changeUsrMoney(tradeUsrReq);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
