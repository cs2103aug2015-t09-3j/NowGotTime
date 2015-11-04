package test.integration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import helper.CommonHelper;
import object.Item;
import storage.FileHandler;
import ui.GUI;

public class IntegrationTest {

    GUI gui;

    @Before
    public void setUp() throws Exception {
        FileHandler clear = new FileHandler();
        clear.clearAll();
        Item.setCounter(0);
        gui = new GUI();
        gui.initiateHandler();
    }

    @Test
    public void testTypeInvalidCommand() {
        String commandKey = "helo";
        assertEquals(String.format(CommonHelper.ERROR_INVALID_COMMAND, commandKey),
                gui.executeResponse(commandKey, true));
    }
    
    @Test
    public void testCanAddEvent() {
        String command = "add \"sleep\" on 10 Sep 2016 10:00 to 23:00";
        assertEquals(String.format(CommonHelper.SUCCESS_ITEM_CREATED, "sleep"),
                gui.executeResponse(command, true));
    
    }

}
