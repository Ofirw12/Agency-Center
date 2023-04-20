import java.util.*;

public class Executive extends Thread {
	private String name;
	private BoundedQueue<Operation> operationQ;
	private AgencyManager boss;
	private Agency agency;
	private static ArrayList<String> names = new ArrayList<String>(Arrays.asList("Mendel", "Moses", "Kratos", "Ibrahim",
			"Artyom", "Ofir", "Danny", "Tomer", "David", "Shkolnik", "Homer", "Ahmed", "Avner"));
	private static int nameCounter;

	public Executive(BoundedQueue<Operation> oQ, AgencyManager boss, Agency a) {
		this.name = giveName();
		this.operationQ = oQ;
		this.boss = boss;
		this.agency = a;
	}
	
	/**
	 * Assigns a name for the Executive.
	 * @return - The first name of the names Array List to the Executive.
	 */
	private synchronized String giveName() {
		if(nameCounter>12) {
			nameCounter=0;
		}
		String str = names.get(nameCounter);
		nameCounter++;
		return str;
	}
	
	/**
	 * As long as the day is not over, the Executive trying to extract an operation.
	 * If successful, after sleeping for the calculated duration of the operation,
	 * The Executive ends the operation and reports to the Agency Manager.
	 * If it's the end of the day, The Executive notify all who's waiting to extract an operation to stop waiting.
	 */
	public void run() {
		while (!checkEndOfDay()) {
			Operation operation = operationQ.extract();
			if (checkEndOfDay()) {
				break;
			}
			long operationDuration = this.operationTime(operation);
			try {
				sleep(operationDuration);
			} catch (InterruptedException e) {
			}
			this.endOperation(operation);
			boss.report(operation);
		}
		operationQ.leave();

	}
	
	public long operationTime(Operation operation) {
		int numOfAgents = agency.numOfDetectivesNeeded(operation.getLevel())
				+ agency.numOfInvestigatorsNeeded(operation.getLevel());
		return ((long) ((numOfAgents + operation.getNumOfVehicles() + (Math.random() * (6) + 2)) * 1000));
	}

	private synchronized boolean checkEndOfDay() {
		if (boss.getEndOfDay()) {
			notifyAll();
			return true;
		}
		return false;
	}

	private synchronized void endOperation(Operation o) {
		agency.closeOperation(o);
	}
}
