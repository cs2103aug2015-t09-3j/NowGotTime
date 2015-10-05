import java.util.ArrayList;


public class ServiceHandler implements ServiceManager{
    private FileHandler eventHandler;
    private FileHandler taskHandler;
    
    public ServiceHandler (){
        eventHandler = new FileHandler();
        taskHandler = new FileHandler();
    }

    @Override
    public boolean createEvent(Event newEvent) {
        if (viewSpecificEvent(newEvent.getName()) != null) return false;
        return eventHandler.saveNewEventHandler(newEvent);
    }

    @Override
    public boolean createTask(Todo newTask) {
        if (viewSpecificTask(newTask.getName()) != null) return false;
        return taskHandler.saveNewTodoHandler(newTask);
    }

    @Override
    public ArrayList<Event> viewEventByDate(String date) {
        return eventHandler.retrieveEventByDate(date);
    }

    @Override
    public ArrayList<Todo> viewTaskByDate(String date) {
        return taskHandler.retrieveTodoByDate(date);
    }

    @Override
    public ArrayList<Todo> viewTaskNoDate() {
        return taskHandler.retrieveUniversalTodo();
    }

    @Override
    public boolean deleteEvent(String eventName) {
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        
        for (Event event:completeEventBook){
            
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                completeEventBook.remove(event);
                return eventHandler.saveEditedEventHandler();
            }
        }
        return false;
    }

    @Override
    public boolean deleteEvent(int eventIndex) {    //this is the by index way, for now don't do.
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteTask(String taskName) {

        ArrayList<Todo> completeTodoList = taskHandler.retrieveTodoToDelete();      
        
        for (Todo task:completeTodoList){
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                completeTodoList.remove(task);
                return taskHandler.saveEditedTodoHandler();
            }
        }
        return false;
    }

    @Override
    public boolean deleteTask(int taskIndex) {      //this is the by index way, for now don't do.
        // TODO Auto-generated method stub
        return false;
    }

    
    @Override
    public boolean editEventName(String eventName, String newEventName) {
        if (!eventName.equals(newEventName) && viewSpecificEvent(newEventName) != null) return false;
        int eventIndex = 0;
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                Event _event = completeEventBook.get(eventIndex);
                _event.setName(newEventName);
                return eventHandler.saveEditedEventHandler();
            }
            else {
                eventIndex++; // finding index with same name as eventName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editEventStartDate(String eventName, String newStartDate) {
        int eventIndex = 0;
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                Event _event = completeEventBook.get(eventIndex);
                _event.updateStartDate(newStartDate);
                return eventHandler.saveEditedEventHandler(); 
            }
            else {
                eventIndex++; // finding index with same name as eventName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editEventEndDate(String eventName, String newEndDate) {
        int eventIndex = 0;
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                Event _event = completeEventBook.get(eventIndex);
                _event.updateEndDate(newEndDate);
                return eventHandler.saveEditedEventHandler(); 
            }
            else {
                eventIndex++; // finding index with same name as eventName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editEventStartTime(String eventName, String newStartTime) {
        int eventIndex = 0;
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                Event _event = completeEventBook.get(eventIndex);
                _event.updateStartTime(newStartTime);
                return eventHandler.saveEditedEventHandler(); 
            }
            else {
                eventIndex++; // finding index with same name as eventName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editEventEndTime(String eventName, String newEndTime) {
        int eventIndex = 0;
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                Event _event = completeEventBook.get(eventIndex);
                _event.updateEndTime(newEndTime);
                return eventHandler.saveEditedEventHandler(); 
            }
            else {
                eventIndex++; // finding index with same name as eventName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editEventStartDateTime(String eventName, String newStartDateTime){
        int eventIndex = 0;
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                Event _event = completeEventBook.get(eventIndex);
                _event.updateStartDateTime(newStartDateTime);
                return eventHandler.saveEditedEventHandler(); 
            }
            else {
                eventIndex++; // finding index with same name as eventName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editEventEndDateTime(String eventName, String newEndDateTime){
        int eventIndex = 0;
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                Event _event = completeEventBook.get(eventIndex);
                _event.updateEndDateTime(newEndDateTime);
                return eventHandler.saveEditedEventHandler(); 
            }
            else {
                eventIndex++; // finding index with same name as eventName passed in
            }
        }
            return false;
    }
    
    
    @Override
    public boolean editTaskName(String taskName, String newTaskName) {
        if (!taskName.equals(newTaskName) && viewSpecificTask(newTaskName) != null) return false;
        int taskIndex = 0;
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveTodoToDelete();
        for (Todo task:completeTaskBook){ //finding task with deadline
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                Todo _task = completeTaskBook.get(taskIndex);
                _task.setName(newTaskName);
                return taskHandler.saveEditedTodoHandler(); 
            }
            else {
                taskIndex++; // finding index with same name as taskName passed in
            }
        }
        return false; //task not found
    }
    
    @Override
    public boolean editTaskDeadlineDate(String taskName, String newDeadlineDate) {
        int taskIndex = 0;
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveTodoToDelete();
        for (Todo task:completeTaskBook){
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                Todo _task = completeTaskBook.get(taskIndex);
                _task.updateDeadlineDate(newDeadlineDate);
                return taskHandler.saveEditedTodoHandler(); 
            }
            else {
                taskIndex++; // finding index with same name as taskName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editTaskDeadlineTime(String taskName, String newDeadlineTime) {
        int taskIndex = 0;
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveTodoToDelete();
        for (Todo task:completeTaskBook){
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                Todo _task = completeTaskBook.get(taskIndex);
                _task.updateDeadlineTime(newDeadlineTime);
                return taskHandler.saveEditedTodoHandler(); 
            }
            else {
                taskIndex++; // finding index with same name as taskName passed in
            }
        }
            return false;
    }
    
    @Override
    public boolean editTaskDeadlineDateTime(String taskName, String newDeadlineDateTime){
        int taskIndex = 0;
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveTodoToDelete();
        for (Todo task:completeTaskBook){
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                Todo _task = completeTaskBook.get(taskIndex);
                _task.updateDeadlineDateTime(newDeadlineDateTime);
                return taskHandler.saveEditedTodoHandler(); 
            }
            else {
                taskIndex++; // finding index with same name as taskName passed in
            }
        }
            return false;
    }

    
    @Override
    public Event viewSpecificEvent (String eventName) {
        ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
        int eventIndex = 0;
        for (Event event:completeEventBook){
            if (event.getName().equals(eventName)){
                Event _event = completeEventBook.get(eventIndex);
                return _event;
            }
            else {
                eventIndex++;
            }
    }
        return null; //@Stef returns null if no task found with same name as eventName passed in
    }
    
    public Todo viewSpecificTask(String taskName){
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveTodoToDelete();
        int taskIndex = 0;
        for (Todo task:completeTaskBook){ //checking taskbook with deadline
            if (task.getName().equals(taskName)){
                Todo _task = completeTaskBook.get(taskIndex);
                return _task;
            }
            else{
                taskIndex++;
            }
        }
        return null; //@Stef returns null if no task found with same name as taskName passed in
    }
    
}
