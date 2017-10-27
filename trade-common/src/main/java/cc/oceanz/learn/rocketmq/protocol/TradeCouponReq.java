package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;

/**
 * Created by lwz on 2017/10/20 14:13.
 */
public class TradeCouponReq implements Serializable {

    private static final long serialVersionUID = 702036211675895392L;

    private String            couponId;
    private String            orderId;
    private String            isUsed;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
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
        this.isUsed = isUsed;
    }
}