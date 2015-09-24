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
	public ArrayList<Todo> viewTaskNoDate() {
		return taskBookNoDeadline = taskHandler.retrieveUniversalTodo();
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
	public boolean deleteEvent(int eventIndex) {	//this is the by index way, for now don't do.
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTask(String taskName) {

		ArrayList<Todo> completeTodoList = taskHandler.retrieveTodoToDelete();		
		
		for (Todo task:completeTodoList){ // checking task with deadline
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				completeTodoList.remove(task);
				return taskHandler.saveEditedTodoHandler();
			}
		}
		for (Todo task:taskBookNoDeadline){
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){ //checking task without deadline
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
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				Event _event = eventBook.get(eventIndex);
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
		for (Event event:eventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				Event _event = eventBook.get(eventIndex);
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
		for (Event event:eventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				Event _event = eventBook.get(eventIndex);
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
		for (Event event:eventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				Event _event = eventBook.get(eventIndex);
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
		for (Event event:eventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				Event _event = eventBook.get(eventIndex);
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
		for (Event event:eventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				Event _event = eventBook.get(eventIndex);
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
		for (Event event:eventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				Event _event = eventBook.get(eventIndex);
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
		int taskIndex = 0;
		for (Todo task:taskBookWithDeadline){ //finding task with deadline
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				Todo _task = taskBookWithDeadline.get(taskIndex);
				_task.setName(newTaskName);
				return taskHandler.saveEditedTodoHandler(); 
			}
			else {
				taskIndex++; // finding index with same name as taskName passed in
			}
		}
		taskIndex = 0;
		for (Todo task:taskBookNoDeadline){ //finding task without deadline
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				Todo _task = taskBookNoDeadline.get(taskIndex);
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
		for (Todo task:taskBookWithDeadline){
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				Todo _task = taskBookWithDeadline.get(taskIndex);
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
		for (Todo task:taskBookWithDeadline){
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				Todo _task = taskBookWithDeadline.get(taskIndex);
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
		for (Todo task:taskBookWithDeadline){
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				Todo _task = taskBookWithDeadline.get(taskIndex);
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
	
	public Todo viewSpecificTask(String taskName){
		int taskIndex = 0;
		for (Todo task:taskBookWithDeadline){ //checking taskbook with deadline
			if (task.getName().equals(taskName)){
				Todo _task = taskBookWithDeadline.get(taskIndex);
				return _task;
			}
			else{
				taskIndex++;
			}
		}
		taskIndex = 0;
		for (Todo task:taskBookNoDeadline){ //checking taskbook without deadline
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
	
/*	@Override
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
*/
}
