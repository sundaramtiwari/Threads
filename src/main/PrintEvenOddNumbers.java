package main;

public class PrintEvenOddNumbers {

	public static void main(String[] args) {
		String lock = "lock";
		Thread oddThread = new Thread(new OddThread(lock, 1, 20), "OddThread");
		Thread evenThread = new Thread(new EvenThread(lock, 2, 20), "EvenThread");
		
		oddThread.start();
		evenThread.start();
	}

}

class EvenThread implements Runnable {
	private Object lock;
	private int start, max;
	
	public EvenThread(Object lock, int start, int max) {
		this.lock = lock;
		this.start = start;
		this.max = max;
	}

	@Override
	public void run() {
		synchronized(lock) {
			for (int i=start; i<max; i+=2) {
				try {
					System.out.println(Thread.currentThread().getName()+ ": " + i);
					lock.notify();
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class OddThread implements Runnable {
	private Object lock;
	private int start, max;
	
	public OddThread(Object lock, int start, int max) {
		this.lock = lock;
		this.start = start;
		this.max = max;
	}

	@Override
	public void run() {
		synchronized(lock) {
			for (int i=start; i<max; i+=2) {
				try {
					System.out.println(Thread.currentThread().getName()+ ": " + i);
					lock.notify();
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}