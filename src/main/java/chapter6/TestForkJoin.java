package chapter6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2019/4/20.
 */
public class TestForkJoin {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CountTask countTask = new CountTask(1,15);
        ForkJoinPool forkJoinPool = new ForkJoinPool(15);
        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);
        if(countTask.isCompletedAbnormally()){
            System.out.println(countTask.getException());
        }
        System.out.println(result.get());

    }

    static class CountTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected  Integer compute() {
            int sum = 0;
            //如果任务足够小，就计算任务
            boolean canCompute = end - start <= THRESHOLD;
            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                //对任务进行拆分
                int middle = (end + start) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                //执行任务
                invokeAll(leftTask,rightTask);
                Integer left = leftTask.join();
                Integer right = rightTask.join();
                sum = left + right;
                System.out.println("--"+ left+"--"+right+"--"+sum);
            }
            return sum;

        }
    }
}
