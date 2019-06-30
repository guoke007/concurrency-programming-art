package chapter7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2019/4/27.
 */
public class MyCounter {
    //定义一个原子变量
    private AtomicInteger atom = new AtomicInteger();
    //定义一个普通变量
    private int i = 0;
    //定义一个countDwonLatch
    private static CountDownLatch latch = new CountDownLatch(100);

    public static void main(String[] args) throws InterruptedException {
        final MyCounter counter = new MyCounter();
        List<Thread> list = new ArrayList<>(200);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 100000; i++) {
                        counter.safeCount();
                        counter.count();
                    }
                    latch.countDown();
                }
            });
            list.add(t);
        }
        for (Thread t : list)
            t.start();
        //等待所有线程执行完毕
      /*  for (Thread t : list)
            t.join();*/
        latch.await();
        System.out.println(counter.i);
        System.out.println(counter.atom.get());
        System.out.println(System.currentTimeMillis() - start);
        //System.exit(0);
    }

    //使用cas实现线程安全计数
    private void safeCount() {
        for (; ; ) {
            //获取当前值
            int currenct = atom.get();
            int newValue = currenct + 1;
            if (atom.compareAndSet(currenct, newValue)) {
                break;
            }
        }
    }

    //非线程安全计数
    private void count() {
        i++;
    }
}
