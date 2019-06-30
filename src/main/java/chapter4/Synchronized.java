package chapter4;

/**
 * Created by Administrator on 2019/5/3.
 */
public class Synchronized {
    public static void main(String[] args) {
        synchronized (Synchronized.class){

        }
        m();
    }

    public  static synchronized void m() {

    }
}
