//@@author A0124402Y

package storage;

import helper.CalendarHelper;
import helper.MyLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.logging.Level;

import object.Todo;

/**
 * 
 * @author A0124402Y
 *
 */
public class FileTodoHandler {
	
	private MyLogger myLogger = new MyLogger();
	
	private static final String BASIC_TODO_TYPE = "basic";
	private static final String PARTIAL_TODO_TYPE = "partial";
	private static final String COMPLETE_TODO_TYPE = "complete";
	
	private static final String FLOATING_TODO = "Floating_Todo.txt";
	private static final String NORMAL_TODO = "Normal_Todo.txt";
	private static final String TODO = "Todo";
	
	private String baseDirectory;
	
	private ArrayList<Todo> allTodo = new ArrayList<Todo>();
	private ArrayList<Todo> allTodoClone = new ArrayList<Todo>();
	private ArrayList<Todo> universalTodo = new ArrayList<Todo>();
	
/********************************** Public method for users *****************************************/		
	
	public FileTodoHandler(String baseDirectory){
		this.baseDirectory = baseDirectory.concat("/");
		retrievePassedTasks();
	}
	
	public ArrayList<Todo> retrieveTodoByDate(String dateString){
		
		assert(dateString != null); 	//checked by FileHandler first
		
		ArrayList<Todo> toDoListByDate = new ArrayList<Todo>();
		Calendar date = Calendar.getInstance();
		
		if( (date = createDate(dateString, date)) == null){
			return null;
		}
		
		extractTodoByDate(toDoListByDate, date);
		return toDoListByDate;	
	}
	
	public ArrayList<Todo> retrieveFloatingTodo(){
		return universalTodo;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Todo> retrieveAllTodo(){
		ArrayList<Todo> tempTodo = (ArrayList<Todo>) allTodo.clone();
		ArrayList<Todo> tempUniversalTodo = (ArrayList<Todo>) universalTodo.clone();
		
		allTodoClone.clear();
		allTodoClone.addAll(tempTodo);
		allTodoClone.addAll(tempUniversalTodo);
		return allTodoClone;
	}
	
	public boolean saveNewTodoHandler(Todo todo){
		
		if(todo.hasDate()){
			allTodo.add(todo);
			return saveChange(NORMAL_TODO);
			
		} else{
			universalTodo.add(todo);
			return saveChange(FLOATING_TODO);
		}	
	}
			
	public boolean saveChange(String type){
		
		assert(type == NORMAL_TODO || type == FLOATING_TODO);
		
		ArrayList<Todo> tempTodo;
		
		if(type.equals(NORMAL_TODO)){
			tempTodo = allTodo;
		}else{
			tempTodo = universalTodo;
		}
		
		sortTodoByDate(tempTodo);
		return writeToFile(type);	
	}
	
	public boolean separateTodoTypes(){
		
		assert(allTodoClone != null);
		assert(allTodo != null);
		assert(universalTodo != null);
		
		allTodo.clear();
		universalTodo.clear();
		
		for(Todo todo: allTodoClone){
			if(todo.hasDate()){
				allTodo.add(todo);
			} else{
				universalTodo.add(todo);
			}
		}
		return true;
	}
	
	public boolean setNewDirectory(String newBaseDirectory){
		if((newBaseDirectory != null) && (new File(newBaseDirectory).exists())){
			baseDirectory = newBaseDirectory.concat("/" + TODO + "/");
			return true;
		}
		return false;
	}
	
/***************************** Private methods sorted alphabetically ********************************/	
	
	private Calendar createDate(String dateString, Calendar date) {
		try {
			date = CalendarHelper.parseDate(dateString);
			setZeroTime(date);
		} catch (ParseException e) {
			myLogger.logp(Level.SEVERE, getClass().getName(), 
					"writeToFile", e.getMessage());
			return null;
		}
		return date;
	}
	
	private Todo createTodo(String todoName, String todoDate, String todoTime,
			String addInfo, String todoType, String Id, boolean isDone) {
		Todo todo;
		if(isPartialType(todoType)){
			todo = new Todo(todoName, addInfo, todoDate);
		
		}else if(isCompleteType(todoType)){
			todo = new Todo(todoName, addInfo, todoDate, todoTime);
			
		}else{
			todo = new Todo(todoName, addInfo);
		}
		
		todo.setId(Integer.parseInt(Id));
		todo.setDone(isDone);
		return todo;
	}
	
	private String determineType(Todo todo) {
		if(todo.hasDate() && todo.hasTime()){
			return COMPLETE_TODO_TYPE;
		}else if(todo.hasDate()){
			return PARTIAL_TODO_TYPE;
		}else{
			return BASIC_TODO_TYPE;
		}
	}
	
	private void extractTodoByDate(ArrayList<Todo> toDoListByDate, Calendar date) {
		
		assert(date != null);
		assert(toDoListByDate != null);
		
		ArrayList<Todo> myTodo = allTodo;
		Calendar todoDate;
		for(Todo todo: myTodo){
			todoDate = (Calendar) todo.getDeadline().clone();
			setZeroTime(todoDate);
			
			if( date.equals(todoDate) ){
				toDoListByDate.add(todo);
			}
		}
	}

	private boolean isCompleteType(String type){
		return type.equals(COMPLETE_TODO_TYPE);
	}
	
	private boolean isPartialType(String type){
		return type.equals(PARTIAL_TODO_TYPE);
	}
		
	private boolean readTodoFile(){				
		ArrayList<Todo> myTodo = new ArrayList<Todo>();
		String todoName, todoDate, todoTime, addInfo, todoType, Id;
		Todo todo;
		
		try {
			File inputFile = new File(baseDirectory + NORMAL_TODO);
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			boolean isDone;
			
			Id = reader.readLine();
			while( Id != null){
				
				todoType = reader.readLine();
				todoName = reader.readLine();
				todoDate = reader.readLine();
				todoTime = reader.readLine();
				isDone = Boolean.parseBoolean(reader.readLine());
				addInfo = "";
				
				todo = createTodo(todoName, todoDate, todoTime, addInfo,
						todoType, Id, isDone);
				myTodo.add(todo);
				Id = reader.readLine();
			}
			
			allTodo = myTodo;
			reader.close();		 
			return true;
		
		}catch (FileNotFoundException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"readTodoFile", e.getMessage());
			saveChange(NORMAL_TODO);
			return false;
			
		}catch (IOException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"readTodoFile", e.getMessage());
			return false;
		}	
	}
	
	private boolean readUniversalTodoFile() {
		ArrayList<Todo> myUniversalTodo = new ArrayList<Todo>();
		String todoName, addInfo, Id;
		boolean isDone;
		
		try {
			File inputFile = new File(baseDirectory + FLOATING_TODO);
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String lineOfText;
			
			while( (lineOfText = reader.readLine()) != null ){
				Id = lineOfText;
				todoName = reader.readLine();
				isDone = Boolean.parseBoolean(reader.readLine());
				addInfo = "";
				
				Todo todo = new Todo(todoName, addInfo);
				todo.setId(Integer.parseInt(Id));
				todo.setDone(isDone);
				myUniversalTodo.add(todo);
			}
			
			universalTodo = myUniversalTodo;	
			reader.close();		 
			return true;
		}catch (FileNotFoundException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"readUniversalTodoFile", e.getMessage());
			saveChange(FLOATING_TODO);
			return false;
		}catch (IOException e) {
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"readUniversalTodoFile", e.getMessage());
			return false;
		}	
	}
	
	private boolean retrievePassedTasks(){
		readUniversalTodoFile();
		readTodoFile();
		return true;
	}
	
	private void setZeroTime(Calendar date){
		
		assert(date != null);
		
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}
	
	private void sortTodoByDate(ArrayList<Todo> toDoList){
		
		assert(toDoList != null);
		
		if(!toDoList.isEmpty()){
			Collections.sort(toDoList);
		}		
	}	
	
	private boolean writeToFile(String type){
		
		assert(type == NORMAL_TODO || type == FLOATING_TODO);
		
		try{
			File outfile = new File(baseDirectory +  type);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			if(type.equals(NORMAL_TODO)){
				for(Todo aTodo : allTodo){
					writer.write(aTodo.getId() + ""); writer.newLine();
					String todoType = determineType(aTodo);
					writer.write(todoType); 
					writer.newLine();
					writer.write(aTodo.toString()); 
					writer.newLine();
					writer.write(aTodo.getDone() + "");
					writer.newLine();
				}
			}else{
				for(Todo aTodo : universalTodo){
					writer.write(aTodo.getId() + ""); 
					writer.newLine();
					writer.write(aTodo.getName()); 
					writer.newLine();
					writer.write(aTodo.getDone() + "");
					writer.newLine();
				}
			}	
			
			writer.close();
			return true;
	
		}catch(IOException e){
			myLogger.logp(Level.WARNING, getClass().getName(), 
					"writeToFile", e.getMessage());
			return false;
		}
	}
	
}