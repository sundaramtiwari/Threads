package multi.thread.sync;

public class Counter {

	private final int numOfThreads;
	private int value = 0;
	private final int maxValue;

	public Counter(int numParties, int maxValue) {
		this.numOfThreads = numParties;
		this.maxValue = maxValue;
	}

	public synchronized boolean isMyTurn(int partyNum) {
		return value % numOfThreads == partyNum;
	}

	public synchronized void setValue(int val) {
		this.value = val;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public synchronized boolean isMaxValueReached() {
		return value >= maxValue;
	}

	public synchronized int increment() {
		this.value++;
		return value;
	}

	public synchronized int getValue() {
		return value;
	}
}
