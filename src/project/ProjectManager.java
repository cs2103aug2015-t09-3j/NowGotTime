//@@author A0130445R
package project;
import java.util.ArrayList;

import object.Event;


public interface ProjectManager {
	
	public boolean addProjectEvent (Event event, String projectName);
	public boolean deleteProjectEvent(Event event, String projectName);
	public boolean editProjectName(String newProjectName, String oldProjectName);
	public ArrayList<Integer> viewProjectTimeline(String name);
	public ArrayList<Event> viewEventProgressTimeline(String projectName);
	public double progressBar(String projectName);
	public ArrayList<Event> viewProjectTimelineInEvents(String projectName);
	public boolean deleteProjectEvent(int index, String projectName);
	public Event editEvent(int arrayIndex, String projectName);
	public boolean addProgressMessage(int index, String progressMessage, String projectName);
	public boolean deleteProgressMessage(int index, String projectName);
	public boolean editProgressMessage(int index, String newProgressMessage, String projectName);
	}
