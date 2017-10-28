package cc.oceanz.learn.rocketmq.uitl.mq;

import cc.oceanz.learn.rocketmq.uitl.exception.TradeMqException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Created by lwz on 2017/10/12 16:36.
 */
public class MqProducer {

    private static final Logger LOGGER         = LoggerFactory.getLogger(MqProducer.class);

    private DefaultMQProducer   producer;
    private String              namesrvAddr;
    private String              groupName;

    private int                 maxMessageSize = 1024 * 128;
    private int                 sendMsgTimeout = 3000;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void init() throws TradeMqException {
        if (StringUtils.isBlank(this.namesrvAddr)) {
            throw new TradeMqException("namesrvAddr can not be blank");
        }
        if (StringUtils.isBlank(this.groupName)) {
            throw new TradeMqException("groupName can not be blank");
        }

        this.producer = new DefaultMQProducer(this.groupName);
        this.producer.setNamesrvAddr(this.namesrvAddr);
        this.producer.setMaxMessageSize(this.maxMessageSize);
        this.producer.setSendMsgTimeout(this.sendMsgTimeout);

        try {
            this.producer.start();
            LOGGER.info("producer is started! namesrv: {}, groupName: {}.", this.groupName, this.namesrvAddr);
        } catch (MQClientException e) {
            LOGGER.error("producer start error! namesrv: {}, groupName: {}.", this.groupName, this.namesrvAddr, e);
            throw new TradeMqException(e);
        }
    }

    public void destory() {
        if (producer != null) {
            producer.shutdown();
        }
    }

    public SendResult sendMsg(String topic, String tags, String keys, String msgText) throws TradeMqException {
        if (StringUtils.isBlank(topic)) {
            throw new TradeMqException("topic can not be blank");
        }
        if (StringUtils.isBlank(msgText)) {
            throw new TradeMqException("msgText can not be blank");
        }

        Message msg = new Message(topic, tags, keys, msgText.getBytes());
        try {
            return producer.send(msg);
        } catch (Exception e) {
            LOGGER.error("message send error!", e);
            throw new TradeMqException(e);
        }
    }
}