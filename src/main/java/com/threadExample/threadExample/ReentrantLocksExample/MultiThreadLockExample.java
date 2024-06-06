package com.threadExample.threadExample.ReentrantLocksExample;

public class MultiThreadLockExample {
    public static void main(String[] args) throws Exception {
        final Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.threadAction("Thread 1");
                } catch (InterruptedException ignored) {
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.threadAction("Thread 2");
                } catch (InterruptedException ignored) {
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.threadAction("Thread 3");
                } catch (InterruptedException ignored) {
                }
            }
        });

        Thread t4 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.threadAction("Thread 4");
                } catch (InterruptedException ignored) {
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        runner.finished();
    }
}
