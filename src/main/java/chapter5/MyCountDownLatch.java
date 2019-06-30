package chapter5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 班级有六个同学，必须六个同学都走了，才能锁门
 * <p/>
 * Created by Administrator on 2019/4/26.
 */
public class MyCountDownLatch {
    private static final int count = 6;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(count);
        //ExecutorService service = Executors.newFixedThreadPool(1);
        for (int i = 1; i <= 6; i++) {
            GoHomeTask task = new GoHomeTask(i, countDownLatch);
            new Thread(task, StudentEnum.foreach(i));
        }
        countDownLatch.await();
        System.out.println("班长锁门了");
    }

    static class GoHomeTask implements Runnable {
        private int i;
        private CountDownLatch countDownLatch;

        public GoHomeTask(int i, CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "同学走了");
            countDownLatch.countDown();
        }
    }

    enum StudentEnum {
        ONE(1, "张三"), TWO(2, "李四"), THREE(3, "王五"), FORE(4, "赵六"), FIVE(5, "钱七"), SIX(6, "孙九"),SEVEN(7, "周十");

        StudentEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String foreach(int i) {
            StudentEnum[] values = StudentEnum.values();
            for (StudentEnum elem : values) {
                if (elem.getCode() == i) {
                    return elem.getName();
                }
            }
            return null;
        }
    }
}
