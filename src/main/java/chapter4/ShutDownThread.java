package chapter4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2019/5/3.
 */
public class ShutDownThread {
    public static void main(String[] args) {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();
        //睡眠一秒后，对countThread线程进行中断，使得countThread线程能够感知到中断然后结束
        SleepUtils.second(1);
        countThread.interrupt();
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        //睡眠一秒钟对countThread线程进行取消，使得countThread线程能够感知到取消然后结束
        SleepUtils.second(1);
        two.cancel();
    }

    private static class Runner implements Runnable {
        private AtomicInteger i = new AtomicInteger();
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                 i.getAndIncrement();
            }
            System.out.println("Count i = " + i);
        }

        public void cancel() {
            on = false;
        }
    }
}
