package cc.oceanz.learn.rocketmq.service;

import java.util.List;

/**
 * Created by lwz on 2017/10/18 12:34.
 */
public interface IBaseService<T> {

    T selectByKey(Object key);

    int insert(T t);

    int delete(T t);

    int update(T t);

    int selectCountByExample(Object example);

    List<T> selectByExample(Object example);
}
