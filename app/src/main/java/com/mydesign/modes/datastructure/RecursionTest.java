package com.mydesign.modes.datastructure;

import android.util.Log;

/**
 * 递归实例:汉诺塔，欧几里得，阶乘；穷举（泊松分酒）
 */

public class RecursionTest {

    private String TAG = RecursionTest.class.getSimpleName();
    private static RecursionTest instance;

    private int i;

    private RecursionTest() {
    }

    public static RecursionTest getInstance() {
        if (instance == null) {
            synchronized (RecursionTest.class) {
                if (instance == null) {
                    instance = new RecursionTest();
                }
            }
        }
        return instance;
    }

    /**
     * 汉诺塔（又称河内塔）问题是源于印度一个古老传说的益智玩具。大梵天创造世界的时候做了三根金刚石柱子，
     * 在一根柱子上从下往上按照大小顺序摞着64片黄金圆盘。大梵天命令婆罗门把圆盘从下面开始按大小顺序重新摆放在另一根柱子上。
     * 并且规定，在小圆盘上不能放大圆盘，在三根柱子之间一次只能移动一个圆盘。
     * <p>
     * 后来，这个传说就演变为汉诺塔游戏，玩法如下:
     * <p>
     * 1.有三根杆子A,B,C。A杆上有若干碟子
     * 2.每次移动一块碟子,小的只能叠在大的上面
     * 3.把所有碟子从A杆全部移到C杆上
     */
    public void hnt() {
        int total = 64;
        char a = 'A';
        char b = 'B';
        char c = 'C';

        i = 1;
        hnt(total, a, b, c);
    }

    private void hnt(int total, char from, char dependOn, char to) {
        move(total, from, to);
        move(total - 1, from, dependOn);

        //hnt(total - 1, );
    }

    private void move(int total, char from, char to) {
        Log.e(TAG, "第" + i + "次 从" + from + "----" + to);
    }
}
