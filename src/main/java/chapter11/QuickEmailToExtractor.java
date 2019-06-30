package chapter11;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/4/20.
 */
public class QuickEmailToExtractor extends AbstractExtractor {
    private static Logger logger = Logger.getLogger(QuickEmailToExtractor.class);
    private ThreadPoolExecutor threadPool;
    private ArticleBlockingQueue<ExchangeEmailShallowDTO> emailQueue;

    public QuickEmailToExtractor() {
        emailQueue = new ArticleBlockingQueue<ExchangeEmailShallowDTO>();
        int corPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        threadPool = new ThreadPoolExecutor(corPoolSize, corPoolSize, 101, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000));
    }

    public void extract() throws InterruptedException {
        logger.debug("开始" + getExtractorName() + "...");
        long start = System.currentTimeMillis();
        //抽取所有任务放到队列里面(开启一个线程进行处理)
        new ExtractEmailTask().start();
        //把队列里面的所有文章插入到WIKI中(使用线程池进行处理)
        insertToWiki();
        long end = System.currentTimeMillis();
        double cost = (end - start) / 1000;
        logger.debug("完成" + getExtractorName() + ",花费时间：" + cost + "秒");
    }

    private void insertToWiki() throws InterruptedException {
        //登录Wiki
        while (true) {
            //2秒内取不出就退出
            ExchangeEmailShallowDTO email = emailQueue.poll(2, TimeUnit.SECONDS);
            if (email == null) {
                break;
            }
            threadPool.submit(new InsertToWikiTask(email));
        }
    }

    //获取邮件名称的方法
    private String getExtractorName() {
        return null;
    }

    //将队列里面的文章插入到Wiki任务
    private class InsertToWikiTask implements Runnable {
        private ExchangeEmailShallowDTO exchangeEmailShallowDTO;

        public InsertToWikiTask(ExchangeEmailShallowDTO exchangeEmailShallowDTO) {
            this.exchangeEmailShallowDTO = exchangeEmailShallowDTO;
        }
        @Override
        public void run() {

        }
    }

    //抽取邮件放入到队列里面的任务
    private class ExtractEmailTask extends Thread {
        public void run() {
            extractEmail();
        }
    }

    //抽取邮件到队列中
    private List<Article> extractEmail() {
        List<ExchangeEmailShallowDTO> allEmails = queryAllEmails();
        if (allEmails == null) {
            throw new RuntimeException("没有邮件");
        }
        for (ExchangeEmailShallowDTO exchangeEmailShallowDTO : allEmails) {
            emailQueue.offer(exchangeEmailShallowDTO);
        }
        return null;

    }

    //查询邮件的方法
    private List<ExchangeEmailShallowDTO> queryAllEmails() {
        return null;
    }

    //存放邮件的队列
    private class ArticleBlockingQueue<T> extends LinkedBlockingQueue<T> {
    }

    //文章实体类
    private class Article {
    }

   //
    private class ExchangeEmailShallowDTO {
    }
}
