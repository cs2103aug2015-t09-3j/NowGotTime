package command;

import helper.CommonHelper;
import helper.Parser;

public interface CommandAdd extends Command, Revertible {
    
    public static final String KEYWORD = "add";
    
    public static CommandAdd parseCommandAdd(String text) throws Exception {
        
        CommandAdd commandAdd = null;
        
        if (text.matches(Parser.PATTERN_ADD_EVENT) || text.matches(Parser.PATTERN_ADD_TASK) 
                || text.matches(Parser.PATTERN_NAME)) {
            commandAdd = new CommandAddItem(text);
            
        } else if (text.matches(Parser.PATTERN_PROJECT)) {
            commandAdd = new CommandAddProject(text);
            
        } else if (text.matches(Parser.PATTERN_ADD_KEYWORD_TO_PROJECT) 
                || text.matches(Parser.PATTERN_ADD_INDEX_TO_PROJECT)) {
            commandAdd = new CommandAddToProject(text);
            
        } else if (text.matches(Parser.PATTERN_ADD_PROGRESS)) {
            commandAdd = new CommandAddProgress(text);
            
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandAdd.KEYWORD));
            
        }
        
        
        return commandAdd;
    }
    
}
