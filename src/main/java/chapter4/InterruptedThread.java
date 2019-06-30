package chapter4;

/**
 * Created by Administrator on 2019/5/3.
 */
public class InterruptedThread {
    public static void main(String[] args) {
        //sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "sleepThread");
        sleepThread.setDaemon(true);
        //busyThread不停地运行
        Thread busyThread = new Thread(new BusyRunner(), "busyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();
        //休眠5秒，让sleepThread和BusyThread充分地运行
        SleepUtils.second(5);
        sleepThread.interrupt();
        busyThread.interrupt();
//        SleepThread interrupt is false
//        BusyThread interrupt is true
        System.out.println("SleepThread interrupt is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupt is " + busyThread.isInterrupted());
        //防止sleepThread 和 busyThread 立刻退出
        SleepUtils.second(2);
    }
    static class SleepRunner implements Runnable {

        @Override
        public void run() {
            while (true)
                SleepUtils.second(10);
        }
    }

    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
            }
        }
    }
}
