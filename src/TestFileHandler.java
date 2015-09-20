import java.util.ArrayList;

/**
 * 
 * @author RX.huang
 * This class will test the operations in FileHandler Class
 * You may report bugs under the not solves section
 * 
 * After running, do refresh the NowGotTime package. You should be able to see
 * the folders and files created.
 * 
 * ****************************
 * Test cases solved:         *
 * ****************************
 * 	Events: 
 * 		1) Spill over days and months for .saveNewEventHandler(event)
 * 		2) 
 * 
 * 	Projects:
 * 		-
 * ****************************
 * 	Test cases not solved:	  *
 * ****************************
 * 	Events:
 * 		1) Spill over days and months for edited events
 * 
 * 	Projects:
 * 		-
 * 
 **/


public class TestFileHandler {
	
	private FileHandler fh = new FileHandler();
	
	public static void main(String[] args){
			
		TestFileHandler testo = new TestFileHandler();
			
		System.out.println("Cleaning all data before use...");
		testo.fh.clearAll();
		System.out.println("All data in storage wiped.");
		System.out.println();
			
		testo.fh = new FileHandler();
			
		testo.testFileTodoHandler();
		testo.testFileProjectHandler();
		testo.testFileEventHandler();
			
	}
	
	public void testFileTodoHandler(){

		FileTodoHandler fth = new FileTodoHandler("C:\\Users\\RX.huang\\git\\main\\Todo");
		System.out.println("This is to test the TodoHandler:");
		System.out.println();
		
		Todo todo1 = new Todo("TestName", "This is extra");
		System.out.println("create todo1: Name, additional info --> basic todo type.");
		
		Todo todo2 = new Todo("This is shag", "I don't know what I am doing", "20 oct 2015");
		System.out.println("Create todo2: Name, additional info, date --> partial todo type.");
		
		Todo todo3 = new Todo("What is shit", "It is what I eat", "20 oct 2015", "10:00");
		System.out.println("Create todo3: Name, additional Info, date, time --> complete todo type");
		
		Todo todo4 = new Todo("Watch Pony On Red Nails", "Best one", "20 oct 2015", "07:30");
		System.out.println("Create todo3: Name, additional Info, date, time --> complete todo type");
		
		fh.saveNewTodoHandler(todo1);
		System.out.println("Added todo1 as a new Todo (floating todo).");
		
		fh.saveNewTodoHandler(todo2);
		System.out.println("Added todo2 as a new Todo.");
		
		fh.saveNewTodoHandler(todo3);
		System.out.println("Added todo3 as a new Todo.");
		
		System.out.println("Retrieving to-do-list on 20 Oct 2015...");
		ArrayList<Todo> taskBook = fh.retrieveTodoByDate("20 Oct 2015");
		System.out.println("To-do-list for 20 Oct 2015:");
		customPrinting(taskBook);
		System.out.println ("//************************** End **************************//");
		
		System.out.println("Testing the edited todo add:");
		System.out.println();
		System.out.println("After retrieved the arraylist<todo>, we add in todo4.");
		taskBook.add(todo4);
		System.out.println("Stimulate process on the logic side: Added edited todo4 ");
		
		fth.saveToDoList("20 oct 2015", taskBook);
		System.out.println("Saved whole todo arraylist with date into storage");
		System.out.println();
		
		System.out.println("Retrieving to-do-list on 20 Oct 2015...");
		taskBook = fh.retrieveTodoByDate("20 Oct 2015");
		
		System.out.println("To-do-list for 20 Oct 2015:");
		customPrinting(taskBook);
		System.out.println ("//************************** End **************************//");
		
		System.out.println("Testing the retrieval of floating todo add:");
		System.out.println();
		taskBook = fth.retrieveUniversalTodo();
		System.out.println("Floating to-do-list:");
		customPrinting(taskBook);
		System.out.println ("//****************** End of test todo ********************//");
		System.out.println();
		
	}
	
	public void testFileProjectHandler(){
		System.out.println ("//***************** Start test project *******************//");
		System.out.println("Create new project: Software development");
		fh.createNewProject("Software development");
		System.out.println("Project \"Software development\" has been created.");
		System.out.println();
		
		System.out.println("Generating events to put into project.");
		Event event1 = new Event("Brainstorming", "10 mar 2015", "11 mar 2015", "09:00", "15:00", "Brainstorm features");
		Event event2 = new Event("Split work", "14 mar 2015", "14 mar 2015", "09:00", "10:00", "I will do storage handling");
		Event event3 = new Event("Project meeting", "20 mar 2015", "21 mar 2015", "20:00", "03:00", "Meet up for code review.");
		System.out.println();
		
		System.out.println("Retrieving projecting: \"Software development\"");
		ArrayList<Event> book = fh.retrieveProjectTimeLine("Software development");
		book.add(event3);
		book.add(event1);
		book.add(event2);
		System.out.println("Adding events into project");
		System.out.println();
		System.out.println("Saving project.");
		fh.saveEditedProjectDetails(book, "Software development");
		System.out.println("");
		
		System.out.println("Retrieving projecting: \"Software development\"");
		ArrayList<Event> projectBook = fh.retrieveProjectTimeLine("Software development");
		System.out.println("Opening project book.");
		System.out.println("Events in \"Software development\" project:");
		for(Event event: projectBook){
			System.out.println(event);
			System.out.println();
		}
		System.out.println ("//************************** End **************************//");
		System.out.println ("Test delete project: ");
		System.out.println("Create new project: \"Project to test delete\"");
		fh.createNewProject("Project to test delete");
		System.out.println("Delete the project: \"Project to test delete\"");
		fh.deleteProject("Project to test delete");
		System.out.println("Project Deleted: \"Project to test delete\"");
		System.out.println("Please check if the file exist. It should not exist");
		System.out.println ("//**************** End of project test ********************//");
		System.out.println();
	}
	
	public void testFileEventHandler(){
		
		System.out.println ("//***************** Start test event *******************//");
		System.out.println ("Creating new events.....");
		Event event = new Event("Dinner with Tim", "31 aug 2015", "1 sep 2015", "23:00", "02:00", "Prepare car");
		System.out.println ("event1 created");
		Event event2 = new Event("Do coding", "31 aug 2015", "31 aug 2015", "12:00", "22:00", "In java");
		System.out.println ("event2 created");
		Event event3 = new Event("sleep", "31 aug 2015", "31 aug 2015", "03:00", "10:00", "lean on the left");
		System.out.println ("event3 created");
		System.out.println();
		System.out.println ("Adding events into the storage in reverse time sequence");
		fh.saveNewEventHandler(event3);
		fh.saveNewEventHandler(event2);
		fh.saveNewEventHandler(event);
		System.out.println ("Events added");
		System.out.println();
		System.out.println ("Creating the fourth event");
		Event event4 = new Event("Project", "24 aug 2015", "31 aug 2015", "20:00", "02:00", "chiong ah");
		System.out.println();
		System.out.println ("Retrieving events on 31 Aug 2015...");
		ArrayList<Event> eventBook = fh.retrieveEventByDate("31 Aug 2015");
		System.out.println();
		
		System.out.println ("Adding fourth event into event book.");
		System.out.println ("This is to stimulate logic side editing of projects.");
		eventBook.add(event4);
		System.out.println ("Save changes.");
		fh.saveEditedEventHandler("31 aug 2015", eventBook);
		System.out.println();
		
		System.out.println ("Retrieving event on 31 Aug 2015.");
		eventBook = fh.retrieveEventByDate("31 Aug 2015");
		System.out.println("Events on 31 Aug 2015:");
		customPrint(eventBook);
		
		System.out.println("Check if the events are sorted.");
		System.out.println ("//***************** End of test event *******************//");
		System.out.println();
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
