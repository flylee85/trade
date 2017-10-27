package cc.oceanz.learn.rocketmq.coupon.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "trade_coupon")
public class TradeCoupon implements Serializable {

    private static final long serialVersionUID = 702036211675895392L;

    @Id
    @Column(name = "coupon_Id")
    private String            couponId;

    @Column(name = "coupon_Price")
    private BigDecimal        couponPrice;

    @Column(name = "user_id")
    private Integer           userId;

    @Column(name = "order_id")
    private String            orderId;

    @Column(name = "is_used")
    private String            isUsed;

    @Column(name = "used_time")
    private Date              usedTime;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId == null ? null : couponId.trim();
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed == null ? null : isUsed.trim();
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }
}