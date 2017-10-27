package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lwz on 2017/10/20 14:47.
 */
public class TradeOrderRet implements Serializable {

    private static final long serialVersionUID = 3412169032850200725L;

    private String            orderId;
    private BigDecimal        payAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
