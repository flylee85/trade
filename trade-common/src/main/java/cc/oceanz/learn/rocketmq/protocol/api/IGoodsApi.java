package cc.oceanz.learn.rocketmq.protocol.api;

import cc.oceanz.learn.rocketmq.protocol.TradeGoodsReq;
import cc.oceanz.learn.rocketmq.protocol.TradeGoodsRet;

/**
 * Created by lwz on 2017/10/20 14:26.
 */
public interface IGoodsApi {

    void addGoodsNumber(TradeGoodsReq tradeGoodsReq);

    void reduceGoodsNumber(TradeGoodsReq tradeGoods);

    TradeGoodsRet queryGoods(TradeGoodsReq tradeGoodsReq);
}
