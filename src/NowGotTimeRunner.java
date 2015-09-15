import java.text.SimpleDateFormat;
import java.util.Date;

public class NowGotTimeRunner {
    
    
    public static void testToDO() {
        Todo todo1 = new Todo("sleep", "tonight", "20 August 2015", "09:15");
        System.out.println(todo1.getDeadlineDate());
        System.out.println(todo1.getDeadlineTime());
        
        Todo todo2 = new Todo("sleep", "tonight", "20 August 2015");
        System.out.println(todo2.getDeadlineDate());
        System.out.println(todo2.getDeadlineTime());

        Todo todo3 = new Todo("sleep", "tonight", "20 August 2015", "13:27");
        System.out.println(todo3.getDeadlineDate());
        System.out.println(todo3.getDeadlineTime());
        

        Todo todo4 = new Todo("sleep", "tonight");
        System.out.println(todo4.getDeadlineDate());
        System.out.println(todo4.getDeadlineTime());
    }
    
    public static void testEvent() {
        Event event = new Event("Name", "20 August 2015", "22 August 2015", "10:12", "15:12", "Additional information");
        System.out.println(event);
    }
    
    public static void main(String[] args) {
        testToDO();
        testEvent();
    }
}
