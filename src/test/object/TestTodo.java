//@@author A0130445R
package test.object;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import object.Item;
import object.Todo;
import storage.FileHandler;

public class TestTodo {
	
	protected static FileHandler clear;
	
	private static Todo todo1;
	private static Todo todo2;
	private static Todo todo3;
	private static Todo todo4;
	private static Todo todo5;
	private static Todo todo6;
	
	private SimpleDateFormat testingFormat = new SimpleDateFormat ("dd MMM yyyy HH:mm");

	@BeforeClass
	public static void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Files Cleared for Todo Testing");
	
		Item.setCounter(0);
		todo1 = new Todo("Todo1");
		todo2 = new Todo("Todo2", "");
		todo3 = new Todo("Todo3", "", "09 Nov 1993");
		todo4 = new Todo("Todo4", "", "03 May 1995", "16:00");
		todo5 = new Todo("Todo5", "");
		todo6 = new Todo("Todo6", "", "31 Oct 2015", "14:52");
	}
	
	@AfterClass
	public static void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		Item.setCounter(0);
		System.out.println("Test Files used in Todo Testing Cleared");
	}
	
	@Test
	public void testUpdateGetAndHas() throws AssertionError {
		try {
//			System.out.println("A");
			assertTrue("Failed hasDate()", todo3.hasDate());
			assertFalse("Failed (Todo has no date) hasDate()", todo1.hasDate());
			assertTrue("Failed hasTime()", todo4.hasTime());
			assertFalse("Failed (Todo has no time) hasTime()", todo2.hasTime());
			
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
//			System.out.println("B");
			assertEquals("Failed toString() hasDatehasTime", "Todo4\n03 May 1995\n16:00", todo4.toString());
			assertEquals("Failed toString() hasNoDatehasNoTime", "Todo2\nno date\nno time", todo2.toString());		
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testFormattedStringWithParameters() throws AssertionError, Exception {
		try {
//			System.out.println("C");
			assertEquals("Failed toFormattedStringWithPara (hasDatehasTime)", "[   by 14:52] Todo6", todo6.toFormattedString("25 Oct 2015"));
			assertEquals("Failed toFormattedStringWithPara (hasNoDatehasNoTime)", "[           ] Todo5", todo5.toFormattedString("25 Oct 2015"));
			
//			System.out.println("1" + todo4.toFormattedString());
//			System.out.println(todo2.toFormattedString());
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testFormattedString() throws AssertionError, Exception {
		try {
//			System.out.println("D");
//			System.out.println("2" + todo4.toFormattedString());
			assertEquals("Failed toFormattedString (hasDatehasTime)", "[   by 31 Oct 2015 14:52] Todo6", todo6.toFormattedString());
			assertEquals("Failed toFormattedString (hasNoDatehasNoTime)", "[           ] Todo5", todo5.toFormattedString());
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
}
