//@@author A0130445R

package project;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import object.Event;
import storage.FileHandler;


public class ProjectHandler implements ProjectManager{

	private FileHandler project;
	// String in HashMap => Progress
	private HashMap<Integer, String> map;
	private ArrayList<Integer> projectBook = new ArrayList<Integer>();
		
	public ProjectHandler (){
		project = new FileHandler();
	}		
	
	/**
	 * Adds an existing Event by ID into Project ArrayList
	 */
	@Override
	public boolean addProjectEvent(Event event, String projectName) {
		assert(projectName != null); 	//checked by Projects first
		try {
			projectBook = viewProjectTimeline(projectName.toLowerCase());
			projectBook.add(event.getId());
			sortEvents();
			project.saveEditedProjectDetails(projectBook, map, projectName.toLowerCase());
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	
	@Override
	public boolean deleteProjectEvent(int index, String projectName) {
		assert(projectName != null); 	//checked by Projects first
		viewProjectTimeline(projectName);
		if (index < projectBook.size()) {
			int id = projectBook.get(index);
			Event event = project.retrieveEventById(id);
			return deleteProjectEvent(event, projectName);
		} else {
			return false;
		}
	}
	
	
	@Override
	public boolean deleteProjectEvent(Event event, String projectName) {
		assert(projectName != null); 	//checked by Projects first
		viewProjectTimeline(projectName);
		int id = extractIdFromEvent(event);
		int index = 0;
		for (int savedId : projectBook) {
			if (savedId == id) {
				projectBook.remove(index);
				map.remove(id);
				sortEvents();
				project.saveEditedProjectDetails(projectBook, map, projectName.toLowerCase());
				return true;
			}
			index++;
		} return false;
	}

	public int extractIdFromEvent(Event event) {
		int id = event.getId();
		return id;
	}
	
	@Override
	public boolean editProjectName(String newProjectName, String oldProjectName) {
		ArrayList<Integer> newProject = viewProjectTimeline(oldProjectName);
		return project.saveEditedProjectDetails(newProject, map, newProjectName);
	}
	
	/**
	 * Edits an existing Event in the Project ArrayList by the ID
	 */
	
	@Override
	public Event editEvent(int arrayIndex, String projectName) {
		assert(projectName != null); 	//checked by Projects first
		viewProjectTimeline(projectName);
		int id = projectBook.get(arrayIndex);
		Event event = project.retrieveEventById(id); 
		return event;
	}
	
	@Override
	public ArrayList<Integer> viewProjectTimeline(String projectName) {
		
		projectBook = project.retrieveProjectTimeLine(projectName.toLowerCase());
		map = project.retrieveProjectProgress();
		return projectBook;
	}

	@Override
	public ArrayList<Event> viewEventProgressTimeline(String projectName) {
		
		ArrayList<Integer> arrayListOfId = viewProjectTimeline(projectName);
		ArrayList<Event> eventProgress = new ArrayList<Event>();
		
		for (int id : arrayListOfId) {
			Event event = project.retrieveEventById(id);
			String progressMessage = map.get(id);
			event.setAdditionalInfo(progressMessage);
           // System.out.println(event.getName() + " " + event.getAdditionalInfo());
			eventProgress.add(event);
		}
		return eventProgress;
	}
	
	@Override
	public ArrayList<Event> viewProjectTimelineInEvents(String projectName) {
		ArrayList<Event> viewTimeline = new ArrayList<Event>();
		ArrayList<Integer> arrayListOfId = viewProjectTimeline(projectName);
		
		for (int id : arrayListOfId) {
			Event event = project.retrieveEventById(id);
			viewTimeline.add(event);
		}
		
		return viewTimeline;
	}

	private void sortEvents() {
		ArrayList<Event> eventsToBeSorted = new ArrayList<Event>();
		ArrayList<Integer> newProjectBook = new ArrayList<Integer>();
		
		for (int id : projectBook) {
			Event event = project.retrieveEventById(id);
			eventsToBeSorted.add(event);
		}
		
		EventComparator eventComparator = new EventComparator();
		Collections.sort(eventsToBeSorted, eventComparator);
		
		for (Event event : eventsToBeSorted) {
			int id = extractIdFromEvent(event);
			newProjectBook.add(id);
		}
		projectBook = newProjectBook;
	}
	
	@Override
	public double progressBar(String projectName) {
		assert(projectName != null); 	//checked by Projects first
		ArrayList<Integer> projectIdTimeline = viewProjectTimeline(projectName);
		// ArrayList<Event> projectEventTimeline = new ArrayList<Event>();
		int totalEvents = projectIdTimeline.size();
		
		if (totalEvents == 0) return 0;
		int eventsDone = 0;
		double percentageDone = 0;
		
		for (int id : projectIdTimeline) {
			Event event = project.retrieveEventById(id);
			if (event.getDone()) {
				eventsDone++;
			}
		}
		
		percentageDone = 100.0*((double)eventsDone/(double)totalEvents);

        DecimalFormat df = new DecimalFormat("#.##");      
        percentageDone = Double.valueOf(df.format(percentageDone));
		
		return percentageDone;
	}
	
	@Override
	public boolean addProgressMessage(int index, String progressMessage, String projectName) {
		assert(projectName != null); 	//checked by Projects first
		viewProjectTimeline(projectName);
        
        if (0 <= index && index < projectBook.size()) {
            int id = projectBook.get(index);
            map.put(id, progressMessage);
            return project.saveEditedProjectDetails(projectBook, map, projectName);
        } else {
            return false;
        }
	}
	
	@Override
	public boolean deleteProgressMessage(int index, String projectName) {
		assert(projectName != null); 	//checked by Projects first
	    viewProjectTimeline(projectName);
        if (0 <= index && index < projectBook.size()) {
            int id = projectBook.get(index);
            //System.out.println(map.remove(id));
            if (map.remove(id).equals("")) {
                return false;
            }
            return project.saveEditedProjectDetails(projectBook, map, projectName);
        
        } else {
            return false;
        }
	}
	
	
	@Override
	public boolean editProgressMessage(int index, String newProgressMessage, String projectName) {
		assert(projectName != null); 	//checked by Projects first
        if (deleteProgressMessage(index, projectName)) {
            return addProgressMessage(index, newProgressMessage, projectName);
        } else {
            return false;
        }
	}

    public boolean findItem(int id, String projectName) {
        projectBook = viewProjectTimeline(projectName.toLowerCase());
        return projectBook.contains(id);
    }
}
