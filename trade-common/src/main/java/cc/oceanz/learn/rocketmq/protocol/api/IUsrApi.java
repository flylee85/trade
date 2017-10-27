package cc.oceanz.learn.rocketmq.protocol.api;

import cc.oceanz.learn.rocketmq.protocol.TradeUsrReq;
import cc.oceanz.learn.rocketmq.protocol.TradeUsrRet;

/**
 * Created by lwz on 2017/10/18 11:42.
 */
public interface IUsrApi {

    TradeUsrRet queryTradeUsr(TradeUsrReq tradeUsrReq);

    void changeUsrMoney(TradeUsrReq tradeUsrReq);
}
