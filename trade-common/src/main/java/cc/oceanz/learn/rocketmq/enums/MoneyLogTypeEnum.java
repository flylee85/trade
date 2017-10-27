package cc.oceanz.learn.rocketmq.enums;

/**
 * Created by lwz on 2017/10/18 21:05.
 */
public enum MoneyLogTypeEnum {
                              PAYMENT("1", "��������"), REFUND("2", "�����˿�");

    private String code;
    private String desc;

    MoneyLogTypeEnum(String code, String desc) {
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
