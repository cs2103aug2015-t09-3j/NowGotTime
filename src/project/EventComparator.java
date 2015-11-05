//@@author A0130445R

package project;
import object.Event;
import java.util.*;

public class EventComparator implements Comparator<Event> {
	
	Calendar event1Start, event2Start, event1End, event2End;
	
	@Override
	public int compare(Event event1, Event event2) {
		extractDate(event1, event2);
		return compareStart();
	}
	
	private int compareStart() {
		/*if (event1Start.compareTo(event2Start) == 1) {
			return 1;
		} else if (event1Start.compareTo(event2Start) == 0){
			return compareEnd();
		} else {
			return -1;
		}*/
		if (event1Start.compareTo(event2Start) == 0){
			return compareEnd();
		} else {
			return event1Start.compareTo(event2Start);
		}
	}
	
	private int compareEnd() {
		/*if (event1End.compareTo(event2End) == 1) {
			return 1;
		} else if (event1End.compareTo(event2End) == 0){
			return 0;
		} else {
			return -1;
		}*/
		return event1End.compareTo(event2End);
	}
	
	private void extractDate(Event event1, Event event2) {
		event1Start = event1.getStartCalendar();
		event2Start = event2.getStartCalendar();
		event1End = event1.getEndCalendar();
		event2End = event2.getEndCalendar();
	}
}
