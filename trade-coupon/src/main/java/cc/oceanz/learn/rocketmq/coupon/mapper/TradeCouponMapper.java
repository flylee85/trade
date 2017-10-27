package cc.oceanz.learn.rocketmq.coupon.mapper;

import cc.oceanz.learn.rocketmq.dal.TradeMapper;
import cc.oceanz.learn.rocketmq.coupon.model.TradeCoupon;

public interface TradeCouponMapper extends TradeMapper<TradeCoupon> {

    int useCoupon(TradeCoupon tradeCoupon);

    int unUseCoupon(String couponId);
}