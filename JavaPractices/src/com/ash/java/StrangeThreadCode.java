package com.ash.java;

public class StrangeThreadCode {
	private static boolean stop = false;

	public static void main(String[] args) throws InterruptedException {
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " Starting");
			while (!stop) {
				// int i = 10;
				// int j = 100 * i;
				// System.out.print(".");
			}
			// System.out.print(".")
			/*
			 * try { Thread.sleep(500); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
			;
			System.out.println(Thread.currentThread().getName() + " Stopping");
		}).start();

		System.out.println("Stopper thread started...");
		Thread.sleep(1_000);
		stop = true;
		System.out.println("Stop set to true, main exiting..");
	}
}
