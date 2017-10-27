package cc.oceanz.learn.rocketmq.enums;

/**
 * Created by lwz on 2017/10/19 06:05.
 */
public enum ShippingStatusEnum {

                                NO_SHIP("0", "未发货"), SHIPPED("1", "已发货"), RECEIVED("2", "已收货");

    private String code;
    private String desc;

    ShippingStatusEnum(String code, String desc) {
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
