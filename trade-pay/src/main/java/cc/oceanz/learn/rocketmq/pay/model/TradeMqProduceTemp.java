package cc.oceanz.learn.rocketmq.pay.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "trade_mq_produce_temp")
public class TradeMqProduceTemp implements Serializable {

    private static final long serialVersionUID = -5835441934154811290L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer           id;

    @Column(name = "group_name")
    private String            groupName;

    @Column(name = "topic")
    private String            topic;

    @Column(name = "msg_tag")
    private String            msgTag;

    @Id
    @Column(name = "msg_keys")
    private String            msgKeys;

    @Column(name = "msg_body")
    private String            msgBody;

    @Column(name = "create_time")
    private Date              createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMsgTag() {
        return msgTag;
    }

    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    public String getMsgKeys() {
        return msgKeys;
    }

    public void setMsgKeys(String msgKeys) {
        this.msgKeys = msgKeys;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}