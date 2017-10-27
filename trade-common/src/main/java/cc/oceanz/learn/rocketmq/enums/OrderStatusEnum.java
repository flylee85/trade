package cc.oceanz.learn.rocketmq.enums;

/**
 * Created by lwz on 2017/10/18 21:32.
 */
public enum OrderStatusEnum {

                             UNCONFIRMED("0", "δȷ��"), CONFIRMED("1", "��ȷ��"), CANCELLED("2", "��ȡ��"), INVALID("3", "��Ч"), RETURNED("4", "�˻�");

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
