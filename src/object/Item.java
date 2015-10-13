package object;

import java.text.ParseException;

public abstract class Item {
    private String name;
    private String additionalInfo;
	private int id;
	private static int counter = 0;
    
    public Item(String name, String additionalInfo) {
        setName(name);
        setAdditionalInfo(additionalInfo);
        setId(counter++);
    }
    
    /**
     * Retrieves item name	
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets item name
     */
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

    /**
     * Retrieves the additional info of an item
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the additional info of an item
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    
    /**
     * Gets the Id of object
     */
    public int getId() {
    	return id;
    }
    
    /**
     * Sets the Id of object
     */
    public void setId(int counter) {
    	this.id = counter;
    }
    
    /**
     * Generates a number to set to object
     */
    public static int getCounter() {
    	return counter;
    }
    
    /**
     * Sets counter to set the Id of object
     */
    public static void setCounter(int count){
    	counter = count;
    }
    
    public abstract String toFormattedString() throws ParseException;
    
}
