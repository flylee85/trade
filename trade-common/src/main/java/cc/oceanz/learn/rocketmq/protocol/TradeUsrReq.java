package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

public class TradeUsrReq implements Serializable {

    private static final long serialVersionUID = 1884183856184105256L;

    private Integer           usrId;
    private BigDecimal        usrMoney;
    private String            orderId;
    private String            moneyLogType;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public BigDecimal getUsrMoney() {
        return usrMoney;
    }

    public void setUsrMoney(BigDecimal usrMoney) {
        this.usrMoney = usrMoney;
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
}