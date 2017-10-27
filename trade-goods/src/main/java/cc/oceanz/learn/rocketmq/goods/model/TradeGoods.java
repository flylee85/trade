package cc.oceanz.learn.rocketmq.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "trade_goods")
public class TradeGoods implements Serializable {

    private static final long serialVersionUID = -6756998403244302614L;

    @Id
    @Column(name = "goods_id")
    private Integer           goodsId;

    @Column(name = "goods_name")
    private String            goodsName;

    @Column(name = "goods_number")
    private Integer           goodsNumber;

    @Column(name = "goods_price")
    private BigDecimal        goodsPrice;

    @Column(name = "goods_desc")
    private String            goodsDesc;

    @Column(name = "add_time")
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

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}