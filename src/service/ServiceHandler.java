package service;
import helper.CommonHelper;

import java.util.ArrayList;
import java.util.Collections;

import object.Event;
import object.Item;
import object.Todo;
import storage.FileHandler;

public class ServiceHandler implements ServiceManager{
	private FileHandler itemHandler;
	private ArrayList<Item> searchedItems = new ArrayList<Item>();

	public ServiceHandler (){
		itemHandler = new FileHandler();
	}

	/**
	 * Runs the process of creating an Event
	 */
	@Override
	public boolean createEvent(Event newEvent) {
		return itemHandler.saveNewEventHandler(newEvent);
	}

	/**
	 * Runs the process of creating a Todo
	 */
	@Override
	public boolean createTask(Todo newTask) {
		return itemHandler.saveNewTodoHandler(newTask);
	}

	/**
	 * Returns an ArrayList<Event> containing details of the events on the date
	 * Returns an empty ArrayList<Event> if that date has no events 
	 */
	@Override
	public ArrayList<Event> viewEventByDate(String date) {
		return itemHandler.retrieveEventByDate(date);
	}

	/**
	 * Returns an ArrayList<Todo> containing details of the tasks on the date
	 * Returns an empty ArrayList<ToDo> if that date has no events 
	 */
	@Override
	public ArrayList<Todo> viewTaskByDate(String date) {
		return itemHandler.retrieveTodoByDate(date);
	}

	/**
	 * Returns an ArrayList<Todo> containing floating tasks
	 * Returns empty ArrayList<ToDo> if that date has no floating tasks
	 */
	@Override
	public ArrayList<Todo> viewTaskNoDate() {
		return itemHandler.retrieveUniversalTodo();
	}
	
	/**
	 *  Deletes a given object
	 */
	@Override
	public boolean deleteItem(Item item){
		if (item.getClass() == Event.class) {
			return deleteEvent((Event) item);
		}
		else
			return deleteTask((Todo) item);   	
	}

	/**
	 * Checks if the event exist
	 * Returns event when exist, else return null
	 */
	@Override
	public Event viewSpecificEvent (String eventName) {
		return findEvent(eventName);
	}

	/**
	 * Checks if the task exist
	 * Returns Todo when exist, else return null
	 */
	@Override
	public Todo viewSpecificTask(String taskName){
		return findTask(taskName);
	}


	/**
	 * Search for a task or event
	 * Returns ArrayList<Item> if there are matches
	 * Returns null is there are no matches
	 */
	@Override
	public ArrayList<Item> search (String inputs){
		searchedItems.clear();
		int eventIndex = 0;
		int taskIndex = 0;
		ArrayList<Todo> completeTaskBook = itemHandler.retrieveAllTodo();
		ArrayList<Event> completeEventBook = itemHandler.retrieveAllEvents();

		for (Event event:completeEventBook){
			if (event.getName().toLowerCase().contains(inputs.toLowerCase())){
				searchedItems.add(completeEventBook.get(eventIndex));
			}
			eventIndex++;
		}

		for (Todo task:completeTaskBook){
			if (task.getName().toLowerCase().contains(inputs.toLowerCase())){
				searchedItems.add(completeTaskBook.get(taskIndex));
			}
			taskIndex++;
		}
		Collections.sort(searchedItems);

		return searchedItems;
	}

	/**
	 * Deletes an item after searching via the search index
	 */
	@Override
	public boolean deleteItemByIndex(int index) {   	
		return deleteItem(searchedItems.get(index));
	}

	/**
	 * Views an item after searching via the search index
	 */
	@Override
	public Item viewItemByIndex(int index) {   	
		return searchedItems.get(index);
	}
	
	/**
	 * Edits an item after searching via search index
	 */
	public String editItem(Item item, String fieldName, String newInputs ) throws Exception {
		if (item.getClass() == Event.class) {
			return editEvent((Event) item, fieldName, newInputs);
		}
		else
			return editTask((Todo) item, fieldName, newInputs);   	
	}

	/**
	 *  Changes directory
	 */
	@Override
	public boolean changeDirectory(String newDirectory) {
		return itemHandler.changeBaseDirectory(newDirectory);
	}

	// ****************************************Private Methods******************************************************   

	private Event findEvent(String eventName) {
		int eventIndex = 0;
		ArrayList<Event> completeEventBook = itemHandler.retrieveAllEvents();

		for (Event event:completeEventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				assert(eventIndex >= 0);
				return completeEventBook.get(eventIndex);
			}
			else{
				eventIndex++;
			}
		}
		return null;
	}

	private Todo findTask(String taskName) {
		int taskIndex = 0;
		ArrayList<Todo> completeTaskBook = itemHandler.retrieveAllTodo();

		for (Todo task:completeTaskBook){
			if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
				assert(taskIndex >= 0);
				return completeTaskBook.get(taskIndex);
			}
			else {
				taskIndex++; // finding index with same name as taskName passed in
			}
		}
		return null;
	}
	
	private String editEvent(Event _event, String fieldName, String newInputs) throws Exception {
		String oldValue = null;

		switch(fieldName) {
		//case edit eventName
		case (CommonHelper.FIELD_NAME):
			oldValue = _event.getName();
			_event.setName(newInputs);
			itemHandler.saveEditedEventHandler();		
		break;
		
		// case edit start
		case (CommonHelper.FIELD_START): 
			oldValue = _event.getStartDateTimeString();
			_event.updateStart(newInputs);
			itemHandler.saveEditedEventHandler();
		break;
		
		//case edit end
		case (CommonHelper.FIELD_END): 
			oldValue = _event.getEndDateTimeString();
			_event.updateEnd(newInputs);
			itemHandler.saveEditedEventHandler();
		break;
		
		//case edit unexpected
		default :
			assert(false);
		}
		return oldValue;
	}
	
	private String editTask(Todo _task, String fieldName, String newInputs) throws Exception{
		String oldValue = null;

		switch(fieldName) {
		//case edit taskName
		case (CommonHelper.FIELD_NAME):
			oldValue = _task.getName();
			_task.setName(newInputs);
			itemHandler.saveAllEditedTodo();        
		break;
		
		//case edit deadline
		case (CommonHelper.FIELD_DUE):
			oldValue = _task.getDeadlineDateTimeString();
			_task.updateDeadline(newInputs);
			itemHandler.saveAllEditedTodo();
		break;
		
		//case edit unexpected
		default :
			assert(false);
		}
		return oldValue;
	}
	
	private boolean deleteTask(Todo _task) {
		ArrayList<Todo> completeTodoList = itemHandler.retrieveAllTodo();      
			completeTodoList.remove(_task);
			return itemHandler.saveAllEditedTodo();
		}
	
	private boolean deleteEvent(Event _event) {
		ArrayList<Event> completeEventBook = itemHandler.retrieveAllEvents();
			completeEventBook.remove(_event);
			return itemHandler.saveEditedEventHandler();
	}

}