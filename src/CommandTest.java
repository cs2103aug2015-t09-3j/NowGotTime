
import java.util.Stack;

import org.junit.Before;

public class CommandTest {

    protected ServiceHandler service;
    protected ProjectHandler project;
    protected Stack<Command> history;
    
    @Before
    public void setUp() throws Exception {
        service = new ServiceHandler();
        project = new ProjectHandler();
        history = new Stack<Command>();
    }
    

}
