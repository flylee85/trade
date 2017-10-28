package cc.oceanz.learn.rocketmq.coupon.service;

import cc.oceanz.learn.rocketmq.coupon.model.TradeCoupon;
import cc.oceanz.learn.rocketmq.protocol.TradeCouponReq;
import cc.oceanz.learn.rocketmq.service.IBaseService;

/**
 * Created by lwz on 2017/10/18 19:06.
 */
public interface ICouponService extends IBaseService<TradeCoupon> {

    void changeCouponStatus(TradeCouponReq tradeCouponReq);
}