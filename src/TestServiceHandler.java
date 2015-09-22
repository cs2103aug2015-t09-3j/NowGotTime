public class TestServiceHandler {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ServiceHandler test = new ServiceHandler();
        //Event testEvent = new Event("study", "12 mar 2020", "08:00 ", "18:00", "");
        Event testEvent1 = new Event("study 2020", "12 mar 2020", "18:00 ", "19:00", "");
        
        //System.out.println(test.createEvent(testEvent)); 
        System.out.println(test.createEvent(testEvent1));
        System.out.println(test.viewEventByDate("12 mar 2020"));
        //System.out.println(test.deleteEvent("study"));
        System.out.println(test.editEventName("study 2020", "play"));
        System.out.println(test.editEventStartDate("play", "13 mar 2020"));
        System.out.println(test.editEventStartTime("play", "09:00"));
        System.out.println(test.editEventEndTime("play", "09:01"));
        System.out.println(test.viewEventByDate("12 mar 2020"));
        System.out.println(test.viewEventByDate("13 mar 2020"));
        
/*      System.out.println(test.createTask(testTask1));
        System.out.println(test.viewTaskNoDate("12 March 2020"));
        System.out.println(test.deleteTaskWithoutDeadline("study"));
        */
    }

}