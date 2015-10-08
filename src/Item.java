
public class Item {
    private String name;
    private String additionalInfo;
	private int id;
	private static int counter;
    
    public Item(String name, String additionalInfo) {
        setName(name);
        setAdditionalInfo(additionalInfo);
        setId(counter);
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
    
    private void setId(int counter) {
    	this.id = counter;
    	incrementCounter();
    }
    
    public int getCounter() {
    	return counter;
    }
    
    private void incrementCounter() {
    	counter++;
    }
}
