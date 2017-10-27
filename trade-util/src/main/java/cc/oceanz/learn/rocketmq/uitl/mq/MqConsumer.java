package cc.oceanz.learn.rocketmq.uitl.mq;

import cc.oceanz.learn.rocketmq.uitl.exception.TradeMqException;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * Created by lwz on 2017/10/12 21:00.
 */
public class MqConsumer {

    private static final Logger   LOGGER           = LoggerFactory.getLogger(MqConsumer.class);

    private DefaultMQPushConsumer consumer;
    private String                namesrvAddr;
    private String                groupName;
    private String                topic;
    private String                tags             = "*";

    private MessageProcessor      messageProcessor;

    private int                   consumeThreadMin = 20;
    private int                   consumeThreadMax = 64;

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public void init() throws Exception {
        consumer = new DefaultMQPushConsumer(groupName);
        this.consumer.setNamesrvAddr(this.namesrvAddr);

        try {
            this.consumer.subscribe(this.topic, this.tags);
            this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            this.consumer.setMessageModel(MessageModel.BROADCASTING);
            this.consumer.setConsumeThreadMin(consumeThreadMin);
            this.consumer.setConsumeThreadMax(consumeThreadMax);

            this.consumer.registerMessageListener(new MessageListener(this.messageProcessor));
            consumer.start();

            LOGGER.info("consumer is started! namesrv: {}, groupName: {}, topic: {}, tags: {}.", this.namesrvAddr, this.groupName, this.topic, this.tags);
        } catch (Exception e) {
            LOGGER.error("consumer start error! namesrv: {}, groupName: {}, topic: {}, tags: {}.", this.namesrvAddr, this.groupName, this.topic, this.tags, e);
            throw new TradeMqException(e);
        }
    }

    public void destory() {
        if (consumer != null) {
            consumer.shutdown();
        }
    }
}
