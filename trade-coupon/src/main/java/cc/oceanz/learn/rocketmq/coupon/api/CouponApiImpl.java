package cc.oceanz.learn.rocketmq.coupon.api;

import cc.oceanz.learn.rocketmq.coupon.service.ICouponService;
import cc.oceanz.learn.rocketmq.coupon.model.TradeCoupon;
import cc.oceanz.learn.rocketmq.protocol.TradeCouponReq;
import cc.oceanz.learn.rocketmq.protocol.TradeCouponRet;
import cc.oceanz.learn.rocketmq.protocol.api.ICouponApi;
import org.apache.commons.beanutils.BeanUtils;
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

        try {
            TradeCouponRet tradeCouponRet = new TradeCouponRet();
            BeanUtils.copyProperties(tradeCouponRet, tradeCoupon);
            return tradeCouponRet;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void changeCouponStatus(TradeCouponReq tradeCouponReq) {
        couponService.changeCouponStatus(tradeCouponReq);
    }
}
