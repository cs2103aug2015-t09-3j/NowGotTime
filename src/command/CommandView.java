//@@author A0126509E

package command;

import helper.CommonHelper;
import helper.Parser;

public interface CommandView extends Command, Displayable {

    public static final String KEYWORD = "view";
    
    public static CommandView parseCommandView(String text) throws Exception {
        
        CommandView commandView = null;
        if (Parser.matches(text, Parser.PATTERN_VIEW_PROJECT)) {
            commandView = new CommandViewProject();
        } else if (Parser.matches(text, Parser.PATTERN_NAME)) {
            commandView = new CommandViewProjectName(text);
            
        } else if (Parser.matches(text, Parser.PATTERN_DATE) || Parser.matches(text, Parser.PATTERN_EMPTY)) {
            commandView = new CommandViewDate(text);
            
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandView.KEYWORD));
            
        }
        
        
        return commandView;
    }
    
}
