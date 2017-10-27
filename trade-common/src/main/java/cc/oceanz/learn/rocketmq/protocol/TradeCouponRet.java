package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lwz on 2017/10/20 14:13.
 */
public class TradeCouponRet implements Serializable {

    private static final long serialVersionUID = 702036211675895392L;

    private String            couponId;
    private BigDecimal        couponPrice;
    private Integer           userId;
    private String            orderId;
    private String            isUsed;
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