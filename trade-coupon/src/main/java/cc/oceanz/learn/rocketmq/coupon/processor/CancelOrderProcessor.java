package cc.oceanz.learn.rocketmq.coupon.processor;

import cc.oceanz.learn.rocketmq.coupon.service.ICouponService;
import cc.oceanz.learn.rocketmq.uitl.mq.MessageProcessor;
import cc.oceanz.learn.rocketmq.uitl.mq.protocol.CancelOrderMq;
import cc.oceanz.learn.rocketmq.coupon.model.TradeCoupon;
import cc.oceanz.learn.rocketmq.enums.YesNoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Created by lwz on 2017/10/19 20:51.
 */
public class CancelOrderProcessor implements MessageProcessor {

    @Autowired
    private ICouponService      couponService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderProcessor.class);

    @Override
    public boolean handleMessage(MessageExt messageExt) {

        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            String msgId = messageExt.getMsgId();
            String tags = messageExt.getTags();
            String keys = messageExt.getKeys();

            LOGGER.info("coupon cancelOrderProcessor received msg: {}.", body);

            CancelOrderMq cancelOrderMq = JSON.parseObject(body, CancelOrderMq.class);

            if (cancelOrderMq != null && cancelOrderMq.getUsrMoney() != null) {
                TradeCoupon tradeCoupon = new TradeCoupon();
                tradeCoupon.setCouponId(cancelOrderMq.getCouponId());
                tradeCoupon.setIsUsed(YesNoEnum.NO.getCode());
                couponService.update(tradeCoupon);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
