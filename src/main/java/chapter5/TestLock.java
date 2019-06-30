package chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程
 * 操作（方法:判断  干活  通知） +  资源类  = 任务
 * 判断  干活  通知
 * 防止虚假唤醒机制
 * Created by Administrator on 2019/4/21.
 */
public class TestLock {
    public static void main(String[] args) {
        //创建资源类对象
        OperatorData data = new OperatorData();
        //定义两个任务
        IncreaseTask increaseTask = new IncreaseTask(data);
        DecreaseTask decreaseTask = new DecreaseTask(data);
        //创建两条线程
        Thread thread1 = new Thread(increaseTask, "增加线程1");
        Thread thread2 = new Thread(increaseTask, "增加线程2");
        Thread thread5 = new Thread(decreaseTask, "减少线程2");
        Thread thread6 = new Thread(decreaseTask, "减少线程3");
        //同时启动两条线程
        thread1.start();
        thread2.start();
        thread5.start();
        thread6.start();
    }

    //定义一个资源类
    private static class OperatorData {
        private int number = 0;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void increase() {//操作
            lock.lock();
            try {
                while (number != 0) {//先判断（使用while,不能用if,防止虚假唤醒机制）
                    //线程等待
                    condition.await();
                }
                number++; //干活
                //通知唤醒
                //Thread.sleep(1000);
                condition.signalAll();//通知
                System.out.println(Thread.currentThread().getName() + ":" + number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        public void decrease() {//操作
            lock.lock();
            try {
                while (number == 0) {
                    //线程等待
                    condition.await();
                }
                number--;
                //Thread.sleep(1000);
                //通知唤醒
                condition.signalAll();
                System.out.println(Thread.currentThread().getName() + ":" + number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 增加数字五次的任务
     */
    private static class IncreaseTask implements Runnable {
        private OperatorData operatorData;

        public IncreaseTask(OperatorData operatorData) {
            this.operatorData = operatorData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                operatorData.increase();
            }
        }
    }

    /**
     * 减少数字五次的任务
     */
    private static class DecreaseTask implements Runnable {
        private OperatorData operatorData;

        public DecreaseTask(OperatorData operatorData) {
            this.operatorData = operatorData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                operatorData.decrease();
            }
        }
    }
}
