package cc.oceanz.learn.rocketmq.usr.service;

import cc.oceanz.learn.rocketmq.usr.model.TradeUsr;
import cc.oceanz.learn.rocketmq.protocol.TradeUsrReq;
import cc.oceanz.learn.rocketmq.service.IBaseService;

/**
 * Created by lwz on 2017/10/18 11:42.
 */
public interface IUsrService extends IBaseService<TradeUsr> {

    void changeUsrMoney(TradeUsrReq tradeUsrReq);
}
