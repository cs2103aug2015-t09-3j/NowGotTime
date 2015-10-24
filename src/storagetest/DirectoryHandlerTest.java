package storagetest;

import static org.junit.Assert.assertEquals;

import java.io.File;

import object.Item;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import storage.DirectoryHandler;

//TODO: test creation of files when there are already files of the same name

public class DirectoryHandlerTest {

	private static final String ALTERNATE_TEST_FILES = "alternateTestFiles";
	private static final String DATABASE = "database";
	private DirectoryHandler dirHand = new DirectoryHandler();
	private static String newBaseDirectory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Item.setCounter(0);
		PreparationCleanUp.manualCleanUp();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Item.setCounter(0);
		PreparationCleanUp.manualCleanUp();
		PreparationCleanUp.cleanUp(newBaseDirectory);
	}

	@Test
	public void testDirectoryHandler() {
		String baseDirectory = System.getProperty("user.dir").toString() + "/database";
		File file = new File(baseDirectory + "/Event");
		assertEquals("Test with valid new directory",
				true, file.exists());
		
		file = new File(baseDirectory + "/Todo");
		assertEquals("Test with valid new directory",
				true, file.exists());
		
		file = new File(baseDirectory + "/Project");
		assertEquals("Test with valid new directory",
				true, file.exists());
	}

	@Test
	public void testSetNewBaseDirectory() {
		newBaseDirectory = null;
		assertEquals("Test change to null directory",
				false, dirHand.setNewBaseDirectory(newBaseDirectory));
		
		newBaseDirectory = "";
		assertEquals("Test change to empty directory",
				false, dirHand.setNewBaseDirectory(newBaseDirectory));
		
		newBaseDirectory = "non-existing";
		assertEquals("Test change to non-existing directory",
				false, dirHand.setNewBaseDirectory(newBaseDirectory));
		
		newBaseDirectory = System.getProperty("user.dir").toString() + "/" + ALTERNATE_TEST_FILES + "/";
		PreparationCleanUp.makeNewDirectory(newBaseDirectory);
		assertEquals("Test with valid new directory",
				true, dirHand.setNewBaseDirectory(newBaseDirectory));
		
		testIfDirectoriesExist();
		
	}

	private void testIfDirectoriesExist() {
		String baseDirectory = System.getProperty("user.dir").toString() + "/" + ALTERNATE_TEST_FILES;
		File file = new File(baseDirectory + "/" + DATABASE + "/Event");
		assertEquals("Test with valid new directory",
				true, file.exists());
		
		file = new File(baseDirectory + "/" + DATABASE + "/Todo");
		assertEquals("Test with valid new directory",
				true, file.exists());
		
		file = new File(baseDirectory + "/" + DATABASE + "/Project");
		assertEquals("Test with valid new directory",
				true, file.exists());
	}
		
}
