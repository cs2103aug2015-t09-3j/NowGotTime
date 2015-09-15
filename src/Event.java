import java.util.Calendar;

/**
 * Assumption is that the time and date format goes as follow:
 * 1) date: dd mm yyyy
 * 2) time: hhmm
 * 
 */

public class Event extends Item {
	private static final String START_DATE = "start";
	private static final String END_DATE = "end";
	
	private Calendar start;
	private Calendar end;
	
	
	/*********************************** Constructor ******************************************/
	
	public Event(String name, String date, String startTime, String endTime, String additionalInfo){
		this(name, date, date, startTime, endTime, additionalInfo);
	}
	
	public Event(String name, String startDate, String endDate, String startTime, String endTime, String additionalInfo ){
		super(name, additionalInfo);
		
		start = Calendar.getInstance();
		end = Calendar.getInstance();
		
		updateDate(start, startDate);
		updateDate(start, endDate);
		updateTime(end, startTime);
		updateTime(end, endTime);
		
	}
	
	/*********************************** Accessors ********************************************/
	
	public Calendar getStartDateCalendar(String calendarChoice){
		Calendar calendar = chooseCalendar(calendarChoice);
		return calendar;
	}
	
	public String getStringDate(String choice){
		Calendar calendar = chooseCalendar(choice);
		
		return calendar.get(Calendar.DATE) + " " + 
				convertMonthToWords(calendar.get(Calendar.MONTH)) + " " + 
				calendar.get(Calendar.YEAR);
	}
	
	public String getStringTime(String choice){
		Calendar calendar = chooseCalendar(choice);
		String hour = calendar.get(Calendar.HOUR) + "";
		String minute = calendar.get(Calendar.MINUTE) + "";
		return hour.concat(minute);
	}
	
/**************************************  Mutators ********************************************/
	
	public boolean changeDate(String newDate, String calendarChoice){
				
		if(dateFormatIsCorrect(newDate)){
			setCalendarDate(processStringDate(newDate), calendarChoice);
			return true;
		}
		return false;
	}
	
	public boolean changeTime(String newTime,String calendarChoice){
		if(timeFormatIsCorrect(newTime)){
			setCalendarTime(processStringTime(newTime), calendarChoice);
			return true;
		}
		return false;
	}
	
/*********************************** Process Dates *******************************************/
	
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
	
	private boolean setCalendarDate(int[] numDate, String calendarChoice){
		if(numDate.length == 0){
			return false;
		}
		Calendar calendar = chooseCalendar(calendarChoice);
		calendar.set(numDate[2], numDate[1], numDate[0]);
		return true;
	}

	private String convertMonthToWords(int month){
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
	
/************************************* Process Time ****************************************/
	
	private boolean timeFormatIsCorrect(String time){
		//TODO: implement check
		return true;
	}
	
	private int[] processStringTime(String time){
		if(time.isEmpty()){
			return new int[0];
		}
		
		char[] splitTime = time.toCharArray();
		int hours = Integer.parseInt( ("" + splitTime[0]) + ("" + splitTime[1]));
		int minutes = Integer.parseInt(("" + splitTime[2]) + ("" + splitTime[3]));
		int[] numTime = {hours, minutes};
		return numTime;
	}

	private boolean setCalendarTime(int[] numTime, String calendarChoice){
		if(numTime.length == 0){
			return false;
		}
		Calendar calendar = chooseCalendar(calendarChoice);
		calendar.set(Calendar.HOUR, numTime[0]);
		calendar.set(Calendar.MINUTE, numTime[1]);
		return true;
	}

/*********************************** Overriding Methods ***********************************/
	
	public String toString(){
		return  getName() + "\n" 
				+ getAdditionalInfo() + "\n" 
				+ getStringDate(START_DATE) + "\n" 
				+ getStringDate(END_DATE) + "\n" 
				+ getStringTime(START_DATE) + "\n" 
				+ getStringTime(END_DATE);
	}
	
/************************************ Other Methods ***************************************/
	
	private Calendar chooseCalendar(String choice){
		if(choice.equals(START_DATE)){
			return start;
		}
		else if(choice.equals(END_DATE)){
			return end;
		}
		else{
			return Calendar.getInstance();
		}
	}
}