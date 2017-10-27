package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;

/**
 * Created by lwz on 2017/10/27 14:41.
 */
public class TradePayQuery implements Serializable {

    private static final long serialVersionUID = 8937171134175885855L;

    private String            isPaid;

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }
}