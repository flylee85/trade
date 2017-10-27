package cc.oceanz.learn.rocketmq.uitl.mq;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Created by lwz on 2017/10/12 21:15.
 */
public interface MessageProcessor {

    boolean handleMessage(MessageExt messageExt);
}
