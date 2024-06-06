package com.threadExample.threadExample.ProducerConsumerExampleV2;

import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("InfiniteLoopStatement")
public class Processor {

    private LinkedList<Integer> list = new LinkedList<>();
    private final int LIMIT = 10;
    private final Object lock = new Object();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                //iş parçacığı bilgilendirildiğinde döngüden tekrar başlar
                while (list.size() == LIMIT) {
                    lock.wait();// wait() de kullanılabilir
                }
                list.add(value);

                System.out.println("Producer added: " + value + " queue size is " + list.size());
                value++;
                lock.notify(); // Bekleyen tüketici thread uyandırır.
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait(); // uyuttuk threadi
                }

                while (list.size()> 0 ){
                    int value = list.removeFirst(); // ilk eleman sildik comsume etmis gibi simule ettik
                    System.out.print("Removed value by consumer is: " + value);
                    System.out.println(" Now list size is: " + list.size());
                }

                lock.notify(); // Tüketim tamamlandıktan sonra üreticiye bildirim yapılıyor

            }
            Thread.sleep(random.nextInt(1000)); //force producer fill the queue to LIMIT_SIZE
        }
    }
}

/*

bu analiz etmek icin mantikli ama notify liste bitmeden habire git gel yapmasi mantiksiz

  public void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait(); // uyuttuk threadi
                }

                int value = list.removeFirst(); // ilk eleman sildik comsume etmis gibi simule ettik
                System.out.print("Removed value by consumer is: " + value);
                System.out.println(" Now list size is: " + list.size());

            }
            Thread.sleep(random.nextInt(1000)); //force producer fill the queue to LIMIT_SIZE
        }
    }
 */

/*
Datalari once doldurdu LIMITE KADAR sonrasinda her capasiteye ulastiginda tekrar aralaridna dönduler 1 data sildigimizde

Producer added: 0 queue size is 1
Producer added: 1 queue size is 2
Producer added: 2 queue size is 3
Producer added: 3 queue size is 4
Producer added: 4 queue size is 5
Producer added: 5 queue size is 6
Producer added: 6 queue size is 7
Producer added: 7 queue size is 8
Producer added: 8 queue size is 9
Producer added: 9 queue size is 10
Removed value by consumer is: 0 Now list size is: 9
Producer added: 10 queue size is 10
Removed value by consumer is: 1 Now list size is: 9
Producer added: 11 queue size is 10
Removed value by consumer is: 2 Now list size is: 9
Producer added: 12 queue size is 10
Removed value by consumer is: 3 Now list size is: 9
Producer added: 13 queue size is 10
Removed value by consumer is: 4 Now list size is: 9
Producer added: 14 queue size is 10
Removed value by consumer is: 5 Now list size is: 9
Producer added: 15 queue size is 10
 */


/*
2 veya daha fazla data sildigimizde once produce limite ulasana kadar o thread calisiyor sonra consumeda datalar eksiltiliyor

Producer added: 6 queue size is 7
Producer added: 7 queue size is 8
Producer added: 8 queue size is 9
Producer added: 9 queue size is 10
Removed value by consumer is: 0 Now list size is: 9
Removed value by consumer is: 1 Now list size is: 8
Producer added: 10 queue size is 9
Producer added: 11 queue size is 10
Removed value by consumer is: 2 Now list size is: 9
Removed value by consumer is: 3 Now list size is: 8
Producer added: 12 queue size is 9
Producer added: 13 queue size is 10
Removed value by consumer is: 4 Now list size is: 9
Removed value by consumer is: 5 Now list size is: 8
 */