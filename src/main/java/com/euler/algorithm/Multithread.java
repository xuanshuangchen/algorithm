package com.euler.algorithm;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Multithread {
    private ReentrantLock lock = new ReentrantLock();
    private Condition ca = lock.newCondition();
    private Condition cb = lock.newCondition();
    private Condition cc = lock.newCondition();

    private int current = 1;

    public Multithread() {
    }

    public void printA() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            if (current != 1) {
                ca.await();
            }
            System.out.print("A");
            current = 2;
            cb.signal();
            lock.unlock();
        }
    }

    public void printB() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            if (current != 2) {
                cb.await();
            }
            System.out.print("B");
            current = 3;
            cc.signal();
            lock.unlock();
        }
    }

    public void printC() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            if (current != 3) {
                cc.await();
            }
            System.out.print("C");
            System.out.println("");
            current = 1;
            ca.signal();
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        //lock.lock();
        final Multithread exam = new Multithread();
        Thread printA = new Thread() {
            @SneakyThrows
            public void run() {
                exam.printA();
            }
        };

        Thread printB = new Thread() {
            @SneakyThrows
            public void run() {
                exam.printB();
            }
        };

        Thread printC = new Thread() {
            @SneakyThrows
            public void run() {
                exam.printC();
            }
        };

        printA.start();
        printB.start();
        printC.start();
    }

}
