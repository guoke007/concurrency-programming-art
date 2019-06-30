package chapter5;

public class TestDeadlock {
    public static void main(String[] args) {
        String obj1 = "obj1";
        String obj2 = "obj2";
        Task task1 = new Task(obj1, obj2);
        Task task2 = new Task(obj2, obj1);
        Thread thread1 = new Thread(task1, "线程1");
        Thread thread2 = new Thread(task2, "线程2");
        thread1.start();
        thread2.start();
    }

    //定义一个任务类
    private static class Task implements Runnable {
        //定义A,B两把锁
        private String lockA;
        private String lockB;

        public Task(String lockA, String lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            synchronized (lockA) {//拿到A锁，然后试图获取B锁
                try {
                    System.out.println(Thread.currentThread().getName() +"持有："+ lockA);
                    Thread.sleep(1000);
                    synchronized (lockB) {
                        System.out.println(Thread.currentThread().getName() + lockB);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
