import java.util.*;

public class InformationSystem {
	private TreeSet<Strategy> informationSystemStorage;
	private Database database;

	/**
	 * Every day the previous record of the database is deleted and a new one is
	 * created to replace it.
	 * @param db
	 */
	public InformationSystem(Database db) {
		informationSystemStorage = new TreeSet<Strategy>();
		this.database = db;
		this.database.dropTable();
		this.database.createTable();
	}

	public synchronized void insert(Strategy strategy) {
		this.informationSystemStorage.add(strategy);
		this.database.insert(strategy.getStrategyId(), strategy.getCodeName(), strategy.getCustomer(),
				strategy.getLevel(), ((int) (strategy.getTimeApprox())));
		notifyAll();
	}

	/**
	 * @return - The strategy with the minimal duration. if there are no available
	 *         strategies to extract it returns null.
	 */
	public synchronized Strategy extract() {
		Strategy min = informationSystemStorage.pollFirst();
		notifyAll();
		return min;
	}
}
