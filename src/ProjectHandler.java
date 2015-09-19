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

		return project.createNewProject(projectName);
	}

	@Override
	public boolean deleteProject(String projectName) {
		
		return project.deleteProject(projectName);
	}

	@Override
	public boolean deleteProjectEvent(String eventName, String projectName) {
		
		for (Event anEvent : projectBook)
		{
			if (anEvent.getName().equals(eventName))
			{
				projectBook.remove(anEvent);
				project.saveEditedProjectDetails(projectBook, projectName);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean deleteProjectEvent(int index, String projectName) {
		
		if (index < projectBook.size())
		{
			projectBook.remove(index);
			project.saveEditedProjectDetails(projectBook, projectName);
			return true;
		}
		
		else
			return false;
	}

	@Override
	public boolean editProjectEvent(int eventIndex, int infoIndex, String newValue) {
		
		if (eventIndex <= 0 || eventIndex > projectBook.size())
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
				
				case (6):
				{
					event.setAdditionalInfo(newValue);
					break;
				}
			}
			
			return true;
		}
	}

	@Override
	public boolean editProjectEvent(String name, int infoIndex, String newValue) {
		
		if (!projectBook.contains(name))
		{
			return false;
		}

		else
		{
			//Event event = projectBook.get(projectBook.indexOf(name));
			
			//recursion
				int eventIndex = projectBook.indexOf(name);
				return editProjectEvent(eventIndex, infoIndex, newValue);
		}	
		
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
		
		projectBook = project.retrieveProjectTimeLine(projectName);
		return projectBook;
	}

	@Override
	public ArrayList<Event> viewProjectTimeline(int index) {
		// TODO Auto-generated method stub
		
		String projectName = projectList.get(index);
		projectBook = project.retrieveProjectTimeLine(projectName);
		return projectBook;
	}
}