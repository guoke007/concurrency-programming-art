package chapter5;

import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2019/4/26.
 */
public class MySemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(4);
        for (int i = 0; i < 6; i++) {
            new Thread(new Parker(semaphore)).start();
        }
    }

    static class Parker implements Runnable {
        private Semaphore semaphore;

        public Parker(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire(2);
                System.out.println(Thread.currentThread().getName() + "抢到停车位");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + "停车3秒后离开");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(2);
            }
        }
    }
}
