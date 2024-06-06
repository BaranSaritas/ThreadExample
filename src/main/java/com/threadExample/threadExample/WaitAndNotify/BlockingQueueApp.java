package com.threadExample.threadExample.WaitAndNotify;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;

@SuppressWarnings("InfiniteLoopStatement")
public class BlockingQueueApp {

// EN ALTTA ACIKLAMA EKLEDIM
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(10);
        final Random random = new Random();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        blockingQueue.put(random.nextInt(10));
                    }
                } catch (InterruptedException ignored) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);//wait for putting to the queue first
                } catch (InterruptedException ex) {
                    System.out.println("Exception " + ex.getMessage());
                }
                try {
                    while (true) {
                        blockingQueue.take();
                    }
                } catch (InterruptedException ignored) {}
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

class BlockingQueue<T> {

    private Queue<T> queue = new LinkedList<>();
    private int capacity;
    private Lock lock = new ReentrantLock();
    //condition variables
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T element) throws InterruptedException {
        lock.lock(); // giren thread kitliyor ve digeri bekliyor
        try {
            while (queue.size() == capacity) {
                System.out.println("Kuyruk dolu, ekleme yapılamaz");
                notFull.await(); // threadi beklemeye aliyor
            }

            queue.add(element);
            System.out.println("Kuyruğa eklendi: " + element);
            notEmpty.signal();
            // Kuyruğun artık boş olmadığını belirtir.
            // Kuyruğun boş olmasını bekleyen (tüketici) iş parçacıklarından
            // birini uyandırır, böylece o iş parçacığı kuyruktan eleman alabilir.
        } finally {
            lock.unlock(); // baska thread girebilmesi icin lock kaldirdik
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("Kuyruk boş, alınamaz");
                notEmpty.await(); // threadi beklemeye aliyor
            }

            T item = queue.remove();
            System.out.println("Kuyruktan çıkarıldı: " + item);
            notFull.signal();// Bekleyen iş parçacığını çağırır
            return item;
        } finally {
            lock.unlock();
        }
    }
}

// GENEL MANTIK SU BIR THREAD BITINCE GOREVI DIGERINI UYANDIRIYOR VE PROCUDE SONRA CONSUME SEKLINDE LOOP OLUYOR
    /*
    Kuyruk boş, alınamaz
Kuyruğa eklendi: 1
Kuyruğa eklendi: 7
Kuyruğa eklendi: 1
Kuyruğa eklendi: 3
Kuyruğa eklendi: 1
Kuyruğa eklendi: 5
Kuyruğa eklendi: 2
Kuyruğa eklendi: 2
Kuyruğa eklendi: 3
Kuyruğa eklendi: 7
Kuyruk dolu, ekleme yapılamaz
Kuyruktan çıkarıldı: 1
Kuyruktan çıkarıldı: 7
Kuyruktan çıkarıldı: 1
Kuyruktan çıkarıldı: 3
Kuyruktan çıkarıldı: 1
Kuyruktan çıkarıldı: 5
Kuyruktan çıkarıldı: 2
Kuyruktan çıkarıldı: 2
Kuyruktan çıkarıldı: 3
Kuyruktan çıkarıldı: 7
Kuyruk boş, alınamaz
     */