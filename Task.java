
public class Task {
	private static int numOfTasks;
	private int taskId;
	private String name;
	private String service;
	private int customer;
	private int arrival;
	private static Queue<Task> taskQueue = new Queue<Task>(); // should be same queue as secretary's queue.

	public Task(Call call) {
		this.taskId = taskCounter();
		this.name = call.getCustomerName();
		this.service = call.getService();
		this.customer = this.getCustomer(call);
		this.arrival = call.getArrival();
	}

	private static synchronized int taskCounter() {
		return ++numOfTasks;
	}

	private int getCustomer(Call call) {
		if (call.getCustomer().equals("private")) {
			return 1;
		} else if (call.getCustomer().equals("business")) {
			return 2;
		} else {
			return 3;
		}
	}

	public int getTaskId() {
		return this.taskId;
	}

	public String getName() {
		return this.name;
	}

	public String getService() {
		return this.service;
	}

	public int getCustomer() {
		return this.customer;
	}

	public int getArrival() {
		return this.arrival;
	}

	public Queue<Task> getTaskQueue() {
		return taskQueue;
	}

	public String toString() {
		return (this.taskId + ": " + this.name);
	}

}
