package cc.oceanz.learn.rocketmq.goods.api;

import cc.oceanz.learn.rocketmq.goods.service.IGoodsService;
import cc.oceanz.learn.rocketmq.goods.model.TradeGoods;
import cc.oceanz.learn.rocketmq.protocol.TradeGoodsReq;
import cc.oceanz.learn.rocketmq.protocol.TradeGoodsRet;
import cc.oceanz.learn.rocketmq.protocol.api.IGoodsApi;
import org.apache.commons.beanutils.BeanUtils;
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
        goodsService.addGoodsNumber(tradeGoodsReq);
    }

    @Override
    public void reduceGoodsNumber(TradeGoodsReq tradeGoodsReq) {
        goodsService.reduceGoodsNumber(tradeGoodsReq);
    }

    @Override
    public TradeGoodsRet queryGoods(TradeGoodsReq tradeGoodsReq) {
        TradeGoods tradeGoods = goodsService.selectByKey(tradeGoodsReq.getGoodsId());

        try {
            TradeGoodsRet tradeGoodsRet = new TradeGoodsRet();
            BeanUtils.copyProperties(tradeGoodsRet, tradeGoods);
            return tradeGoodsRet;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
