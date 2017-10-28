package cc.oceanz.learn.rocketmq.pay.api;

import cc.oceanz.learn.rocketmq.pay.model.TradePay;
import cc.oceanz.learn.rocketmq.pay.service.IPayService;
import cc.oceanz.learn.rocketmq.protocol.CreatePaymentReq;
import cc.oceanz.learn.rocketmq.protocol.CallbackPaymentReq;
import cc.oceanz.learn.rocketmq.protocol.TradePayQuery;
import cc.oceanz.learn.rocketmq.protocol.TradePayRet;
import cc.oceanz.learn.rocketmq.protocol.api.IPayApi;
import cc.oceanz.learn.rocketmq.uitl.util.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lwz on 2017/10/20 14:29.
 */
@Repository("payApi")
public class PayApiImp implements IPayApi {

    @Autowired
    private IPayService payService;

    @Override
    public Page<TradePayRet> queryTradePays(TradePayQuery tradePayQuery, int page, int rows) {
        PageInfo<TradePay> tradePayPageInfo = payService.queryTradePays(tradePayQuery, page, rows);
        return new Page(TradePayRet.class, tradePayPageInfo);
    }

    @Override
    public void createPayment(CreatePaymentReq createPaymentReq) {
        payService.createPayment(createPaymentReq);
    }

    @Override
    public void callbackPayment(CallbackPaymentReq callbackPaymentReq) {
        payService.callbackPayment(callbackPaymentReq);
    }
}