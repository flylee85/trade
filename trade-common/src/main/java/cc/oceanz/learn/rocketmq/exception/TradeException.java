package cc.oceanz.learn.rocketmq.exception;

/**
 * Created by lwz on 2017/10/20 10:35.
 */
public class TradeException extends RuntimeException {

    public TradeException() {
    }

    public TradeException(String message) {
        super(message);
    }

    public TradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeException(Throwable cause) {
        super(cause);
    }

    public TradeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
