package com.ash.java.thread;

public class InterruptionExceptionExample {

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(5000);
                        System.out.println("Hello World!");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();// if u comment-out this line then isInterrupted() will return you false and
                    // caller will not know about this thread was interrupted by somebody.. which is the reason of its early return.
                    System.out.println("somebody stopped me here...."+Thread.currentThread().isInterrupted());
                }
            }
        });
        
        thread.start();
        System.out.println("press enter to quit");
        System.in.read();
        thread.interrupt();	// Main thread try to stop the worker thread process now...
        System.out.println("Going to stop here...."+Thread.currentThread().isInterrupted());
    }
}