package cc.oceanz.learn.rocketmq.enums;

/**
 * Created by lwz on 2017/10/19 06:03.
 */
public enum PayStatusEnum {

                           NO_PAY("0", "δ����"), PAYING("1", "֧����"), PAID("2", "�Ѹ���");

    private String code;
    private String desc;

    PayStatusEnum(String code, String desc) {
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
