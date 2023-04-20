import java.util.*;

public class Operation {
	private int operationId;
	private int level;
	private String codeName;
	private int customer;
	private long timeApprox;
	private int numOfCars;
	private int numOfMotorcycles;

	public Operation(int level, String codeName, int customer, long timeApprox, int operationCars,
			int operationMotorCycles, int operationId) {
		this.level = level;
		this.codeName = codeName;
		this.operationId = operationId;
		this.customer = customer;
		this.timeApprox = timeApprox;
		this.numOfCars = operationCars;
		this.numOfMotorcycles = operationMotorCycles;
	}

	public int getNumOfVehicles() {
		return (this.getNumOfCars() + this.getNumOfMotorcycles());
	}

	public int getNumOfCars() {
		return this.numOfCars;
	}

	public int getNumOfMotorcycles() {
		return this.numOfMotorcycles;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public int getLevel() {
		return this.level;
	}

	public int getCustomer() {
		return this.customer;
	}

	public long getTimeApprox() {
		return this.timeApprox;
	}
	
	public int getOperationId() {
		return this.operationId;
	}



}
