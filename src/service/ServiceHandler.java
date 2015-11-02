package service;
import helper.CalendarHelper;
import helper.CommonHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import object.Event;
import object.Item;
import object.Todo;
import storage.FileHandler;

public class ServiceHandler implements ServiceManager{
	private FileHandler itemHandler;
	private ArrayList<Item> searchedItems = new ArrayList<Item>();
	private ArrayList<ArrayList<Item>> viewMultipleDays = new ArrayList<ArrayList<Item>>();

	public ServiceHandler (){
		itemHandler = new FileHandler();
	}

	/**
	 * Runs the process of creating an item
	 * @throws Exception 
	 */
	@Override
	public boolean createItem(Item item) throws Exception {
		if (item.getClass() == Event.class) {
		return createEvent((Event) item);
	}else {
		return createTask((Todo) item);
	}
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
		}else {
			return deleteTask((Todo) item);   	
	}
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
		if((index >= searchedItems.size()) || index < 0){
			return false;
		}else{
			return deleteItem(searchedItems.get(index));
		}
	}

	/**
	 * Views an item after searching via the search index
	 */
	@Override
	public Item viewItemByIndex(int index) {   	
		if((index >= searchedItems.size()) || index <0 ){
			return null;			
		}else{
			return searchedItems.get(index);
		}
	}
	/**
	 * Edits an item after searching via search index
	 */
	public String editItem(Item item, String fieldName, String newInputs ) throws Exception {
		Item _item = null;
		
		if (item.getClass() == Event.class) {
			ArrayList<Event> completeEventBook = itemHandler.retrieveAllEvents();
			for (int i = 0; i < completeEventBook.size(); i++)
		        if(item.equals(completeEventBook.get(i))) {
		            _item = completeEventBook.get(i);
					break;
		        }
			return editEvent((Event) _item, fieldName, newInputs);
		}else {
			ArrayList<Todo> completeTaskBook = itemHandler.retrieveAllTodo();
			for (int i = 0; i < completeTaskBook.size(); i++)
		        if(item.equals(completeTaskBook.get(i))) {
		            _item = completeTaskBook.get(i);
					break;
		        }
			return editTask((Todo) _item, fieldName, newInputs);   	
		}
	}

	/**
	 *  Changes directory
	 */
	@Override
	public boolean changeDirectory(String newDirectory) {
		return itemHandler.changeBaseDirectory(newDirectory);
	}

	/**
	 *  Marks the item as done
	 */
	@Override
	public boolean mark(Item item){
	    item = getItemFromItemHandler(item);
		if (item.getDone() == true){
			return false;
		}else {
			item.setDone(true);
			itemHandler.saveEditedEventHandler();
            itemHandler.saveEditedTodoHandler();
            itemHandler.saveAllEditedTodo();
			return true;
		}
	}
	/**
	 *  Marks the item as undone
	 */
	@Override
	public boolean unmark(Item item){
        item = getItemFromItemHandler(item);
		if (item.getDone() == false){
			return false;
		}else {
			item.setDone(false);
            itemHandler.saveEditedEventHandler();
            itemHandler.saveEditedTodoHandler();
            itemHandler.saveAllEditedTodo();
			return true;
		}
	}
	
	/**
	 *  Views multiple days worth of tasks and events
	 * @throws ParseException 
	 */
	@Override
	//currently set as 3
	public ArrayList<ArrayList<Item>> viewMultipleDays(String date) throws ParseException{ 
		int noOfDays = 3;
		Calendar _date = CalendarHelper.parseDate(date);
		ArrayList<Item> todayItems = new ArrayList<Item>();
		ArrayList<Item> floatingTodo = new ArrayList<Item>();
		
		// today
		viewMultipleDays.clear();
		todayItems.clear();
		floatingTodo.clear();
		searchedItems.clear();
		
		if(!(viewEventByDate((CalendarHelper.getDateString(_date))).equals(null))) {
			todayItems.addAll(viewEventByDate((CalendarHelper.getDateString(_date))));
		}
		
		if(!(viewTaskByDate((CalendarHelper.getDateString(_date))).equals(null))) {
			todayItems.addAll(viewTaskByDate((CalendarHelper.getDateString(_date))));
		}
		
		if(!(viewTaskNoDate().equals(null))) {
			floatingTodo.addAll(viewTaskNoDate());
		}
		Collections.sort(todayItems);
		viewMultipleDays.add(todayItems);
		viewMultipleDays.add(floatingTodo);
		searchedItems.addAll(todayItems);
		searchedItems.addAll(floatingTodo);		
		
		//2 additional days
		for (int i = 0; i < noOfDays - 1; i++){	
			ArrayList<Item> subsequentDays = new ArrayList<Item>();
			
			addDate(_date);
			if(!(viewEventByDate((CalendarHelper.getDateString(_date))).equals(null))) {
				subsequentDays.addAll(viewEventByDate((CalendarHelper.getDateString(_date))));
			}
			
			if(!(viewTaskByDate((CalendarHelper.getDateString(_date))).equals(null))) {
				subsequentDays.addAll(viewTaskByDate((CalendarHelper.getDateString(_date))));
			} 
			Collections.sort(subsequentDays);
			viewMultipleDays.add(subsequentDays);
			searchedItems.addAll(subsequentDays);	
		}
		assert (viewMultipleDays.size() == noOfDays + 1); // no of items each day + 1 floatingTodo ArrayList<Item>
		return viewMultipleDays;		
	}
	// ****************************************Private Methods******************************************************   

	private Event findEvent(String eventName) {
		int eventIndex = 0;
		ArrayList<Event> completeEventBook = itemHandler.retrieveAllEvents();

		for (Event event:completeEventBook){
			if (event.getName().toLowerCase().equals(eventName.toLowerCase())){
				assert(eventIndex >= 0);
				return completeEventBook.get(eventIndex);
			}else {
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
			}else {
				taskIndex++; // finding index with same name as taskName passed in
			}
		}
		return null;
	}

	private String editEvent(Event _event, String fieldName, String newInputs) throws Exception {
	    _event = (Event) getItemFromItemHandler(_event);
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
    		if (!compareDate(_event.getStartCalendar(),_event.getEndCalendar())){
    		    _event.updateStart(oldValue);
    			throw new Exception(CommonHelper.ERROR_START_AFTER_END);
    		}else {
    		    itemHandler.saveEditedEventHandler();
    		}
    		break;

		//case edit end
		case (CommonHelper.FIELD_END): 
			oldValue = _event.getEndDateTimeString();
		    _event.updateEnd(newInputs);
    		if (!compareDate(_event.getStartCalendar(),_event.getEndCalendar())){
    		    _event.updateEnd(oldValue);
    			throw new Exception(CommonHelper.ERROR_START_AFTER_END);
    		}else {
    		    itemHandler.saveEditedEventHandler();
    		}
    		break;

		//case edit unexpected
		default :
			assert(false);
		}
		return oldValue;
	}

	private String editTask(Todo _task, String fieldName, String newInputs) throws Exception{
        _task = (Todo) getItemFromItemHandler(_task);
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
        _task = (Todo) getItemFromItemHandler(_task);
		ArrayList<Todo> completeTodoList = itemHandler.retrieveAllTodo();      
		boolean check = completeTodoList.remove(_task);
		itemHandler.saveAllEditedTodo(); //saves no matter if an item is deleted
		return check;
	}

	private boolean deleteEvent(Event _event) {
        _event = (Event) getItemFromItemHandler(_event);
		ArrayList<Event> completeEventBook = itemHandler.retrieveAllEvents();
		boolean check = completeEventBook.remove(_event);
		itemHandler.saveEditedEventHandler(); //saves no matter if an item is deleted
		return check;
	}
	
	private boolean createEvent(Event newEvent) throws Exception {
		if (compareDate(newEvent.getStartCalendar(),newEvent.getEndCalendar())){
		return itemHandler.saveNewEventHandler(newEvent);
		}else {
			throw new Exception(CommonHelper.ERROR_START_AFTER_END);
		}
	}
	
	private boolean createTask(Todo newTask) {
		return itemHandler.saveNewTodoHandler(newTask);
	}

	private boolean compareDate(Calendar startDate, Calendar endDate){
		if (startDate.before(endDate)){
			return true;
		}else {
			return false;
		}
	}
	
	private void addDate(Calendar calendar) {
		calendar.add(Calendar.DAY_OF_MONTH,1);
	}
	
	private Item getItemFromItemHandler(Item item) {
	    int id = item.getId();
	    if (item instanceof Event) {
	        return itemHandler.retrieveEventById(id);
	    } else {
            return itemHandler.retrieveTaskById(id);
	    }
	}
}