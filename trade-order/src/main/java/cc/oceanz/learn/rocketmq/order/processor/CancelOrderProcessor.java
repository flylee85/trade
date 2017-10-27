package cc.oceanz.learn.rocketmq.order.processor;

import cc.oceanz.learn.rocketmq.enums.OrderStatusEnum;
import cc.oceanz.learn.rocketmq.order.model.TradeOrder;
import cc.oceanz.learn.rocketmq.order.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;

import cc.oceanz.learn.rocketmq.uitl.mq.MessageProcessor;
import cc.oceanz.learn.rocketmq.uitl.mq.protocol.CancelOrderMq;

/**
 * Created by lwz on 2017/10/19 20:51.
 */
public class CancelOrderProcessor implements MessageProcessor {

    @Autowired
    private IOrderService       orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Override
    public boolean handleMessage(MessageExt messageExt) {

        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();

            LOGGER.info("order cancelOrderProcessor received msg: {}.", body);

            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);

            if (cancelOrderMq != null && cancelOrderMq.getUsrMoney() != null) {
                TradeOrder tradeOrder = new TradeOrder();
                tradeOrder.setOrderId(cancelOrderMq.getOrderId());
                tradeOrder.setOrderStatus(OrderStatusEnum.CANCELLED.getCode());
                orderService.update(tradeOrder);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}