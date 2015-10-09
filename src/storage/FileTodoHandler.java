package storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import object.Todo;

public class FileTodoHandler {
	private static final String BASIC_TODO_TYPE = "basic";
	private static final String PARTIAL_TODO_TYPE = "partial";
	private static final String COMPLETE_TODO_TYPE = "complete";
	
	private static final String UNIVERSAL_TODO = "Universal Todo.txt";
	private static final String UPCOMING_TODO = "Upcoming Todo.txt";
	private static final String PAST_TODO = "Past Todo.txt";
	private static final String PAST_UNIVERSAL_TODO = "Past Universal Todo.txt";
	private static final String TODO = "Todo";
	
	private static final String PATTERN_DATE = "dd MMM yyyy";
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
	
	private String baseDirectory;
	private File inputFile;	
	
	private ArrayList<Todo> allTodo = new ArrayList<Todo>();
	private ArrayList<Todo> universalTodo = new ArrayList<Todo>();
	private ArrayList<Todo> todoHistory;
	private ArrayList<Todo> universalTodoHistory = new ArrayList<Todo>();
	
	ArrayList<Todo> tempTodo;
	ArrayList<Todo> tempUniversalTodo;
	
	private Calendar todaysDate;
	
/********************************** Public method for users *****************************************/		
	
	public FileTodoHandler(String baseDirectory){
		this.baseDirectory = baseDirectory.concat("/");
		retrievePassedTasks();
		pushPassedTodoToHistoryFile();
	}
	
	public ArrayList<Todo> retrieveTodoByDate(String date){
		
		ArrayList<Todo> toDoListByDate = filterTodoToSpecificDate(date);
		
		return toDoListByDate;
	}
	
	public ArrayList<Todo> retrieveFloatingTodo(){
		return universalTodo;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Todo> retrieveTodoToDelete(){
		
		tempTodo = (ArrayList<Todo>) allTodo.clone();
		tempUniversalTodo = (ArrayList<Todo>) universalTodo.clone();
		
		ArrayList<Todo> allTodoClone = new ArrayList<Todo>();
		allTodoClone.addAll(tempTodo);
		allTodoClone.addAll(tempUniversalTodo);
		return allTodoClone;
	}
	
	public boolean saveNewTodoHandler(Todo todo){
		if(todo.hasDate()){
			allTodo.add(todo);
			saveToDoList(true);
		}
		else{
			saveAsUniversalTodo(todo);
		}
		return true;
	}
			
	public boolean saveToDoList(boolean saveNewTodo){
		System.out.println("here?? 1");
		System.out.println(allTodo);
		
		sortTodoByDate(allTodo);
		
		try{
			File outfile = new File(baseDirectory +  UPCOMING_TODO);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Todo aTodo : allTodo){
				writer.write(aTodo.getId() + ""); writer.newLine();
				String todoType = determineType(aTodo);
				writer.write(todoType); writer.newLine();
				writer.write(aTodo.toString()); writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
	
	public boolean saveUniversalToDoList(){
		System.out.println("here??2");
		sortTodoByDate(universalTodo);
		
		try{
			File outfile = new File(baseDirectory +  UNIVERSAL_TODO);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Todo aTodo : universalTodo){
				writer.write(aTodo.getId() + ""); writer.newLine();
				writer.write(aTodo.getName()); writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
	
	public boolean separateTodoTypes(){
		
		allTodo = tempTodo;
		universalTodo = tempUniversalTodo;
		return true;
		
//		allTodo.clear();
//		universalTodo.clear();
//		
//		for(Todo todo: allTodoClone){
//			if(todo.hasDate()){
//				allTodo.add(todo);
//			}else{
//				universalTodo.add(todo);
//			}
//		}
//		return true;
	}
	
	public boolean setNewDirectory(String newBaseDirectory){
		baseDirectory = newBaseDirectory.concat("/" + TODO + "/");
		return true;
	}
	
	public boolean updateHistory(){
		sortTodoByDate(todoHistory);
		System.out.println("herere");
		try{
			File outfile = new File(baseDirectory +  PAST_TODO);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Todo aTodo : todoHistory){
				writer.write(aTodo.getId() + ""); writer.newLine();
				String todoType = determineType(aTodo);
				writer.write(todoType); writer.newLine();
				writer.write(aTodo.toString()); writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
	
/***************************** Private methods sorted alphabetically ********************************/	
	
	private String determineType(Todo todo) {
		if(todo.hasDate() && todo.hasTime()){
			return COMPLETE_TODO_TYPE;
		}else if(todo.hasDate()){
			return PARTIAL_TODO_TYPE;
		}else{
			return BASIC_TODO_TYPE;
		}
	}

	private ArrayList<Todo> filterTodoToSpecificDate(String dateString) {
		ArrayList<Todo> toDoListByDate = new ArrayList<Todo>();
		Calendar todoDate;
	
		Calendar date = Calendar.getInstance();
		updateDate(date, dateString);
		setZeroTime(date);
		
		ArrayList<Todo> myTodo;
		
		if(date.before(todaysDate)){
			myTodo = todoHistory;
		}else{
			myTodo = allTodo;
		}
		
		for(Todo todo: myTodo){
			todoDate = (Calendar) todo.getDeadline().clone();
			setZeroTime(todoDate);
			
			if( date.equals( todoDate ) ){
				toDoListByDate.add(todo);
			}
		}

		return toDoListByDate;
	}
	
	private boolean isCompleteType(String type){
		return type.equals(COMPLETE_TODO_TYPE);
	}
	
	private boolean isPartialType(String type){
		return type.equals(PARTIAL_TODO_TYPE);
	}
	
	private boolean pushPassedTodoToHistoryFile(){
		todaysDate = Calendar.getInstance();
		setZeroTime(todaysDate);
		boolean allTodoValid = true;
		int counter = 0;
		
		for(Todo todo: allTodo){

			if(todo.getDeadline().before(todaysDate)){
				todoHistory.add(todo);
				counter++;
				allTodoValid = false;
			}else{
				break;
			}
		}
		
		if(allTodoValid){
			return false;
		}else{
			for(int i=0; i<counter; i++){
				allTodo.remove(0);
			}
			saveToDoList(false);
			updateHistory();
			return true;
		}
		
	}
	
	private boolean readTodoFile(String type){
		
		selectFileAsInputFile(baseDirectory + type);
				
		ArrayList<Todo> myTodo = new ArrayList<Todo>();
		String todoName, todoDate, todoTime, addInfo, todoType, Id;
		Todo todo;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			Id = reader.readLine();
			while( Id != null){
				
				todoType = reader.readLine();
				todoName = reader.readLine();
				todoDate = reader.readLine();
				todoTime = reader.readLine();
				addInfo = reader.readLine();
				
				if(isPartialType(todoType)){
					todo = new Todo(todoName, addInfo, todoDate);
				
				}else if(isCompleteType(todoType)){
					todo = new Todo(todoName, addInfo, todoDate, todoTime);
					
				}else{
					todo = new Todo(todoName, addInfo);
				}
				
				todo.setId(Integer.parseInt(Id));
				myTodo.add(todo);
				Id = reader.readLine();
			}
			
			if(type.equals(UPCOMING_TODO)){
				allTodo = myTodo;
			}else if(type.equals(PAST_TODO)){
				todoHistory = myTodo;
			}
			
			reader.close();		 
			return true;
		}
		catch (FileNotFoundException e) {
			return false;
		}catch (IOException e) {
			return false;
		}	
	}
	
	private boolean readUniversalTodoFile(String type) {
		
		selectFileAsInputFile(baseDirectory + type);
		ArrayList<Todo> myUniversalTodo = new ArrayList<Todo>();
		String todoName, addInfo, Id;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String lineOfText;
			
			while( (lineOfText = reader.readLine()) != null ){
				Id = lineOfText;
				todoName = reader.readLine();
				addInfo = reader.readLine();
				Todo todo = new Todo(todoName, addInfo);
				todo.setId(Integer.parseInt(Id));
				myUniversalTodo.add(todo);
				
			}
			
			if(type.equals(UNIVERSAL_TODO)){
				universalTodo = myUniversalTodo;
			}else if(type.equals(PAST_UNIVERSAL_TODO)){
				universalTodoHistory = myUniversalTodo;
			}
			
			reader.close();		 
			return true;
		}
		catch (FileNotFoundException e) {
			return false;
		}catch (IOException e) {
			return false;
		}	
	}
	
	private boolean retrievePassedTasks(){
		readTodoFile(UPCOMING_TODO);
		readTodoFile(PAST_TODO);
		readUniversalTodoFile(UNIVERSAL_TODO);
		readUniversalTodoFile(PAST_UNIVERSAL_TODO);
		return true;
	}
	
	private boolean saveAsUniversalTodo(Todo todo) {
		
		universalTodo.add(todo);
		
		try{
			File outfile = new File(baseDirectory + UNIVERSAL_TODO);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Todo aTodo : universalTodo){
				writer.write(aTodo.getId() + ""); writer.newLine();
				writer.write(aTodo.getName()); writer.newLine();
				writer.write(aTodo.getAdditionalInfo()); writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
	
	private void selectFileAsInputFile(String fileName){
		inputFile = new File(fileName);
	}
	
	private void setZeroTime(Calendar date){
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}
	
	private void sortTodoByDate(ArrayList<Todo> toDoList){
		if(!toDoList.isEmpty()){
			Collections.sort(toDoList, new customTodoComparator());
		}		
	}
	
	public boolean updateDate(Calendar calendar, String dateString) {
        try {
            Calendar date = Calendar.getInstance();
            date.setTime(FORMAT_DATE.parse(dateString));
            
            calendar.set(Calendar.DATE, date.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

/***********************************************************************************************/
	class customTodoComparator implements Comparator<Todo>{
		
		Calendar date1, date2;
		
		@Override
		public int compare(Todo task1, Todo task2) {
			setupCalendar(task1, task2);
			
			if(task1.hasDate() && !task2.hasDate()){
				return -1;
			}else if(!task1.hasDate() && task2.hasDate() ){
				return 1;
			}else if(task1.hasDate() && task2.hasDate()){
				return compareDateDirectly();
			}else{
				return compareDateWithoutTime();
			}
		}
		
		private int compareDateDirectly() {
			
			if(date1.before(date2)){
				return -1;
			}else if(date1.after(date2)){
				return 1;
			}else{
				return 0;
			}
		}

		private int compareDateWithoutTime() {
			setZeroTime(date1);
			setZeroTime(date2);
			return compareDateDirectly();
		}

		private void setupCalendar(Todo task1, Todo task2) {
			date1 = (Calendar) task1.getDeadline().clone();
			date2 = (Calendar) task2.getDeadline().clone();
			
		}
	}	
}

