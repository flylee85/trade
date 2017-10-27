package cc.oceanz.learn.rocketmq.usr.api;

import cc.oceanz.learn.rocketmq.usr.model.TradeUsr;
import cc.oceanz.learn.rocketmq.protocol.TradeUsrReq;
import cc.oceanz.learn.rocketmq.protocol.TradeUsrRet;
import cc.oceanz.learn.rocketmq.protocol.api.IUsrApi;
import cc.oceanz.learn.rocketmq.usr.service.IUsrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lwz on 2017/10/20 14:08.
 */
@Repository("usrApi")
public class UsrApiImpl implements IUsrApi {

    @Autowired
    private IUsrService usrService;

    @Override
    public TradeUsrRet queryTradeUsr(TradeUsrReq tradeUsrReq) {
        TradeUsr tradeUsr = usrService.selectByKey(tradeUsrReq.getUsrId());
        TradeUsrRet tradeUsrRet = new TradeUsrRet();
        BeanUtils.copyProperties(tradeUsr, tradeUsrRet);
        return tradeUsrRet;
    }

    @Override
    public void changeUsrMoney(TradeUsrReq tradeUsrReq) {
        usrService.changeUsrMoney(tradeUsrReq);
    }
}
