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

public class FileTodoHandler {
	private static final String BASIC_TODO_TYPE = "basic";
	private static final String PARTIAL_TODO_TYPE = "partial";
	private static final String COMPLETE_TODO_TYPE = "complete";
	private static final String UNIVERSAL_TODO = "UniversalTodo.txt";
	private static final String TODO_OVERVIEWER = "todoOverviewer.txt";
	
	private static final String PATTERN_DATE = "dd MMM yyyy";
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
	
	private String baseDirectory;
	private File inputFile;
	private ArrayList<String> availableMonths;	
	
/*****************************************************************************************/		
	
	public FileTodoHandler(String baseDirectory){
		this.baseDirectory = baseDirectory.concat("\\");
		readOverviewerFile();
	}

	public boolean saveNewTodoHandler(Todo todo){
		if(todo.hasDate()){
			String date = todo.getDeadlineDate();
			ArrayList<Todo> toDoList = retrieveTodoByMonth(date);
			toDoList.add(todo);
			sortTodoByDate(toDoList);
			saveToDoList(date, toDoList);
		}
		else{
			saveAsUniversalTodo(todo);
		}
		return true;
	}
			
	public boolean saveToDoList(String date, ArrayList<Todo> toDoList){
		//TODO: think of the case when an edited event has date that over spill month
		
		try{
			File outfile = new File(baseDirectory + setFileName(date));
			
			if(!outfile.exists()){
				updateOverviewFile(setFileName(date));
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));	
			
			for(Todo aTodo : toDoList){
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
	
	public ArrayList<Todo> retrieveTodoEventByDate(String date){
		
		ArrayList<Todo> toDoListByMonth = retrieveTodoByMonth(date);
		
		for(Todo t: toDoListByMonth){
			System.out.println(t);
		}
		
		ArrayList<Todo> toDoListByDate = filterEventsToSpecificDate(date, toDoListByMonth);
		
		return toDoListByDate;
	}
	
	public ArrayList<Todo> retrieveUniversalTodo() {
		
		selectFileAsInputFile(baseDirectory + UNIVERSAL_TODO);
		ArrayList<Todo> toDoList  = new ArrayList<Todo>();
		String todoName, addInfo;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String lineOfText;
			
			while( (lineOfText = reader.readLine()) != null ){
				todoName = lineOfText;
				addInfo = reader.readLine();
				
				Todo todo = new Todo(todoName, addInfo);
				toDoList.add(todo);
			}
			
			reader.close();		 
			return toDoList;
		}
		catch (FileNotFoundException e) {
			return toDoList;
		}catch (IOException e) {
			return toDoList;
		}	
	}
	
/*****************************************************************************************/	
	
	private String determineType(Todo todo) {
		if(todo.hasDate() && todo.hasTime()){
			return COMPLETE_TODO_TYPE;
		}else if(todo.hasDate()){
			return PARTIAL_TODO_TYPE;
		}else{
			return BASIC_TODO_TYPE;
		}
	}

	private ArrayList<Todo> filterEventsToSpecificDate(String dateString, ArrayList<Todo> toDoListByMonth) {
		ArrayList<Todo> toDoListByDate = new ArrayList<Todo>();
		Calendar date = Calendar.getInstance();
		updateDate(date, dateString);
		
		for(Todo todo: toDoListByMonth){
			if( date.equals( todo.getDeadline() ) ){
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
	
 	private ArrayList<Todo> retrieveTodoByMonth(String date){
		
		String fileName = setFileName(date);
		selectFileAsInputFile(baseDirectory + fileName);
		
		ArrayList<Todo> toDoList  = new ArrayList<Todo>();
		String todoName, todoDate, todoTime, addInfo, todoType;
		Todo todo;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			
			while( (todoType = reader.readLine().trim()) != null ){
				System.out.println("todoType: " + todoType);
				todoName = reader.readLine();
				System.out.println("todoName: " + todoName);
				addInfo = reader.readLine();
				System.out.println("AddInfo: " + addInfo);
				
				
				if(isPartialType(todoType)){
					System.out.println("Enter");
					todoDate = reader.readLine();
					
					System.out.println(todoDate);
					todo = new Todo(todoName, addInfo, todoDate);
				
				}else if(isCompleteType(todoType)){
					todoDate = reader.readLine();
					todoTime = reader.readLine();
					todo = new Todo(todoName, addInfo, todoDate, todoTime);
				
				}else{
					todo = new Todo(todoName, addInfo);
				}
				
				toDoList.add(todo);
			}
			
			reader.close();		 
			return toDoList;
		}
		catch (FileNotFoundException e) {
			return toDoList;
		}catch (IOException e) {
			return toDoList;
		}	
	}
	
	private void readOverviewerFile() {
		inputFile = new File( baseDirectory + TODO_OVERVIEWER);	
		availableMonths = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String date;
			
			while((date = reader.readLine()) != (null)){
				availableMonths.add(date);
			}		
			reader.close();		 
		}
		catch (FileNotFoundException e) {
			// Do nothing
		}catch (IOException e) {
			// Do nothing
		}
	}
	
	private boolean saveAsUniversalTodo(Todo todo) {
		ArrayList<Todo> floatingtoDoList = retrieveUniversalTodo();
		floatingtoDoList.add(todo);
		
		return true;
	}

	private void selectFileAsInputFile(String fileName){
		inputFile = new File(fileName);
	}

	private String setFileName(String date){
		String[] brokenUpDate = date.split(" ");
		return brokenUpDate[1].concat(brokenUpDate[2] + ".txt");	
	}
	
	private void sortTodoByDate(ArrayList<Todo> toDoList){
		//Collections.sort(currentWorkingMonthFile, new customComparator);
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
	
	private boolean updateOverviewFile(String fileName){
		
		availableMonths.add(fileName);
		try{
			File outfile = new File(baseDirectory + TODO_OVERVIEWER);
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			
			for(String month : availableMonths){
				writer.write(month.trim());
				writer.newLine();
			}
			writer.close();
			return true;
	
		}catch(IOException e){
			System.out.println("File cannot be written.\n");
			return false;
		}
	}
	
//	private boolean isBasicType(String type){
//		return type.equals(BASIC_TODO_TYPE);
//	}
//		
//	private String parseMonth(int month){
//		switch(month){
//			case 0: return "Jan";
//			case 1: return "Feb";
//			case 2: return "Mar";
//			case 3: return "Apr";
//			case 4: return "May";
//			case 5: return "Jun";
//			case 6: return "Jul";
//			case 7: return "Aug";
//			case 8: return "Sep";
//			case 9: return "Oct";
//			case 10: return "Nov";
//			case 11: return "Dec";
//			default: return null;
//		}
//	}
//	
}
