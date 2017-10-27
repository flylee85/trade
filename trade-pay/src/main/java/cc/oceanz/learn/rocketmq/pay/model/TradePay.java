package cc.oceanz.learn.rocketmq.pay.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "trade_pay")
public class TradePay implements Serializable {

    private static final long serialVersionUID = 5773018959118309482L;

    @Id
    @Column(name = "order_id")
    private String            orderId;

    @Column(name = "pay_id")
    private String            payId;

    @Column(name = "pay_amount")
    private BigDecimal        payAmount;

    @Column(name = "is_paid")
    private String            isPaid;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
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

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid == null ? null : isPaid.trim();
    }

}