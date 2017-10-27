package cc.oceanz.learn.rocketmq.web;

import cc.oceanz.learn.rocketmq.protocol.*;
import cc.oceanz.learn.rocketmq.protocol.api.*;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;
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

    @Before
    public void Before() {
        System.out.println("============================================ start ============================================");
    }

    @After
    public void after() {
        System.out.println("============================================ end ============================================");
    }

    @Test
    public void testCallbackPay() {
        ExecutorService executor = Executors.newFixedThreadPool(22);

        final TradePayQuery tradePayQuery = new TradePayQuery();
        PageInfo<TradePayRet> pageInfo = payApi.queryTradePays(tradePayQuery, 1, 100);

        List<TradePayRet> list = pageInfo.getList();
        for (final TradePayRet tradePayRet : list) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    CallbackPaymentReq callbackPaymentReq = new CallbackPaymentReq();
                    callbackPaymentReq.setPayId(tradePayRet.getPayId());
                    callbackPaymentReq.setPayAmount(tradePayRet.getPayAmount());
                    payApi.callbackPayment(callbackPaymentReq);
                }
            });
        }

        executor.shutdown();
        while (true) {
            if (executor.isTerminated()) {
                break;
            }
        }
    }

    @Test
    public void createPayment() {
        CallbackPaymentReq callbackPaymentReq = new CallbackPaymentReq();
        payApi.callbackPayment(callbackPaymentReq);
    }

    @Test
    public void testConfirmOrder2() {

        final CountDownLatch latch = new CountDownLatch(100);
        ExecutorService executor = Executors.newFixedThreadPool(22);
        ExecutorService executor2 = Executors.newFixedThreadPool(33);

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
            // 创建订单
            final Future<TradeOrderRet> future = executor.submit(new Callable<TradeOrderRet>() {
                @Override
                public TradeOrderRet call() throws Exception {

                    try {
                        latch.await();

                        TradeOrderRet tradeOrderRet = orderApi.confirmOrder(tradeOrderReq);
                        System.out.println(String.format("%-3s----> ", ai.incrementAndGet()) + JSON.toJSONString(tradeOrderRet));
                        return tradeOrderRet;
                    } catch (Exception e) {
                        System.out.println(String.format("%-3s----> ", ai.incrementAndGet()) + e.getMessage());
                    }

                    return null;
                }
            });

            // 创建支付订单
            executor2.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        TradeOrderRet tradeOrderRet = future.get();
                        if (tradeOrderRet != null) {
                            CreatePaymentReq createPaymentReq = new CreatePaymentReq();
                            createPaymentReq.setOrderId(tradeOrderRet.getOrderId());
                            createPaymentReq.setPayAmount(tradeOrderRet.getPayAmount());
                            payApi.createPayment(createPaymentReq);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            latch.countDown();
        }

        executor.shutdown();
        executor2.shutdown();
        while (true) {
            if (executor.isTerminated() && executor2.isTerminated()) {
                break;
            }
        }
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