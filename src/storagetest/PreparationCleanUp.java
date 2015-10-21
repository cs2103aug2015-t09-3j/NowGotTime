package storagetest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;

import object.Event;
import object.Todo;

public class PreparationCleanUp {
	
	public static boolean cleanUp(String baseDirectory){
				
		File dir = new File(baseDirectory);
		if(dir.isDirectory() && dir.list().length > 0){
			for(File file: dir.listFiles()) {
				if(!file.isDirectory()){
					file.delete(); 
				}else{
					cleanUp(file.getPath());
				}
			}
		}
		
		Path path = Paths.get(baseDirectory);
		
		try {
			Files.delete(path);
			return true;
		} catch(NoSuchFileException e) {
			System.out.println("No such file exist to delete");
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean setUpDirectory(String baseDirectory){
		File dir = new File(baseDirectory);
		if (!dir.exists()) {
			dir.mkdir();
			return true;
		}		
		return false;
	}

	public static boolean makeNewDirectory(String directoryName){
		File file = new File(directoryName);
		if (!file.exists()) {
			return file.mkdir();
		}
		return false;
	}

	public static void manualCleanUp(){
		delete("database");
		delete("overview.txt");	
		delete("Counter.txt");	
	}
	
	//temp comparators
	public static boolean compareEventsArrayList(ArrayList<Event> list1, ArrayList<Event> list2){
		if(list1.size() == list2.size()){
			for(int i=0; i<list1.size(); i++){
				if(!compareEvents(list1.get(i), list2.get(i))){
					return false;
				}
			}
		}else{
			return false;
		}
		return true;
	}	
	
	public static boolean compareTodoArrayList(ArrayList<Todo> list1, ArrayList<Todo> list2){
		
		if(list1.size() != list2.size()){
			return false;
		}else{
			for(int i=0; i<list1.size(); i++){
				if(!compareTodo(list1.get(i), list2.get(i))){
					return false;
				}
			}
			return true;
		}		
	}
	
	private static void delete(String name){
		String baseDirectory = System.getProperty("user.dir").toString() + "/" + name;
		File file = new File(baseDirectory);
		if(file.isDirectory()){
			cleanUp(baseDirectory);
		}else{
			file.delete();
		}
	}

	private static boolean compareEvents(Event event1, Event event2){
		return ( event1.getId() == event2.getId() ) &&
				( event1.getName().equals( event2.getName() ) ) &&
				( event1.getStartCalendar().get(Calendar.DATE) == event2.getStartCalendar().get(Calendar.DATE) ) &&
				( event1.getStartCalendar().get(Calendar.HOUR_OF_DAY) == event2.getStartCalendar().get(Calendar.HOUR_OF_DAY) ) &&
				( event1.getStartCalendar().get(Calendar.MINUTE) == event2.getStartCalendar().get(Calendar.MINUTE) ) &&
				( event1.getEndCalendar().get(Calendar.DATE) ==  event2.getEndCalendar().get(Calendar.DATE) ) &&
				( event1.getEndCalendar().get(Calendar.HOUR_OF_DAY) ==  event2.getEndCalendar().get(Calendar.HOUR_OF_DAY) ) &&
				( event1.getEndCalendar().get(Calendar.MINUTE) ==  event2.getEndCalendar().get(Calendar.MINUTE) );
	}

	private static boolean compareTodo(Todo todo1, Todo todo2){	
		//TODO: change this!
		return (todo1.getId() == todo2.getId());
	}
}
