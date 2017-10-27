package cc.oceanz.learn.rocketmq.enums;

/**
 * Created by lwz on 2017/10/18 21:32.
 */
public enum OrderStatusEnum {

                             UNCONFIRMED("0", "未确认"), CONFIRMED("1", "已确认"), CANCELLED("2", "已取消"), INVALID("3", "无效"), RETURNED("4", "退货");

    private String code;
    private String desc;

    OrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
