package cc.oceanz.learn.rocketmq.coupon.service.impl;

import cc.oceanz.learn.rocketmq.coupon.service.ICouponService;
import cc.oceanz.learn.rocketmq.enums.YesNoEnum;
import cc.oceanz.learn.rocketmq.exception.TradeException;
import cc.oceanz.learn.rocketmq.coupon.mapper.TradeCouponMapper;
import cc.oceanz.learn.rocketmq.coupon.model.TradeCoupon;
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
    public void changeCouponStatus(TradeCoupon tradeCoupon) {
        if (tradeCoupon == null || StringUtils.isBlank(tradeCoupon.getCouponId())) {
            throw new TradeException("������������Ż�ȯ���Ϊ��");
        }

        if (StringUtils.equalsIgnoreCase(tradeCoupon.getIsUsed(), YesNoEnum.YES.getCode())) {
            if (StringUtils.isBlank(tradeCoupon.getOrderId())) {
                throw new TradeException("����������󣬶�����Ϊ��");
            }
            int i = tradeCouponMapper.useCoupon(tradeCoupon);
            if (i <= 0) {
                throw new TradeException("ʹ���Ż�ȯʧ��");
            }
        } else if (StringUtils.equalsIgnoreCase(tradeCoupon.getIsUsed(), YesNoEnum.NO.getCode())) {
            tradeCouponMapper.unUseCoupon(tradeCoupon.getCouponId());
        }
    }
}
