
public class TestEvent {
	
	public static void main(String[] args){
//		Event event = new Event("Name", "20 August 2015", "22 August 2015", "1012", "1012", "Additional information");
//		System.out.println(event);
		
		Todo todo = new Todo("name", "This is one test");
		System.out.println(todo);
		System.out.println();
		todo = new Todo("name", "This is one test", "20 August 2010");
		System.out.println(todo);
		System.out.println();
		todo = new Todo("name", "This is one test", "20 August 2010", "10:00");
		System.out.println(todo);
	}		
}

