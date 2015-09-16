
public interface ProjectManager {
	
	public boolean createProject(String projectName);
	
	public boolean deleteProject(String projectName);
	public boolean deleteProjectTask(String projectTaskName);
	public boolean deleteProjectTask(int index);
	
	public boolean editProject(String taskName);
	public boolean editProject(int index);
	
	public boolean listExistingProjects();
	
	public boolean viewProject(String projectName);
	public boolean viewProject(int index);
}
