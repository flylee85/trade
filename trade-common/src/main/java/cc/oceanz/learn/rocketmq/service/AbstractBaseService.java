package cc.oceanz.learn.rocketmq.service;

import java.util.List;

import cc.oceanz.learn.rocketmq.dal.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lwz on 2017/09/18 17:39.
 */
public abstract class AbstractBaseService<T> {

    @Autowired
    private TradeMapper<T> mapper;

    @Transactional(readOnly = true)
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Transactional(readOnly = true)
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    @Transactional(readOnly = true)
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
}