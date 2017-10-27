package cc.oceanz.learn.rocketmq.protocol.api;

import cc.oceanz.learn.rocketmq.protocol.CallbackPaymentReq;
import cc.oceanz.learn.rocketmq.protocol.CreatePaymentReq;

/**
 * Created by lwz on 2017/10/24 16:23.
 */
public interface IPayApi {

    void createPayment(CreatePaymentReq createPaymentReq);

    void callbackPayment(CallbackPaymentReq callbackPaymentReq);
}
