package chapter11;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2019/4/21.
 */
public class TestProducerAndConsumer {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue queue = new ArrayBlockingQueue(20);
        //创建生产任务
        ProducerTask producerTask1 = new ProducerTask(queue);
        //创建消费任务
        ConsumeTask consumeTask1 = new ConsumeTask(queue);
        //创建线程池来执行任务
        ExecutorService service = Executors.newCachedThreadPool();
        System.out.println("执行");
        service.submit(producerTask1);
        service.submit(consumeTask1);
        Thread.sleep(10 * 1000);
        producerTask1.stop();
        service.shutdown();
    }
}
