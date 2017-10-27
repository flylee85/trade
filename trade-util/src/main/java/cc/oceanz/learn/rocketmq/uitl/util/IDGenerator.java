package cc.oceanz.learn.rocketmq.uitl.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by lwz on 2017/10/19 06:00.
 */
public class IDGenerator {

    public static String generateUUID() {
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
