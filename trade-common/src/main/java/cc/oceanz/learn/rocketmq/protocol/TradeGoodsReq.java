package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;

public class TradeGoodsReq implements Serializable {

    private static final long serialVersionUID = -6756998403244302614L;

    private Integer           goodsId;
    private Integer           goodsNumber;

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
}