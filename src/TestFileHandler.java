import java.util.ArrayList;

/**
 * 
 * @author RX.huang
 * This class will test the operations in FileHandler Class
 * You may report bugs under the not solves section
 * 
 * After running, do refresh the NowGotTime package. You should be able to see
 * the folders and files created.
 * ****************************
 * Test cases to noted:       *
 * ****************************
 * 		1) cannot add date of the past.
 * 		2) cannot edit events of the past.
 * 		3) 
 * 
 * 
 * 
 * ****************************
 * Test cases solved:         *
 * ****************************
 * 	Events: 
 * 		1) Spill over days and months for .saveNewEventHandler(event)
 * 		2) Spill over days and months for edited events
 * 		3) 
 * 
 * 	Projects:
 * 		-
 * ****************************
 * 	Test cases not solved:	  *
 * ****************************
 * 	Events:
 * 		1) Spill over days and months for edited events
 * 		2) Empty string name
 * 		3) delete 
 * 	Projects:
 * 		-
 * 
 **/


public class TestFileHandler {
	
	private FileHandler fh = new FileHandler();
	private static TestFileHandler testo = new TestFileHandler();
	
	public static void main(String[] args){
		
		clearAllFiles(testo);
		kickStart();

		testo.testFileEventHandler();
		testo.testFileTodoHandler();
		testo.testFileProjectHandler();
		
		testo.changeDirect();
	}
	
	private void changeDirect(){
		fh.changeBaseDirectory("C:\\Users\\RX.huang\\Desktop");
	}

	private static void kickStart() {
		testo.fh = new FileHandler();
	}

	public void testFileTodoHandler(){

		System.out.println("This is to test the TodoHandler:");
		System.out.println("Test2: adding and saving todo:");
		System.out.println();
		
		System.out.println("create todo1: Name, additional info --> basic todo type.");
		System.out.println("Create todo2: Name, additional info, date --> partial todo type.");
		System.out.println("Create todo3: Name, additional Info, date, time --> complete todo type");
		System.out.println("Create todo3: Name, additional Info, date, time --> complete todo type");
		Todo todo1 = new Todo("TestName", "This is extra");
		Todo todo2 = new Todo("This is shag", "I don't know what I am doing", "20 oct 2015");
		Todo todo3 = new Todo("What is shit", "It is what I eat", "20 oct 2015", "10:00");
		Todo todo4 = new Todo("Watch Pony On Red Nails", "Best one", "20 oct 2015", "07:30");
		
		fh.saveNewTodoHandler(todo1);
		fh.saveNewTodoHandler(todo2);
		fh.saveNewTodoHandler(todo3);
		fh.saveNewTodoHandler(todo4);
		System.out.println("Added todo1 as a new Todo (floating todo).");
		System.out.println("Added todo2, todo3, todo4 as new Todo(s).");		
		
		System.out.println("Retrieving to-do-list on 20 Oct 2015...");
		ArrayList<Todo> taskBook = fh.retrieveTodoByDate("20 Oct 2015");
		System.out.println("To-do-list for 20 Oct 2015:");
		customPrinting(taskBook);
		
		System.out.println ("//************************** End **************************//");
	
		System.out.println("Test2: saving edited todo:");
		System.out.println();
		
		System.out.println("After retrieved the arraylist<todo>, we edit addInfo: \"Hope this will work.\".");
		taskBook.get(0).setAdditionalInfo("Hope this will work.");
		fh.saveEditedTodoHandler();
		System.out.println("Saved whole todo arraylist with date into storage");
		System.out.println();
		
		System.out.println("Retrieving to-do-list on 20 Oct 2015...");
		taskBook = fh.retrieveTodoByDate("20 Oct 2015");
		System.out.println("To-do-list for 20 Oct 2015:");
		customPrinting(taskBook);
		
		System.out.println ("//************************** End **************************//");
		
		System.out.println("Test3: Retrieval and deletion of floating todo:");
		System.out.println();
		
		System.out.println("Add in todo5 and todo6 as floating tasks");
		Todo todo5 = new Todo("Watch this is hat", "nothing");
		Todo todo6 = new Todo("not shown", "to delete");
		fh.saveNewTodoHandler(todo5);
		fh.saveNewTodoHandler(todo6);
		System.out.println("Saved todo5 and todo6 as floating tasks");
		
		System.out.println("Retrieving floating tasks to delete...");
		System.out.println("Delete todo6.");
		System.out.println("Save deletion");
		taskBook = fh.retrieveUniversalTodo();
		taskBook.remove(2);
		fh.saveEditedTodoHandler();
		
		System.out.println("Retrieving floating tasks to view...");
		taskBook = fh.retrieveUniversalTodo();
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
		
		System.out.println("Creating events to put into project.");
		Event event1 = new Event("Brainstorming", "10 mar 2015", "11 mar 2015", "09:00", "15:00", "Brainstorm features");
		Event event2 = new Event("Split work", "14 mar 2015", "14 mar 2015", "09:00", "10:00", "I will do storage handling");
		Event event3 = new Event("Project meeting", "20 mar 2015", "21 mar 2015", "20:00", "03:00", "Meet up for code review.");
		System.out.println();
		
		System.out.println("Retrieving projecting: \"Software development\"");
		System.out.println("Adding events into project");
		System.out.println("Saving project.");
		ArrayList<Event> book = fh.retrieveProjectTimeLine("Software development");
		book.add(event3);
		book.add(event1);
		book.add(event2);
		fh.saveEditedProjectDetails(book, "Software development");
		System.out.println();
		
		System.out.println("Retrieving projecting: \"Software development\"");
		System.out.println("Events in \"Software development\" project:");
		ArrayList<Event> projectBook = fh.retrieveProjectTimeLine("Software development");
		for(Event event: projectBook){
			System.out.println(event);
			System.out.println();
		}
		
		System.out.println ("//************************** End **************************//");
		
		System.out.println ("Test delete project: ");
		System.out.println("Create new project: \"Project to test delete\"");
		System.out.println("Delete the project: \"Project to test delete\"");
		
		fh.createNewProject("Project to test delete");
		fh.deleteProject("Project to test delete");
		
		System.out.println("Project Deleted: \"Project to test delete\"");
		System.out.println("Please check if the file exist. It should not exist");
		
		System.out.println ("//**************** End of project test ********************//");
		System.out.println();
	}
	
	public void testFileEventHandler(){
		
		System.out.println ("//***************** Start test event *******************//");
		System.out.println ("Creating new events.....");
		Event event = new Event("Dinner with Tim", "31 aug 2016", "1 sep 2016", "23:00", "02:00", "Prepare car");
		System.out.println ("event1 created");
		Event event2 = new Event("Do coding", "31 aug 2016", "31 aug 2016", "12:00", "22:00", "In java");
		System.out.println ("event2 created");
		Event event3 = new Event("sleep", "31 aug 2016", "31 aug 2016", "03:00", "10:00", "lean on the left");
		System.out.println ("event3 created");
		Event event4 = new Event("Project", "24 aug 2016", "31 aug 2016", "20:00", "02:00", "chiong ah");
		System.out.println ("event4 created");
		System.out.println();
		System.out.println ("Adding events into the storage in reverse time sequence");
		fh.saveNewEventHandler(event4);
		fh.saveNewEventHandler(event3);
		fh.saveNewEventHandler(event2);
		fh.saveNewEventHandler(event);
		System.out.println ("Events added");
		System.out.println();
		
		System.out.println ("Retrieving events on 31 Aug 2016...");
		ArrayList<Event> eventBook = fh.retrieveEventByDate("31 Aug 2016");
		System.out.println();
		
		System.out.println ("Change event4 additional details.");
		System.out.println ("This is to stimulate logic side editing of projects.");
		eventBook.get(3).setAdditionalInfo("I love coding.");
		eventBook.get(2).setAdditionalInfo("Eat like pig.");
		
		System.out.println ("Save changes.");
		fh.saveEditedEventHandler();
		System.out.println();
		
		System.out.println ("Retrieving event on 31 Aug 2016.");
		eventBook = fh.retrieveEventByDate("31 Aug 2016");
		System.out.println("Events on 31 Aug 2016:");
		customPrint(eventBook);
		
		System.out.println("Check if the events are sorted.");
		System.out.println();
		System.out.println("Test Delete.");
		eventBook = fh.retrieveEventsToDelete();
		eventBook.remove(0);
		fh.saveEditedEventHandler();
		System.out.println("Event deleted and saved");
		System.out.println();
		
		System.out.println ("Retrieving event on 31 Aug 2016.");
		eventBook = fh.retrieveEventByDate("31 Aug 2016");
		System.out.println("Events on 31 Aug 2016:");
		customPrint(eventBook);
		
		System.out.println ("//***************** End of test event *******************//");
		System.out.println();

	}
	
	private static void clearAllFiles(TestFileHandler testo) {
		System.out.println("Cleaning all data before use...");
		testo.fh.clearAll();
		System.out.println("All data in storage wiped.");
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
