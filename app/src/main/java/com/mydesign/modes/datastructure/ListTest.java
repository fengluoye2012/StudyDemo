package com.mydesign.modes.datastructure;

import android.os.Build;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseArray;

import com.mydesign.modes.module.LoginBean;
import com.mydesign.modes.module.MyNode;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ListTest {

    private int a = 5;
    private String TAG = ListTest.class.getSimpleName();

    private static ListTest instance;
    private MyNode last;
    private MyNode head;


    private ListTest() {
    }

    public static ListTest getInstance() {
        if (instance == null) {
            instance = new ListTest();
        }
        return instance;
    }


    public void test() {


        // arrayListTest();
        // vectorTest();
//        stackTest();
//        copyOnWriteArrayList();

//        linkedListTest();
//        mapList();
//        linkedHashMap();

//        lruCacheTest();
//        hashtable();

//        queueTest();
//        setTest();

//        treeTest();
        // copyTest();
        singleLinkedList();

    }

    public void singleLinkedList() {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<>();
        singleLinkedList.insert(new Integer[]{5, 7, 4, 3, 6, 1, 12, 9, 8});

        singleLinkedList.insertFirst(20);
        singleLinkedList.remove(4);
        singleLinkedList.remove(20);
        singleLinkedList.traversal();
        singleLinkedList.reversal();
        Logger.e("反转之后");
        singleLinkedList.traversal();
    }

    // <dimen name="dp30">30dp</dimen>
    private void huansuan() {
        String s = "";
        for (int i = 0; i < 750; i++) {
            s = s + "<dimen name=\"dp" + i + "\">" + (int) Math.rint(i * 0.96) + "dp</dimen>";
        }
        Log.e(TAG, s);
    }

    /**
     * pos 表示第多少位
     * 1 1 2 3 5 8 13 21
     */

    private int recursionTest(int pos) {
        if (pos < 2) {
            return 1;
        } else {
            return recursionTest(pos - 1) + recursionTest(pos - 2);
        }
    }

    private void copyTest() {
        int[] arr = {1, 2, 3};
        int[] arrb = arr;
        int[] arrc = {1, 2};
        arr = arrc;

    }

    /**
     *
     */
    private void treeTest() {
        int sum = recursionTest(10);
        Log.e(TAG, sum + "");

        binarySearchTreeTest();
        avlTreeTest();
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("feng", "1");
        //TreeSet
    }

    private void avlTreeTest() {
        AVLTree avlTree = new AVLTree();
        //avlTree.add(1);
    }

    //先学会遍历，再创建
    private void binarySearchTreeTest() {
        Log.e(TAG, "////////////////");
        //前序
        Integer[] arr = {15, 15, 10, 7, 5, 13, 14, 25, 18, 30};
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        //手动创建树
        //binarySearchTree.createTree();
        //自动创建树
        binarySearchTree.createTree(Arrays.asList(arr));
        //binarySearchTree.insertNode(2);
        //遍历树
        // searchTest(binarySearchTree);
        Log.e(TAG, "/////添加之后遍历///////////");
        binarySearchTree.preOderSearch(binarySearchTree.getRoot());
        Log.e(TAG, "/////非递归排序///////////");
//        binarySearchTree.midNotRecSearch(binarySearchTree.getRoot());

//        Log.e(TAG, "//////删除节点值18//////////");
//        binarySearchTree.remove(18);
//        binarySearchTree.preOderSearch(binarySearchTree.getRoot());

//        Log.e(TAG, "////////删除节点值7////////");
//        binarySearchTree.remove(7);
//        binarySearchTree.preOderSearch(binarySearchTree.getRoot());

//        Log.e(TAG, "///////删除节点值13/////////");
//        binarySearchTree.remove(13);
//        binarySearchTree.preOderSearch(binarySearchTree.getRoot());

        Log.e(TAG, "//////删除节点值15//////////");
        binarySearchTree.remove(15);
        binarySearchTree.preOderSearch(binarySearchTree.getRoot());
    }

    private void searchTest(BinarySearchTree binarySearchTree) {
        binarySearchTree.preOderSearch(binarySearchTree.getRoot());
        Log.e(TAG, "////////////////");
        binarySearchTree.midOrderSearch(binarySearchTree.getRoot());
        Log.e(TAG, "////////////////");
        binarySearchTree.postOrderSearch(binarySearchTree.getRoot());
        Log.e(TAG, "////////////////");
        binarySearchTree.breadthFirstSearch(binarySearchTree.getRoot());
    }

    private void setTest() {
        HashSet<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("3");
        set.remove("");
        set.contains("");

        for (String str : set) {
            Log.e(TAG, str);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            ArraySet<String> arraySet = new ArraySet<>();
            arraySet.add("");
            arraySet.add("");
            arraySet.add("");

            arraySet.remove("");
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("1");
        linkedHashSet.add("2");
        linkedHashSet.add("3");
        linkedHashSet.add("4");
        linkedHashSet.add("5");
        linkedHashSet.add("6");

        for (String str : linkedHashSet) {
            Log.e(TAG, str);
        }
    }

    private void queueTest() {
        last = head = new MyNode(null);
        //arrayQueueTest();
        linkedQueueTest();
    }

    private void linkedQueueTest() {
        MyNode newNode = new MyNode("fengluoye");
        last = last.next = newNode;
        // last =last.next;

        MyNode newNode1 = new MyNode("luoye");
        MyNode newNode2 = new MyNode("ye");

        last = last.next = newNode1;

        last = newNode2;

        int a;
        int b;
        a = b = 5;

        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        //添加
        boolean feng = linkedBlockingQueue.add("feng");
        boolean luo = linkedBlockingQueue.offer("luo");
        try {
            linkedBlockingQueue.put("ye");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //获取
        String element = linkedBlockingQueue.element();
        String peek = linkedBlockingQueue.peek();
        //删除
        String poll = linkedBlockingQueue.poll();
        try {
            String take = linkedBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean remove = linkedBlockingQueue.remove("");

    }

    private void arrayQueueTest() {
        int[] a = new int[10];
        int length = a.length;

        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);
        //插入
        queue.add("feng");
        queue.offer("luo");
        try {
            queue.put("ye");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //查询
        String element = queue.element();
        String peek = queue.peek();

        //删除
        String poll = queue.poll();
        String take = "";
        try {
            take = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int size = queue.size();

        boolean ni = queue.add("ni");
        boolean remove = queue.remove("ye");
        boolean contains = queue.contains("");
    }

    private void hashtable() {

        LoginBean loginBean = new LoginBean();
        loginBean.age = 13;
        loginBean.name = "feng";

        loginBean.say();

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("", "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int i = ~0;
            int a = i, b;
            b = 5;

            int[] aa = new int[10];
            int length = aa.length;

            ArrayMap<String, String> arrayMap = new ArrayMap<>();
            arrayMap.put("", "");
            arrayMap.get("");

            SparseArray<String> sparseArray = new SparseArray<>();
            sparseArray.put(1, "");
        }

    }

    private void lruCacheTest() {
        LruCache<String, String> lruCache = new LruCache<String, String>(10) {
            //默认返回1；如果是Bitmap 得到对应的byte；
            @Override
            protected int sizeOf(String key, String value) {
//                super.sizeOf(key,value);
                return 2;
            }

            //值被替换或者被删除
            @Override
            protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
        lruCache.put("1", "a");
        lruCache.put("2", "b");
        lruCache.put("3", "c");
        lruCache.put("4", "d");
        lruCache.put("5", "e");
        lruCache.put("6", "f");

        String s = lruCache.get("1");
        String remove = lruCache.remove("1");
        //清空
        lruCache.evictAll();

//        TreeMap
//        TreeSet
    }


    private void linkedHashMap() {

        Children children = new Children();
        children.add();


        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        //put方法
        linkedHashMap.put("feng", "123");
        linkedHashMap.get("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            linkedHashMap.replace("", "");
        }
        linkedHashMap.remove("");
        //遍历双链表
        linkedHashMap.containsValue("");
    }

    /**
     * hashCode: 返回该对象的哈希码值;int;主要用于查询的快捷性，hashCode用来找到对应的桶，equals 用来找到相等的对象
     * 1.hashcode是用来查找的，如果你学过数据结构就应该知道，在查找和排序这一章有
     * 例如内存中有这样的位置
     * 0  1  2  3  4  5  6  7
     * 而我有个类，这个类有个字段叫ID,我要把这个类存放在以上8个位置之一，如果不用hashcode而任意存放，
     * 那么当查找时就需要到这八个位置里挨个去找，或者用二分法一类的算法。
     * 但如果用hashcode那就会使效率提高很多。
     * 我们这个类中有个字段叫ID,那么我们就定义我们的hashcode为ID％8，然后把我们的类存放在取得得余数那个位置。
     * 比如我们的ID为9，9除8的余数为1，那么我们就把该类存在1这个位置，如果ID是13，求得的余数是5，
     * 那么我们就把该类放在5这个位置。这样，以后在查找该类时就可以通过ID除 8求余数直接找到存放的位置了。
     * <p>
     * 2.但是如果两个类有相同的hashcode怎么办那（我们假设上面的类的ID不是唯一的），例如9除以8和17除以8的余数都是1，
     * 那么这是不是合法的，回答是：可以这样。那么如何判断呢？在这个时候就需要定义 equals了。
     * 也就是说，我们先通过 hashcode来判断两个类是否存放某个桶里，但这个桶里可能有很多类，
     * 那么我们就需要再通过 equals 来在这个桶里找到我们要的类。
     * 那么。重写了equals()，为什么还要重写hashCode()呢？
     * 想想，你要在一个桶里找东西，你必须先要找到这个桶啊，你不通过重写hashcode()来找到桶，光重写equals()有什么用啊
     * <p>
     * hashCode 找到对应的桶，方便快速查询；
     * equals 在桶中找到对应的值
     */
    private void mapList() {
        String a = "aa";
        String s = "a";
        Log.e(TAG, "hashcode::" + a.hashCode());
        Log.e(TAG, "hashcode::" + s.hashCode());

        Log.e(TAG, "/////////////////");
        Object o = new Object();
        Object o1 = new Object();
        Log.e(TAG, "hashcode::" + o.hashCode());
        Log.e(TAG, "hashcode::" + o1.hashCode());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("feng", "fengluoye");

        String feng = hashMap.get("feng");
        boolean b = hashMap.containsKey("");
        boolean b1 = hashMap.containsValue("");
        //直接清空数组中的元素
        hashMap.clear();

        String remove = hashMap.remove("");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            String replace = hashMap.replace("", "");
        }


        Set<String> strings = hashMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            Log.e(TAG, next);
        }

        Log.e(TAG, "//////");
        for (String str : strings) {
            Log.e(TAG, str);
        }

        Collection<String> values = hashMap.values();
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.e(TAG, key + "::" + value);
        }

    }

    /**
     * LinkedList 初始化时 first last 都为null；
     * 主要包括：add,set,get,remove,iterator,Deque接口中的方法；
     * <p>
     * <p>
     * add(int index, E element) 添加操作,将当前元素插入到index 位置，index 从0 到 size；
     * 1）先判断是否加在链尾；如果是，curNode = last;创建newNode,per指向curNode,next指向null; last 指向newNode;
     * 判断curNode是否为空；为空的话，此时是空链表，first为null,将first 指向newNode；
     * 不为空，将curNode 的 next 指向newNode;
     * <p>
     * 2) 非链尾；找到当前index 对应的节点curNode，获取上一个节点preNode,创建新节点newNode,pre指向preNode，next暂时指向curNode;
     * curNode 的pre 指向newNode;
     * 判断preNode 是否为空，为空的话，说明index为0，将first指向newNode;不为空，将preNode 的next指向newNode，
     */
    private void linkedListTest() {
        LinkedList<String> linkedList = new LinkedList<>();
        String s1 = linkedList.get(0);
        linkedList.add("feng");
        linkedList.add(0, "luo");

        ListIterator<String> iterator = linkedList.listIterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            Log.e(TAG, next);
        }

        Log.e(TAG, "///////////////");
        int size = linkedList.size();
        for (int i = 0; i < size; i++) {
            String s = linkedList.get(i);
            Log.e(TAG, s);
        }


    }

    private void copyOnWriteArrayList() {

        int b = a;
        a = 10;
        int i1 = a + b;
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("hh");
        list.add("dd");
        list.add("ee");
        list.add("ff");
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            Log.e(TAG, s);
            if (s.equals("hh")) {
                list.remove(s);
            }
            if (s.equals("ee")) {
                list.add("gg");
            }
        }
    }

    //Activity 栈
    private void stackTest() {
        Stack<String> stack = new Stack<>();
        stack.push("");
        stack.push("");
        stack.push("");
        stack.push("");

        String pop = stack.pop();


    }

    private void vectorTest() {
        Vector<String> vector = new Vector<>();
        vector.add("feng");
        vector.add("fengluoye");
        vector.add("fengluo");

        vector.set(0, "abc");

        String[] strings = vector.toArray(new String[vector.size()]);

        Iterator<String> iterator = vector.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            Log.e(TAG, next);
            if (next.equals("abc")) {
                iterator.remove();
            }
        }
    }


    private void arrayListTest() {
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        stringList.add("e");
        stringList.add("f");
        stringList.add("g");

        List<String> list = new ArrayList<>(stringList);
        Log.e(TAG, "" + list.size());

        String[] s = new String[stringList.size()];
        //List转换为数组
        String[] strings = stringList.toArray(s);

        int w = strings.length;
        for (int i = 0; i < strings.length; i++) {
            strings[--w] = strings[i];
        }


        Log.e(TAG, "/////");
        Iterator<String> iterator = stringList.iterator();
        /**
         * hasNext:判断游标右侧是否还有元素；
         * next():返回游标右边的数据，并将游标移动到下一个位置；
         * remove():删除游标右侧的数据；在执行完next()方法之后，只能执行一次，否则会报错；
         */
        for (; iterator.hasNext(); ) {
            String next = iterator.next();
            Log.e(TAG, next);
            if (next.equals("c")) {
                iterator.remove();
            }
        }

        Iterator<String> stringIterator = stringList.iterator();
        Log.e(TAG, "/////");
        while (stringIterator.hasNext()) {
            String next = stringIterator.next();
            Log.e(TAG, next);
            if (next.equals("c")) {
                stringIterator.remove();
            }
        }
    }
}
