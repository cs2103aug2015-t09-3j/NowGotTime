//@@author A0130445R
package test.object;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import object.Event;
import object.Item;
import storage.FileHandler;

public class TestEvent {

	private static final String EXPECTED_TO_STRING = "2\nEventName3\n21 Oct 2015\n30 Oct 2015\n17:00\n02:00";

	private static final String MESSAGE_TEAR_DOWN = "Test Files used in Event Testing Cleared";

	private static final String MESSAGE_SET_UP = "Files Cleared for Event Class Testing";

	private static final String DATE_FORMAT = "dd MMM yyyy HH:mm";

	private static final String ADD_INFO_BLANK = "";

	private static final String EVENT3_END_TIME = "02:00";

	private static final String EVENT2_END_TIME = "23:00";

	private static final String EVENT1_END_TIME = "21:00";

	private static final String EVENT3_START_TIME = "17:00";

	private static final String EVENT2_START_TIME = "19:00";

	private static final String EVENT1_START_TIME = "10:00";

	private static final String EVENT3_END_DATE = "30 Oct 2015";

	private static final String EVENT2_END_DATE = "31 Oct 2015";

	private static final String EVENT1_END_DATE = "26 Oct 2015";

	private static final String EVENT2_START_DATE = "21 Oct 2015";

	private static final String EVENT1_START_DATE = "25 Oct 2015";

	private static final String EVENT_NAME3 = "EventName3";

	private static final String EVENT_NAME2 = "EventName2";

	private static final String EVENT_NAME1 = "EventName1";

	protected static FileHandler clear;
	
	// Make sure dates are in DD MMM YYY format 
	private Event event1 = new Event(EVENT_NAME1, EVENT1_START_DATE, EVENT1_END_DATE, EVENT1_START_TIME, EVENT1_END_TIME, ADD_INFO_BLANK);
	private Event event2 = new Event(EVENT_NAME2, EVENT2_START_DATE, EVENT2_END_DATE, EVENT2_START_TIME, EVENT2_END_TIME, ADD_INFO_BLANK);
	private Event event3 = new Event(EVENT_NAME3, EVENT2_START_DATE, EVENT3_END_DATE, EVENT3_START_TIME, EVENT3_END_TIME, ADD_INFO_BLANK);
	
	// Use SimpleDateFormat because of the millisecond difference when comparing the Calendar Objects
	private SimpleDateFormat testingFormat = new SimpleDateFormat (DATE_FORMAT);
	
	@Before
	public void beforeTest() {
	    Item.setCounter(0);
	}
	
	@BeforeClass
	public static void setUpBeforeTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println(MESSAGE_SET_UP);
	}
	
	@AfterClass
	public static void tearDownAfterTesting() throws Exception {
		clear = new FileHandler();
		clear.clearAll();
		System.out.println(MESSAGE_TEAR_DOWN);
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
			
			assertTrue("Failed updateStartDate()", event1.updateStartDate(EVENT1_END_DATE));
			String retrievedStartDate = event1.getStartDateString();
			assertEquals("Failed getStartDateString()", EVENT1_END_DATE, retrievedStartDate);
			
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
			String isString = event3.toString();
			assertEquals("Failed toString()",  EXPECTED_TO_STRING, isString);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testFormattedStringWithParameters() throws AssertionError, Exception {
		try {
			String formattedWithParameters = event3.toFormattedString("09 Nov 1993");
			assertEquals("Fail toFormattedString(para)", EVENT_NAME3, formattedWithParameters);
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}
	
	@Test
	public void testFormattedString() throws AssertionError, Exception {
		try {
			String formatted = event3.toFormattedString();
			assertEquals("Fail toFormattedString", EVENT_NAME3, formatted);
			
		} catch (AssertionError AE) {
			System.out.println(AE.getMessage());
			throw AE;
		}
	}

}
