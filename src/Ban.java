/**
 * Ban.java - The ban class! Ban! Ban! Ban!
 * 
 * @author James
 */
public class Ban {

    private String name = "", ip = "", reason = etc.getInstance().getDefaultBanMessage();
    private int    id = -1, timestamp = -1;
    
    public Ban() {}

    public Ban(String name) {
        this.name = name;
    }

    /**
     * Returns the specified IP address for this ban. Will be empty if no IP ban
     * is associated
     * 
     * @return the IP address
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the IP address of this ban
     * 
     * @param ip
     *            ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Returns the player's name for this ban
     * 
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name for this ban
     * 
     * @param name
     *            player's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the ban reason. Will be "N/A" if none specified, or is an old ban
     * 
     * @return ban reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the ban reason
     * 
     * @param reason
     *            ban reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Returns a UNIX timestamp for when this ban expires
     * 
     * @return unix timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the UNIX timestamp for this ban to expire at.
     * 
     * @param timestamp
     */
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the SQL id for this ban (only used with SQL, not flat files)
     * 
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the SQL id for this ban (only used with SQL, not flat files)
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Ban))
            return false;
        Ban that = (Ban) other;
        return this.name.equalsIgnoreCase(that.name) && this.ip.equals(that.ip);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (this.ip != null ? this.ip.hashCode() : 0);
        return hash;
    }
}
