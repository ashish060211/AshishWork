package com.ash.java.thread;

public class InterruptionExceptionExample2 {

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {

			public void run() {
				try {
					while (!Thread.currentThread().isInterrupted()) {
						Thread.sleep(1000);
						System.out.println("Hello World!");
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					//System.out.println("somebody stopped me here...." + Thread.currentThread().isInterrupted());
					throw new RuntimeException(e);// this exception will be received by worker thread only and not by main thread..
				}
			}
		});

		try {
			thread.start();
			System.out.println("press enter to quit");
			System.in.read();
			thread.interrupt(); // Main thread try to stop the worker thread process now...
			Thread.sleep(5000);
			System.out.println("Going to stop here...."+Thread.currentThread().isInterrupted());
		} catch (Exception e) {
			// any of the below statements will not be printed because RuntimeException occur in worker thread and not in main thread
			System.out.println("Exception class name is : "+e.getClass().getCanonicalName());
			System.out.println("Main thread was interrupted ??? "+Thread.currentThread().isInterrupted());
			System.out.println("Worker thread was really interrupted ??? "+thread.isInterrupted());// try to comment line-15 then re-run this and check log
		}
	}
}