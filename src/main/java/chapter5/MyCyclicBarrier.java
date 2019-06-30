package chapter5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2019/4/26.
 */
public class MyCyclicBarrier {
    private static final int count = 7;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, new StartWork());
        for (int i = 1; i <= 7; i++) {
            new Thread(new PrepareWork(cyclicBarrier), MyCountDownLatch.StudentEnum.foreach(i)).start();
        }
    }

    static class StartWork implements Runnable {

        @Override
        public void run() {
            System.out.println("人到齐了，开始开会");
        }
    }

    static class PrepareWork implements Runnable {
        private CyclicBarrier cyclicBarrier;

        public PrepareWork(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "到了");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
