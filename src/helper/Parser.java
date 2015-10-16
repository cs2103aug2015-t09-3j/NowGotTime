package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    
    // "<name>" on <start> to <end>
    public static final String PATTERN_ADD_EVENT                 = "\\s*\"(?<name>.+)\"\\s+on (?<start>.+) to (?<end>.+)\\s*";
    // "<name>" on <due>
    public static final String PATTERN_ADD_TASK                  = "\\s*\"(?<name>.+)\"\\s+on (?<date>.+)\\s*";
    // project "<name>"
    public static final String PATTERN_PROJECT                   = "\\s*project\\s+\"(?<name>.+)\"\\s*";
    // "<keyword>" to "<name>"
    public static final String PATTERN_ADD_KEYWORD_TO_PROJECT    = "\\s*\"(?<keyword>.+)\"\\s+to\\s+\"(?<name>.+)\"\\s*";
    // <index> to "<name>"
    public static final String PATTERN_ADD_INDEX_TO_PROJECT      = "\\s*(?<index>\\d+)\\s+to\\s+\"(?<name>.+)\"\\s*";
    // progress "<progress>" <index> to "<name>"
    public static final String PATTERN_ADD_PROGRESS              = "\\s*progress\\s+\"(?<progress>.+)\"\\s+(?<index>\\d+)\\s+to\\s+\"(?<name>.+)\"\\s*";
    // <index> from "<project>"
    public static final String PATTERN_DELETE_INDEX_FROM_PROJECT = "\\s*(?<index>\\d+)\\s+from\\s+\"(?<name>.+)\"\\s*";
    // progress <index>
    public static final String PATTERN_DELETE_PROGRESS           = "\\s*progress\\s+(?<index>\\d+)\\s+from\\s+\"(?<name>.+)\"\\s*";
    // "<keyword>" <field>(name) "<name>"
    public static final String PATTERN_EDIT_NAME_BY_KEY          = "\\s*\"(?<keyword>.+)\"\\s+(?<field>name)\\s+\"(?<name>.+)\"\\s*";
    // "<keyword>" <field>(start|end|due) <date>
    public static final String PATTERN_EDIT_DATE_TIME_BY_KEY     = "\\s*\"(?<keyword>.+)\"\\s+(?<field>start|end|due)\\s+(?<date>.+)\\s*";
    // <index> <field>(name) "<name>"
    public static final String PATTERN_EDIT_NAME_BY_INDEX        = "\\s*(?<index>\\d+)\\s+(?<field>name)\\s+\"(?<name>.+)\"\\s*";
    // <index> <field>(start|end|due) <date>
    public static final String PATTERN_EDIT_DATE_TIME_BY_INDEX   = "\\s*(?<index>\\d+)\\s+(?<field>start|end|due)\\s+(?<date>.+)\\s*";
    // project "<name>" <field>(name) "<value>"
    public static final String PATTERN_EDIT_PROJECT              = "\\s*project\\s+\"(?<name>.+)\"\\s+(?<field>name)\\s+\"(?<value>.+)\"\\s*";
    // "<name>"
    public static final String PATTERN_NAME                      = "\\s*\"(?<name>.+)\"\\s*"; 
    // <index>
    public static final String PATTERN_INTEGER                   = "\\s*(?<index>\\d+)\\s*"; 
    // <value>
    public static final String PATTERN_ANY                       = "\\s*(?<value>.+)\\s*";  
    // ~empty~
    public static final String PATTERN_EMPTY                     = "\\s*";

    // String
    public static final String TAG_NAME     = "name";
    public static final String TAG_VALUE    = "value";
    public static final String TAG_KEYWORD  = "keyword";
    public static final String TAG_PROGRESS = "progress";
    public static final String TAG_FIELD    = "field";
    
    // Integer
    public static final String TAG_INDEX    = "index";
    
    // Date
    public static final String TAG_START    = "start";
    public static final String TAG_END      = "end";
    public static final String TAG_DATE      = "date";
    
    public static Matcher matchRegex(String args, String patternString) {
        Pattern regex = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher = regex.matcher(args);
        if (regexMatcher.matches()) {
            
            // check if valid calendar string
            try {
                if (!CalendarHelper.isCalendarString(regexMatcher.group(TAG_START))) {
                    return null;
                }
            } catch (IllegalArgumentException iae) {
                
            }
            
            try {
                if (!CalendarHelper.isCalendarString(regexMatcher.group(TAG_END))) {
                    return null;
                }
            } catch (IllegalArgumentException iae) {
                
            }
            
            try {
                if (!CalendarHelper.isCalendarString(regexMatcher.group(TAG_DATE))) {
                    return null;
                }
            } catch (IllegalArgumentException iae) {
                
            }
            
            return regexMatcher;
        }
        else {
            return null;
        }
    }
    
    public static boolean matches(String args, String patternString) {
        Pattern regex = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher = regex.matcher(args);
        if (regexMatcher.matches()) {
            
            // check if valid calendar string
            try {
                if (!CalendarHelper.isCalendarString(regexMatcher.group(TAG_START))) {
                    return false;
                }
            } catch (IllegalArgumentException iae) {
                
            }
            
            try {
                if (!CalendarHelper.isCalendarString(regexMatcher.group(TAG_END))) {
                    return false;
                }
            } catch (IllegalArgumentException iae) {
                
            }
            
            try {
                if (!CalendarHelper.isCalendarString(regexMatcher.group(TAG_DATE))) {
                    return false;
                }
            } catch (IllegalArgumentException iae) {
                
            }
            
            return true;
        }
        else {
            return false;
        }
    }
}
