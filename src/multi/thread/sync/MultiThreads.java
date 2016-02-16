package multi.thread.sync;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MultiThreads {

	private final int numOfThreads;
	private final Counter counter;
	private final CyclicBarrier barrier;
	private final Thread[] threads;

	public MultiThreads(int numOfThreads, int maxCounterValue) {
		this.numOfThreads = numOfThreads;
		counter = new Counter(numOfThreads, maxCounterValue);
		barrier = new CyclicBarrier(numOfThreads + 1);
		threads = new Thread[numOfThreads];

		for (int i = 0; i < numOfThreads; i++) {
			threads[i] = new Thread(new MyRunnable(i, counter, barrier),
					getThreadName(i));
		}
	}

	public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
		MultiThreads main = new MultiThreads(4, 30);
		main.runInSingleJvm();
	}

	private void runInSingleJvm() throws BrokenBarrierException,
			InterruptedException {
		for (int i = 0; i < numOfThreads; i++) {
			threads[i].start();
		}
		startCounting();
	}

	private void startCounting() throws InterruptedException,
			BrokenBarrierException {
		System.out.println(Thread.currentThread().getName()
				+ ": Sleeping for 1 secs....");
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName()
				+ ": ... And letting all the counting threads go!!");
		barrier.await();
	}
	
	private String getThreadName(int partyNum) {
		String prefix = "";
		for (int i = 0; i < partyNum; i++) {
			prefix += "  ";
		}
		return prefix + "Thread-" + partyNum;
	}
}
