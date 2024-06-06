package com.threadExample.threadExample.ReentrantLocksExample;

/*
Java'da senkronize edilmiş kod bloklarına alternatif olarak kullanılan
ReentrantLock sınıfı, senkronize, Object#wait() ve Object#notify() ile
 yapabileceğiniz tüm işleri ve daha fazlasını yapmanıza olanak tanır.
  ReentrantLock sınıfının ek özellikleri ve avantajları şunlardır:

* Her monitör için birden fazla Condition değişkenine sahip olma yeteneği.
* Birden fazla wait()/notify() kuyruğunu destekler.
* Kilidi daha "adil" hale getirme yeteneği (adil kilitler, en uzun süredir bekleyen ipliğe öncelik verir).
* Kilidin tutulup tutulmadığını kontrol edebilme.
* Kilit üzerinde bekleyen ipliklerin listesini alabilme.


-> ReentrantLock sınıfının dezavantajları ise şunlardır:

* İçe aktarma ifadesi ekleme gerekliliği.
* Kilit edinimlerini bir try/finally bloğuna sarmalama gerekliliği, bu da kodu daha karmaşık hale getirir.
* Senkronize anahtar kelimesi metot tanımlarında kullanılabilir, bu da bir blok gereksinimini ortadan
kaldırarak iç içe geçmeyi azaltır.
 */
public class App {

    public static void main(String[] args) throws Exception {
        final Runner runner = new Runner();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException ignored) {
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException ignored) {
                }
            }
        });
        Thread t1Check = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.checkLockStatus();
                } catch (InterruptedException ignored) {
                }
            }
        });

        t1.start();
        t2.start();
        t1Check.start();

        t1.join();
        t2.join();
        runner.finished();
    }

}