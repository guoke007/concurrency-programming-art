package chapter5;

import java.util.concurrent.atomic.AtomicReference;

public class TestSpinLock {
    private class SpinLock {
        private AtomicReference<Thread> cas = new AtomicReference<>();

        //加锁
        public void lock() {
            Thread thread = Thread.currentThread();
            while (!cas.compareAndSet(null, thread)) {
            }
        }

        //解锁
        public void unlock() {
            Thread thread = Thread.currentThread();
            cas.compareAndSet(thread, null);
        }
    }

    private class ReentrantSpinLock {
        private AtomicReference<Thread> cas = new AtomicReference<>();
        private int count;//代表线程重入的次数

        public void lock() {
            Thread thread = Thread.currentThread();
            if (thread == cas.get()) {
                count++;
                return;
            }
            while (!cas.compareAndSet(null, thread)) {

            }
        }

        public void unLock() {
            Thread thread = Thread.currentThread();
            if (thread != cas.get())
                throw new IllegalMonitorStateException();
            if (count > 0) {
                count--;
            } else {
                cas.compareAndSet(thread, null);
            }

        }
    }
}
