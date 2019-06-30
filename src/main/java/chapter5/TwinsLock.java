package chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 设计一个同步工具：该工具在同一时刻，只允许至多两个线程同时访问，超过两个线程的
 * 访问将被阻塞
 */
public class TwinsLock implements Lock {
    private final class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {//自旋操作
                int current = getState();
                int newCount = current - reduceCount;
                //当前资源线程已满或者成功获取同步资源-->退出自旋
                if (newCount < 0 || compareAndSetState(current, newCount))
                    return newCount;
            }
        }

        public boolean tryReleaseShared(int increaseCount) {
                for (; ; ) {
                    int current = getState();//获取当前状态值
                    int newState = current + increaseCount;//设置新的状态值
                    if (compareAndSetState(current, newState))
                        return true;
                }
        }
    }

    private final Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
