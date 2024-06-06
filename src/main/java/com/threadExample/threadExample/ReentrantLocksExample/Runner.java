package com.threadExample.threadExample.ReentrantLocksExample;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Lock: Java'nın Lock API'si için temel arabirimdir. Senkronize anahtar kelimesinin
 tüm işlevlerini sağlar ve farklı kilitlenme koşulları oluşturabilir. Önemli metotlar
  arasında kilit almak için lock(), kilidi serbest bırakmak için unlock(), belirli bir
   süre için kilidi beklemek için tryLock(), koşul oluşturmak için newCondition() bulunur.

ReentrantLock: Kilit arabirimine dayalı olarak en yaygın kullanılan uygulama sınıfıdır.
 Senkronize anahtar kelimesiyle aynı şekilde Lock arabirimini uygular.

Condition: Farklı bekleme setleri oluşturmayı sağlayan koşul nesneleridir.
 Her zaman bir kilit nesnesi tarafından oluşturulurlar. Önemli metotlar arasında
  beklemek için await(), sinyal göndermek için signal() ve signalAll() bulunur.
   Bunlar, Object#notify() ve Object#notifyAll() metodlarına benzerdir.
 */
public class Runner {

    private int count = 0; // Bir sayaç değişkeni
    private Lock lock = new ReentrantLock(); // ReentrantLock ile bir kilit nesnesi oluşturuluyor
    private Condition cond = lock.newCondition(); // Bu kilit üzerinde bir koşul nesnesi oluşturuluyor
    private long totalLockTime = 0;

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock(); // Kilidi al
        System.out.println("Bekleniyor ....");
        cond.await(); // Koşulu beklet
        System.out.println("Uyanıldı!");
        try {
            increment(); // Artırma işlemini gerçekleştir
        } finally {
            lock.unlock(); // Kilidi serbest bırak
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000); // 1 saniye uyut
        lock.lock(); // Kilidi al
        System.out.println("Return tuşuna basınız!");
        new Scanner(System.in).nextLine(); // Kullanıcıdan bir giriş bekleniyor
        System.out.println("Return tuşu alındı!");
        cond.signal(); // Koşulu sinyalle ve wait olan islemleri uyandir yani firstThread suan uyku durumunda
        try {
            increment(); // Artırma işlemini gerçekleştir
        } finally {
            // sinyal() çağrıldığında Thread'in kilidini serbest bırakması gerekmektedir
            lock.unlock(); // Kilidi serbest bırak
        }
    }

    //threadleri daha iyi anlamak icin bir örnek
    public void threadAction(String threadName) throws InterruptedException {
        long startTime = System.nanoTime();
        lock.lock();
        totalLockTime += System.nanoTime() - startTime;

        System.out.println(threadName + " kilidi aldı. Kilit durumu: " + lock.toString());
        System.out.println("Bekleyen iş parçacıkları: " + Thread.currentThread().getName());

        try {
            increment();
            Thread.sleep(1000); // 1 saniye beklet
        } finally {
            long lockHoldStartTime = System.nanoTime();
            lock.unlock();
            totalLockTime += System.nanoTime() - lockHoldStartTime;
            System.out.println(threadName + " kilidi bıraktı. Kilit durumu: " + lock.toString());
        }
    }

    public void checkLockStatus() throws InterruptedException {
        while (true) {
            if (lock.tryLock()) {
                try {
                    System.out.println("Kilidi tutuyor mu? Evet");
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Kilidi tutuyor mu? Hayır");
            }
            Thread.sleep(500); // Durumu her yarım saniyede bir kontrol et
        }
    }

    public void finished() {
        System.out.println("Sayaç: " + count);
    }
}