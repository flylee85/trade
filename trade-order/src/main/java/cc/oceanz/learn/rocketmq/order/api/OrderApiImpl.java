package cc.oceanz.learn.rocketmq.order.api;

import cc.oceanz.learn.rocketmq.order.model.TradeOrder;
import cc.oceanz.learn.rocketmq.order.service.IOrderService;
import cc.oceanz.learn.rocketmq.protocol.TradeOrderReq;
import cc.oceanz.learn.rocketmq.protocol.TradeOrderRet;
import cc.oceanz.learn.rocketmq.protocol.api.IOrderApi;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lwz on 2017/10/20 14:50.
 */
@Repository("orderApi")
public class OrderApiImpl implements IOrderApi {

    @Autowired
    private IOrderService orderService;

    @Override
    public TradeOrderRet confirmOrder(TradeOrderReq tradeOrderReq) {
        TradeOrder tradeOrder = orderService.confirmOrder(tradeOrderReq);

        try {
            TradeOrderRet tradeOrderRet = new TradeOrderRet();
            BeanUtils.copyProperties(tradeOrderRet, tradeOrder);
            return tradeOrderRet;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
