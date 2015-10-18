package storagetest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.FileProjectHandler;

public class FileProjectHandlerTest {
	
	private static final String PROJECT_OVERVIEWER = "\\projectOverviewer.txt";
	private static String baseDirectory;
	private static FileProjectHandler fProjH;	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		baseDirectory = System.getProperty("user.dir").toString() + "\\testFiles";
		System.out.println("This is the base directory: \n" + baseDirectory);
		
		preparationCleanUp.cleanUp(baseDirectory);
		preparationCleanUp.setUpDirectory(baseDirectory);
		
	}
	
	@Test
	public void testFileProjectHandlerInIdealSequence(){
		testFileProjectHandlerConstructor();
		testCreateNewProject();
		testDeleteProject();
		testSaveEditedProjectDetails();
		testRetrieveProject();
		testRetrieveProjectProgress();
		testGetListOfExistingProjects();
		testChangeDirectory();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Exiting, cleaning up folders");
		if(preparationCleanUp.cleanUp(baseDirectory)){
			System.out.println("Clean up completed. bye");
		}
	}
	
/*****************************************************************************/
	
	public void testFileProjectHandlerConstructor() {
		fProjH = new FileProjectHandler(baseDirectory);

		File overviewFile = new File(baseDirectory + PROJECT_OVERVIEWER);
		System.out.println(baseDirectory + PROJECT_OVERVIEWER); //for checking purposes
		
		assertEquals("Check if overview file exist", 
				true, overviewFile.exists());
		
		assertEquals("Check if there are no existing project", 
				new ArrayList<String>(), fProjH.getListOfExistingProjects());

	}
	
	public void testCreateNewProject() {
		assertEquals("Test adding null project name", 
				false, fProjH.createNewProject(null));
		
		assertEquals("Test adding valid project name", 
				true, fProjH.createNewProject("project1"));
		
		assertEquals("Test adding repeated project name", 
				false, fProjH.createNewProject("project1"));
	}

	public void testDeleteProject() {
		fProjH.createNewProject("project to be deleted");
		
		assertEquals("Test delete project by name", 
				true, fProjH.deleteProject("project to be deleted"));
		
		assertEquals("Test delete non-existing project", 
				false, fProjH.deleteProject("non-existing project"));
		
		assertEquals("Test delete project with null", 
				false, fProjH.deleteProject(null));		
	}
	
	public void testSaveEditedProjectDetails() {
		HashMap<Integer, String> progressBook = new HashMap<Integer, String>();
		ArrayList<Integer> projectBook = fProjH.retrieveProject("project1");
		
		projectBook.add(1); 
		progressBook.put(1, "one");
		
		projectBook.add(2); 
		progressBook.put(2, "two");
		
		projectBook.add(3); 
		progressBook.put(3, "three");
		
		assertEquals("Test saving", 
				true, fProjH.saveEditedProjectDetails(projectBook, progressBook, "project1"));

	}
	
	public void testRetrieveProject() {
		ArrayList<Integer> expectedProjEventId = new ArrayList<Integer>();
		expectedProjEventId.add(1);
		expectedProjEventId.add(2);
		expectedProjEventId.add(3);
		
		assertEquals("Test retrieving", 
				expectedProjEventId, fProjH.retrieveProject("project1"));
		
		assertEquals("Test retrieval of non-existing project", 
				new ArrayList<Integer>(), fProjH.retrieveProject("non-existing project"));
	}

	public void testRetrieveProjectProgress() {
		HashMap<Integer, String> progressBook = new HashMap<Integer, String>();
		
		assertEquals("Test retrieval of progress from non-existing project", 
				progressBook, fProjH.retrieveProjectProgress());
		
		progressBook.put(1, "one");
		progressBook.put(2, "two");
		progressBook.put(3, "three");
		fProjH.retrieveProject("project1");
		
		assertEquals("Test retrieval of progress from non-existing project", 
				progressBook, fProjH.retrieveProjectProgress());
		

		fProjH.createNewProject("project2");
		fProjH.retrieveProject("project2");
		progressBook.clear();
		
		assertEquals("Test retrieval of empty event progress", 
				progressBook, fProjH.retrieveProjectProgress());
		
	}
	
	public void testGetListOfExistingProjects() {
		
		ArrayList<String> expectedExistingProjects = new ArrayList<String>();
		expectedExistingProjects.add("project1");
		expectedExistingProjects.add("project2");
		
		assertEquals("Test retrieval of names of existing project", 
				expectedExistingProjects, fProjH.getListOfExistingProjects());
	}

	public void testSetNewDirectory() {
		assertEquals("Test with new directory being null",
				false, fProjH.setNewDirectory(null));
		
		assertEquals("Test if new directory does not exist",
				false, fProjH.setNewDirectory("this is not a directory format"));
		
		assertEquals("Test with valid new directory",
				false, fProjH.setNewDirectory(baseDirectory + "\\alternateTestFiles"));
	}

	public void testReadAll() {
		assertEquals("Test readAll method",
				true, fProjH.readAll());
	}

	public void testWriteAll() {
		assertEquals("Test writeAll method",
				true, fProjH.writeAll());
	}
	
	public void testChangeDirectory(){
		testReadAll();
		testSetNewDirectory();
		testWriteAll();
		
		//re-test retrieval
		testRetrieveProject();
		testRetrieveProjectProgress();
		testGetListOfExistingProjects();
	}
	
//	private static boolean cleanUp(String baseDirectory){
//		File dir = new File(baseDirectory);
//		
//		if(dir.isDirectory() && dir.list().length > 0){
//			for(File file: dir.listFiles()) file.delete(); 
//		}
//		
//		Path path = Paths.get(baseDirectory);
//		
//		try {
//			Files.delete(path);
//			return true;
//		} catch(NoSuchFileException e) {
//			System.out.println("No such file exist to delete");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return false;
//	}
//	
//	
//	private static boolean setUpDirectory(String baseDirectory){
//		File dir = new File(baseDirectory);
//		if (!dir.exists()) {
//			dir.mkdir();
//			return true;
//		}		
//		return false;
//	}


}
