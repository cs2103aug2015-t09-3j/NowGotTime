import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Assumption is that the time and date format goes as follow:
 * 1) date: dd mm yyyy
 * 2) time: hhmm
 * 
 */

public class Todo extends Item {
	
	private Calendar deadline;
	private boolean hasDate = true;
	private boolean hasTime = true;
	
    
    
	
	/************************************ Constructor ***************************************/
	
	public Todo(String name, String additionalInfo) {
		this(name, additionalInfo, "", "");
	}
	
	public Todo(String name, String additionalInfo, String deadlineDate) {
		this(name, additionalInfo, deadlineDate, "");
	}
	
	public Todo(String name, String additionalInfo, String deadlineDate, String deadlineTime) {
	    super(name, additionalInfo);
	    
		this.deadline = Calendar.getInstance();
		
		hasDate = updateDate(this.deadline, deadlineDate);
		hasTime = updateTime(this.deadline, deadlineTime);
        
	}
	
	/************************************ Accessors ***************************************/
	
	public Calendar getDeadline(){
		return deadline;
	}
	
	public String getDeadlineDate(){
		return getDateString(deadline);
	}
	
	public String getDeadlineTime(){
	    return getTimeString(deadline);
	}
	
	/*********************************  Mutators ********************************************/

	
	/********************************** Process Dates ****************************************/
	
	/********************************* Process Time ******************************************/
	
	/******************************* Overriding Methods ***************************************/
	
	public String toString(){
		String allDetails = (getName() + "\n");
		allDetails = allDetails.concat(getAdditionalInfo() + "\n");
		
		if(hasDate){
			allDetails = allDetails.concat(getDeadlineDate());
		}
		else{
			allDetails = allDetails.concat("no date");
		}
		
		allDetails = allDetails.concat("\n");
		
		if(hasTime){
			allDetails = allDetails.concat(getDeadlineTime());
		}
		else{
			allDetails = allDetails.concat("no time");
		}
		return allDetails;
	}

}
