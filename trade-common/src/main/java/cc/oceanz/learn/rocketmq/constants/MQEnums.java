package cc.oceanz.learn.rocketmq.constants;

/**
 * Created by lwz on 2017/10/19 16:28.
 */
public class MQEnums {

    public enum TopicEnum {

        ORDER_TOPIC("orderTopic", "confirm"),

        ORDER_CANCEL_COUPON("orderTopic", "cancelCoupon"),
        ORDER_CANCEL_ORDER("orderTopic", "cancelOrder"),
        ORDER_CANCEL_GOODS("orderTopic", "cancelGoods"),
        ORDER_CANCEL_ALL("orderTopic", "cancelAll"),

        PAY_PAID("payTopic", "paid");

        private String topic;
        private String tag;

        TopicEnum(String topic, String tag) {
            this.topic = topic;
            this.tag = tag;
        }

        public String getTopic() {
            return topic;
        }

        public String getTag() {
            return tag;
        }
    }

    public enum ConsumeStatusEnum {
                                   PROCESSING("0", "正在处理"), SUCCESS("1", "处理成功"), FAIL("2", "处理失败");

        private String code;
        private String desc;

        ConsumeStatusEnum(String code, String desc) {
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
}
