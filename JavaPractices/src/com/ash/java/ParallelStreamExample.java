package com.ash.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ParallelStreamExample {

	public static void main(String[] args) throws InterruptedException {

		// Long sum = LongStream.iterate(1, i -> i + 1).limit(10_000_000).map(i -> i *
		// 1).sum();
		// System.out.println(sum);

		
		  //Long sum = LongStream.rangeClosed(1, 1_000_000_000)
		//		  .parallel().map(i -> i * i).sum(); 
		  
		  LongStream str = LongStream.rangeClosed(1, 1_000_000_000)
				  .parallel().map(i -> i * i);
		  System.out.println("------");
		  System.out.println(str);
		  System.out.println("++++++");
		  System.out.println(str.sum());

		//System.out.println(countPrimes(1000));
		//System.out.println(countPrimesWithParallel(1000));
		//run() ;
	}

	private static long countPrimesWithParallel(int max) {
		return LongStream.range(1, max).parallel().filter(ParallelStreamExample::isPrime).count();
	}

	private static long countPrimes(int max) {
		return LongStream.range(1, max).filter(ParallelStreamExample::isPrime).count();
	}

	private static boolean isPrime(long n) {
		return n > 1 && LongStream.rangeClosed(2, (long) Math.sqrt(n)).noneMatch(divisor -> n % divisor == 0);
	}

	private static void run() throws InterruptedException {
		int MAX = 1_000;
		ExecutorService es = Executors.newCachedThreadPool();
		// Simulating multiple threads in the system if one of them is executing a
		// long-running task.
		// Some of the other threads/tasks are waiting for it to finish
		es.execute(() -> countPrimes(MAX, 1000));// incorrect task
		es.execute(() -> countPrimes(MAX, 0));
		es.execute(() -> countPrimes(MAX, 0));
		es.execute(() -> countPrimes(MAX, 0));
		es.execute(() -> countPrimes(MAX, 0));
		es.execute(() -> countPrimes(MAX, 0));
		es.shutdown();
		// es.awaitTermination(60, TimeUnit.SECONDS);
	}

	private static void countPrimes(int max, int delay) {
		System.out.println(Thread.currentThread().getName() + " output : "
				+ LongStream.range(1, max).parallel().filter(ParallelStreamExample::isPrime).peek(i -> {
					try {
						TimeUnit.NANOSECONDS.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}).count());
	}
}
