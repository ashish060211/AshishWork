package com.ash.java.thread;

public class InterruptionExceptionThrowsExample {

	public static void main(String[] args) throws Exception {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				try {
					while (!Thread.currentThread().isInterrupted()) {
						Thread.sleep(5000);
						System.out.println("Hello World!");
					}
				} catch (InterruptedException e) {
					//Thread.currentThread().interrupt();
					//System.out.println("somebody stopped me here...." + Thread.currentThread().isInterrupted());
					throw new RuntimeException(e);
				}
			}
		});

		thread.start();
		System.out.println("press enter to quit");
		System.in.read();
		thread.interrupt(); // Main thread try to stop the worker thread process now...
		System.out.println("Going to stop here....but see worker thread was really interrupted ??? "
				+thread.isInterrupted());// try to comment line-15 then re-run this and check log
		
	}
}