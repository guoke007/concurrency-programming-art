package chapter4;

/**
 * Created by Administrator on 2019/5/3.
 */
public class JoinThread {
    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Domain(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
    }

    static class Domain implements Runnable {
        private Thread thread;

        public Domain(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(thread.getName() + "||" + Thread.currentThread().getName() + " terminate");
        }
    }
}
