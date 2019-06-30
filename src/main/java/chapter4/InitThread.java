package chapter4;

import java.security.AccessControlContext;

/**
 * Created by Administrator on 2019/5/3.
 */
public class InitThread {
    ThreadGroup group;
    private void init(ThreadGroup g, Runnable target, String name, long stackSize, AccessControlContext acc) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        //当前线程是该线程的父线程
        Thread parent = currentThread();
        this.group = g;

    }

    private Thread currentThread() {
        return null;
    }
}
