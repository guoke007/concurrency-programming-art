package chapter5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2019/4/26.
 */
public class MyReadAndWriteLock {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //写数据
    public void put(String key, Object value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写入：" + key);
            Thread.sleep(1000);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    //读数据
    public Object get(String key) {
        lock.readLock().lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取数据");
            Thread.sleep(1000);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "完成读取数据");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        return result;
    }

    public static void main(String[] args) {
        MyReadAndWriteLock cache = new MyReadAndWriteLock();
        for (int i = 0; i < 5; i++) {
            new Thread(new PutTask(cache, "" + i, i)).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(new GetTask(cache, "" + i)).start();
        }
    }

    static class PutTask implements Runnable {
        private MyReadAndWriteLock cache;
        private String key;
        private Object value;

        public PutTask(MyReadAndWriteLock cache, String key, Object value) {
            this.key = key;
            this.value = value;
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.put(key, value);
        }
    }

    static class GetTask implements Runnable {
        private MyReadAndWriteLock cache;
        private String key;
        private Object value;

        public GetTask(MyReadAndWriteLock cache, String key) {
            this.key = key;
            this.cache = cache;
        }

        @Override
        public void run() {
            cache.get(key);
        }
    }
}
