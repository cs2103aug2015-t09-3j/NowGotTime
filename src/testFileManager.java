import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class testFileManager {
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		FileHandler fm = new FileHandler();
		String command;
		
		while(true){
			System.out.println("Command: ");
			command = scanner.nextLine();
			
			if(command.equals("add")){
				userInput(fm);
			}
			else if(command.equalsIgnoreCase("display")){
				System.out.println("Date: ");
				display(fm, scanner.nextLine());
			}
			else{
				System.exit(0);
			}
			
		}
		
	}

	private static void display(FileHandler fm, String date) {
		ArrayList<HashMap<String,String>> eventOfDate = fm.retrieveEventByDate(date);
		for(HashMap<String, String> event : eventOfDate){
			System.out.println(event.get("eventName"));
			System.out.println(event.get("startDate"));
			System.out.println(event.get("endDate"));
			System.out.println(event.get("startTime"));
			System.out.println(event.get("endTime"));
			System.out.println(event.get("addInfo"));
		}
	}

	private static void userInput(FileHandler fm) {
		HashMap<String, String> anEvent = new HashMap<String, String>();
		
		System.out.println("Event Name:");
		anEvent.put("eventName", scanner.nextLine());
		
		System.out.println("Start Date:");
		anEvent.put("startDate", scanner.nextLine());
		
		System.out.println("End Date");
		anEvent.put("endDate", scanner.nextLine());
		
		System.out.println("Start Time:");
		anEvent.put("startTime", scanner.nextLine());
		
		System.out.println("End Time:");
		anEvent.put("endTime", scanner.nextLine());
		
		System.out.println("Additional Information:");
		anEvent.put("addInfo", scanner.nextLine());
		
		fm.saveNewEventHandler(anEvent);
	}
		
		
}
