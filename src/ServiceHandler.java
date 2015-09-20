import java.util.ArrayList;


public class ServiceHandler implements ServiceManager{
	private FileHandler eventHandler;
	private FileHandler taskHandler;
	private ArrayList<Event> eventBook = new ArrayList<Event>();
	private ArrayList<Todo> taskBook = new ArrayList<Todo>();

	@Override
	public boolean createEvent(ArrayList<String> eventDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createTask(ArrayList<String> taskDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Event> viewEventByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Todo> viewTaskByDate(String date) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public boolean deleteTask(String taskName) {
		for (Todo task:taskBook){
			if (task.getName().equals(taskName)){
				taskBook.remove(task);
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
	public boolean editEvent(String eventName, int index, String update) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editTask(String taskName, int index, String update) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean viewEvent(String eventName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Event viewSpecificEvent(String eventName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean viewTask(String taskName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Todo viewSpecificTask(String taskName) {
		// TODO Auto-generated method stub
		return null;
	}
}