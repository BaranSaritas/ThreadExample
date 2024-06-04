package com.threadExample.threadExample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThreadExampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ThreadExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Thread thread = new Thread(new Deneme());
		thread.start();
	}



	public class Deneme implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println("Hello: " + i + " Thread: " + Thread.currentThread().getName());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {}
			}
		}
	}
}

