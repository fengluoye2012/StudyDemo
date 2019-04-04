package com.mydesign.modes.datastructure;

/**
 * 算法
 */

public class ArithmeticTest {

    private static ArithmeticTest instance;
    private String TAG = ArithmeticTest.class.getSimpleName();

    private ArithmeticTest() {
    }

    //双重检查
    public static ArithmeticTest getInstance() {
        if (instance == null) {
            synchronized (ArithmeticTest.class) {
                if (instance == null) {
                    instance = new ArithmeticTest();
                }
            }
        }
        return instance;
    }

    public void test() {
        int[] arr = {20, 18, 9, 16, 30, 5, 7};
        SortTest.getInstance().bubbleSort(arr);
        SortTest.getInstance().binarySearch(arr,16);
        int[] arr1 = {20, 18, 9, 16, 30, 5, 7};
        //SortTest.getInstance().selectSort(arr1);
        int[] arr2 = {20, 18, 9, 16, 30, 5, 7};
        // SortTest.getInstance().simpleInsertSort(arr2);
        SortTest.getInstance().heapSort(new int[]{20, 18, 9, 16, 30, 5, 7});
        //SortTest.getInstance().quickSort(new int[]{20, 18, 9, 16, 30, 5, 7});
       // SortTest.getInstance().mergeSort(new int[]{20, 18, 9, 16, 30, 5, 7});
//        SortTest.getInstance().quickSortApi(new int[]{20, 18, 9, 16, 30, 5, 7});
    }
}
