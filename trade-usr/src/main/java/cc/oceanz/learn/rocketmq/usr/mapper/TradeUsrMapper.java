package cc.oceanz.learn.rocketmq.usr.mapper;

import cc.oceanz.learn.rocketmq.dal.TradeMapper;
import cc.oceanz.learn.rocketmq.usr.model.TradeUsr;

/**
 * Created by lwz on 2017/10/18 17:23.
 */
public interface TradeUsrMapper extends TradeMapper<TradeUsr> {

    int reduceUsrMoney(TradeUsr tradeUsr);

    int addUsrMoney(TradeUsr tradeUsr);
}