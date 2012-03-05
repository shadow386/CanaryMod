/**
 * PlayerlistEntry.java - Object for passing to the onPlayerlistEntryGet Hook
 * 
 * @author Talmor
 */
public class PlayerlistEntry {
    
    private String name;
    private int ping;
    private boolean show;
    
    public PlayerlistEntry(String name, int ping, boolean show) {
        super();
        this.name = name;
        this.ping = ping;
        this.show = show;
    }
  
    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getName() {
        return name.substring(0, Math.min(name.length(), 16));
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }
    
}
