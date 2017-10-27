package cc.oceanz.learn.rocketmq.usr.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Trade_Usr_Money_Log")
public class TradeUsrMoneyLog implements Serializable {

    private static final long serialVersionUID = -2231552249129951542L;

    @Id
    @Column(name = "usr_id")
    private Integer           usrId;

    @Id
    @Column(name = "order_id")
    private String            orderId;

    @Id
    @Column(name = "money_log_type")
    private String            moneyLogType;

    @Column(name = "usr_money")
    private BigDecimal        usrMoney;

    @Column(name = "create_time")
    private Date              createTime;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMoneyLogType() {
        return moneyLogType;
    }

    public void setMoneyLogType(String moneyLogType) {
        this.moneyLogType = moneyLogType;
    }

    public BigDecimal getUsrMoney() {
        return usrMoney;
    }

    public void setUsrMoney(BigDecimal usrMoney) {
        this.usrMoney = usrMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}