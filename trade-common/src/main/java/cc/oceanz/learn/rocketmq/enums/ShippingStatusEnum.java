package cc.oceanz.learn.rocketmq.enums;

/**
 * Created by lwz on 2017/10/19 06:05.
 */
public enum ShippingStatusEnum {

                                NO_SHIP("0", "δ����"), SHIPPED("1", "�ѷ���"), RECEIVED("2", "���ջ�");

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
