package cc.oceanz.learn.rocketmq.uitl.exception;

/**
 * Created by lwz on 2017/10/13 11:40.
 */
public class TradeMqException extends Exception {

    public TradeMqException() {
    }

    public TradeMqException(String message) {
        super(message);
    }

    public TradeMqException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeMqException(Throwable cause) {
        super(cause);
    }

    public TradeMqException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}