//@@author A0126509E

package command;

import java.util.regex.Matcher;

import helper.CommonHelper;
import helper.Parser;
import object.State;
import service.ServiceHandler;

public class CommandSet implements Command {

    public static final String KEYWORD = "set";

    public CommandSet(String args) throws Exception {
        if (Parser.matches(args,Parser.PATTERN_NAME)) {
            Matcher matcher = Parser.matchRegex(args, Parser.PATTERN_NAME);
            newDirectory = matcher.group(Parser.TAG_NAME);
        } else {
            throw new Exception(String.format(CommonHelper.ERROR_INVALID_ARGUMENTS, CommandSet.KEYWORD));
            
        }
    }
    
    String newDirectory;

    @Override
    public String execute(State state) throws Exception {
        ServiceHandler serviceHandler = state.getServiceHandler();
        
        if (serviceHandler.changeDirectory(newDirectory)) {
            return String.format(CommonHelper.SUCCESS_SET_DIRECTORY, newDirectory);
        } else {
            throw new Exception(CommonHelper.ERROR_INVALID_PATH);
        }
    }

    @Override
    public Displayable getDisplayable() {
        return null;
    }

}
