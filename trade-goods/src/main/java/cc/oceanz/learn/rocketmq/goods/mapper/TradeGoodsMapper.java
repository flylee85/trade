package cc.oceanz.learn.rocketmq.goods.mapper;

import cc.oceanz.learn.rocketmq.dal.TradeMapper;
import cc.oceanz.learn.rocketmq.goods.model.TradeGoods;

public interface TradeGoodsMapper extends TradeMapper<TradeGoods> {

    int reduceGoodsNumber(TradeGoods tradeGoods);

    int addGoodsNumber(TradeGoods tradeGoods);
}