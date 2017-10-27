package cc.oceanz.learn.rocketmq.usr.service.impl;

import cc.oceanz.learn.rocketmq.enums.MoneyLogTypeEnum;
import cc.oceanz.learn.rocketmq.exception.TradeException;
import cc.oceanz.learn.rocketmq.usr.mapper.TradeUsrMapper;
import cc.oceanz.learn.rocketmq.usr.mapper.TradeUsrMoneyLogMapper;
import cc.oceanz.learn.rocketmq.usr.model.TradeUsr;
import cc.oceanz.learn.rocketmq.usr.model.TradeUsrMoneyLog;
import cc.oceanz.learn.rocketmq.service.AbstractBaseService;
import cc.oceanz.learn.rocketmq.protocol.TradeUsrReq;
import cc.oceanz.learn.rocketmq.usr.service.IUsrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lwz on 2017/10/18 11:44.
 */
@Service
public class UsrServiceImpl extends AbstractBaseService<TradeUsr> implements IUsrService {

    @Autowired
    private TradeUsrMapper         tradeUsrMapper;
    @Autowired
    private TradeUsrMoneyLogMapper tradeUsrMoneyLogMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeUsrMoney(TradeUsrReq tradeUsrReq) {
        if (tradeUsrReq == null || tradeUsrReq.getUsrId() == null || tradeUsrReq.getUsrMoney() == null) {
            throw new TradeException("��������");
        }
        if (BigDecimal.ZERO.compareTo(tradeUsrReq.getUsrMoney()) >= 0) {
            throw new TradeException("����С��0");
        }

        TradeUsrMoneyLog tradeUsrMoneyLog = new TradeUsrMoneyLog();
        tradeUsrMoneyLog.setUsrId(tradeUsrReq.getUsrId());
        tradeUsrMoneyLog.setOrderId(tradeUsrReq.getOrderId());
        tradeUsrMoneyLog.setUsrMoney(tradeUsrReq.getUsrMoney());
        tradeUsrMoneyLog.setMoneyLogType(tradeUsrReq.getMoneyLogType());
        tradeUsrMoneyLog.setCreateTime(new Date());

        // 1.�޸��û����
        // a.����
        // ��ѯ�Ƿ��и����¼
        Example example = new Example(TradeUsrMoneyLog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria //
            .andEqualTo("usrId", tradeUsrReq.getUsrId()) //
            .andEqualTo("orderId", tradeUsrReq.getOrderId()) //
            .andEqualTo("moneyLogType", MoneyLogTypeEnum.PAYMENT.getCode());
        int count = tradeUsrMoneyLogMapper.selectCountByExample(example);

        if (StringUtils.equalsIgnoreCase(MoneyLogTypeEnum.PAYMENT.getCode(), tradeUsrReq.getMoneyLogType())) {
            if (count > 0) {
                throw new TradeException("�����Ѹ���");
            }
            TradeUsr tradeUsr = new TradeUsr();
            tradeUsr.setUsrId(tradeUsrReq.getUsrId());
            tradeUsr.setUsrMoney(tradeUsrReq.getUsrMoney());
            int i = tradeUsrMapper.reduceUsrMoney(tradeUsr);
            if (i <= 0) {
                throw new TradeException("���㣬�ۿ�ʧ��");
            }
        }
        // b.�˿�
        else if (MoneyLogTypeEnum.REFUND.getCode().equalsIgnoreCase(tradeUsrReq.getMoneyLogType())) {
            if (count == 0) {
                throw new TradeException("û�и�����Ϣ�������˿�");
            }

            // ��ֹ����˿�
            criteria //
                .andEqualTo("usrId", tradeUsrReq.getUsrId()) //
                .andEqualTo("orderId", tradeUsrReq.getOrderId()) //
                .andEqualTo("moneyLogType", MoneyLogTypeEnum.REFUND.getCode());

            count = tradeUsrMoneyLogMapper.selectCountByExample(example);
            if (count > 0) {
                throw new TradeException("�Ѿ��˹����ˣ������ظ��˿�");
            }

            TradeUsr tradeUsr = new TradeUsr();
            tradeUsr.setUsrId(tradeUsrReq.getUsrId());
            tradeUsr.setUsrMoney(tradeUsrReq.getUsrMoney());
            tradeUsrMapper.addUsrMoney(tradeUsr);
        }

        // 2.��¼���䶯��־
        this.tradeUsrMoneyLogMapper.insert(tradeUsrMoneyLog);
    }
}