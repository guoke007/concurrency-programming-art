package chapter11;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/4/21.
 */
public class ConsumeTask implements Runnable {

    private BlockingQueue<PcData> queue;
    private static final int SLEEPTIME = 1000;

    public ConsumeTask(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id :" + Thread.currentThread().getId());
        Random random = new Random();
        while (true) {
            try {
                PcData data = queue.poll(2, TimeUnit.SECONDS);
                //如果两秒钟取不出数据就结束
                if (data == null) {
                    System.out.println(queue.size());
                    break;
                }
                int re = data.getData() * data.getData();
                System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(), data.getData(), re));
                Thread.sleep(random.nextInt(SLEEPTIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
