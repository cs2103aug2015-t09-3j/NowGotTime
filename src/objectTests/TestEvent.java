package objectTests;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import object.Event;

import storage.FileHandler;

public class TestEvent {

	protected static FileHandler clear;
	
	// Make sure dates are in DD MMM YYY format 
	private Event event1 = new Event("EventName1", "25 Oct 2015", "26 Oct 2015", "10:00", "21:00", "");
	private Event event2 = new Event("EventName2", "21 Oct 2015", "31 Oct 2015", "19:00", "23:00", "");
	
	// Use SimpleDateFormat because of the millisecond difference when comparing the Calendar Objects
	private SimpleDateFormat testingFormat = new SimpleDateFormat ("dd MMM yyyy HH:mm");
	
	@BeforeClass
	public static void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Files Cleared for Event Class Testing");
	}
	
	@AfterClass
	public static void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println("Test Files used in Event Testing Cleared");
	}
	
	@Test
	public void testUpdateAndGetMethods() throws AssertionError {
		try {
			assertTrue("Failed updateStart()", event1.updateStart("03 May 1995 07:00"));
			Calendar retrievedStartCalendar = event1.getStartCalendar();
			// Use SimpleDateFormat because of the millisecond difference when comparing the Calendar Objects
			assertEquals("Failed getStartCalendar()", "03 May 1995 07:00", testingFormat.format(retrievedStartCalendar.getTime()));
			
			assertTrue("Failed updateEnd()", event2.updateEnd("09 Nov 1993 19:00"));
			Calendar retrievedEndCalendar = event2.getEndCalendar();
			//Use SimpleDateFormat because of the millisecond difference when comparing the Calendar Objects
			assertEquals("Failed getEndCalendar()", "09 Nov 1993 19:00", testingFormat.format(retrievedEndCalendar.getTime()));
			
			assertTrue("Failed updateStartDate()", event1.updateStartDate("26 Oct 2015"));
			String retrievedStartDate = event1.getStartDateString();
			assertEquals("Failed getStartDateString()", "26 Oct 2015", retrievedStartDate);
			
			assertTrue("Failed updateEndDate()", event1.updateEndDate("29 Oct 2015"));
			String retrievedEndDate = event1.getEndDateString();
			assertEquals("Failed getEndDateString()", "29 Oct 2015", retrievedEndDate);
			
			assertTrue("Failed updateStartTime()", event2.updateStartTime("16:00"));
			String retrievedStartTime = event2.getStartTimeString();
			assertEquals("Failed getStartTimeString()", "16:00", retrievedStartTime);
			
			assertTrue("Failed updateEndTime()", event2.updateEndTime("22:00"));
			String retrievedEndTime = event2.getEndTimeString();
			assertEquals("Failed getEndTimeString()", "22:00", retrievedEndTime);
			
			assertTrue("Failed updateStartDateTime()", event2.updateStartDateTime("01 Jan 2001 11:00"));
			String retrievedStartDateTime = event2.getStartDateTimeString();
			assertEquals("Failed getStartDateTimeString()", "01 Jan 2001 11:00", retrievedStartDateTime);
			
			assertTrue("Failed updateEndDateTime()", event2.updateEndDateTime("31 Dec 2001 23:59"));
			String retrievedEndDateTime = event2.getEndDateTimeString();
			assertEquals("Failed getEndDateTimeString()", "31 Dec 2001 23:59", retrievedEndDateTime);
			
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
