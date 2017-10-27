package cc.oceanz.learn.rocketmq.goods.processor;

import cc.oceanz.learn.rocketmq.constants.MQEnums;
import cc.oceanz.learn.rocketmq.goods.model.TradeGoods;
import cc.oceanz.learn.rocketmq.goods.service.IGoodsService;
import cc.oceanz.learn.rocketmq.goods.mapper.TradeMqConsumeLogMapper;
import cc.oceanz.learn.rocketmq.goods.model.TradeMqConsumeLog;
import cc.oceanz.learn.rocketmq.uitl.mq.MessageProcessor;
import cc.oceanz.learn.rocketmq.uitl.mq.protocol.CancelOrderMq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import tk.mybatis.mapper.entity.Example;

/**
 * 包含去重逻辑
 * 
 * Created by lwz on 2017/10/19 20:51.
 */
public class CancelOrderProcessor implements MessageProcessor {

    @Autowired
    private IGoodsService           goodsService;
    @Autowired
    private TradeMqConsumeLogMapper tradeMqConsumeLogMapper;

    private static final Logger     LOGGER = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Override
    public boolean handleMessage(MessageExt messageExt) {
        TradeMqConsumeLog mqConsumeLog = null;

        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();
            String groupName = "goods_order_topic_cancel_group";

            LOGGER.info("goods cancelOrderProcessor received msg: {}", body);

            // 1.幂等处理
            mqConsumeLog = tradeMqConsumeLogMapper.selectByPrimaryKey(msgId);
            // 已经处理
            if (mqConsumeLog != null) {
                // 已经处理成功的消息
                if (MQEnums.ConsumeStatusEnum.SUCCESS.getCode().equals(mqConsumeLog)) {
                    return true;
                } else if (MQEnums.ConsumeStatusEnum.PROCESSING.getCode().equals(mqConsumeLog)) {
                    return false;
                } else if (MQEnums.ConsumeStatusEnum.FAIL.getCode().equals(mqConsumeLog)) {
                    // 处理太多次不成功的转人工
                    if (mqConsumeLog.getConsumerTimes() >= 5) {
                        LOGGER.info("处理超过5次，不再处理");
                        return true;
                    }

                    // 更新处理中状态
                    TradeMqConsumeLog updateMqConsumeLog = new TradeMqConsumeLog();
                    updateMqConsumeLog.setGroupName(groupName);
                    updateMqConsumeLog.setMsgId(msgId);
                    updateMqConsumeLog.setMsgTags(tags);
                    updateMqConsumeLog.setMsgKeys(keys);
                    updateMqConsumeLog.setMsgBody(body);
                    updateMqConsumeLog.setConsumerStatus(MQEnums.ConsumeStatusEnum.PROCESSING.getCode());

                    Example example = new Example(TradeMqConsumeLog.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria //
                        .andEqualTo("msgId", updateMqConsumeLog.getMsgId()) //
                        .andEqualTo("groupName", updateMqConsumeLog.getGroupName()) //
                        .andEqualTo("msgKeys", updateMqConsumeLog.getMsgKeys()) //
                        .andEqualTo("msgTags", updateMqConsumeLog.getMsgTags()) //
                        .andEqualTo("consumerTimes", updateMqConsumeLog.getConsumerTimes());
                    // 乐观锁的方式避免并发
                    int i = tradeMqConsumeLogMapper.updateByExampleSelective(updateMqConsumeLog, example);
                    if (i <= 0) {
                        LOGGER.info("并发处理状态，一会儿重试");
                        return false;
                    }
                }
            }
            // 尚未处理。用主键冲突做并发控制
            else {
                try {
                    mqConsumeLog = new TradeMqConsumeLog();
                    mqConsumeLog.setGroupName(groupName);
                    mqConsumeLog.setMsgId(msgId);
                    mqConsumeLog.setMsgTags(tags);
                    mqConsumeLog.setMsgKeys(keys);
                    mqConsumeLog.setMsgBody(body);
                    mqConsumeLog.setConsumerStatus(MQEnums.ConsumeStatusEnum.PROCESSING.getCode());

                    tradeMqConsumeLogMapper.insertSelective(mqConsumeLog);
                } catch (Exception e) {
                    // 主键冲突，说明订阅正在被处理，稍后再试
                    return false;
                }
            }

            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);

            // 2.业务逻辑处理
            TradeGoods tradeGoods = new TradeGoods();
            tradeGoods.setGoodsId(cancelOrderMq.getGoodsId());
            tradeGoods.setGoodsNumber(cancelOrderMq.getGoodsNumber());
            goodsService.addGoodsNumber(tradeGoods);

            // 3.更新消息处理成功
            TradeMqConsumeLog updateMqConsumeLog = new TradeMqConsumeLog();
            updateMqConsumeLog.setGroupName(groupName);
            updateMqConsumeLog.setMsgId(msgId);
            updateMqConsumeLog.setMsgTags(tags);
            updateMqConsumeLog.setMsgKeys(keys);
            updateMqConsumeLog.setMsgBody(body);
            updateMqConsumeLog.setConsumerTimes(mqConsumeLog.getConsumerTimes() + 1);
            updateMqConsumeLog.setConsumerStatus(MQEnums.ConsumeStatusEnum.SUCCESS.getCode());
            tradeMqConsumeLogMapper.updateByPrimaryKeySelective(updateMqConsumeLog);

            return true;
        } catch (Exception e) {
            TradeMqConsumeLog updateMqConsumeLog = new TradeMqConsumeLog();
            updateMqConsumeLog.setGroupName(mqConsumeLog.getGroupName());
            updateMqConsumeLog.setMsgId(mqConsumeLog.getMsgId());
            updateMqConsumeLog.setMsgTags(mqConsumeLog.getMsgTags());
            updateMqConsumeLog.setMsgKeys(mqConsumeLog.getMsgKeys());
            updateMqConsumeLog.setMsgBody(mqConsumeLog.getMsgBody());
            updateMqConsumeLog.setConsumerTimes(mqConsumeLog.getConsumerTimes() + 1);
            updateMqConsumeLog.setConsumerStatus(MQEnums.ConsumeStatusEnum.FAIL.getCode());
            tradeMqConsumeLogMapper.updateByPrimaryKeySelective(updateMqConsumeLog);

            return false;
        }
    }
}