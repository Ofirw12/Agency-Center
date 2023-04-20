
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnsweringMachine extends Thread {
	private String location;
	private static Queue<Call> callQueue;

	public AnsweringMachine(String location, Queue<Call> callQ) {
		this.location = location;
		callQueue = callQ;
	}

	private static File fillCalls(String calls) {
		File myFile = null;
		try {
			myFile = new File(calls);
			Scanner myReader = new Scanner(myFile);
			String data = myReader.nextLine();
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				String[] dataArr = data.split("\t");
				String name = dataArr[0];
				String service = dataArr[1];
				String customer = dataArr[2];
				int arrival = Integer.parseInt(dataArr[3]);
				int duration = Integer.parseInt(dataArr[4]);
				Call call = new Call(name, service, customer, arrival, duration, callQueue);
				call.start();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		return myFile;
	}

	/**
	 * Reads from a file that contains information about calls, then creates a call
	 * that inserts itself to a list of calls.
	 */
	public void run() {
		fillCalls(location);
	}
}
