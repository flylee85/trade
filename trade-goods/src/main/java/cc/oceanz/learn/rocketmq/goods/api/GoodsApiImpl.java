package cc.oceanz.learn.rocketmq.goods.api;

import cc.oceanz.learn.rocketmq.exception.TradeException;
import cc.oceanz.learn.rocketmq.goods.service.IGoodsService;
import cc.oceanz.learn.rocketmq.goods.model.TradeGoods;
import cc.oceanz.learn.rocketmq.protocol.TradeGoodsReq;
import cc.oceanz.learn.rocketmq.protocol.TradeGoodsRet;
import cc.oceanz.learn.rocketmq.protocol.api.IGoodsApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lwz on 2017/10/20 14:29.
 */
@Repository("goodsApi")
public class GoodsApiImpl implements IGoodsApi {

    @Autowired
    private IGoodsService goodsService;

    @Override
    public void addGoodsNumber(TradeGoodsReq tradeGoodsReq) {
        TradeGoods tradeGoods = new TradeGoods();
        BeanUtils.copyProperties(tradeGoodsReq, tradeGoods);
        goodsService.addGoodsNumber(tradeGoods);
    }

    @Override
    public void reduceGoodsNumber(TradeGoodsReq tradeGoodsReq) {
        TradeGoods tradeGoods = new TradeGoods();
        BeanUtils.copyProperties(tradeGoodsReq, tradeGoods);
        goodsService.reduceGoodsNumber(tradeGoods);
    }

    @Override
    public TradeGoodsRet queryGoods(TradeGoodsReq tradeGoodsReq) {
        TradeGoodsRet tradeGoodsRet = new TradeGoodsRet();
        TradeGoods tradeGoods = goodsService.selectByKey(tradeGoodsReq.getGoodsId());
        if (tradeGoods == null) {
            throw new TradeException("商品不存在");
        }
        BeanUtils.copyProperties(tradeGoods, tradeGoodsRet);
        return tradeGoodsRet;
    }
}
