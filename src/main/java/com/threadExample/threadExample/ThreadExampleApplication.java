package com.threadExample.threadExample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ThreadExampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ThreadExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Deneme deneme = new Deneme();
		deneme.start(); // thread baslattim
		Thread.sleep(2000);
		deneme.shutdown();
	 }



}

