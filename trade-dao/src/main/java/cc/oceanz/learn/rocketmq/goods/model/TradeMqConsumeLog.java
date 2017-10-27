package cc.oceanz.learn.rocketmq.goods.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "trade_mq_consume_log")
public class TradeMqConsumeLog {

    @Id
    @Column(name = "msg_id")
    private String  msgId;

    @Column(name = "group_name")
    private String  groupName;

    @Column(name = "msg_tags")
    private String  msgTags;

    @Column(name = "msg_keys")
    private String  msgKeys;

    @Column(name = "msg_body")
    private String  msgBody;

    @Column(name = "consumer_status")
    private String  consumerStatus;

    @Column(name = "consumer_times")
    private Integer consumerTimes;

    @Column(name = "remark")
    private String  remark;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getMsgTags() {
        return msgTags;
    }

    public void setMsgTags(String msgTags) {
        this.msgTags = msgTags == null ? null : msgTags.trim();
    }

    public String getMsgKeys() {
        return msgKeys;
    }

    public void setMsgKeys(String msgKeys) {
        this.msgKeys = msgKeys == null ? null : msgKeys.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody == null ? null : msgBody.trim();
    }

    public String getConsumerStatus() {
        return consumerStatus;
    }

    public void setConsumerStatus(String consumerStatus) {
        this.consumerStatus = consumerStatus == null ? null : consumerStatus.trim();
    }

    public Integer getConsumerTimes() {
        return consumerTimes;
    }

    public void setConsumerTimes(Integer consumerTimes) {
        this.consumerTimes = consumerTimes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}