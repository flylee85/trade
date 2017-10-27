package cc.oceanz.learn.rocketmq.order.service;

import cc.oceanz.learn.rocketmq.protocol.TradeOrderReq;
import cc.oceanz.learn.rocketmq.order.model.TradeOrder;
import cc.oceanz.learn.rocketmq.service.IBaseService;

/**
 * Created by lwz on 2017/10/18 18:51.
 */
public interface IOrderService extends IBaseService<TradeOrder> {

    TradeOrder confirmOrder(TradeOrderReq tradeOrderReq);
}
