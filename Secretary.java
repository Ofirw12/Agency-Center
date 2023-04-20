import java.util.*;

public class Secretary extends Thread {
	private String name;
	private Queue<Call> callQueue;
	private Queue<Task> taskQueue;
	private AnsweringMachine am;
	private ArrayList<String> names = new ArrayList<String>(
			Arrays.asList("Betty", "Johan", "Ezekiel", "Yehuda", "Miriam"));
	private static int nameCounter;
	

	public Secretary(Queue<Call> call, AnsweringMachine ansMach, Queue<Task> taskQ) {
		this.name = giveName();
		this.callQueue = call;
		this.am = ansMach;
		this.taskQueue = taskQ;

	}
	
	/**
	 * Assigns a name for the Secretary.
	 * @return - The first name of the names Array List to the Secretary.
	 */
	private synchronized String giveName() {
		if(nameCounter>4) {
			nameCounter=0;
		}
		String str = names.get(nameCounter);
		nameCounter++;
		return str;
	}

	private long extraDuration(Call call) {
		int max;
		int min;
		int range;
		if (call.getCustomer().equals("private")) {
			max = 1000;
			min = 500;
		} else if (call.getCustomer().equals("business")) {
			max = 2000;
			min = 1000;
		} else {
			max = 3000;
			min = 2000;
		}
		range = max - min;
		return (long) ((Math.random() * range) + min);
	}

	/**
	 * As long as the Answering Machine is taking calls or the queue of calls is not
	 * empty, The Secretary tries to take a call. If successful Secretary takes the
	 * call for a set duration plus extra calculated duration and inserts it as a
	 * new Task for the Task Managers and ends the call.
	 */
	public void run() {
		while (am.isAlive() || !callQueue.isEmpty()) {
			Call answered = callQueue.extract();
			try {
				sleep(answered.getDuration() + extraDuration(answered));
			} catch (InterruptedException e) {
			}
			taskQueue.insert(new Task(answered));
			endOfCall(answered);
		}
	}

	private void endOfCall(Call c) {
		c.endOfCall();
	}
}
