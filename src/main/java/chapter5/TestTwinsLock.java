package chapter5;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * 测试同步主件
 * 定义了工作者线程Worker，该线程在执行过程中获取锁，当获取锁之后使当前线程睡眠1秒（并不释放锁），
 * 随后打印当前线程名称，最后再次睡眠1秒并释放锁
 */
public class TestTwinsLock {
    static class Work extends Thread {
        static final Lock lock = new TwinsLock();

        public void run() {
            //获取锁
            lock.lock();
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Work work = new Work();
            work.start();
        }
        System.out.println("============");
    }
    @Test
    public void Test1(){
        for (int i = 0; i < 10; i++) {
            Work work = new Work();
            work.start();
        }
        System.out.println("99999999999999999");
    }
}
