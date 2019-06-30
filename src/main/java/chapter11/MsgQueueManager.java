/*
package com.concurrence.practice;

import org.apache.log4j.Logger;
import sun.plugin2.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

*/
/**
 * Created by Administrator on 2019/4/21.
 *//*

public class MsgQueueManager {
    private static final Logger LOGGER = Logger.getLogger(MsgQueueManager.class);

    //消息总队列
    public final BlockingQueue<Message> messageQueue;

    //定义一个私有的构造方法
    private MsgQueueManager() {
        messageQueue = new LinkedTransferQueue<Message>();
    }

    //存放消息到队列中
    public void put(Message msg) {
        try {
            messageQueue.put(msg);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //从队列中取消息
    public Message take() {
        try {
            return messageQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    private class DispatchMessageTask implements Runnable {

        private BlockingQueue<Message> subQueue;

        @Override
        public void run() {
            //定义一个子队列
            BlockingQueue<Message> subQueue = null;
            for (; ; ) {
                //如果没有数据阻塞在这里
                try {
                    Message msg = messageQueue.take();
                    //如果为空，表示没有session机器连上来
                    //需要等待，直到有session机器连上来
                    while ((subQueue = getSubQueue()) == null) {
                        Thread.sleep(1000);
                    }
                    //将消息放到小队列里面
                    subQueue.put(msg);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    //通过hash散列算法获取一个子队列
    public BlockingQueue<Message> getSubQueue() {
        int errorCount = 0;
        for (; ; ) {
            if (subMsgQueues.isEmpty()) {
                return null;
            }
            int index = (System.nanoTime() % subMsgQueues.size());
            try {
                return subMsgQueues.get(index);
            } catch (Exception e) {
                //出现错误表示，在获取队列大小之后，队列进行了一次删除操作
                LOGGER.error("获取子队列出现错误", e);
                if ((++errorCount < 3)) {
                    continue;
                }
            }
        }
    }
}
*/
