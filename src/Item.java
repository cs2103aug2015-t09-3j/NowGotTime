
public class Item {
    private String name;
    private String additionalInfo;
    
    public Item(String name, String additionalInfo) {
        setName(name);
        setAdditionalInfo(additionalInfo);
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
    
    
}
