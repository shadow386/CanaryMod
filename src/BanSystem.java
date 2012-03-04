
/**
 * BanSystem.java - Handles all ban related things.
 *
 * @author James
 */
public class BanSystem {
    private static String defaultReason;
    private static DataSource dataSource;
    private static final int FOREVER = -1;

    /**
     * Files a regular name-only ban.
     *
     * @param player player to ban
     */
    public static void fileBan(Player player) {
        fileBan(player, defaultReason);
    }

    /**
     * Files a regular name-only ban.
     *
     * @param player player to ban
     * @param reason The reason given when the player tries to join.
     */
    public static void fileBan(Player player, String reason) {
        fileBan(player, reason, FOREVER, false);
    }

    /**
     * Files an IP address ban
     *
     * @param player player to IP ban
     */
    public static void fileIpBan(Player player) {
        fileIpBan(player, defaultReason);
    }

    /**
     * Files an IP address ban
     *
     * @param player player to IP ban
     * @param reason The reason given when the player tries to join.
     */
    public static void fileIpBan(Player player, String reason) {
        fileBan(player, reason, FOREVER, true);
    }

    /**
     * Files a temporary ban (both name and IP bans)
     *
     * @param player player to ban
     * @param minutes length in minutes
     * @param hours length in hours
     * @param days length in days
     */
    public static void fileTempBan(Player player, int minutes, int hours, int days) {
        fileTempBan(player, defaultReason, minutes, hours, days);
    }

    /**
     * Files a temporary ban (both name and IP bans)
     *
     * @param player player to ban
     * @param reason The reason given when the player tries to join.
     * @param minutes length in minutes
     * @param hours length in hours
     * @param days length in days
     */
    public static void fileTempBan(Player player, String reason, int minutes, int hours, int days) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        
        hours += 24 * days;
        minutes += 60 * hours;
        timestamp += 60 * minutes;
        
        fileBan(player, reason, timestamp, false);
    }

    /**
     * Files a temporary ban (both name and IP bans)
     *
     * @param player player to ban
     * @param minutes length in minutes
     * @param hours length in hours
     * @param days length in days
     */
    public static void fileTempIpBan(Player player, int minutes, int hours, int days) {
        fileTempIpBan(player, defaultReason, minutes, hours, days);
    }

    /**
     * Files a temporary ban (both name and IP bans)
     *
     * @param player player to ban
     * @param reason The reason given when the player tries to join.
     * @param minutes length in minutes
     * @param hours length in hours
     * @param days length in days
     */
    public static void fileTempIpBan(Player player, String reason, int minutes, int hours, int days) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        
        hours += 24 * days;
        minutes += 60 * hours;
        timestamp += 60 * minutes;
        
        fileBan(player, reason, timestamp, true);
    }
    
    protected static void fileBan(Player player, String reason, int timestamp, boolean byIp) {
        Ban ban = new Ban();
        
        if (byIp)
            ban.setIp(player.getIP());
        else
            ban.setName(player.getName());
        ban.setTimestamp(timestamp);
        ban.setReason(reason);
        
        dataSource.addBan(ban);
    }

    protected static void setDefaultReason(String defaultReason) {
        BanSystem.defaultReason = defaultReason;
    }

    protected static void setDataSource(DataSource dataSource) {
        BanSystem.dataSource = dataSource;
    }
}
