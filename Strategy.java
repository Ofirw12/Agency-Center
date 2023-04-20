
public class Strategy implements Comparable<Strategy> {
	private int strategyId;
	private String codeName;
	private int level;
	private int customer;
	private long timeApprox;
	
	public Strategy(int taskId, String codeName, int level, int customer, long timeApprox) {
		this.strategyId = taskId;
		this.codeName = codeName;
		this.level = level;
		this.customer = customer;
		this.timeApprox = timeApprox;
		
	}
	public int compareTo(Strategy strategy) {
		return (int)(this.getTimeApprox() - strategy.getTimeApprox());
	}
	
	public long getTimeApprox() {
		return this.timeApprox;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getCodeName() {
		return this.codeName;
	}
	
	public int getCustomer() {
		return this.customer;
	}
	public int getStrategyId(){
		return this.strategyId;
	}
	
}
