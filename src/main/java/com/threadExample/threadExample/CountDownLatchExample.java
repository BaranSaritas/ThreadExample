package com.threadExample.threadExample;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
CountDownLatch nedir?: Java'da CountDownLatch sınıfı,
 thread'lerin aktivitelerini senkronize etmek için kullanılır. Bir thread, CountDownLatch.await()
 metodunu çağırarak diğer thread'lerin tamamlanmasını bekler. Diğer thread'ler CountDownLatch.countDown()
  metodunu çağırarak geri sayımı azaltır. Geri sayım sıfıra ulaştığında, bekleyen thread'ler devam eder.

Uygulamanın ana thread'i: Ana thread, diğer thread'lerin tamamlanmasını bekler.
 Örneğin, birden fazla hizmet sağlayan bir sunucu uygulamasında, ana thread, tüm
 hizmetler başlatılana kadar beklemek zorunda olabilir.

Eksiklikler: CountDownLatch, geri sayım sıfıra ulaştığında tekrar kullanılamaz.
Bu nedenle, her seferinde yeni bir CountDownLatch nesnesi oluşturulmalıdır.
 */
public class CountDownLatchExample {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executor.execute(new Processor(latch));
        }
        executor.shutdown();


       try {

            latch.await();
            /*
            latch.await varken
            Started.
            Started.
            Started.
            Completed.
             */

            /*
            latch.await olmasaydi
            Started.
            Completed.
            Started.
            Started.
            ana main thread diğer thread'lerin tamamlanmasını beklemeden
            hemen ilerler ve "Completed." mesajını hemen yazdırırdı.
             */

            // latch.await(); Main thread, latch'in sıfıra ulaşmasını bekler. Yani,
            // 3 thread tamamlandığında main thread devam eder.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        System.out.println("Completed.");

    }

}


class Processor implements Runnable {

    //CountDownLatch nesnesi, thread'lerin tamamlanmasını beklemek için kullanılır.
    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        System.out.println("Started.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}
        latch.countDown();
    }
}