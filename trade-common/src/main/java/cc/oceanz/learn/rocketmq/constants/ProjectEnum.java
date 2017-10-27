package cc.oceanz.learn.rocketmq.constants;

/**
 * Created by lwz on 2017/10/25 15:02.
 */
public enum ProjectEnum {

                         TRADE_COUPON(8080, "/"), TRADE_GOODS(8081, "/"), TRADE_ORDER(8082, "/"), TRADE_PAY(8083, "/"), TRADE_USR(8084, "/");

    int    port;
    String ctxPath;

    ProjectEnum(int port, String ctxPath) {
        this.port = port;
        this.ctxPath = ctxPath;
    }

    public int getPort() {
        return port;
    }

    public String getCtxPath() {
        return ctxPath;
    }
}
