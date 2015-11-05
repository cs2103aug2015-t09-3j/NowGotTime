//@@author A0130445R
package project;
import java.util.ArrayList;

import object.Event;


public interface ProjectManager {
	
	public boolean addProjectEvent (Event event, String projectName); //works
	
	//public boolean deleteProjectEvent(String eventName, String projectName);//tested
	//public boolean deleteProjectEvent(int index, String projectName);//tested
	public boolean deleteProjectEvent(Event event, String projectName);
	public boolean editProjectName(String newProjectName, String oldProjectName);
	//public boolean editProjectEvent(Event event, int infoIndex, String newValue, String projectName); //works
	//public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue, String projectName);
	//public boolean editProjectEvent(Event eventName, int infoIndex, String newValue, String projectName); //works
	
	public ArrayList<Integer> viewProjectTimeline(String name); //works
	public ArrayList<Event> viewEventProgressTimeline(String projectName);
	public double progressBar(String projectName);
	public ArrayList<Event> viewProjectTimelineInEvents(String projectName);
	public boolean deleteProjectEvent(int index, String projectName);
	public Event editEvent(int arrayIndex, String projectName);
	public boolean addProgressMessage(int index, String progressMessage, String projectName);
	public boolean deleteProgressMessage(int index, String projectName);
	public boolean editProgressMessage(int index, String newProgressMessage, String projectName);
	}
