package cc.oceanz.learn.rocketmq.pay.service;

import cc.oceanz.learn.rocketmq.pay.model.TradePay;
import cc.oceanz.learn.rocketmq.protocol.CallbackPaymentReq;
import cc.oceanz.learn.rocketmq.protocol.CreatePaymentReq;
import cc.oceanz.learn.rocketmq.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lwz on 2017/10/18 19:06.
 */
public interface IPayService extends IBaseService<TradePay> {

    void createPayment(CreatePaymentReq createPaymentReq);

    void callbackPayment(CallbackPaymentReq callbackPaymentReq);
}