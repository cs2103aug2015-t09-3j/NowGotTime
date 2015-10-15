package service;
import helper.CommonHelper;

import java.util.ArrayList;
import java.util.Collections;

import object.Event;
import object.Item;
import object.Todo;
import storage.FileHandler;


public class ServiceHandler implements ServiceManager{
    private FileHandler eventHandler;
    private FileHandler taskHandler;
   	private ArrayList<Item> searchedItems = new ArrayList<Item>();
    
    public ServiceHandler (){
        eventHandler = new FileHandler();
        taskHandler = new FileHandler();
    }

    /**
     * Runs the process of creating an Event
     */
    @Override
    public boolean createEvent(Event newEvent) {
        return eventHandler.saveNewEventHandler(newEvent);
    }
    
    /**
     * Runs the process of creating a Todo
     */
    @Override
    public boolean createTask(Todo newTask) {
        return taskHandler.saveNewTodoHandler(newTask);
    }
    
    /**
     * Returns an ArrayList<Event> containing details of the events on the date
     * Returns an empty ArrayList<Event> if that date has no events 
     */
    @Override
    public ArrayList<Event> viewEventByDate(String date) {
        return eventHandler.retrieveEventByDate(date);
    }
    
    /**
     * Returns an ArrayList<Todo> containing details of the tasks on the date
	 * Returns an empty ArrayList<ToDo> if that date has no events 
     */
    @Override
    public ArrayList<Todo> viewTaskByDate(String date) {
        return taskHandler.retrieveTodoByDate(date);
    }
    
    /**
     * Returns an ArrayList<Todo> containing floating tasks
	 * Returns empty ArrayList<ToDo> if that date has no floating tasks
     */
    @Override
    public ArrayList<Todo> viewTaskNoDate() {
        return taskHandler.retrieveUniversalTodo();
    }

    /**
     * Returns true when deleted, else return false
     */
    @Override
    public boolean deleteEvent(String eventName) {
        ArrayList<Event> completeEventBook = eventHandler.retrieveAllEvents();
        
        for (Event event:completeEventBook){
            
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
                completeEventBook.remove(event);
                return eventHandler.saveEditedEventHandler();
            }
        }
        return false;
    }

    /**
     * Returns true when deleted, else return false
     */
    @Override
    public boolean deleteTask(String taskName) {

        ArrayList<Todo> completeTodoList = taskHandler.retrieveAllTodo();      
        
        for (Todo task:completeTodoList){
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                completeTodoList.remove(task);
                return taskHandler.saveAllEditedTodo();
            }
        }
        return false;
    }

    /**
     * Edits a field from existing event from the Arraylist<Event>by name
     */
    @Override
    public String editEvent(String eventName, String fieldName, String newInputs) throws Exception {
    	Event _event;
    	String oldValue = null;

    	switch(fieldName) {
        	//case edit eventName
        	case (CommonHelper.FIELD_NAME):
        	    _event = findEvent(eventName);
    
            	if (_event == null) {
            		throw new Exception (String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, eventName));
            	}
            	else {
            	    oldValue = _event.getName();
            		_event.setName(newInputs);
            		eventHandler.saveEditedEventHandler();
            	}
            	break;
        	// case edit start
        	case (CommonHelper.FIELD_START): 
        		_event = findEvent(eventName);
    
            	if (_event == null) {
            		throw new Exception (String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, eventName));
            	}
            	else {
            	    oldValue = _event.getStartDateTimeString();
            		_event.updateStart(newInputs);
            		eventHandler.saveEditedEventHandler();
            	}
            	break;
        	//case edit end
        	case (CommonHelper.FIELD_END): 
        		_event = findEvent(eventName);
    
            	if (_event == null) {
            		throw new Exception (String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, eventName));
            	}
            	else {
                    oldValue = _event.getEndDateTimeString();
            		_event.updateEnd(newInputs);
            		eventHandler.saveEditedEventHandler();
            	}
            	break;
        	//case edit unexpected
        	default :
        	    assert(false);
    	}
    	return oldValue;
    }

    /**
     *  Edits a field from existing task from the Arraylist<ToDo> by name.
     */
    @Override
    public String editTask(String taskName, String fieldName, String newInputs) throws Exception{

    	Todo _task;
    	String oldValue = null;
    	
    	switch(fieldName) {
        	//case edit taskName
        	case (CommonHelper.FIELD_NAME):
            	_task = findTask(taskName);
        
            	if (_task == null) {
            		throw new Exception (String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, taskName));
            	}
            	else {
            	    oldValue = _task.getName();
            		_task.setName(newInputs);
            		taskHandler.saveAllEditedTodo();
            	}        
            	break;
        	//case edit deadline
        	case (CommonHelper.FIELD_DUE):
            	_task = findTask(taskName);
        
            	if (_task == null) {
            		throw new Exception (String.format(CommonHelper.ERROR_ITEM_NOT_FOUND, taskName));
            	}
            	else {
            	    oldValue = _task.getDeadlineDateTimeString();
            		_task.updateDeadline(newInputs);
            		taskHandler.saveAllEditedTodo();
            	}
            	break;
        	//case edit unexpected
        	default :
        	    assert(false);
    	}
    	return oldValue;
    }
    
    /**
     * Checks if the event exist
	 * Returns true when exist, else return false
     */
    @Override
    public Event viewSpecificEvent (String eventName) {
        ArrayList<Event> completeEventBook = eventHandler.retrieveAllEvents();
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
    
    /**
     * Checks if the task exist
	 * Returns true when exist, else return false
     */
    @Override
    public Todo viewSpecificTask(String taskName){
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveAllTodo();
        int taskIndex = 0;
        for (Todo task:completeTaskBook){
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
    	ArrayList<Todo> completeTaskBook = taskHandler.retrieveAllTodo();
    	ArrayList<Event> completeEventBook = eventHandler.retrieveAllEvents();
    	
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
    public Item deleteItem(int index) {
    	return searchedItems.remove(index);
    }
    

    private Event findEvent(String eventName) {
    	int eventIndex = 0;
    	ArrayList<Event> completeEventBook = eventHandler.retrieveAllEvents();
    	
    	for (Event event:completeEventBook){
            if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
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
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveAllTodo();
        
        for (Todo task:completeTaskBook){
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                return completeTaskBook.get(taskIndex);
            }
            else {
                taskIndex++; // finding index with same name as taskName passed in
            }
        }
            return null;
    }  
}