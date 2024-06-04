package com.threadExample.threadExample;

public class Deneme extends Thread {

		private volatile boolean running = true;
		private int counter = 0;
		public void run() {
			while (running) {
				System.out.println("Running");

				counter += 1;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	public void shutdown() {
			running = false;
		}
	}
