package service;
import helper.CommonHelper;

import java.util.ArrayList;

import object.Event;
import object.Todo;
import storage.FileHandler;


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
    public boolean deleteTask(String taskName) {

        ArrayList<Todo> completeTodoList = taskHandler.retrieveTodoToDelete();      
        
        for (Todo task:completeTodoList){
            if (task.getName().toLowerCase().equals(taskName.toLowerCase())){
                completeTodoList.remove(task);
                return taskHandler.saveAllEditedTodo();
            }
        }
        return false;
    }

    @Override
    public String editEvent(String eventName, String fieldName, String newInputs) throws Exception {
    	Event _event;
    	String oldValue;

    	switch(fieldName) {
        	//case edit eventName
        	case (CommonHelper.FIELD_NAME):
        		if (!eventName.equals(newInputs) && findEvent(newInputs) != null) {
        			throw new Exception(CommonHelper.ERROR_EDIT_DUPLICATE);
        		}
        	    _event = findEvent(eventName);
    
            	if (_event == null) {
            		throw new Exception (String.format(CommonHelper.ERROR_NOT_FOUND, eventName));
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
            		throw new Exception (String.format(CommonHelper.ERROR_NOT_FOUND, eventName));
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
            		throw new Exception (String.format(CommonHelper.ERROR_NOT_FOUND, eventName));
            	}
            	else {
                    oldValue = _event.getEndDateTimeString();
            		_event.updateEnd(newInputs);
            		eventHandler.saveEditedEventHandler();
            	}
            	break;
        	//case edit unexpected
        	default :
        		throw new Exception(CommonHelper.ERROR_UNEXPECTED);
    	}
    	return oldValue;
    }

    @Override
    public String editTask(String taskName, String fieldName, String newInputs) throws Exception{

    	Todo _task;
    	String oldValue;
    	
    	switch(fieldName) {
        	//case edit taskName
        	case (CommonHelper.FIELD_NAME): 
        		if (!taskName.equals(newInputs) && findTask(newInputs) != null) {
        			throw new Exception(CommonHelper.ERROR_EDIT_DUPLICATE);
        		}
            	_task = findTask(taskName);
        
            	if (_task == null) {
            		throw new Exception (String.format(CommonHelper.ERROR_NOT_FOUND, taskName));
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
            		throw new Exception (String.format(CommonHelper.ERROR_NOT_FOUND, taskName));
            	}
            	else {
            	    oldValue = _task.getDeadlineDateTimeString();
            		_task.updateDeadline(newInputs);
            		taskHandler.saveAllEditedTodo();
            	}
            	break;
        	//case edit unexpected
        	default :
        	    throw new Exception(CommonHelper.ERROR_UNEXPECTED);
    	}
    	return oldValue;
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
    
    private Event findEvent(String eventName) {
    	int eventIndex = 0;
    	ArrayList<Event> completeEventBook = eventHandler.retrieveEventsToDelete();
    	
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
        ArrayList<Todo> completeTaskBook = taskHandler.retrieveTodoToDelete();
        
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

	@Override
	public boolean editEventName(String eventName, String newEventName) {
		// TODO Auto-generated method stub
		return false;
	}
   
    
}