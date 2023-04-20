import java.util.*;

public class Agency {
	private int numOfCars = 10;
	private int numOfMotorcycles = 50;
	private int numOfDetectives = 60;
	private int numOfInvestigators = 40;
	private ArrayList<Operation> listOfOperations = new ArrayList<Operation>();

	private synchronized boolean noVehicles() {
		if (numOfCars == 0 && numOfMotorcycles == 0) {
			return true;
		}
		return false;
	}

	private synchronized boolean noAgents() {
		if (numOfDetectives == 0 && numOfInvestigators == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Adds an operation to the list of operations.
	 * 
	 * @param level      - Level of the operation.
	 * @param codeName   - Code-name of the operation.
	 * @param customer   - Customer code.
	 * @param timeApprox - Duration of the operation.
	 * @return - true if creation of an operation was successful, otherwise false.
	 */
	public synchronized boolean openOperation(int level, String codeName, int customer, long timeApprox,
			int operationId) {
		while (this.noVehicles() || this.noAgents()) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		int investigators, detectives, agents;
		investigators = numOfInvestigatorsNeeded(level);
		detectives = numOfDetectivesNeeded(level);
		agents = investigators + detectives;
		List<Integer> operationVehicles = this.assignVehicles(agents);
		Operation operation = new Operation(level, codeName, customer, timeApprox, operationVehicles.get(0),
				operationVehicles.get(1), operationId);
		listOfOperations.add(operation);
		notifyAll();
		return true;
	}

	/**
	 * Assigns Vehicles based on how many agents needed.
	 * 
	 * @param agents - Number of agents needed for an operation.
	 * @return - ArrayList with number of cars and motorcycle assigned to the
	 *         operation.
	 */
	private synchronized ArrayList<Integer> assignVehicles(int agents) {
		ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(0, 0));
		for (int i = 0; i <= numOfCars; i++) {
			if (agents >= 5) {
				agents -= 5;
				result.set(0, result.get(0) + 1);
			}
			if (agents < 5) {
				agents -= agents;
				result.set(0, result.get(0) + 1);
				break;
			}

		}
		for (int i = 0; i <= numOfMotorcycles; i++) {
			if (agents >= 1) {
				agents--;
				result.set(1, result.get(1) + 1);

			}
			if (agents <= 0) {
				break;
			}

		}
		return result;
	}

	/**
	 * Ends the operation by telling the agents and vehicles to return.
	 * 
	 * @param operation - The operation that needs to be closed.
	 */
	public synchronized void closeOperation(Operation operation) {
		numOfInvestigators += this.numOfInvestigatorsNeeded(operation.getLevel());
		numOfDetectives += this.numOfDetectivesNeeded(operation.getLevel());
		numOfCars += operation.getNumOfCars();
		numOfMotorcycles += operation.getNumOfMotorcycles();
		notifyAll();
	}

	/**
	 * Copies the list.
	 * 
	 * @return - The copied list.
	 */
	public synchronized ArrayList<Operation> copyOperationList() {
		ArrayList<Operation> al = new ArrayList<Operation>();
		for (Operation o : this.listOfOperations) {
			al.add(o);
		}
		return al;
	}

	/**
	 * Detectives needed by level.
	 * 
	 * @param level - Level of the operation.
	 * @return - Number of detectives.
	 */
	public int numOfDetectivesNeeded(int level) {
		if (level == 1) {
			return 2;
		} else if (level == 2) {
			return 3;
		} else if (level == 3) {
			return 1;
		} else if (level == 4) {
			return 4;
		} else {
			return 7;
		}
	}

	/**
	 * Investigators needed by level.
	 * 
	 * @param level
	 * @return - Number of investigators.
	 */
	public int numOfInvestigatorsNeeded(int level) {
		if (level == 1) {
			return 0;
		} else if (level == 2) {
			return 2;
		} else if (level == 3) {
			return 5;
		} else if (level == 4) {
			return 6;
		} else {
			return 8;
		}
	}

}
