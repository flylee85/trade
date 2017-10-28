package cc.oceanz.learn.rocketmq.order.service.impl;

import cc.oceanz.learn.rocketmq.constants.MQEnums;
import cc.oceanz.learn.rocketmq.enums.*;
import cc.oceanz.learn.rocketmq.exception.TradeException;
import cc.oceanz.learn.rocketmq.uitl.exception.TradeMqException;
import cc.oceanz.learn.rocketmq.uitl.mq.MqProducer;
import cc.oceanz.learn.rocketmq.uitl.mq.protocol.CancelOrderMq;
import cc.oceanz.learn.rocketmq.order.model.TradeOrder;
import cc.oceanz.learn.rocketmq.order.service.IOrderService;
import cc.oceanz.learn.rocketmq.protocol.*;
import cc.oceanz.learn.rocketmq.protocol.api.ICouponApi;
import cc.oceanz.learn.rocketmq.protocol.api.IGoodsApi;
import cc.oceanz.learn.rocketmq.service.AbstractBaseService;
import cc.oceanz.learn.rocketmq.protocol.api.IUsrApi;
import cc.oceanz.learn.rocketmq.uitl.util.IDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.SendResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.Date;

/**
 * Created by lwz on 2017/10/18 11:44.
 */
@Service
public class OrderServiceImpl extends AbstractBaseService<TradeOrder> implements IOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private IGoodsApi           goodsApi;
    @Autowired
    private ICouponApi          couponApi;
    @Autowired
    private IUsrApi             usrApi;
    @Autowired
    private MqProducer          mqProducer;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public TradeOrder confirmOrder(final TradeOrderReq tradeOrderReq) {

        // 1.У��
        TradeGoodsReq tradeGoodsReq = new TradeGoodsReq();
        tradeGoodsReq.setGoodsId(tradeOrderReq.getGoodsId());
        TradeGoodsRet tradeGoodsRet = goodsApi.queryGoods(tradeGoodsReq);
        checkConfirmTradeOrderReq(tradeOrderReq, tradeGoodsRet);

        // 2.�������ɼ�����
        TradeOrder order = transactionTemplate.execute(new TransactionCallback<TradeOrder>() {
            @Override
            public TradeOrder doInTransaction(TransactionStatus status) {
                return saveNonConfirmOrder(tradeOrderReq);
            }
        });

        // 3.����Զ�̷��񣬿ۿ�棬�ۼ��û�������ɹ�->���Ķ���״̬�ɼ���ʧ��->����mq��Ϣ����ȡ������
        callRemoteService(order.getOrderId(), tradeOrderReq);

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setOrderId(order.getOrderId());
        tradeOrder.setPayAmount(order.getPayAmount());
        return tradeOrder;
    }

    // ------------------------------------------------------------------------
    class CouponException extends RuntimeException {
        public CouponException(Throwable cause) {
            super(cause);
        }
    }

    class OrderException extends RuntimeException {
        public OrderException(Throwable cause) {
            super(cause);
        }
    }

    class GoodsException extends RuntimeException {
        public GoodsException(Throwable cause) {
            super(cause);
        }
    }
    // ------------------------------------------------------------------------

    private void callRemoteService(String orderId, TradeOrderReq tradeOrderReq) {

        try {
            // 1.ʹ���Ż�ȯ
            try {
                if (StringUtils.isNotBlank(tradeOrderReq.getCouponId())) {
                    TradeCouponReq tradeCouponReq = new TradeCouponReq();
                    tradeCouponReq.setOrderId(orderId);
                    tradeCouponReq.setCouponId(tradeOrderReq.getCouponId());
                    tradeCouponReq.setIsUsed(YesNoEnum.YES.getCode());
                    couponApi.changeCouponStatus(tradeCouponReq);
                }
            } catch (Exception e) {
                throw new CouponException(e);
            }

            // 2.�����
            try {
                if (tradeOrderReq.getMoneyPaid() != null && BigDecimal.ZERO.compareTo(tradeOrderReq.getMoneyPaid()) == -1) {
                    TradeUsrReq tradeUsrReq = new TradeUsrReq();
                    tradeUsrReq.setUsrId(tradeOrderReq.getUserId());
                    tradeUsrReq.setOrderId(orderId);
                    tradeUsrReq.setUsrMoney(tradeOrderReq.getMoneyPaid());
                    tradeUsrReq.setMoneyLogType(MoneyLogTypeEnum.PAYMENT.getCode());
                    usrApi.changeUsrMoney(tradeUsrReq);
                }
            } catch (OrderException e) {
                throw new CouponException(e);
            }

            // 3.�ۿ��
            try {
                TradeGoodsReq tradeGoodsReq = new TradeGoodsReq();
                tradeGoodsReq.setGoodsId(tradeOrderReq.getGoodsId());
                tradeGoodsReq.setGoodsNumber(tradeOrderReq.getGoodsNumber());
                goodsApi.reduceGoodsNumber(tradeGoodsReq);
            } catch (GoodsException e) {
                throw new GoodsException(e);
            }

            // �쳣ģ��
            //            if (true) {
            //                throw new TradeException("�˹��쳣");
            //            }

            // 4.���Ķ���״̬
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setOrderId(orderId);
            tradeOrder.setOrderStatus(OrderStatusEnum.CONFIRMED.getCode());
            tradeOrder.setConfirmTime(new Date());
            this.update(tradeOrder);

        } catch (Exception e) {
            MQEnums.TopicEnum topicEnum = MQEnums.TopicEnum.ORDER_CANCEL_ALL;

            if (e instanceof CouponException) {
                topicEnum = MQEnums.TopicEnum.ORDER_CANCEL_COUPON;
            } else if (e instanceof OrderException) {
                topicEnum = MQEnums.TopicEnum.ORDER_CANCEL_ORDER;
            } else if (e instanceof GoodsException) {
                topicEnum = MQEnums.TopicEnum.ORDER_CANCEL_GOODS;
            }

            // ����mq��Ϣ
            CancelOrderMq cancelOrderMq = new CancelOrderMq();
            cancelOrderMq.setOrderId(orderId);
            cancelOrderMq.setUsrId(tradeOrderReq.getUserId());
            cancelOrderMq.setCouponId(tradeOrderReq.getCouponId());
            cancelOrderMq.setGoodsId(tradeOrderReq.getGoodsId());
            cancelOrderMq.setGoodsNumber(tradeOrderReq.getGoodsNumber());
            cancelOrderMq.setUsrMoney(tradeOrderReq.getMoneyPaid());
            try {
                SendResult sendResult = mqProducer.sendMsg(topicEnum.getTopic(), topicEnum.getTag(), orderId, JSON.toJSONString(cancelOrderMq));
                LOGGER.info("sendResult: {}.", JSON.toJSONString(sendResult));
            } catch (TradeMqException tme) {
                // ���ﲻ��������ʱ����ɨtrade_order���Գ�ʱ��δȷ�ϵĶ���������Ϣ
            }

            throw new TradeException(e.getMessage());
        }
    }

    private TradeOrder saveNonConfirmOrder(TradeOrderReq tradeOrderReq) {
        TradeOrder tradeOrder = new TradeOrder();

        String orderId = IDGenerator.generateUUID();
        tradeOrder.setOrderId(orderId);
        tradeOrder.setUserId(tradeOrderReq.getUserId());
        tradeOrder.setAddress(tradeOrderReq.getAddress());
        tradeOrder.setConsignee(tradeOrderReq.getConsignee());
        tradeOrder.setGoodsId(tradeOrderReq.getGoodsId());
        tradeOrder.setGoodsNumber(tradeOrderReq.getGoodsNumber());
        tradeOrder.setGoodsPrice(tradeOrderReq.getGoodsPrice());

        // ��Ʒ�ܼ�
        BigDecimal goodsAmount = tradeOrderReq.getGoodsPrice().multiply(new BigDecimal(tradeOrderReq.getGoodsNumber()));
        tradeOrder.setGoodsAmount(goodsAmount);

        // �˷�
        BigDecimal shippingFee = calculateShippingFee(goodsAmount);
        if (shippingFee.compareTo(tradeOrderReq.getShippingFee()) != 0) {
            throw new TradeException("�˷Ѳ���ȷ");
        }
        tradeOrder.setShippingFee(shippingFee);

        // �����ܼ�
        BigDecimal orderAmount = goodsAmount.add(shippingFee);
        if (orderAmount.compareTo(tradeOrderReq.getOrderAmount()) != 0) {
            throw new TradeException("�����ܼ��쳣���������µ�");
        }
        tradeOrder.setOrderAmount(orderAmount);

        // �Ż�ȯ
        String couponId = tradeOrderReq.getCouponId();
        if (StringUtils.isNotBlank(couponId)) {
            TradeCouponReq tradeCouponReq = new TradeCouponReq();
            tradeCouponReq.setCouponId(couponId);
            TradeCouponRet tradeCouponRet = couponApi.queryCoupon(tradeCouponReq);
            if (tradeCouponRet == null) {
                throw new TradeException("�Ƿ��Ż�ȯ");
            }
            if (StringUtils.equalsIgnoreCase(YesNoEnum.YES.getCode(), tradeCouponRet.getIsUsed())) {
                throw new TradeException("�Ż�ȯ��ʹ��");
            }

            tradeOrder.setCouponId(couponId);
            tradeOrder.setCouponPaid(tradeCouponRet.getCouponPrice());
        } else {
            tradeOrder.setCouponPaid(BigDecimal.ZERO);
        }

        // ���֧��
        if (tradeOrderReq.getMoneyPaid() != null) {
            int i = BigDecimal.ZERO.compareTo(tradeOrderReq.getMoneyPaid());
            if (i == 1) {
                throw new TradeException("�����Ƿ�");
            }

            if (i == -1) {
                TradeUsrReq tradeUsrReq = new TradeUsrReq();
                tradeUsrReq.setUsrId(tradeOrderReq.getUserId());
                TradeUsrRet tradeUsrRet = usrApi.queryTradeUsr(tradeUsrReq);

                if (tradeOrderReq.getMoneyPaid().compareTo(tradeUsrRet.getUsrMoney()) == 1) {
                    throw new TradeException("�˻�����");
                }
            }

            tradeOrder.setMoneyPaid(tradeOrderReq.getMoneyPaid());
        }
        // �����֧��
        else {
            tradeOrder.setMoneyPaid(BigDecimal.ZERO);
        }

        BigDecimal payAmount = orderAmount.subtract(tradeOrder.getMoneyPaid()).subtract(tradeOrder.getCouponPaid());
        tradeOrder.setPayAmount(payAmount);

        tradeOrder.setShippingStatus(ShippingStatusEnum.NO_SHIP.getCode());
        tradeOrder.setPayStatus(PayStatusEnum.NO_PAY.getCode());
        tradeOrder.setOrderStatus(OrderStatusEnum.UNCONFIRMED.getCode());
        tradeOrder.setAddTime(new Date());

        int num = this.insert(tradeOrder);
        if (num != 1) {
            throw new TradeException("��������ʧ��");
        }

        return tradeOrder;
    }

    private BigDecimal calculateShippingFee(BigDecimal goodsAmount) {

        if (goodsAmount.doubleValue() > 100) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(10);
    }

    private void checkConfirmTradeOrderReq(TradeOrderReq tradeOrderReq, TradeGoodsRet tradeGoods) {
        if (tradeOrderReq == null) {
            throw new TradeException("�µ���Ϣ����Ϊ��");
        }

        if (tradeOrderReq.getUserId() == null) {
            throw new TradeException("��Ա�˺Ų���Ϊ��");
        }

        if (tradeOrderReq.getGoodsId() == null) {
            throw new TradeException("��Ʒ��Ų���Ϊ��");
        }

        if (tradeOrderReq.getGoodsNumber() == null || tradeOrderReq.getGoodsNumber() <= 0) {
            throw new TradeException("������������С�ڵ���0");
        }

        if (tradeOrderReq.getAddress() == null) {
            throw new TradeException("�ջ���ַ����Ϊ��");
        }

        if (tradeGoods == null) {
            throw new TradeException("δ��ѯ������Ʒ");
        }

        if (tradeOrderReq.getGoodsNumber() > tradeGoods.getGoodsNumber()) {
            throw new TradeException("�����������");
        }

        if (tradeOrderReq.getGoodsPrice().compareTo(tradeGoods.getGoodsPrice()) != 0) {
            throw new TradeException("��Ʒ�۸��е������������µ�");
        }

        if (tradeOrderReq.getShippingFee() == null) {
            tradeOrderReq.setShippingFee(BigDecimal.ZERO);
        }
        if (tradeOrderReq.getOrderAmount() == null) {
            tradeOrderReq.setOrderAmount(BigDecimal.ZERO);
        }
    }
}
