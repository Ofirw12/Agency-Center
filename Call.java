
public class Call extends Thread {
	private String customerName;
	private String service;
	private String customer;
	private int arrival;
	private int duration;
	private static Queue<Call> callQueue;
	private boolean endOfCall = false;

	public Call(String name, String service, String customer, int arrival, int duration, Queue<Call> callQ) {
		this.customerName = name;
		this.service = service;
		this.customer = customer;
		this.arrival = arrival;
		this.duration = duration;
		callQueue = callQ;
	}
	
	/**
	 * As the call getting answered, it sleeps for the duration of the arrival of the call and then inserting itself to the Queue.
	 * Then the call waits for the Secretary to finish the call.
	 */
	public void run() {
		try {
			sleep(this.getArrival());
		} catch (InterruptedException e) {
		}
		callQueue.insert(this);
		waitTime();
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public String getService() {
		return this.service;
	}

	public String getCustomer() {
		return this.customer;
	}

	public int getArrival() {
		return this.arrival;
	}

	public int getDuration() {
		return this.duration;
	}

	public Queue<Call> getCallQueue() {
		return callQueue;
	}
	
	/**
	 * The call thread waits until the Secretary ends the call.
	 */
	private synchronized void waitTime() {
		while (!this.endOfCall) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public synchronized void endOfCall() {
		this.endOfCall = true;
		notifyAll();
	}

}
