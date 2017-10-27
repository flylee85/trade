package cc.oceanz.learn.rocketmq.enums;

/**
 * Created by lwz on 2017/10/19 06:22.
 */
public enum YesNoEnum {

                       NO("0", "·ñ"), YES("1", "ÊÇ");

    private String code;
    private String desc;

    YesNoEnum(String code, String desc) {
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
