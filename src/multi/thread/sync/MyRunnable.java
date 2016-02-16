package multi.thread.sync;

import java.util.concurrent.CyclicBarrier;

public class MyRunnable implements Runnable {

	private final Counter counter;
	private final int threadId;
	private final CyclicBarrier barrier;

	public MyRunnable(int threadId, Counter counter, CyclicBarrier barrier) {
		this.threadId = threadId;
		this.counter = counter;
		this.barrier = barrier;
	}

	public void run() {
		try {
			System.out.println(Thread.currentThread().getName()
					+ ": Waiting for GREEN signal from main guy...");
			barrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (true) {
			synchronized (counter) {
				while (!(counter.isMyTurn(threadId) || counter
						.isMaxValueReached())) {
					try {
						counter.wait();
					} catch (InterruptedException e) {
						System.out.println(threadId + ": Got Interrupted. Continuing for my turn...");
					}
				}
				if (counter.isMaxValueReached()) {
					counter.notifyAll();
					break;
				}
				int value = counter.increment();
				System.out.println(Thread.currentThread().getName()
						+ ": Counter Value=" + value);
				/*try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}*/
				counter.notifyAll();
			}
		}

		System.out.println(Thread.currentThread().getName() + ": DONE!!");
	}
}
