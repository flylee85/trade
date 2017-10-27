package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lwz on 2017/10/27 14:41.
 */
public class TradePayRet implements Serializable {

    private static final long serialVersionUID = 8937171134175885855L;

    private String            payId;

    private String            orderId;

    private BigDecimal        payAmount;

    private String            isPaid;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

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

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }
}