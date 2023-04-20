import java.util.*;

public class Queue<T> {
	private ArrayList<T> queue;
	private boolean endOfDay = false;

	public Queue() {
		this.queue = new ArrayList<T>();
	}

	public synchronized void insert(T item) {
		this.queue.add(item);
		notifyAll();
	}
	
	/**
	 * While the queue is empty, whoever is trying to extract will wait for a new item to extract.
	 * If the Agency Manager called to end the day, the extraction will return null.
	 * @return - The first item of the queue.
	 */
	public synchronized T extract() {
		while (queue.isEmpty()) {
			if (endOfDay) {
				return null;
			}
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		notifyAll();
		T item = queue.get(0);
		queue.remove(item);
		return item;
	}
	
	public synchronized boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public String toString() {
		return this.queue.toString();
	}
	public synchronized void leave() {
		this.endOfDay = true;
		notifyAll();
	}
	
}
