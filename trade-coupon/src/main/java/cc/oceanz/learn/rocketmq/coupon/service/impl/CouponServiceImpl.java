package cc.oceanz.learn.rocketmq.coupon.service.impl;

import cc.oceanz.learn.rocketmq.coupon.service.ICouponService;
import cc.oceanz.learn.rocketmq.enums.YesNoEnum;
import cc.oceanz.learn.rocketmq.exception.TradeException;
import cc.oceanz.learn.rocketmq.coupon.mapper.TradeCouponMapper;
import cc.oceanz.learn.rocketmq.coupon.model.TradeCoupon;
import cc.oceanz.learn.rocketmq.protocol.TradeCouponReq;
import cc.oceanz.learn.rocketmq.service.AbstractBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lwz on 2017/10/18 11:44.
 */
@Service
public class CouponServiceImpl extends AbstractBaseService<TradeCoupon> implements ICouponService {

    @Autowired
    private TradeCouponMapper tradeCouponMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeCouponStatus(TradeCouponReq tradeCouponReq) {
        if (tradeCouponReq == null || StringUtils.isBlank(tradeCouponReq.getCouponId())) {
            throw new TradeException("请求参数有误，优惠券编号为空");
        }

        if (StringUtils.equalsIgnoreCase(tradeCouponReq.getIsUsed(), YesNoEnum.YES.getCode())) {
            if (StringUtils.isBlank(tradeCouponReq.getOrderId())) {
                throw new TradeException("请求参数有误，订单号为空");
            }

            TradeCoupon tradeCoupon = new TradeCoupon();
            tradeCoupon.setCouponId(tradeCouponReq.getCouponId());
            tradeCoupon.setOrderId(tradeCouponReq.getOrderId());
            int i = tradeCouponMapper.useCoupon(tradeCoupon);
            if (i <= 0) {
                throw new TradeException("使用优惠券失败");
            }
        } else if (StringUtils.equalsIgnoreCase(tradeCouponReq.getIsUsed(), YesNoEnum.NO.getCode())) {
            tradeCouponMapper.unUseCoupon(tradeCouponReq.getCouponId());
        }
    }
}
