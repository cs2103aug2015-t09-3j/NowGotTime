package objectTests;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import object.Todo;
import storage.FileHandler;

public class TestTodo {
	
	protected static FileHandler clear;
	
	private Todo todo1 = new Todo("Todo1");
	private Todo todo2 = new Todo("Todo2", "");
	private Todo todo3 = new Todo("Todo3", "", "09 Nov 1993");
	private Todo todo4 = new Todo("Todo4", "", "03 May 1995", "16:00");
	
	private SimpleDateFormat testingFormat = new SimpleDateFormat ("dd MMM yyyy HH:mm");

	@BeforeClass
	public static void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Files Cleared for Item Testing");
	}
	
	@AfterClass
	public static void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Test Files used in Item Testing Cleared");
	}
	
	@Test
	public void testUpdateGetAndHas() throws AssertionError {
		try {
			assertTrue("Failed hasDate()", todo3.hasDate());
			assertFalse("Failed (Todo has no date) hasDate()", todo1.hasDate());
			assertTrue("Failed hasTime()", todo4.hasTime());
			assertFalse("Failed (Todo has no time) hasTime()", todo2.hasDate());
			
			String deadlineRetrieved = testingFormat.format(todo4.getDeadline().getTime());
			assertEquals("Failed getDeadline()", "03 May 1995 16:00", deadlineRetrieved);
			
			assertTrue("Failed updateDeadlineDate()", todo3.updateDeadlineDate("26 Oct 2015"));
			String retrievedDeadlineDate = todo3.getDeadlineDateString();
			assertEquals("Failed getDeadlineDateString()", "26 Oct 2015", retrievedDeadlineDate);
			
			assertTrue("Failed updateDeadlineTime()", todo4.updateDeadlineTime("17:00"));
			String retrievedDeadlineTime = todo4.getDeadlineTimeString();
			assertEquals("Failed getDeadlineTimeString()", "17:00", retrievedDeadlineTime);
			
			assertTrue("Failed updateDeadlineDateTime()", todo4.updateDeadlineDateTime("24 Oct 2015 22:00"));
			String retrievedDeadlineDateTime = todo4.getDeadlineDateTimeString();
			assertEquals("Failed getDeadlineDateTimeString()", "24 Oct 2015 22:00", retrievedDeadlineDateTime);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testToString() throws AssertionError {
		try {
			
			
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testFormattedStringWithParameters() throws AssertionError {
		try {
			
			
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testFormattedString() throws AssertionError {
		try {
			
			
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
}
