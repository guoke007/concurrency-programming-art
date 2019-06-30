package chapter6;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Administrator on 2019/4/20.
 */
public class TestMergerSort {
    private static int MAX = 100000000;
    private static int initArray[] = new int[MAX];

    // 生成一个数量为MAX的随机整数集合，准备计算数据
    static {
        Random r = new Random();
        for (int index = 1; index <= MAX; index++) {
            initArray[index - 1] = r.nextInt(10000000);
        }
    }

    //测试fangfa
    @Test
    public void Test1() {
        long beginTime = System.currentTimeMillis();
        int sortArr[] = forkArray(initArray);
        long endTime = System.currentTimeMillis();
        // 如果参与排序的数据非常庞大，记得把这种打印方式去掉
        System.out.println("耗时:" + (endTime - beginTime) + "毫秒");//24710毫秒
    }

    @Test
    public void Test2() throws ExecutionException, InterruptedException {
        SortTask sortTask = new SortTask(0, initArray.length - 1);
        ForkJoinPool joinPool = new ForkJoinPool();
        long beginTime = System.currentTimeMillis();
        ForkJoinTask<int[]> task = joinPool.submit(sortTask);
        int[] sortArray = task.get();
        long endTime = System.currentTimeMillis();
        // 如果参与排序的数据非常庞大，记得把这种打印方式去掉
        System.out.println("耗时:" + (endTime - beginTime) + "毫秒");//10755
        System.out.println(sortArray.length);
        //System.out.println(sortArray.length + Arrays.toString(sortArray));
    }
    //大数组拆分为小数组
    private static int[] forkArray(int[] arr) {
        int length = arr.length;
        if (length > 2) {
            int midIndex = length / 2;
            int array1[] = forkArray(Arrays.copyOf(arr, midIndex));//拆分数组
            int array2[] = forkArray(Arrays.copyOfRange(arr, midIndex, length));//拆分数组
            return joinArray(array1, array2); // 将两个有序的数组，合并成一个有序的数组
        } else {
            // 如果条件成立，说明数组中只有一个元素，或者是数组中的元素都已经排列好位置了
            if (length == 1 || arr[0] <= arr[1]) {
                return arr;
            } else {
                int temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
                return arr;
            }
        }
    }

    //合并数组排序结果
    private static int[] joinArray(int[] array1, int[] array2) {
        int len1 = array1.length;
        int len2 = array2.length;
        int mergerArr[] = new int[len1 + len2];
        int mergeLen = len1 + len2;
        // 只需要以新的集合destInts的长度为标准，遍历一次即可
        for (int index = 0, array1Index = 0, array2Index = 0; index < mergeLen; index++) {
            int value1 = array1Index >= len1 ? Integer.MAX_VALUE : array1[array1Index];
            int value2 = array2Index >= len2 ? Integer.MAX_VALUE : array2[array2Index];
            // 如果条件成立，说明应该取数组array1中的值
            if (value1 < value2) {
                array1Index++;
                mergerArr[index] = value1;
            } else {// 否则取数组array2中的值
                array2Index++;
                mergerArr[index] = value2;
            }
        }
        return mergerArr;
    }

    class SortTask extends RecursiveTask<int[]> {
        private int begin;
        private int end;
        private static final int THRESHOLD = 2;

        public SortTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }
        @Override
        protected int[] compute() {
            int[] arr = null;
            boolean canCompute = end - begin < THRESHOLD;
            if (canCompute) {
                //创建一个新数组
                    int[] newArray = new int[end - begin + 1];
                    if (initArray[begin] > initArray[end] && newArray.length > 1) {
                        newArray[0] = initArray[end];
                        newArray[1] = initArray[begin];
                    } else if (initArray[begin] < initArray[end] && newArray.length > 1) {
                        newArray[0] = initArray[begin];
                        newArray[1] = initArray[end];
                    } else if (newArray.length == 1) {
                        newArray[0] = initArray[begin];
                }
                return newArray;
            } else {
                int mid = (begin + end) / 2;
                SortTask leftTask = new SortTask(begin, mid);
                SortTask rightTask = new SortTask(mid + 1, end);
                invokeAll(leftTask, rightTask);
                int[] arr1 = leftTask.join();
                int[] arr2 = rightTask.join();
                arr = joinArray(arr1, arr2);
                System.out.println(Arrays.toString(arr));
            }
            return arr;
        }
    }

}
