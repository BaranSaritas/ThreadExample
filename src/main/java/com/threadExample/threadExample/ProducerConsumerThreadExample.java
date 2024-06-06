package com.threadExample.threadExample;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("InfiniteLoopStatement")   // normalde infinite loopda hata verir ama bunu bilerek yapmak istiyorsak bu sekilde uyariyoruz
public class ProducerConsumerThreadExample {

    // BlockingQueue (engelleyici kuyruk) veri yapısı, bu kuyruk thread-safe'dir
    // 10 verilik kapasitesi var ve doldugunda threadleri bekleticek
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {

        // Üretici thread'i oluştur ve başlat
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    producer();
                } catch (InterruptedException ignored) {}
            }
        });

        // Tüketici thread'i oluştur ve başlat
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException ignored) {}
            }
        });

        t1.start();
        t2.start();

        // 30 saniye duraklayın ve uygulamayı zorla kapatın (çünkü sonsuz döngüdeyiz)
        Thread.sleep(30000);
        System.exit(0);
    }

    // Üretici metodu
    private static void producer() throws InterruptedException {
        Random random = new Random();
        while (true) { // sonsuz döngü
            queue.put(random.nextInt(100));

        }
    }

    // Tüketici metodu
    private static void consumer() throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(100);

            if (random.nextInt(10) == 0) { // yavas akmasi icin ufak bir logic
                System.out.println(Thread.currentThread().getName());
                Integer value = queue.take(); //kuyruktan eleman alir eğer kuyruk boşsa bekler
                System.out.println("Alınan değer: " + value + "; Kuyruk boyutu: " + queue.size());
            }
        }
    }
}