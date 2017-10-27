package cc.oceanz.learn.rocketmq.goods.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "trade_goods_number_log")
public class TradeGoodsNumberLog implements Serializable {

    private static final long serialVersionUID = 8303228522210937293L;

    @Id
    @Column(name = "goods_id")
    private Integer           goodsId;

    @Id
    @Column(name = "order_id")
    private String            orderId;

    @Column(name = "goods_number")
    private Integer           goodsNumber;

    @Column(name = "log_time")
    private Date              logTime;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}