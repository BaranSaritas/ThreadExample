package com.threadExample.threadExample.WaitAndNotify;
/*
 synchronized anahtar kelimesi, özel erişim için kullanılır. Bir
  yöntemi synchronized yapmak için, sadece bildiriminin başına  synchronized
 anahtar kelimesini eklemeniz yeterlidir.
 Ardından, aynı nesne üzerindeki senkronize yöntemlerin iki çağrısı
 birbirine geçmez.
 * Senkronize ifadeler, içsel kilidi sağlayan nesneyi belirtmelidir.
  synchronized(this) kullanıldığında, başka nesnelerin yöntemlerinin senkronize edilmesinden kaçınmalısınız.
  Object#wait(), çağıran iş parçacığına kilidi bırakmasını ve uykuya gitmesini (anlık sorgulama değil) söyler
  başka bir iş parçacığı aynı kilide girer ve  Object#notify() çağrısı yapar.

 */

import java.util.Scanner;

public class Processor  {

    /*
     * public synchronized void getSomething(){ this.hello = "hello World"; }
     * public void getSomething(){ synchronized(this){ this.hello = "hello
     * World"; } }
     * 2 kodda işlev olarak aynı yapıda
     */

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running ....");
            wait();// bu metod uyutulmasini sagliyor  tekrardan calismak icin notify metodunun calistirilmasini bekliyor
            System.out.println("Resumed.");
        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("Waiting for return key.");
            scanner.nextLine();
            System.out.println("Return key pressed.");
            notify();  // uyuyan metrodlarin uyanmasini ve devam etmesini sagliyor
            Thread.sleep(5000);
            System.out.println("Consumption done.");
        }
    }
}
