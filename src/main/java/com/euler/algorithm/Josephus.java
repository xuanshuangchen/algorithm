package com.euler.algorithm;

/**
 * 约瑟夫问题
 * n个人排成一圈，连续报数m，数到m的退出，问最后一个人的编号是谁
 */
public class Josephus  {

    /**
     * 方案一
     * @param n n是人数
     * @param m m是数字
     * @return
     */
    public static int lastIndexFirst(int n, int m) {
        boolean[] itemArray = new boolean[n];
        for(int i = 0; i < n; i ++) {
            itemArray[i] = false;
        }

        int amount = 0;
        int index = 0;
        while (amount < n - 1) {
            int cur = 0;
            while (cur < m) {
                index = index % n;
                if (!itemArray[index]) {
                    cur += 1;
                }
                index += 1;
            }
            amount += 1;
            index -= 1;
            itemArray[index] = true;
        }
        int result = -1;
        for(int i = 0; i < n; i ++) {
            if (!itemArray[i]) {
                result = i + 1;
                break;
            }
        }

        return result;
    }

    /**
     * 方案二：公式法
     * @param n
     * @param m
     * @return
     */
    public static int lastIndexSecond(int n, int m) {
        int s = 0;
        for (int i = 2; i <= n; ++i) {
            s = (s + m) % i;
        }

        return s + 1;
    }

    /**
     * 方案三
     * @param n n是人数
     * @param m m是数字
     * @return
     */
    public static int lastIndexThree(int n, int m) {
        boolean[] itemArray = new boolean[n];
        for(int i = 0; i < n; i ++) {
            itemArray[i] = false;
        }

        int amount = 0;
        int index = 0;

        int cur = 0;
        while (amount < n - 1) {
            if (!itemArray[index]) {
                cur += 1;
            }

            if (cur == m) {
                cur = 0;

                amount += 1;
                itemArray[index] = true;
            }

            index += 1;
            index = index % n;
        }
        int result = -1;
        for(int i = 0; i < n; i ++) {
            if (!itemArray[i]) {
                result = i + 1;
                break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(Josephus.lastIndexFirst(10, 6));
        System.out.println(Josephus.lastIndexSecond(10, 6));
        System.out.println(Josephus.lastIndexThree(10, 6));
    }

}
