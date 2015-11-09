//@@author A0126509E

package command;

import helper.CommonHelper;
import helper.Parser;

public interface CommandEdit extends Command, Revertible {

    public static final String KEYWORD = "edit";
    
    public static CommandEdit parseCommandEdit(String text) throws Exception {
        
        CommandEdit commandEdit = null;
        
        if (Parser.matches(text,Parser.PATTERN_EDIT_NAME_BY_INDEX) 
                || Parser.matches(text,Parser.PATTERN_EDIT_NAME_BY_KEY) 
                || Parser.matches(text,Parser.PATTERN_EDIT_DATE_TIME_BY_INDEX) 
                || Parser.matches(text,Parser.PATTERN_EDIT_DATE_TIME_BY_KEY)) {
            commandEdit = new CommandEditItem(text);
            
        } else if (Parser.matches(text,Parser.PATTERN_EDIT_PROJECT)) {
            commandEdit = new CommandEditProject(text);
            
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandEdit.KEYWORD));
            
        }
        
        return commandEdit;
    }
}
