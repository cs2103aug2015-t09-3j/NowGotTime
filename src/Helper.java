import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Helper {

    /* Prompt messages */
    public static final String MESSAGE_WELCOME = "Welcome to NowGotTime";
    public static final String MESSAGE_PROMPT = "command: ";

    /* Error messages */
    public static final String ERROR_INVALID_COMMAND = "unknown command '%1$s'";
    
    /* Date and time format */
    public static final String PATTERN_DATE = "dd MMM yyyy";
    public static final String PATTERN_TIME = "HH:mm";
    public static final String PATTERN_DATE_TIME = "dd MMM yyyy HH:mm";
    
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat(PATTERN_TIME);
    public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat(PATTERN_DATE_TIME);
    
    /* String Manipulation Helper functions */
    
    /**
     * Return first word from given text
     * @return The first word
     */
    public static String getFirstWord(String text) {
        // Find the first whitespace position
        int spacePosition = text.indexOf(' ');
        if (spacePosition > -1) {
            return text.substring(0, spacePosition);
        }
        else {
            return text;
        }
    }
    
    /**
     * Remove first word from text
     * @return Text without its first word
     */
    public static String removeFirstWord(String text) {
        String firstWord = getFirstWord(text);
        return text.substring(firstWord.length());
    }
    
    /* Calendar Helper functions */
    
    public static boolean updateTime(Calendar calendar, String timeString) {
        try {
            Calendar time = Calendar.getInstance();
            time.setTime(FORMAT_TIME.parse(timeString));
            
            calendar.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    public static boolean updateDate(Calendar calendar, String dateString) {
        try {
            Calendar date = Calendar.getInstance();
            date.setTime(FORMAT_DATE.parse(dateString));
            
            calendar.set(Calendar.DATE, date.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
            calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
}
