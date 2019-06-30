package chapter11;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者任务
 * <p/>
 * Created by Administrator on 2019/4/21.
 */
public class ProducerTask implements Runnable {
    //缓冲队列
    private BlockingQueue<PcData> queue;
    //线程运行标记
    private volatile boolean isRunning = true;
    //线程睡眠时间
    private static final int SLEEPTIME = 1000;
    //操作总数
    private static AtomicInteger count = new AtomicInteger();

    //定义一个构造方法
    public ProducerTask(BlockingQueue<PcData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        PcData pcData = null;
        Random random = new Random();
        System.out.println("start producting id:" + Thread.currentThread().getId());
        while (isRunning) {
            //随机产生一个不大于SLEEPTIME的随机数
            try {
                //线程睡眠
                Thread.sleep(random.nextInt(SLEEPTIME));
                pcData = new PcData(count.incrementAndGet());
                if (!queue.offer(pcData, 3, TimeUnit.SECONDS)) {//如果超过3秒钟仍然没有加入到队列中
                    System.err.println(pcData.getData() + "加入队列失败");
                }else {
                    System.out.println(pcData.getData() + " 加入队列成功");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //终止生产数据到队列中
    public void stop() {
        isRunning = false;
    }
}
