import java.util.ArrayList;
import java.util.Scanner;


public class TestFileManager {
	
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
		ArrayList<Event> eventOfDate = fm.retrieveEventByDate(date);
		for(Event event : eventOfDate){
			System.out.println(event.toString());
		}
	}

	private static void userInput(FileHandler fm) {
			
		System.out.println("Event Name:");
		String eventName = scanner.nextLine();
		
		System.out.println("Start Date:");
		String startDate = scanner.nextLine();
		
		System.out.println("End Date");
		String endDate = scanner.nextLine();
		
		System.out.println("Start Time:");
		String startTime = scanner.nextLine();
		
		System.out.println("End Time:");
		String endTime = scanner.nextLine();
		
		System.out.println("Additional Information:");
		String addInfo = scanner.nextLine();
		
		Event anEvent = new Event(eventName, startDate, endDate, startTime, endTime, addInfo);
		fm.saveNewEventHandler(anEvent);
	}
		
		
}