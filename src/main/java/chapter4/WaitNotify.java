package chapter4;

/**
 * Created by Administrator on 2019/5/3.
 * 总结：
 * 等待方：1）获取对象的锁 2）如果条件不满足，调用对象的wait()方法，被通知后仍要检查条件  3）条件满足，执行对应的逻辑
 * 通知方：1）获得对象的锁 2）改变条件 3)通知所有等待在对象上的线程
 */
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        SleepUtils.second(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                //当条件不满足是，继续等待，同时释放锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + "  flag is true wait");
                        lock.wait(200000);
                        System.out.println("===================");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //当条件满足时完成工作
                System.out.println(Thread.currentThread() + "  flag is false running");
            }

        }
    }

    static class Notify implements Runnable {

        @Override
        public void run() {
            //加锁，拥有Lock的monitor
            synchronized (lock) {
                //获取Lock的锁，然后进行通知，通知时不会释放Lock的锁
                //直到当前线程释放了lock后，WaitingThread才能从wait方法中返回
                System.out.println(Thread.currentThread() + "  hold lock notify");
                lock.notify();
                flag = false;
                SleepUtils.second(5);
            }
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "  hold lock again");
                SleepUtils.second(5);
            }

        }
    }
}
