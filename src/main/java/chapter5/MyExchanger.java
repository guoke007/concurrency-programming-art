package chapter5;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/5/4.
 */
public class MyExchanger {
    private static final Exchanger<String> exgr = new Exchanger<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String a = "银行流水a";//a录入的银行流水数据
                    String x = exgr.exchange(a);
                    System.out.println(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String b = "银行流水b";//b录入的银行流水
                    String a = exgr.exchange(b);
                    System.out.println("a和b数据是否一致：" + a.equals(b) + ",a录入的是：" + a + ",b录入的是：" + b);
                } catch (Exception e) {

                }finally {
                    pool.shutdown();
                }
            }
        });
    }
}
