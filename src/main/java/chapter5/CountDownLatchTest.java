package chapter5;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    static CountDownLatch c = new CountDownLatch(2);
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(1);
                    Thread.sleep(1000);
                    c.countDown();
                    System.out.println(2);
                    Thread.sleep(1000);
                    c.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        c.await();
        System.out.println("3");
    }
}