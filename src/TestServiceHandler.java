import java.util.ArrayList;

public class TestServiceHandler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ServiceHandler test = new ServiceHandler();
		ArrayList<String> testEvent = new ArrayList<String>();
		testEvent.add("study");
		testEvent.add("12 March 2020");
		testEvent.add("0800");
		testEvent.add("1800");
		testEvent.add("no lunch required");
		
		ArrayList<String> testTask1 = new ArrayList<String>();
		testTask1.add("study");
		testTask1.add("no lunch required");
		
		ArrayList<String> testTask2 = new ArrayList<String>();
		testTask2.add("study");
		testTask2.add("no lunch required");
		testTask2.add("12 March 2020");
		
		ArrayList<String> testTask3 = new ArrayList<String>();
		testTask3.add("study");
		testTask3.add("no lunch required");
		testTask3.add("12 March 2020");
		testTask3.add("1800");
		
		System.out.println(test.createEvent(testEvent));
		System.out.println(test.viewEventByDate("12 March 2020"));
		System.out.println(test.deleteEvent("study"));
		
		System.out.println(test.createTask(testTask1));
		System.out.println(test.viewTaskNoDate("12 March 2020"));
		System.out.println(test.deleteTaskWithoutDeadline("study"));
		
		System.out.println(test.createTask(testTask2));
		System.out.println(test.createTask(testTask3));
	}

}