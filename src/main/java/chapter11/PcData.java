package chapter11;

/**
 * Created by Administrator on 2019/4/21.
 */
public class PcData {
    private final int data;

    public PcData(int data) {
        this.data = data;
    }

    public PcData(String data) {
        this.data = Integer.valueOf(data);
    }

    public int getData() {
        return data;
    }

    @Override
    public String toString() {
        return "data:" + data;
    }
}
