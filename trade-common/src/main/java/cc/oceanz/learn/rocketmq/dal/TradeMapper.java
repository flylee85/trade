package cc.oceanz.learn.rocketmq.dal;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by lwz on 2017/10/20 10:24.
 */
public interface TradeMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
