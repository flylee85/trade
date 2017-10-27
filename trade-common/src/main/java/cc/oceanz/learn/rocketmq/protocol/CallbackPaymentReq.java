package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lwz on 2017/10/24 16:26.
 */
public class CallbackPaymentReq implements Serializable {

    private static final long serialVersionUID = 7807329815588362837L;

    private String            orderId;
    private String            payId;
    private BigDecimal        payAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
}
