package chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程 操作 资源类
 * 判断  干活 通知
 * 防止虚假唤醒
 * <p/>
 * Created by Administrator on 2019/4/21.
 */
public class TestMoreLock {
    public static void main(String[] args) throws InterruptedException {
        //创建资源类
        OperatorData operatorData = new OperatorData();
        //创建线程任务
        Print5Task print5Task = new Print5Task(operatorData);
        Print10Task print10Task = new Print10Task(operatorData);
        Print15Task print15Task = new Print15Task(operatorData);
        //创建三条执行线程
        Thread thread1 = new Thread(print5Task, "线程1");
        Thread thread2 = new Thread(print10Task, "线程2");
        Thread thread3 = new Thread(print15Task, "线程3");
        //启动三条线程，执行任务
        thread1.start();
        thread2.start();
        thread3.start();
    }

    //打印5次的任务
    private static class Print5Task implements Runnable {
        private OperatorData operatorData;

        public Print5Task(OperatorData operatorData) {
            this.operatorData = operatorData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 4; i++)
                operatorData.print5();
        }
    }

    //打印10次的任务
    private static class Print10Task implements Runnable {
        private OperatorData operatorData;

        public Print10Task(OperatorData operatorData) {
            this.operatorData = operatorData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 4; i++)
                operatorData.print10();
        }
    }

    //打印15次的任务
    private static class Print15Task implements Runnable {
        private OperatorData operatorData;

        public Print15Task(OperatorData operatorData) {
            this.operatorData = operatorData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 4; i++)
                operatorData.print15();
        }
    }

    //定义一个资源类
    //定义一个资源类
    private static class OperatorData {
        private int number = 1;//定义一个标志位
        private Lock lock = new ReentrantLock();
        private Condition condition1 = lock.newCondition();
        private Condition condition2 = lock.newCondition();
        private Condition condition3 = lock.newCondition();

        //打印记录5次的方法
        public void print5() {
            lock.lock();
            try {
                //判断
                while (number != 1) {
                    condition1.await();
                }
                //干活
                for (int i = 0; i < 5; i++)
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                //通知
                number = 2;//修改标志位
                Thread.sleep(1000);
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        //打印记录10次的方法
        public void print10() {
            lock.lock();
            try {
                //判断
                while (number != 2) {
                    condition2.await();
                }
                //干活
                for (int i = 0; i < 10; i++)
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                //通知
                number = 3;//修改标志位
                Thread.sleep(1000);
                condition3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        //打印记录15次的方法
        public void print15() {
            lock.lock();
            try {
                //判断
                while (number != 3) {
                    condition3.await();
                }
                //干活
                for (int i = 0; i < 15; i++)
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                //通知
                number = 1;//修改标志位
                Thread.sleep(1000);
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

