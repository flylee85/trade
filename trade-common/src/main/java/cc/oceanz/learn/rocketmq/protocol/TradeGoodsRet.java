package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lwz on 2017/10/20 14:28.
 */
public class TradeGoodsRet implements Serializable {

    private static final long serialVersionUID = -6756998403244302614L;

    private Integer           goodsId;
    private String            goodsName;
    private Integer           goodsNumber;
    private BigDecimal        goodsPrice;
    private Integer           goodsDesc;
    private Date              addTime;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(Integer goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}