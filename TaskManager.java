import java.util.ArrayList;
import java.util.Arrays;

public class TaskManager extends Thread {
	private String name;
	private Queue<Task> taskQueue;
	private InformationSystem infoSys;
	private AgencyManager boss;
	private ArrayList<String> names = new ArrayList<String>(Arrays.asList("Abraham", "George", "Richard"));
	private static int nameCounter;

	public TaskManager(Queue<Task> taskQ, AgencyManager boss, InformationSystem is) {
		this.name = giveName();
		this.taskQueue = taskQ;
		this.boss = boss;
		this.infoSys = is;
	}

	/**
	 * Assigns a name for the Task Manager.
	 * @return - The first name of the names Array List to the Task Manager.
	 */
		private synchronized String giveName() {
			if(nameCounter>2) {
				nameCounter=0;
			}
			String str = names.get(nameCounter);
			nameCounter++;
			return str;
		

	}
	
	/**
	 * As long as the day is not over, the Task Manager trying to extract a task.
	 * If successful, after sleeping for the 3 seconds,
	 * The Task Manager creates a new Strategy and inserts it into the Information System.
	 * If it's the end of the day, The Task Manager notify all who's waiting to extract a task to stop waiting.
	 */
	public void run() {
		while (!checkEndOfDay()) {
			Task task = taskQueue.extract();
			if (checkEndOfDay()) {
				break;
			}
			try {
				sleep(3000);
			} catch (InterruptedException e) {
			}
			Strategy strategy = new Strategy(task.getTaskId(), this.anagram(task.getName()),
					this.serviceCode(task.getService()), task.getCustomer(),
					this.time(task.getCustomer(), task.getService()));
			infoSys.insert(strategy);
			System.out.println("Task " + task.getTaskId() + " converted and stored on the system");
		}
		taskQueue.leave();
	}

	/**
	 * Reverses a name. This is used for the code name of the strategy.
	 * @param name - tasks name.
	 * @return The reversed name to use as a code name.
	 */
	private String anagram(String name) {
		String rName = "";
		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			rName = ch + rName;
		}
		return rName;
	}

	private int serviceCode(String service) {
		if (service.equals("inquiry")) {
			return 1;
		} else if (service.equals("Background check")) {
			return 2;
		} else if (service.equals("surveillance")) {
			return 3;
		} else if (service.equals("fraud and illegal activity")) {
			return 4;
		} else {
			return 5;
		}
	}

	private long time(int customer, String service) {
		return (serviceCode(service) * customer) * 1000;
	}

	private synchronized boolean checkEndOfDay() {
		if (boss.getEndOfDay()) {
			notifyAll();
			return true;
		}
		return false;
	}
}