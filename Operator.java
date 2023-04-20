import java.util.ArrayList;
import java.util.Arrays;

public class Operator extends Thread {
	private String name;
	private InformationSystem infoSys;
	private static Agency agency;
	private AgencyManager boss;
	private static BoundedQueue<Operation> operationQ;
	private double workingTime;
	private ArrayList<String> names = new ArrayList<String>(Arrays.asList("Itzik", "Hava", "Horhe"));
	private static int nameCounter;
	

	public Operator(InformationSystem infoSys, Agency a, AgencyManager boss, BoundedQueue<Operation> oQ, double wT) {
		this.name = giveName();
		this.infoSys = infoSys;
		agency = a;
		this.boss = boss;
		operationQ = oQ;
		this.workingTime = wT;
	}
	
	/**
	 * Assigns a name for the Operator.
	 * @return - The first name of the names Array List to the Operator.
	 */
	private synchronized String giveName() {
		if(nameCounter>2) {
			nameCounter=0;
		}
		String str = names.get(nameCounter);
		nameCounter++;
		return str;
	}

	private synchronized boolean openOperation(Strategy strategy) {
		return agency.openOperation(strategy.getLevel(), strategy.getCodeName(), strategy.getCustomer(),
				strategy.getTimeApprox(),strategy.getStrategyId());
	}
	
	/**
	 * As long as the day is not over, the Operator trying to extract a strategy.
	 * If successful, after sleeping for the duration of the operation,
	 * The Operator inserts the strategy as an operation for the Executives.
	 */
	public void run() {
		while (!checkEndOfDay()) {
			Strategy strategy = infoSys.extract();
			if (checkEndOfDay()) {
				break;
			}
			if (strategy == null) { // If the strategy is null that means there are no available strategies, so try
									// again.
				continue;
			}
			try {
				sleep((Math.round(workingTime)));
			} catch (InterruptedException e) {

			}
			if (this.openOperation(strategy)) {
				for (Operation o : agency.copyOperationList()) {
					if (strategy.getStrategyId()==(o.getOperationId())) {
						operationQ.insert(o);
						break;
						
					}
				}
			}
		}
	}

	private synchronized boolean checkEndOfDay() {
		if (boss.getEndOfDay()) {
			notifyAll();
			return true;
		}
		return false;
	}
}
