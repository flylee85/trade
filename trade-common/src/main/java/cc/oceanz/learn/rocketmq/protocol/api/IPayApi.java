package cc.oceanz.learn.rocketmq.protocol.api;

import cc.oceanz.learn.rocketmq.protocol.CallbackPaymentReq;
import cc.oceanz.learn.rocketmq.protocol.CreatePaymentReq;
import cc.oceanz.learn.rocketmq.protocol.TradePayQuery;
import cc.oceanz.learn.rocketmq.protocol.TradePayRet;
import com.github.pagehelper.PageInfo;

/**
 * Created by lwz on 2017/10/24 16:23.
 */
public interface IPayApi {

    PageInfo<TradePayRet> queryTradePays(TradePayQuery tradePayQuery, int page, int rows);

    void createPayment(CreatePaymentReq createPaymentReq);

    void callbackPayment(CallbackPaymentReq callbackPaymentReq);
}
