import java.util.Calendar;

/**
 * Assumption is that the time and date format goes as follow:
 * 1) date: dd mm yyyy
 * 2) time: hhmm
 * 
 */

public class Todo extends Item {
	
	private Calendar deadline = null;
	private boolean hasDate = true;
	private boolean hasTime = true;
    
	
	/************************************ Constructor ***************************************/
	
	public Todo(String name) {
	    this(name, "");
	}
	
	public Todo(String name, String additionalInfo) {
		this(name, additionalInfo, "", "");
	}
	
	public Todo(String name, String additionalInfo, String deadlineDate) {
		this(name, additionalInfo, deadlineDate, "");
	}
	
	public Todo(String name, String additionalInfo, String deadlineDate, String deadlineTime) {
	    super(name, additionalInfo);
	    
		this.deadline = Calendar.getInstance();
		
		hasDate = Helper.updateDate(this.deadline, deadlineDate);
		hasTime = Helper.updateTime(this.deadline, deadlineTime);
        
	}
	
	/************************************ Accessors ***************************************/
	
	public Calendar getDeadline(){
		return deadline;
	}
	
	public String getDeadlineDateString(){
		return Helper.getDateString(deadline);
	}
	
	public String getDeadlineTimeString(){
	    return Helper.getTimeString(deadline);
	}
	
	public String getDeadlineDateTimeString(){
        return Helper.getDateTimeString(deadline);
    }
	
	public boolean hasDate(){
		return hasDate;
	}
	
	public boolean hasTime(){
		return hasTime;
	}
	
	/*********************************  Mutators ********************************************/

	public boolean updateDeadlineDate(String dateString) {
	    return Helper.updateDate(deadline, dateString);
	}
	
	public boolean updateDeadlineTime(String timeString) {
        return Helper.updateTime(deadline, timeString);
    }
	
	public boolean updateDeadlineDateTime(String dateTimeString) {
        return Helper.updateDateTime(deadline, dateTimeString);
    }
	
	/********************************** Process Dates ****************************************/
	
	/********************************* Process Time ******************************************/
	
	/******************************* Overriding Methods ***************************************/
	
	public String toString(){
		String allDetails = (getName() + "\n");
		allDetails = allDetails.concat(getAdditionalInfo() + "\n");
		
		if (hasDate) {
			allDetails = allDetails.concat(getDeadlineDateString());
		}
		else {
			allDetails = allDetails.concat("no date");
		}
		
		allDetails = allDetails.concat("\n");
		
		if (hasTime) {
			allDetails = allDetails.concat(getDeadlineTimeString());
		}
		else {
			allDetails = allDetails.concat("no time");
		}
		return allDetails;
	}

}
