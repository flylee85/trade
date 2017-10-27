package cc.oceanz.learn.rocketmq.coupon.api;

import cc.oceanz.learn.rocketmq.coupon.service.ICouponService;
import cc.oceanz.learn.rocketmq.coupon.model.TradeCoupon;
import cc.oceanz.learn.rocketmq.protocol.TradeCouponReq;
import cc.oceanz.learn.rocketmq.protocol.TradeCouponRet;
import cc.oceanz.learn.rocketmq.protocol.api.ICouponApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lwz on 2017/10/20 14:13.
 */
@Repository("couponApi")
public class CouponApiImpl implements ICouponApi {

    @Autowired
    private ICouponService couponService;

    @Override
    public TradeCouponRet queryCoupon(TradeCouponReq tradeCouponReq) {
        TradeCoupon tradeCoupon = couponService.selectByKey(tradeCouponReq.getCouponId());
        TradeCouponRet tradeCouponRet = new TradeCouponRet();
        BeanUtils.copyProperties(tradeCoupon, tradeCouponRet);
        return tradeCouponRet;
    }

    @Override
    public void changeCouponStatus(TradeCouponReq tradeCouponReq) {
        TradeCoupon tradeCoupon = new TradeCoupon();
        BeanUtils.copyProperties(tradeCouponReq, tradeCoupon);
        couponService.changeCouponStatus(tradeCoupon);
    }
}
