import java.util.*;

public class BoundedQueue<T> {
	private ArrayList<T> boundedQ;
	private final int MAX_SIZE = 15;
	private boolean endOfDay = false;

	public BoundedQueue() {
		this.boundedQ = new ArrayList<T>();
	}

	/**
	 * Adds the item to the Array List. Cannot exceed MAX_SIZE.
	 * @param item
	 */
	public synchronized void insert(T item) {
		while (boundedQ.size() >= MAX_SIZE) {
			if (endOfDay) {
				return;
			}
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		this.boundedQ.add(item);
		notifyAll();
	}

	/**
	 * Removes the first item and returns it, as long as the Array List is not
	 * empty.
	 * @return
	 */
	public synchronized T extract() {
		while (boundedQ.isEmpty()) {
			if (endOfDay) {
				return null;
			}
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		T item = boundedQ.get(0);
		boundedQ.remove(item);
		notifyAll();
		return item;
	}

	public synchronized boolean isEmpty() {
		return this.boundedQ.isEmpty();
	}

	public synchronized boolean isFull() {
		return this.boundedQ.size() == MAX_SIZE;
	}

	/**
	 * A way to notify the threads that are waiting to extract, to stop waiting.
	 */
	public synchronized void leave() {
		this.endOfDay = true;
		notifyAll();
	}

}
