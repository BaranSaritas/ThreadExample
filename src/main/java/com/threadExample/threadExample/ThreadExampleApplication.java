package com.threadExample.threadExample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ThreadExampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ThreadExampleApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2); // thread sayisi
        for (int i = 0; i < 2; i++) {
            executor.submit(new Producer(i));
        }
        executor.shutdown(); // atama yapmayi bitiriyor pooldaki threadlere
        System.out.println("Tüm görevler gönderildi.");
        try {
            executor.awaitTermination(1, TimeUnit.DAYS); //Tüm görevlerin tamamlanmasını 1 gün boyunca bekle demek.
        } catch (InterruptedException ignored) {
        }
        System.out.println("Tüm görevler tamamlandı.");


    }
}

class Producer implements Runnable {
    private int id;


    public Producer(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting: " + id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("Completed: " + id);
    }
}
/*

    int count = 0;

    // synchronized sadece 1 thread kullanabilmesini saglar
    public synchronized void activeThread(String threadName) {
        count++;
        //Thread.sleep(1000);
        System.out.println("Thread in Progress: " + threadName + " and count is: " + count);
    }

    @Override
    public void run(String... args) throws Exception {
		Deneme deneme = new Deneme();
		deneme.start(); // thread baslattim
		Thread.sleep(2000);
		deneme.shutdown();
	 }

Thread thread1 = new Thread(new Runnable() {
    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            activeThread(Thread.currentThread().getName());
        }
    }
});


Thread thread2 = new Thread(new Runnable() {
    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            activeThread(Thread.currentThread().getName());
        }
    }
});

// start isleminde hangisi once gelirse o önce geliyor join sadece bekle infosu veriyor
        thread2.start();
		thread1.start();

//join() bitmesini bekle demek
		thread2.join();
        thread1.join();

    }


 */