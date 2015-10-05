import java.util.ArrayList;


public class ProjectHandler implements ProjectManager{

	private FileHandler project;
	private ArrayList<Event> projectBook = new ArrayList<Event>();
	private ArrayList<String> projectList = new ArrayList<String>();
		
	public ProjectHandler (){
		project = new FileHandler();
	}
	
	@Override
	public boolean createProject(String projectName) {
		//check: file name too long and blank file name

		return project.createNewProject(projectName.toLowerCase());
	}
	
	@Override
	public boolean addProjectEvent(Event eventName, String projectName) {
		
		// Don't create new project, in case user types wrongly. Stef, Prompt user to create proj first.
		if (!listExistingProjects().contains(projectName.toLowerCase()))
		{
			return false;
		}
		
		else
		{
			projectBook = viewProjectTimeline(projectName.toLowerCase());
			projectBook.add(eventName);
			project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
			return true;
		}
	}

	@Override
	public boolean deleteProject(String projectName) {
		
		return project.deleteProject(projectName.toLowerCase());
	}

	@Override
	public boolean deleteProjectEvent(String eventName, String projectName) {
		
		for (Event anEvent : projectBook)
		{
			if (anEvent.getName().toLowerCase().equals(eventName.toLowerCase()))
			{
				projectBook.remove(anEvent);
				project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean deleteProjectEvent(int index, String projectName) {
		
		if (index < projectBook.size())
		{
			System.out.println("A " + projectBook.remove(index));
			project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
			return true;
		}
		
		else
			return false;
	}

	@Override
	public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue, String projectName) {
		
		if (eventIndex < 0 || eventIndex > projectBook.size())
		{
			return false;
		}

		else
		{
			Event event = projectBook.get(eventIndex);
			
			switch(infoIndex)
			{
				case (1):
				{	
					event.setName(newValue);
					break;
				}
				
				case (2):
				{
					event.updateStartDate(newValue); //Stef, for single date events, change both case 2 and 3
					break;
				}
				
				case (3):
				{
					event.updateEndDate(newValue);
					break;
				}
				
				case (4):
				{
					event.updateStartTime(newValue);
					break;
				}
				
				case (5):
				{
					event.updateEndTime(newValue);
					break;
				}
				
				/*case (6):
				{
					event.setAdditionalInfo(newValue);
					break;
				}*/
			}
			
			project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
			return true;
		}
	}

	@Override
	public boolean editProjectEvent(Event eventName, int infoIndex, String newValue, String projectName) {
		//for testing: System.out.println("1" + projectBook);
	
		for (int i=0; i<projectBook.size(); i++)
		{
			// System.out.println(eventName.getName() + "compare " + projectBook.get(i).getName());
			if (eventName.getName().toLowerCase().equals(projectBook.get(i).getName().toLowerCase()))
			{
				// System.out.println("entered" + projectBook.indexOf(eventName) + " " + i);
				int eventIndex = i;
				/*Event event = projectBook.get(eventIndex);
				switch(infoIndex)
				{
					case (1):
					{
						event.setName(newValue);
						break;
					}
					
					case (2):
					{
						event.updateStartDate(newValue); //Stef, for single date events, change both case 2 and 3
						break;
					}
					
					case (3):
					{
						event.updateEndDate(newValue);
						break;
					}
					
					case (4):
					{
						event.updateStartTime(newValue);
						break;
					}
					
					case (5):
					{
						event.updateEndTime(newValue);
						break;
					}
					
					/*case (6):
					{
						event.setAdditionalInfo(newValue);
						break;
					}
				}*/
				
				return editProjectEvent(eventIndex, infoIndex, newValue, projectName);
				//project.saveEditedProjectDetails(projectBook, projectName.toLowerCase());
				//return true;
			}
		}
		
		return false;
		
		/* if (!projectBook.contains(eventName))
		{	
			return false;
		}

		else
		{
			//Event event = projectBook.get(projectBook.indexOf(name));
			
			//recursion
				
		}*/	
		
			//if recursion doesn't work:
		
			/*switch(infoIndex)
			{
				case (1):
				{
					event.setName(newValue);
					break;
				}
				
				case (2):
				{
					event.updateStartDate(newValue); //Stef, for single date events, change both case 2 and 3
					break;
				}
				
				case (3):
				{
					event.updateEndDate(newValue);
					break;
				}
				
				case (4):
				{
					event.updateStartTime(newValue);
					break;
				}
				
				case (5):
				{
					event.updateEndTime(newValue);
					break;
				}
				
				case (6):
				{
					event.setAdditionalInfo(newValue);
					break;
				}
			}
			
			return true;
		}*/
			
	}

	@Override
	public ArrayList<String> listExistingProjects() {
		
		projectList = project.getListOfExistingProject();
		return projectList;
	}
	
	@Override
	public ArrayList<Event> viewProjectTimeline(String projectName) {
		
		projectBook = project.retrieveProjectTimeLine(projectName.toLowerCase());
		return projectBook;
	}

	@Override
	public ArrayList<Event> viewProjectTimeline(int index) {
		// TODO Auto-generated method stub
		
		if (index < projectList.size())
		{
			String projectName = projectList.get(index);
			projectBook = project.retrieveProjectTimeLine(projectName);
			return projectBook;
		}
		
		else
		{
			return null;
		}
	}
	
	/*
	 -Change from arraylist of events to arraylist of int (event IDs). Edit, add, del by ID as well.
	 -Change all the saves to saveAll.
	 -Change arraylist of events to arraylist of items => includes both to-dos and events.
	
	 -Have a search function to search through project book (arraylist of events). Return event ID.
	 -Progress bar function, show % completed.
	 -Progress function. => Hashmap in project (containing int(ID) and string(Addinfo)). [HM key = ID]
			==> String Project Name, Int ID and String Addinfo passed to me.
			==> setProgress (String progressString, String projectNAme, Int ID)
	 -Progress (delete and edit) functions too.
	 		==> deleteProgress (); and editProgress ();
	 -Have a method to call RX for viewing. I pass ID to RX, RX returns name of event. 
			==> I return Stef arraylist of string (events).
	 
	 **see phone picture. Note that Jon's event does not have additional info. only proj has. thus nid to
	   add additional info into the event. BUT I DON'T SAVE IT, else Jon's events will also have additional
	   info. Additional info is exclusive only to projects.**
	*/
}
