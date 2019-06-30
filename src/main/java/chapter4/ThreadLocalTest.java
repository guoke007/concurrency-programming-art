package chapter4;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2019/5/3.
 */
public class ThreadLocalTest {
    //第一次get()方法时进行初始化操作（如果set方法没有调用，每个线程都会调用一次）
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        protected Long initialValue() {
            System.out.println("==========");
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final Long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    /*public static void main(String[] args) {
        ThreadLocalTest.begin();
        SleepUtils.second(1);
        System.out.println(ThreadLocalTest.end());
    }*/
    private static ThreadLocal<AtomicInteger> sequecer = ThreadLocal.withInitial(() -> new AtomicInteger(0));

    static class Task implements Runnable {
        public void run() {
            int increment = sequecer.get().getAndIncrement();
            System.out.println("----" + increment);
             sequecer.remove();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DefaultThreadPool<Task> threadPool = new DefaultThreadPool<>();
        for (int i = 0; i < 10000; i++) {
            Task task = new Task();
            threadPool.execute(task);
        }
         //Thread.sleep(1000);
        threadPool.shutdown();
    }
}
