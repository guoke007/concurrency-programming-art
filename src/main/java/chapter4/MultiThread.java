package chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by Administrator on 2019/5/3.
 */
public class MultiThread {
    public static void main(String[] args) {
        //获取线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        /**
         * [10]Monitor Ctrl-Break
         * [5]Attach Listener
         * [4]Signal Dispatcher
         * [3]Finalizer
         * [2]Reference Handler
         * [1]main
         */
        for (int i = 0; i < threadInfos.length; i++) {
            ThreadInfo info = threadInfos[i];
            System.out.println("[" + info.getThreadId() + "]" + info.getThreadName());
        }
    }
}
