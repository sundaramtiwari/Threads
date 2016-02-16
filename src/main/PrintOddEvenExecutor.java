package main;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrintOddEvenExecutor {

	public static void main(String[] args) {
		String lock = "lock";
		ExecutorService service = Executors.newFixedThreadPool(2);

		Future<Integer> submit = service.submit(new MyThread(lock, 1, 20));
		Future<Integer> submit2 = service.submit(new MyThread(lock, 2, 20));

		try {
			System.out.println(submit.get());
			System.out.println(submit2.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}

class MyThread implements Callable<Integer> {

	private Object lock;
	private int start;
	private int max;

	public MyThread (Object lock, int start, int max) {
		this.lock = lock;
		this.start = start;
		this.max = max;
	}

	public void run() {
		synchronized(lock) {
			for (int i=start; i<=max; i += 2) {
				try {
					System.out.println("Runnable: " + Thread.currentThread().getName() + ": " + i);
					lock.notify();
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public Integer call() throws Exception {
		int i=start;
		synchronized(lock) {
			for (; i<=max; i += 2) {
				try {
					System.out.println("Callable: " + Thread.currentThread().getName() + ": " + i);
					lock.notify();
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}
	
}