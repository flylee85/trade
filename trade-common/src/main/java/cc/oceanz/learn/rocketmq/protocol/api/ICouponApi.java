package cc.oceanz.learn.rocketmq.protocol.api;

import cc.oceanz.learn.rocketmq.protocol.TradeCouponReq;
import cc.oceanz.learn.rocketmq.protocol.TradeCouponRet;

/**
 * Created by lwz on 2017/10/20 14:12.
 */
public interface ICouponApi {

    TradeCouponRet queryCoupon(TradeCouponReq tradeCouponReq);

    void changeCouponStatus(TradeCouponReq tradeCouponReq);
}
