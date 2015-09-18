import java.util.ArrayList;


public class ProjectHandler implements ProjectManager{

	@Override
	public boolean createProject(String projectName) {
		
		return false;
	}

	@Override
	public boolean deleteProject(String projectName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProjectEvent(String eventName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProjectEvent(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editProjectEvent(int eventIndex, int infoIndex,
			String newValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editProjectEvent(String name, int infoIndex, String newValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Event> viewProjectTimeline(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Event> viewProjectTimeline(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> listExistingProjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
