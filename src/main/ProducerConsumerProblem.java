package main;

import java.util.Queue;

public class ProducerConsumerProblem {

	public static void main(String[] args) {
		
	}

}

class ProducerThread implements Runnable {

	private Queue queue;

	public ProducerThread(Queue queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		synchronized (queue) {
			try {
				while (true) {
					if (false) {
						continue;
					} else {
						queue.notify();
						queue.wait();
					}
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}		
	}
	
}

class ConsumerThread implements Runnable {

	private Queue queue;

	public ConsumerThread(Queue queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		synchronized (queue) {
			try {
				while (true) {
					if (queue.poll() != null) {
						continue;
					} else {
						queue.notify();
						queue.wait();
					}
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}		
	}
	
}
