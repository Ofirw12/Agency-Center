
public class AgencyManager extends Thread {
	private int numOfOperationsWanted;
	private int reportedOperations;
	private boolean endOfDay = false;
	private int totalOperationsDuration;

	public AgencyManager(int num) {
		this.numOfOperationsWanted = num;
	}

	public void run() {
		this.checkEndOfDay();
	}

	/**
	 * The Agency Manager waits for all the required operations to end, before he
	 * calls to end the working day.
	 */
	public synchronized void checkEndOfDay() {
		while (numOfOperationsWanted > reportedOperations) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		printEndOfDay();
		this.endOfDay = true;
		notifyAll();
	}

	/**
	 * A way for Executives to report to the Agency Manager. The Executives report
	 * how long the ended operation took and add 1 to the reported operations.
	 * 
	 * @param operation - The operation that ended.
	 */
	public synchronized void report(Operation operation) {
		totalOperationsDuration += operation.getTimeApprox();
		reportedOperations++;
		notifyAll();
	}

	/**
	 * A way for Executives, Operators and Task Managers to check if the Agency
	 * Manager ended the working day.
	 * 
	 * @return
	 */
	public boolean getEndOfDay() {
		return this.endOfDay;
	}

	private void printEndOfDay() {
		System.out.println("Number of Operations Completed: " + reportedOperations);
		System.out.println("Total time of Operations: " + totalOperationsDuration);

	}
}
