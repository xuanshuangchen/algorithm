package com.euler.algorithm;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultithreadN {
    private ReentrantLock lock = new ReentrantLock();
    private Condition[] conditions;

    private static final char[] DIGITS_BASE = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '[', ']'
    };

    private int current = 0;
    private int amount = 6;

    private int printAmount = 20;

    public MultithreadN(int amount, int printAmount) {
        this.amount = amount;
        this.printAmount = printAmount;
        conditions = new Condition[amount];
        for (int i = 0; i < amount; i++) {
            conditions[i] = lock.newCondition();
        }
    }

    public void print(int index) throws InterruptedException {
        for (int i = 0; i < printAmount; i++) {
            lock.lock();
            if (current != index) {
                conditions[index].await();
            }
            System.out.print(DIGITS_BASE[index]);
            current = (current + 1) % amount;
            if (current == 0) {
                System.out.println();
            }

            conditions[current].signal();
            lock.unlock();
        }
    }

    public void run() {
        for (int i = 0; i < this.amount; i++) {
            int finalI = i;
            Thread print = new Thread() {
                @SneakyThrows
                public void run() {
                    print(finalI);
                }
            };
            print.start();
        }
    }

    public static void main(String[] args) {

        final MultithreadN exam = new MultithreadN(16, 30);

        exam.run();
    }

}
