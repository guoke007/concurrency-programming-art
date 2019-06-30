package chapter5;

/**
 * Created by Administrator on 2019/4/23.
 */
public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        //定义两个公共锁
        String lock1 = "锁1";
        String lock2 = "锁2";
        //创建两个执行任务
        Task task1 = new Task(lock1, lock2);
        Task task2 = new Task(lock2, lock1);
        //创建两条线程分别执行两个任务
        Thread thread1 = new Thread(task1, "线程1");
        Thread thread2 = new Thread(task2, "线程2");
        //启动两条线程开始工作
        thread1.start();
        thread2.start();
    }


    private static class Task implements Runnable {
        //定义两把锁
        private final  String lock1;
        private final  String lock2;

        //定义构造方法
        public Task(String lock1, String lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                //获取锁1
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "获取锁：" + lock1);
                    //休息三秒钟
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "睡醒后试图获取锁：" + lock2);
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread().getName() + "获取锁：" + lock2);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
