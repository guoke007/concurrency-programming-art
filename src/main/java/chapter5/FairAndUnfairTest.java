package chapter5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试公平锁和非公平锁
 */
public class FairAndUnfairTest {

    private static final ReentrantLock fairLock = new ReentrantLock2(true);
    private static final ReentrantLock unFairLock = new ReentrantLock2(false);

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> threadList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(threadList);
            return threadList;
        }
    }

    //定义任务类
    private class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        public void run() {
            while (true) {
                lock.lock();
                try {
                    Thread.sleep(1000);
                    // 连续2次打印当前的Thread和等待队列中的Thread
                    ReentrantLock2 rLock = (ReentrantLock2) lock;
                    Collection<Thread> queuedThreads = rLock.getQueuedThreads();
                    String str = "waiting by:" + queuedThreads.toString();
                    System.out.print("lock by " + Thread.currentThread().getName() + "  ");
                    System.out.println(str);
                } catch (Exception e) {
                } finally {
                    lock.unlock();
                }
            }

        }
    }

    @Test
    public void TestFairLock() throws InterruptedException {
        testLock(fairLock);
        Thread.sleep(1000 * 10);
    }

    @Test
    public void TestUnfairLock() throws InterruptedException {
        testLock(unFairLock);
        Thread.sleep(1000 * 10);
    }

    // 启动5个Job
    private void testLock(Lock lock) {
        for (int i = 0; i < 5; i++) {
            Job job = new Job(lock);
            job.start();
        }
    }
}
