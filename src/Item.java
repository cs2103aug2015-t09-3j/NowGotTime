import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Item {
    private String name;
    private String additionalInfo;
    
    private static final String PATTERN_DATE = "dd MMM yyyy";
    private static final String PATTERN_TIME = "HH:mm";
    
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(PATTERN_DATE);
    private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat(PATTERN_TIME);
    
    public Item(String name, String additionalInfo) {
        setName(name);
        setAdditionalInfo(additionalInfo);
    }
    
    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        name = name.trim();
        if (name.isEmpty()) {
            return false;
        }
        else {
            this.name = name;
            return true;
        }
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public boolean updateTime(Calendar calendar, String timeString) {
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
    
    public boolean updateDate(Calendar calendar, String dateString) {
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
    
    public String getDateString(Calendar calendar) {
        return FORMAT_DATE.format(calendar.getTime());
    }
    
    public String getTimeString(Calendar calendar) {
        return FORMAT_TIME.format(calendar.getTime());
    }
    
    
}
