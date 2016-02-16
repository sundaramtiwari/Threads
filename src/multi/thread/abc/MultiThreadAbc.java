package multi.thread.abc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MultiThreadAbc {

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		Lock lock = new Lock();
		CyclicBarrier barrier = new CyclicBarrier(3);
		Thread oddThread = new Thread(new OddEvenThread(lock, 1, 20, barrier));
		Thread evenThread = new Thread(new OddEvenThread(lock, 2, 20, barrier));
		
		oddThread.start();
		evenThread.start();
		
		System.out.println(Thread.currentThread().getName()
				+ ": Sleeping for 1 secs....");
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName()
				+ ": ... And letting all the counting threads go!!");
		barrier.await();
		
	}
}

class OddEvenThread implements Runnable {
	
	private Lock lock;
	private int start;
	private int max;
	private CyclicBarrier barrier;
	
	public OddEvenThread(Lock lock, int start, int max, CyclicBarrier barrier) {
		super();
		this.lock = lock;
		this.start = start;
		this.max = max;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName()
					+ ": Waiting for GREEN signal from main guy...");
			barrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i=start; i<max; i+=2) {
			synchronized (lock) {
				try {
					System.out.println(Thread.currentThread().getName() + ": " + i);
					Thread.sleep(500);
					lock.notify();
					lock.wait();
				} catch (InterruptedException e) {
					System.out.println("thread interrupted");
				}
			}
		}
	}	
}

class Lock {
	
}