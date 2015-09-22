import java.util.ArrayList;


public class ServiceHandler implements ServiceManager{
	private FileHandler eventHandler;
	private FileHandler taskHandler;
	private ArrayList<Event> eventBook = new ArrayList<Event>();
	private ArrayList<Todo> taskBookWithDeadline = new ArrayList<Todo>();
	private ArrayList<Todo> taskBookNoDeadline = new ArrayList<Todo>();
	
	public ServiceHandler (){
		eventHandler = new FileHandler();
		taskHandler = new FileHandler();
	}

	@Override
	public boolean createEvent(Event newEvent) {
		return eventHandler.saveNewEventHandler(newEvent);
	}

	@Override
	public boolean createTask(Todo newTask) {
		return taskHandler.saveNewTodoHandler(newTask);
	}

	@Override
	public ArrayList<Event> viewEventByDate(String date) {
		return eventBook = eventHandler.retrieveEventByDate(date);
	}

	@Override
	public ArrayList<Todo> viewTaskByDate(String date) {
		return taskBookWithDeadline = taskHandler.retrieveTodoByDate(date);
	}

	@Override
	public ArrayList<Todo> viewTaskNoDate(String date) {
		return taskBookNoDeadline = taskHandler.retrieveUniversalTodo();
	}

	@Override
	public boolean deleteEvent(String eventName) {
		ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
		
		for (Event event:completeEventBook){
			
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				completeEventBook.remove(event);
				eventHandler.saveEditedEventHandler();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteEvent(int eventIndex) {	//this is the by index way, for now don't do.
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTaskWithDeadline(String taskName) {

		ArrayList<Todo> completeTodoList = taskHandler.retrieveTodoToDelete();		
		
		for (Todo task:completeTodoList){
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				completeTodoList.remove(task);
				taskHandler.saveEditedTodoHandler();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteTaskWithoutDeadline(String taskName) {
		for (Todo task:taskBookNoDeadline){
			if (task.getName().equals(taskName)){
				taskBookNoDeadline.remove(task);
				return taskHandler.saveEditedTodoHandler();
			}
		}
		return false;
	}

	@Override
	public boolean deleteTask(int taskIndex) {		//this is the by index way, for now don't do.
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editEventName(String eventName, String newEventName) {
		int eventIndex = 0;
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				Event _event = eventBook.get(eventIndex);
				_event.setName(newEventName);
				eventHandler.saveEditedEventHandler(); 
				return true;
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
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				Event _event = eventBook.get(eventIndex);
				_event.updateStartDate(newStartDate);
				eventHandler.saveEditedEventHandler(); 
				return true;
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
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				Event _event = eventBook.get(eventIndex);
				_event.updateEndDate(newEndDate);
				eventHandler.saveEditedEventHandler(); 
				return true;
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
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				Event _event = eventBook.get(eventIndex);
				_event.updateStartTime(newStartTime);
				eventHandler.saveEditedEventHandler(); 
				return true;
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
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				Event _event = eventBook.get(eventIndex);
				_event.updateEndTime(newEndTime);
				eventHandler.saveEditedEventHandler(); 
				return true;
			}
			else {
				eventIndex++; // finding index with same name as eventName passed in
			}
		}
			return false;
	}
	
	@Override
	public boolean editTaskNameWithDeadline(String taskName, String newTaskName) {
		int taskIndex = 0;
		for (Todo task:taskBookWithDeadline){
			if (task.getName().equals(taskName)){
				Todo _task = taskBookWithDeadline.get(taskIndex);
				_task.setName(newTaskName);
				taskHandler.saveEditedTodoHandler(); 
				return true;
			}
			else {
				taskIndex++; // finding index with same name as taskName passed in
			}
		}
			return false;
	}
	
	@Override
	public boolean editTaskDeadlineWithDeadline(String taskName, String newDeadline) {
		int taskIndex = 0;
		for (Todo task:taskBookWithDeadline){
			if (task.getName().equals(taskName)){
				Todo _task = taskBookWithDeadline.get(taskIndex);
				_task.updateDeadlineDate(newDeadline);
				taskHandler.saveEditedTodoHandler(); 
				return true;
			}
			else {
				taskIndex++; // finding index with same name as taskName passed in
			}
		}
			return false;
	}
	
	@Override
	public boolean editTaskTimeWithDeadline(String taskName, String newTime) {
		int taskIndex = 0;
		for (Todo task:taskBookWithDeadline){
			if (task.getName().equals(taskName)){
				Todo _task = taskBookWithDeadline.get(taskIndex);
				_task.updateDeadlineTime(newTime);
				taskHandler.saveEditedTodoHandler(); 
				return true;
			}
			else {
				taskIndex++; // finding index with same name as taskName passed in
			}
		}
			return false;
	}
	
	@Override
	public boolean editTaskNameWithoutDeadline(String taskName, String newTaskName) {
		int taskIndex = 0;
		for (Todo task:taskBookNoDeadline){
			if (task.getName().equals(taskName)){
				Todo _task = taskBookNoDeadline.get(taskIndex);
				_task.setName(newTaskName);
				//@rx needs new method with no date
				//taskHandler.saveEditedTodoHandler(taskBookNoDeadline); 
				return true;
			}
			else {
				taskIndex++; // finding index with same name as taskName passed in
			}
		}
			return false;
	}
	
	@Override
	public Event viewSpecificEvent (String eventName) {
		int eventIndex = 0;
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				Event _event = eventBook.get(eventIndex);
				return _event;
			}
			else {
				eventIndex++;
			}
	}
		return null; //@Stef returns null if no task found with same name as eventName passed in
	}
	
	@Override
	public Todo viewSpecificTaskWithDeadline(String taskName) {
		int taskIndex = 0;
		for (Todo task:taskBookWithDeadline){
			if (task.getName().equals(taskName)){
				Todo _task = taskBookWithDeadline.get(taskIndex);
				return _task;
			}
			else{
				taskIndex++;
			}
		}
		return null; //@Stef returns null if no task found with same name as taskName passed in
	}


	@Override
	public Todo viewSpecificTaskWithoutDeadline(String taskName){
		int taskIndex = 0;
		for (Todo task:taskBookNoDeadline){
			if (task.getName().equals(taskName)){
				Todo _task = taskBookNoDeadline.get(taskIndex);
				return _task;
			}
			else {
				taskIndex++;
			}
		}
		return null; //@Stef returns null if no task found with same name as taskName passed in
	}
}