package cc.oceanz.learn.rocketmq.web;

import cc.oceanz.learn.rocketmq.protocol.*;
import cc.oceanz.learn.rocketmq.protocol.api.*;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lwz on 2017/10/26 11:08.
 */
@ContextConfiguration(locations = { "classpath:spring.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TradeTest {

    @Autowired
    private ICouponApi couponApi;
    @Autowired
    private IOrderApi  orderApi;
    @Autowired
    private IUsrApi    usrApi;
    @Autowired
    private IGoodsApi  goodsApi;
    @Autowired
    private IPayApi    payApi;

    @Test
    public void testConfirmOrder2() {
        final CountDownLatch latch = new CountDownLatch(100);
        ExecutorService executor = Executors.newFixedThreadPool(50);

        final TradeOrderReq tradeOrderReq = new TradeOrderReq();
        tradeOrderReq.setUserId(1);
        tradeOrderReq.setGoodsId(100001);
        tradeOrderReq.setGoodsNumber(1);
        tradeOrderReq.setAddress("北京");
        tradeOrderReq.setGoodsPrice(new BigDecimal(5000));
        tradeOrderReq.setOrderAmount(new BigDecimal(5000));
        tradeOrderReq.setMoneyPaid(new BigDecimal(200));
        //        tradeOrderReq.setCouponId("123456");

        final AtomicInteger ai = new AtomicInteger(0);
        for (int i = 0; i < 100; ++i) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();

                        TradeOrderRet tradeOrderRet = orderApi.confirmOrder(tradeOrderReq);
                        System.out.println(ai.incrementAndGet() + "------------->" + JSON.toJSONString(tradeOrderRet));
                    } catch (Exception e) {
                        //                e.printStackTrace();
                        System.out.println(ai.incrementAndGet() + e.getMessage());
                    }
                }
            });

            latch.countDown();
        }

        executor.shutdown();
        while (true) {
            if (executor.isTerminated()) {
                break;
            }
        }

        System.out.println("========================================");
    }

    @Test
    public void testConfirmOrder1() {

        final TradeOrderReq tradeOrderReq = new TradeOrderReq();
        tradeOrderReq.setUserId(1);
        tradeOrderReq.setGoodsId(100001);
        tradeOrderReq.setGoodsNumber(1);
        tradeOrderReq.setAddress("北京");
        tradeOrderReq.setGoodsPrice(new BigDecimal(5000));
        tradeOrderReq.setOrderAmount(new BigDecimal(5000));
        tradeOrderReq.setMoneyPaid(new BigDecimal(200));
        //        tradeOrderReq.setCouponId("123456");
        TradeOrderRet tradeOrderRet = orderApi.confirmOrder(tradeOrderReq);
        System.out.println(JSON.toJSONString(tradeOrderRet));
    }

    @Test
    public void testCoupon() {
        TradeCouponReq tradeCouponReq = new TradeCouponReq();
        tradeCouponReq.setCouponId("123456");
        TradeCouponRet tradeCouponRet = couponApi.queryCoupon(tradeCouponReq);

        System.out.println(JSON.toJSONString(tradeCouponRet));
    }

    @Test
    public void testGoods() {
        TradeGoodsReq tradeGoodsReq = new TradeGoodsReq();
        tradeGoodsReq.setGoodsId(100001);
        TradeGoodsRet tradeGoodsRet = goodsApi.queryGoods(tradeGoodsReq);

        System.out.println(JSON.toJSONString(tradeGoodsRet));
    }

    @Test
    public void testUsr() {
        TradeUsrReq tradeUsrReq = new TradeUsrReq();
        tradeUsrReq.setUsrId(1);
        TradeUsrRet tradeUsrRet = usrApi.queryTradeUsr(tradeUsrReq);

        System.out.println(JSON.toJSONString(tradeUsrRet));
    }
}