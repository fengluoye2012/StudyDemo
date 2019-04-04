package com.mydesign.modes.datastructure;

import android.util.Log;

import java.util.Arrays;

/**
 * 十种常见排序算法：https://www.cnblogs.com/shixiangwan/p/6724292.html
 * <p>
 * 1：非线性时间比较类排序算法：
 * 交换类排序（快速排序和冒泡排序）
 * 插入类排序（简单插入排序和希尔排序）
 * 选择类排序（简单选择排序和堆排序）
 * 归并排序（二路归并排序和多路归并排序）
 * 2：线性时间非比较类排序:计数排序，基数排序和桶排序；
 * <p>
 * 总结：在比较类排序中，归并排序号称最快，其次是快速排序和堆排序；
 * <p>
 * 稳定性：时间复杂度和原数组的顺序没有关系；
 */

public class SortTest {

    private String TAG = SortTest.class.getSimpleName();
    private static SortTest instance;

    private SortTest() {
    }

    public static SortTest getInstance() {
        if (instance == null) {
            synchronized (SortTest.class) {
                if (instance == null) {
                    instance = new SortTest();
                }
            }
        }
        return instance;
    }

    /**
     * 冒泡排序：相邻的两个元素相互比较，
     * 时间复杂度为O(n2)。实际：n(n-1)/2；移动次数多；
     * <p>
     * 冒泡排序算法的原理如下：
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素做同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     */
    public void bubbleSort(int[] arr) {
        if (preJudge(arr)) {
            Log.e(TAG, "数组长度不满足");
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            //(arr.length - i)去掉已经排好序的部分；
            for (int j = 0; j < arr.length - i - 1; j++) {
                //交换
                if (arr[j] > arr[j + 1]) {
                    int tem = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tem;
                }
            }
        }
        systemOut(arr);
    }

    private boolean preJudge(int[] arr) {
        return arr == null || arr.length < 2;
    }

    public void systemOut(int[] arr) {
        String str = "";
        for (int i = 0; i < arr.length; i++) {
            str = str + arr[i] + "  ,";
        }
        Log.e(TAG, str);
    }

    /**
     * 简单选择排序:每一趟从待排序的记录中选出最小的元素，顺序放在已排好序的序列最后，直到全部记录排序完毕;
     * 时间复杂度O(n*n)；实际n(n-1)/2;但是移动次数比冒泡排序少很多；性能相对较好；
     * <p>
     * 基本思想：
     * 给定数组：int[] arr={里面n个数据}；
     * 第1趟排序，在待排序数据arr[1]—arr[n]中选出最小的数据，将它与arr[1]交换；
     * 第2趟，在待排序数据arr[2]—arr[n]中选出最小的数据，将它与r[2]交换；
     * 以此类推，第i趟在待排序数据arr[i]—arr[n]中选出最小的数据，将它与r[i]交换，直到全部排序完成。
     */
    public void selectSort(int[] arr) {
        if (preJudge(arr)) {
            return;
        }
        int minIndex;
        for (int i = 0; i < arr.length; i++) {
            minIndex = i;
            //(i+1)排除已经排序的部分；找到最小的
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }

            int tem = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tem;
        }
        systemOut(arr);
    }

    /**
     * 插入排序就是把当前待排序的元素插入到一个已经排好序的列表里面;
     * 时间复杂度：O(n*n)，但是移动的次数要比 冒泡排序和简单选择排序 少，相对来说效率高些；
     * <p>
     * 原理：把n个待排序的元素看成一个有序表和无序表。一开始有序表只包含一个元素，无序表中包含n-1个元素。
     * 排序过程中每次从无序表中取出第一个元素，把它依次与有序表中的元素进行比较。然后把它插入到有序表的适当位置，形成新的有序表。
     * <p>
     * 一个非常形象的例子就是右手抓取一张扑克牌，把它插入左手拿着的排好序的扑克里面;
     * <p>
     */
    public void simpleInsertSort(int[] arr) {
        if (preJudge(arr)) {
            return;
        }

        int j;
        for (int i = 1; i < arr.length; i++) {
            int insertNode = arr[i];

            j = i - 1;
            //从右向左遍历，将最大的元素放在右边；
            while (j >= 0 && insertNode < arr[j]) {
                arr[j + 1] = arr[j];//移动位置；
                j--;
            }
            //给对应的下标，重新赋值；
            arr[j + 1] = insertNode;
        }
        systemOut(arr);
    }

    /**
     * 堆排序(选择排序的一种)：二叉堆的原理；二叉堆一般用数组来表示。
     * 二叉堆是一种特殊的堆，二叉堆是完全二元树（二叉树）或者是近似完全二元树（二叉树）。
     * <p>
     * 时间复杂度：初始化建堆的时间复杂度为O(n)，排序重建堆的时间复杂度为nlog2(n)，所以总的时间复杂度为O(n+nlogn)=O(nlogn)。
     * 另外堆排序的比较次数和序列的初始状态有关，但只是在序列初始状态为堆的情况下比较次数显著减少，在序列有序或逆序的情况下比较次数不会发生明显变化。
     * <p>
     * 二叉堆有两种：最大堆和最小堆。
     * 最大堆：父结点的键值总是大于或等于任何一个子节点的键值；
     * 最小堆：父结点的键值总是小于或等于任何一个子节点的键值。
     * <p>
     * 步骤：
     * 1：构建最大堆。
     * 2：选择顶，并与第0位置元素交换
     * 3：由于步骤2的的交换可能破环了最大堆的性质，第0不再是最大元素，需要调用maxHeap调整堆(沉降法)，如果需要重复步骤2
     * <p>
     * 原数组：20 18 9 16 30 5 7
     * 构建大堆之后：30 20 9 16 18 5 7
     *
     * @param arr
     */
    void heapSort(int[] arr) {
        if (preJudge(arr)) {
            return;
        }
        buildMaxHeap(arr);

        //为什么不将已经构建好的最大堆，遍历；输出呢？采用二叉树的前序（中 左 右），中序（左 中 右），
        // 后序（左 右 中）都无法正确输出；
        //为什么不直接构成AVL数来排序呢？

        //排序；将最大的元素往后交换；从剩下的i个元素 继续将最大的元素往上冒；
        for (int i = arr.length - 1; i >= 1; i--) {
            exchange(arr, 0, i);
            //交换后， 新的二叉树 不一定满足最大堆，继续将相对最大元素往上冒；
            maxHeap(arr, 0, i);
        }
        systemOut(arr);
    }

    /**
     * 构建二叉堆--最大堆：父结点的键值总是大于或等于任何一个子节点的键值；
     * 左子节点的index= 父节点的index*2+1;(因为根节点的index从0开始)
     * 右子节点的index= 父节点的index*2+2;
     *
     * @param arr
     */
    private void buildMaxHeap(int[] arr) {
        int half = arr.length / 2;

        for (int i = half; i >= 0; i--) {
            maxHeap(arr, i, arr.length);
        }
    }

    /**
     * 满足最大堆条件，将三者之间最大的元素 往上冒；
     *
     * @param arr
     * @param curNode
     * @param size    总元素数
     */
    private void maxHeap(int[] arr, int curNode, int size) {
        if (curNode > size) {
            return;
        }
        int left = curNode * 2 + 1;
        int right = curNode * 2 + 2;

        //默认curNode下标对应最大值；比较，得出最大值下标
        int largest = curNode;
        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }

        //交换元素
        if (largest != curNode) {
            exchange(arr, curNode, largest);
            //交换后，子树不一定满足最大堆，继续将相对最大元素往上冒；
            maxHeap(arr, largest, size);
        }
    }

    //交换位置
    private void exchange(int[] arr, int pos, int position) {
        int tem = arr[pos];
        arr[pos] = arr[position];
        arr[position] = tem;
    }

    /**
     * 快速排序又称为分区交换排序：是对冒泡排序的改进，快速排序采用的思想是分治思想；
     * 时间复杂度： O（nlog2n）至O(n2)，平均时间复杂度为O（nlog2n）。
     * 原理：
     * (1)从待排序的n个记录中任意选取一个记录（通常选取第一个记录）为分区标准;
     * (2)把所有小于该排序列的记录移动到左边，把所有大于该排序码的记录移动到右边，中间放所选记录，称之为第一趟排序；
     * (3)然后对前后两个子序列分别重复上述过程，直到所有记录都排好序。
     * <p>
     * 首先在数组中选择一个基准点（该基准点的选取可能影响快速排序的效率，后面讲解选取的方法），
     * 然后分别从数组的两端扫描数组，设两个指示标志（lo指向起始位置，hi指向末尾)，首先从后半部分开始，
     * 如果发现有元素比该基准点的值小，就交换lo和hi位置的值，然后从前半部分开始扫秒，发现有元素大于基准点的值，
     * 就交换lo和hi位置的值，如此往复循环，直到lo>=hi,然后把基准点的值放到hi这个位置。一次排序就完成了。
     * <p>
     * 以后采用递归的方式分别对前半部分和后半部分排序，当前半部分和后半部分均有序时该数组就自然有序了。
     * <p>
     * 以selected = 20 为例
     * 原数组：20 18 9 16 30 5 7
     * 第一趟排序：7 18 9 16 5 20 30
     *
     * @param arr
     */
    public void quickSort(int[] arr) {
        if (preJudge(arr)) {
            return;
        }

        int low = 0;
        int height = arr.length - 1;

        quickSort(arr, low, height);
        systemOut(arr);
    }

    //对于基准位置的选取一般有三种方法：固定切分，随机切分和三取样切分。固定切分的效率并不是太好，
    // 随机切分是常用的一种切分，效率比较高，最坏情况下时间复杂度有可能为O(N2)。
    // 对于三数取中选择基准点是最理想的一种。
    private void quickSort(int[] arr, int low, int height) {
        int start = low;
        int end = height;
        int selected = arr[low];//固定切分，以第一个或者最后一个元素；

        //三数取中选择基准数
        //int selected = selectMid(arr, low, height);

        //基准数 左边的都比基准数小，右边的都比基准数大；
        while (start < end) {
            //从后往前,将小于基准数的元素，放在左边；
            while (start < end && arr[end] >= selected) {
                end--;
            }
            //当前下标的元素 小于等于 基准数；交换
            if (arr[end] <= selected) {
                exchange(arr, start, end);
            }

            //从前往后，将大于基准数的元素，放在右边；
            while (start < end && arr[start] < selected) {
                start++;
            }

            //当前下标的元素 小于等于 基准数；交换
            if (arr[start] > selected) {
                exchange(arr, start, end);
            }
        }

        //重复
        if (low < start) {
            quickSort(arr, low, start - 1);
        }

        if (end < height) {
            quickSort(arr, end + 1, height);
        }
    }

    //三数取中基准数选择法
    private int selectMid(int[] arr, int low, int height) {
        int mid = low + (height - low) / 2;
        if (arr[mid] > arr[height]) {
            exchange(arr, arr[mid], arr[height]);
        }
        if (arr[low] > arr[height]) {
            exchange(arr, arr[low], arr[height]);
        }
        if (arr[mid] > arr[low]) {
            exchange(arr, arr[mid], arr[low]);
        }
        return mid;
    }

    //API
    public void quickSortApi(int[] arr) {
        if (preJudge(arr)) {
            return;
        }
        Arrays.sort(arr);
        systemOut(arr);
    }

    /**
     * 二路--归并排序：采用分治法；
     * 首先用分割的方法，将这个序列分割成一个个已经排好序的子序列（长度为1的子序列）
     * 再用归并的方法将子序列合并成排好序的序列；
     * <p>
     * 我们首先把一个未排序的序列从中间分割成2部分，再把2部分分成4部分，依次分割下去，直到分割成一个一个的数据，
     * 再把这些数据两两归并到一起，使之有序，不停的归并，最后成为一个排好序的序列。
     * <p>
     * 时间复杂度：O(N*log2N)；稳定
     */
    public void mergeSort(int[] arr) {
        if (preJudge(arr)) {
            return;
        }
        int[] tem = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, tem);
        systemOut(arr);
    }

    //分割
    private void mergeSort(int[] arr, int left, int right, int[] tem) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, tem);//分割左边
            mergeSort(arr, mid + 1, right, tem);//分割右边

            mergeArray(arr, left, mid, right, tem);
        }
    }

    //将两个数组合并
    private void mergeArray(int[] arr, int left, int mid, int right, int[] tem) {
        //数组的起始
        int i = left;
        int m = mid + 1;
        //数组的末端
        int j = mid;
        int n = right;

        int k = 0;
        //排序
        while (i <= j && m <= n) {
            if (arr[i] < arr[m]) {
                tem[k] = arr[i];
                k++;
                i++;
            } else {
                tem[k] = arr[m];
                k++;
                m++;
            }
        }
        //将剩余的元素 复制到数组tem中来；
        while (i <= j) {
            tem[k] = arr[i];
            k++;
            i++;
        }

        while (m <= n) {
            tem[k] = arr[m];
            k++;
            m++;
        }

        //重新赋值；
        for (int p = 0; p < k; p++) {
            arr[left + p] = tem[p];
        }
    }

    public void binarySearch(int[] arr, int target) {
        int low = 0;
        int height = arr.length - 1;
        int mid;
        while (low < height) {
            mid = low + (height - low) / 2;
            if (target < arr[mid]) {
                height = mid - 1;
            } else if (target > arr[mid]) {
                low = mid + 1;
            } else {
                Log.e(TAG, "找到了下标为：：" + mid);
                break;
            }
        }

    }
}
