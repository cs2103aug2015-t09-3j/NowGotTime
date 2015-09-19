import java.util.ArrayList;

public class test {
	public static void main(String[] args){
			// FileHandler fh = new FileHandler();
			test t = new test();
			t.testFileTodoHandler();
//			t.testFileProjectHandler();
//			t.testFileEventHandler();
	}
	
	public void testFileTodoHandler(){
		FileTodoHandler fth = new FileTodoHandler("C:\\Users\\RX.huang\\git\\main\\Todo");
		Todo todo1 = new Todo("TestName", "This is extra");
		Todo todo2 = new Todo("This is shag", "I don't know what I am doing", "20 oct 2015");
		Todo todo3 = new Todo("What is shit", "It is what I eat", "20 oct 2015", "10:00");
		
		fth.saveNewTodoHandler(todo1);
		fth.saveNewTodoHandler(todo2);
		ArrayList<Todo> taskBook = fth.retrieveTodoEventByDate("20 Oct 2015");
		customPrinting(taskBook);
	}
	
	public void testFileProjectHandler(){
		FileProjectHandler fph = new FileProjectHandler("C:\\Users\\RX.huang\\git\\main\\Project");
		fph.createNewProject("test");
		
		Event event1 = new Event("one", "10 mar 2015", "11 mar 2015", "09:00", "10:00", "nothing lah" );
		Event event2 = new Event("one", "15 mar 2015", "11 mar 2015", "09:00", "10:00", "nothing lah" );
		Event event3 = new Event("one", "20 mar 2015", "11 mar 2015", "09:00", "10:00", "nothing lah" );
		
		ArrayList<Event> book = new ArrayList<Event>();
		book.add(event1);
		book.add(event2);
		book.add(event3);
		
		fph.saveEditedProjectDetails(book, "test");
		ArrayList<Event> boob = fph.retrieveProject("test");
		for(Event event: boob){
			System.out.println(event);
		}
	}
	
	public void testFileEventHandler(){
		FileEventHandler fEventH = new FileEventHandler("C:\\Users\\RX.huang\\git\\main\\Event");
		Event event = new Event("Dinner with Tim", "31 aug 2015", "1 sep 2015", "23:00", "02:00", "Prepare car");
		fEventH.saveNewEventHandler(event);
		Event event2 = new Event("Do coding", "31 aug 2015", "31 aug 2015", "20:00", "22:00", "In java");
		fEventH.saveNewEventHandler(event2);
		Event event3 = new Event("sleep", "31 aug 2015", "3 sep 2015", "20:00", "22:00", "lean on the left");
		fEventH.saveNewEventHandler(event3);
		
		Event event4 = new Event("sleep", "24 aug 2015", "31 aug 2015", "20:00", "22:00", "lean on the left");
		ArrayList<Event> eventBook = fEventH.retrieveEventByDate("31 Aug 2015");
		eventBook.add(event4);
		fEventH.saveEventBook("31 aug 2015", eventBook);
		
		eventBook = fEventH.retrieveEventByDate("31 Aug 2015");
		customPrint(eventBook);
	}
	
	private static void customPrint(ArrayList<Event> stuff){
		if(stuff.isEmpty()){
			System.out.println("It is empty.");
		}
		
		for(Event item: stuff){
			System.out.println(item);
			System.out.println();
		}
	}
	
	private static void customPrinting(ArrayList<Todo> stuff){
		if(stuff.isEmpty()){
			System.out.println("It is empty.");
		}
		
		for(Todo item: stuff){
			System.out.println(item);
			System.out.println();
		}
	}
}


/**
 * Test cases solved: 
 * Events: 
 * 	1) Spill over days and months for .saveNewEventHandler(event)
 * 	2) 
 * 
 * Projects:
 * 	-
 * 
 * Test cases not solved:
 * Events:
 * 	1) Spill over days and months for edited events
 * 
 * Projects:
 * 	-
 */
