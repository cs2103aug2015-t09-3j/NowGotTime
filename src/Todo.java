import java.util.Calendar;

/**
 * Assumption is that the time and date format goes as follow:
 * 1) date: dd mm yyyy
 * 2) time: hhmm
 * 
 */

public class Todo {
	
	private String taskName;
	private String addInfo;
	private Calendar dateNTime;
	private boolean hasDate = true;
	private boolean hasTime = true;
	
	/************************************ Constructor ***************************************/
	
	public Todo(String name, String additionalInfo){
		this(name, additionalInfo, "", "");
		hasDate = false;
		hasTime = false;
	}
	
	public Todo(String name, String additionalInfo, String date){
		this(name, additionalInfo, date, "");
		hasTime = false;
	}
	
	public Todo(String name, String additionalInfo, String date, String time){
		taskName = name;
		addInfo = additionalInfo;
		dateNTime = Calendar.getInstance();
		if(hasDate){
			setCalendarDate(processStringDate(date));
		}
		
		if(hasTime){
			setCalendarTime(processStringTime(time));
		}
	}
	
	/************************************ Accessors ***************************************/
	
	public String getTaskName(){
		return taskName;
	}
	
	public String getAddInfo(){
		return addInfo;
	}
	
	public Calendar getCalendar(){
		return dateNTime;
	}
	
	public String getStringDate(){
		return dateNTime.get(Calendar.DATE) + " " + 
				convertMonthToWords(dateNTime.get(Calendar.MONTH)) + " " + 
				dateNTime.get(Calendar.YEAR);
	}
	
	public String getStringTime(){
		return "" + dateNTime.get(Calendar.HOUR) + ("" +dateNTime.get(Calendar.MINUTE));
	}
	
/*********************************  Mutators ********************************************/
	
	// return true if successfully changed; return false if newName is an empty string. 
	public boolean changeTaskName(String newName){
		if(newName.isEmpty()){
			return false;
		}
		taskName = newName;
		return true;
	}

	// return true if successfully changed; return false if newAddInfo is an empty string. 
	public boolean changeAddInfo(String newAddInfo){
		if(newAddInfo.isEmpty()){
			return false;
		}
		addInfo = newAddInfo;
		return true;
	}

	public boolean changeDate(String newDate){
		if(dateFormatIsCorrect(newDate)){
			setCalendarDate(processStringDate(newDate));
			return true;
		}
		return false;
	}
	
	public boolean changeTime(String newTime){
		if(timeFormatIsCorrect(newTime)){
			setCalendarTime(processStringTime(newTime));
			return true;
		}
		return false;
	}
	
/********************************** Process Dates ****************************************/
	
	private boolean dateFormatIsCorrect(String date){
		//TODO: implement check;
		return true;
	}
	
	private int[] processStringDate(String date){
		if(date.isEmpty()){
			return new int[0];
		}
		String[] splitDate = date.split(" ");
		int numDate = Integer.parseInt(splitDate[0]);
		int numMonth = setMonth(splitDate[1]);
		int numYear = Integer.parseInt(splitDate[2]);
		int[] numCalendar = {numDate, numMonth, numYear};
		return numCalendar;
	}

	private int setMonth(String month) {
		int numMonth = -1;
		
		if(month.toLowerCase().equals("january")){
			return numMonth = 0;
		}
		else if(month.toLowerCase().equals("february")){
			return numMonth = 1;
		}
		else if(month.toLowerCase().equals("march")){
			return numMonth = 2;
		}
		else if(month.toLowerCase().equals("april")){
			return numMonth = 3;
		}
		else if(month.toLowerCase().equals("may")){
			return numMonth = 4;
		}
		else if(month.toLowerCase().equals("june")){
			return numMonth = 5;
		}
		else if(month.toLowerCase().equals("july")){
			return numMonth = 6;
		}
		else if(month.toLowerCase().equals("august")){
			return numMonth = 7;
		}
		else if(month.toLowerCase().equals("september")){
			return numMonth = 8;
		}
		else if(month.toLowerCase().equals("october")){
			return numMonth = 9;
		}
		else if(month.toLowerCase().equals("november")){
			return numMonth = 10;
		}
		else if(month.toLowerCase().equals("december")){
			return numMonth = 11;
		}
		else{
			return numMonth;
		}
	}
	
	private boolean setCalendarDate(int[] numDate){
		if(numDate.length == 0){
			return false;
		}
		
		dateNTime.set(numDate[2], numDate[1], numDate[0]);
		return true;
	}

	public String convertMonthToWords(int month){
		switch(month){
			case 0: return "January";
			case 1: return "February";
			case 2: return "March";
			case 3: return "April";
			case 4: return "May";
			case 5: return "June";
			case 6: return "July";
			case 7: return "August";
			case 8: return "September";
			case 9: return "October";
			case 10: return "November";
			case 11: return "december";
			default: return "";
		}
	}
	
/********************************* Process Time ******************************************/
	
	private boolean timeFormatIsCorrect(String time){
		//TODO: implement check
		return true;
	}
	
	private int[] processStringTime(String time){
		if(time.isEmpty()){
			return new int[0];
		}
		
		char[] splitTime = time.toCharArray();
		int hours = Integer.parseInt( ("" + splitTime[0]) + splitTime[1]);
		int minutes = Integer.parseInt( ("" + splitTime[2]) + splitTime[3]);
		int[] numTime = {hours, minutes};
		return numTime;
	}

	private boolean setCalendarTime(int[] numTime){
		if(numTime.length == 0){
			return false;
		}
		dateNTime.set(Calendar.HOUR, numTime[0]);
		dateNTime.set(Calendar.MINUTE, numTime[1]);
		return true;
	}

/******************************* Overriding Methods ***************************************/
	
	public String toString(){
		String allDetails = (getTaskName() + "\n");
		allDetails = allDetails.concat(getAddInfo() + "\n");
		
		if(hasDate){
			allDetails = allDetails.concat(getStringDate());
		}
		else{
			allDetails = allDetails.concat("no date");
		}
		
		allDetails = allDetails.concat("\n");
		
		if(hasTime){
			allDetails = allDetails.concat(getStringTime());
		}
		else{
			allDetails = allDetails.concat("no time");
		}
		return allDetails;
	}

}
