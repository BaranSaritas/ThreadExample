package com.threadExample.threadExample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ThreadExampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ThreadExampleApplication.class, args);
    }

    int count = 0;

    // synchronized sadece 1 thread kullanabilmesini saglar
    public synchronized void activeThread(String threadName) {
        count++;
        //Thread.sleep(1000);
        System.out.println("Thread in Progress: " + threadName + " and count is: " + count);
    }

    @Override
    public void run(String... args) throws Exception {
	/*	Deneme deneme = new Deneme();
		deneme.start(); // thread baslattim
		Thread.sleep(2000);
		deneme.shutdown();
	 }
	 */
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


}

