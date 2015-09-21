import java.util.ArrayList;


public class ServiceHandler implements ServiceManager{
	private FileHandler eventHandler;
	private FileHandler taskHandler;
	private ArrayList<Event> eventBook = new ArrayList<Event>();
	private ArrayList<Todo> taskBook = new ArrayList<Todo>();
	
	public ServiceHandler (){
		eventHandler = new FileHandler();
		taskHandler = new FileHandler();
	}

	@Override
	public boolean createEvent(ArrayList<String> eventDetails) {
		Event newEvent = new Event(eventDetails.get(0), eventDetails.get(1),eventDetails.get(2),eventDetails.get(3),eventDetails.get(4));
		return eventHandler.saveNewEventHandler(newEvent);
	}

	@Override
	public boolean createTask(ArrayList<String> taskDetails) {
		int determineTodoType = taskDetails.size();
		switch(determineTodoType)
		{
		case (2):
		{
			Todo newTask = new Todo(taskDetails.get(0), taskDetails.get(1));
			return taskHandler.saveNewTodoHandler(newTask);
		}
		case (3):
		{
			Todo newTask = new Todo(taskDetails.get(0), taskDetails.get(1), taskDetails.get(2));
			return taskHandler.saveNewTodoHandler(newTask);
		}
		case (4):{
			Todo newTask = new Todo(taskDetails.get(0), taskDetails.get(1), taskDetails.get(2), taskDetails.get(3));
			return taskHandler.saveNewTodoHandler(newTask);
		}
		default:{
			return false;
		}
		}
	}

	@Override
	public ArrayList<Event> viewEventByDate(String date) {
		return eventBook = eventHandler.retrieveEventByDate(date);
	}

	@Override
	public ArrayList<Todo> viewTaskByDate(String date) {
		return taskBook = taskHandler.retrieveTodoByDate(date);
	}

	@Override
	public ArrayList<Todo> viewTaskNoDate(String date) {
		return taskBook = taskHandler.retrieveUniversalTodo(date);
	}

	@Override
	public boolean deleteEvent(String eventName) {
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				eventBook.remove(event);
				eventHandler.saveEditedEventHandler(event.getStartDateString() ,eventBook);
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

	@Override // needs discussion,  shld i make 1 delete task only? compare to editTask
	public boolean deleteTaskWithDeadline(String taskName) {
		for (Todo task:taskBook){
			if (task.getName().equals(taskName)){
				taskBook.remove(task);
				taskHandler.saveEditedTodoHandler(task.getDeadlineDateString(), taskBook);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteTaskWithoutDeadline(String taskName) {
		for (Todo task:taskBook){
			if (task.getName().equals(taskName)){
				taskBook.remove(task);
				taskHandler.saveEditedTodoHandler(null, taskBook);   //rx, saveEditedTodoHandler needs 1 with no date?
				return true;
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
	public boolean editEvent(String eventName, int infoIndex, String newValue) {
		int eventIndex = 0;
		for (Event event:eventBook){
			if (event.getName().equals(eventName)){
				Event _event = eventBook.get(eventIndex);
				switch(infoIndex)
				{
				case (1):
				{
					_event.setName(newValue);
					break;
				}

				case (2):
				{
					_event.updateStartDate(newValue);
					break;
				}

				case (3):
				{
					_event.updateEndDate(newValue);
					break;
				}

				case (4):
				{
					_event.updateStartTime(newValue);
					break;
				}

				case (5):
				{
					_event.updateEndTime(newValue);
					break;
				}

				case (6):
				{
					_event.setAdditionalInfo(newValue);
					break;
				}
				}

				eventHandler.saveEditedEventHandler(_event.getStartDateString() ,eventBook); 
				return true;
			}
			else {
				eventIndex++;
			}
		}
		return false;
	}

	@Override // needs discussion, shld i make a edit task without deadline? compare to deleteTask
	public boolean editTask(String taskName, int index, String newValue) {
		int taskIndex = 0;
		for (Todo task:taskBook){
			if (task.getName().equals(taskName)){
				Todo _task = taskBook.get(taskIndex);
				switch(index)
				{
				case (1):
				{
					_task.setAdditionalInfo(newValue);
					break;
				}

				case (2):
				{
					_task.updateDeadlineDate(newValue);
					break;
				}

				case (3):
				{
					_task.updateDeadlineTime(newValue);
					break;
				}
				}
				taskHandler.saveEditedTodoHandler(_task.getDeadlineDateString(), taskBook); //possible to make date return null if its a task with no deadline?
				return true;
			}
			else{
				taskIndex++;
			}
		}
		return false;
	}

	@Override
	public boolean viewEvent(String date) {
		if (eventBook.contains(eventHandler.retrieveEventByDate(date)))
		{
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Event viewSpecificEvent(String eventName, String date) {
		int eventIndex = 0;
		if (viewEvent(date)==true){
			eventBook = eventHandler.retrieveEventByDate(date);
			for (Event event:eventBook){
				if (event.getName().equals(eventName)){
					Event _event = eventBook.get(eventIndex);
					return _event;
				}
				eventIndex++;
			}
		}
		return null;
	}

	@Override
	public boolean viewTask(String date) {
		if (taskBook.contains(taskHandler.retrieveTodoByDate(date)))
		{
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Todo viewSpecificTask(String taskName, String date) {
		int taskIndex = 0;
		if (viewTask(date)==true) {
			taskBook = taskHandler.retrieveTodoByDate(date);
			for (Todo task:taskBook){
				if (task.getName().equals(taskName)){
					Todo _task = taskBook.get(taskIndex);
					return _task;
				}
				taskIndex++;
			}
		}
		if (date == null) {
			taskBook = taskHandler.retrieveUniversalTodo(date);  // same problem as viewTaskWithoutDeadlines, why date needed?
			for (Todo task:taskBook){
				if (task.getName().equals(taskName)){
					Todo _task = taskBook.get(taskIndex);
					return _task;
				}
				taskIndex++;
			}

		}
		return null;
	}
}