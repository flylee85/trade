package cc.oceanz.learn.rocketmq.goods.service.impl;

import cc.oceanz.learn.rocketmq.exception.TradeException;
import cc.oceanz.learn.rocketmq.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.oceanz.learn.rocketmq.goods.mapper.TradeGoodsMapper;
import cc.oceanz.learn.rocketmq.goods.model.TradeGoods;
import cc.oceanz.learn.rocketmq.service.AbstractBaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lwz on 2017/10/18 11:44.
 */
@Service
public class GoodsServiceImpl extends AbstractBaseService<TradeGoods> implements IGoodsService {

    @Autowired
    private TradeGoodsMapper tradeGoodsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void reduceGoodsNumber(TradeGoods tradeGoods) {
        if (tradeGoods == null || tradeGoods.getGoodsId() == null || tradeGoods.getGoodsNumber() == null) {
            throw new TradeException("��������");
        }
        int i = tradeGoodsMapper.reduceGoodsNumber(tradeGoods);
        if (i <= 0) {
            throw new TradeException("��Ʒ���ۼ�ʧ��");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addGoodsNumber(TradeGoods tradeGoods) {
        if (tradeGoods == null || tradeGoods.getGoodsId() == null || tradeGoods.getGoodsNumber() == null) {
            throw new TradeException("��������");
        }
        int i = tradeGoodsMapper.addGoodsNumber(tradeGoods);
        if (i <= 0) {
            throw new TradeException("��Ʒ������ʧ��");
        }
    }
}
