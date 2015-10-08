package object;

public class Item {
    private String name;
    private String additionalInfo;
	private int id;
	private static int counter = 0;
    
    public Item(String name, String additionalInfo) {
        setName(name);
        setAdditionalInfo(additionalInfo);
        setId(counter++);
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
    
    public int getId() {
    	return id;
    }
    
    public void setId(int counter) {
    	this.id = counter;
    }
    
    public static int getCounter() {
    	return counter;
    }
    
    public static void setCounter(int count){
    	counter = count;
    }
}
