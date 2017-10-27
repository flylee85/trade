package cc.oceanz.learn.rocketmq.goods.service;

import cc.oceanz.learn.rocketmq.goods.model.TradeGoods;
import cc.oceanz.learn.rocketmq.service.IBaseService;

/**
 * Created by lwz on 2017/10/18 19:06.
 */
public interface IGoodsService extends IBaseService<TradeGoods> {

    void reduceGoodsNumber(TradeGoods tradeGoods);

    void addGoodsNumber(TradeGoods tradeGoods);

}