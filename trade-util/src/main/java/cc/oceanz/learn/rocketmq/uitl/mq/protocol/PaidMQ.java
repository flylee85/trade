package cc.oceanz.learn.rocketmq.uitl.mq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lwz on 2017/10/25 10:08.
 */
public class PaidMQ implements Serializable {

    private static final long serialVersionUID = 3523280344643211256L;

    private String            payId;
    private String            orderId;
    private BigDecimal        payAmount;

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
}