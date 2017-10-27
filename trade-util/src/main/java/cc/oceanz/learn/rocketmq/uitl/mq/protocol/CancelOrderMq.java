package cc.oceanz.learn.rocketmq.uitl.mq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lwz on 2017/10/19 16:52.
 */
public class CancelOrderMq implements Serializable {

    private static final long serialVersionUID = -5494410847584214617L;

    private Integer           usrId;

    private String            orderId;

    private Integer           goodsId;

    private Integer           goodsNumber;

    private String            couponId;

    private BigDecimal        usrMoney;

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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getUsrMoney() {
        return usrMoney;
    }

    public void setUsrMoney(BigDecimal usrMoney) {
        this.usrMoney = usrMoney;
    }
}
