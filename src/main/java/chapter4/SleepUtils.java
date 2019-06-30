package chapter4;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/5/3.
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
