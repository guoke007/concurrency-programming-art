package chapter4;

/**
 * Created by Administrator on 2019/5/3.
 */
public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }
    static class DaemonRunner implements Runnable{

        @Override
        public void run() {
            try {
                SleepUtils.second(10000);
            } finally {
                System.out.println("DeamonThread finally run");
            }
        }
    }
}
