package com.threadExample.threadExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Worker implements Runnable {

    private Random random = new Random();
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    public List<Integer> list1 = new ArrayList<>();
    public List<Integer> list2 = new ArrayList<>();

    @Override
    public void run() {
        process();
    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void stageOne() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                //do your work here
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    public void stageTwo() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                //do your work here
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }
}

public class WorkerThreadPool {

    public static void main(String[] args) {
        int numberOfThreads = 1;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads); // SAYI ARTIMASI HERZAMAN HIZ DEMEK DEGIL HATTA COGU ZAMAN YAVASLATIR
        System.out.println("Başlıyor ...");
        long start = System.currentTimeMillis();
        Worker worker = new Worker();
        for (int i = 0; i < numberOfThreads; i++) { // worker.run iki (thread'ler başlatıldı) kez iki thread tarafından çağrılır
            executor.submit(worker);
        }
        executor.shutdown(); // ExecutorService'in yeni görevler kabul etmesini engeller
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            // Thread'lerin görevleri tamamlamasını beklemek için ne kadar beklemeliyim?
            // Kaynak: http://stackoverflow.com/questions/1250643/how-to-wait-for-all-threads-to-finish-using-executorservice
            // Sürekli çalışan bir toplu iş türü için işleri gönderip tamamlanmalarını beklemeniz gerekir.
            // Böyle bir durumda, shutdown'dan ziyade bir latch veya bariyer daha mantıklıdır
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        long end = System.currentTimeMillis();
        System.out.println("Geçen süre: " + (end - start));
        System.out.println("List1: " + worker.list1.size() + "; List2: " + worker.list2.size());
    }

}
