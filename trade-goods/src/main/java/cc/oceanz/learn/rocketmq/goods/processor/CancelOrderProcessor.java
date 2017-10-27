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
 * ����ȥ���߼�
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

            // 1.�ݵȴ���
            mqConsumeLog = tradeMqConsumeLogMapper.selectByPrimaryKey(msgId);
            // �Ѿ�����
            if (mqConsumeLog != null) {
                // �Ѿ�����ɹ�����Ϣ
                if (MQEnums.ConsumeStatusEnum.SUCCESS.getCode().equals(mqConsumeLog)) {
                    return true;
                } else if (MQEnums.ConsumeStatusEnum.PROCESSING.getCode().equals(mqConsumeLog)) {
                    return false;
                } else if (MQEnums.ConsumeStatusEnum.FAIL.getCode().equals(mqConsumeLog)) {
                    // ����̫��β��ɹ���ת�˹�
                    if (mqConsumeLog.getConsumerTimes() >= 5) {
                        LOGGER.info("������5�Σ����ٴ���");
                        return true;
                    }

                    // ���´�����״̬
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
                    // �ֹ����ķ�ʽ���Ⲣ��
                    int i = tradeMqConsumeLogMapper.updateByExampleSelective(updateMqConsumeLog, example);
                    if (i <= 0) {
                        LOGGER.info("��������״̬��һ�������");
                        return false;
                    }
                }
            }
            // ��δ������������ͻ����������
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
                    // ������ͻ��˵���������ڱ������Ժ�����
                    return false;
                }
            }

            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);

            // 2.ҵ���߼�����
            TradeGoods tradeGoods = new TradeGoods();
            tradeGoods.setGoodsId(cancelOrderMq.getGoodsId());
            tradeGoods.setGoodsNumber(cancelOrderMq.getGoodsNumber());
            goodsService.addGoodsNumber(tradeGoods);

            // 3.������Ϣ����ɹ�
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