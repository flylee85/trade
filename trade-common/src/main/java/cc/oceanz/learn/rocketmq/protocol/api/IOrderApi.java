package cc.oceanz.learn.rocketmq.protocol.api;

import cc.oceanz.learn.rocketmq.protocol.TradeOrderReq;
import cc.oceanz.learn.rocketmq.protocol.TradeOrderRet;

/**
 * Created by lwz on 2017/10/20 14:46.
 */
public interface IOrderApi {

    TradeOrderRet confirmOrder(TradeOrderReq tradeOrderReq);
}
