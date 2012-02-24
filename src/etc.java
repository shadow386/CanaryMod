import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;


/**
 * etc.java - My catch-all class for a bunch of shit. If there's something you
 * need it's probably in here.
 * 
 * @author James
 */
public class etc {
    private static final Logger           log = Logger.getLogger("Minecraft");
    private static final etc              instance = new etc();
    private static MinecraftServer        server;
    private String                        configDir = "config/";
    private String                        usersLoc = "config/users.txt", kitsLoc = "config/kits.txt", homeLoc = "config/homes.txt", warpLoc = "config/warps.txt", itemLoc = "config/items.txt", groupLoc = "config/groups.txt", enderBlocksLoc = "config/endermanblocks.txt", muteListLoc = "config/muted-players.txt";
    private String                        whitelistLoc = "config/whitelist.txt", reservelistLoc = "config/reservelist.txt", antiXRayBlocksLoc = "config/antixray.txt";
    private String                        whitelistMessage = "Not on whitelist.";

    private Set<Integer>                  allowedItems = new HashSet<Integer>();
    private Set<Integer>                  disallowedItems = new HashSet<Integer>();
    private Set<Integer>                  itemSpawnBlacklist = new HashSet<Integer>();

    private String                        motd = null;
    private String                        motdLoc = "config/motd.txt";
    private boolean                       saveHomes = true;
    private boolean                       hideSeed = false;
    private boolean                       whitelistEnabled = false;
    private boolean                       reservelistEnabled = false;
    private int                           playerLimit = 20;
    private int                           spawnProtectionSize = 16;
    private LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
    private String                        dataSourceType;
    private DataSource                    dataSource;
    private PropertiesFile                properties;
    private PluginLoader                  loader;
    private boolean                       logging = false;
    private boolean                       enableHealth = true;
    private boolean                       enableExperience = false;
    private boolean                       enableAntiXRay = false;
    private boolean                       enableAntiXRayLighting = false;
    private int[]                         opaqueAntiXRayBlocks = new int[] { 1, 2, 3, 4, 5, 7, 12, 13, 14, 15, 16, 17, 19, 21, 22, 23, 24, 25, 29, 33, 35, 36, 41, 42, 43, 45, 46, 47, 48, 49, 54, 56, 57, 58, 60, 61, 62, 73, 74, 80, 82, 84, 86, 87, 88, 89, 91, 95, 97, 98, 99, 100, 103, 110, 112, 120, 121};
    private PluginLoader.HookResult       autoHeal = PluginLoader.HookResult.DEFAULT_ACTION;
    private PluginLoader.HookResult       protectFromSpam = PluginLoader.HookResult.DEFAULT_ACTION;
    private boolean                       showUnknownCommand = true;
    private String                        versionStr;
    private boolean                       tainted = true;
    // Version, DO NOT CHANGE (is loaded from file version.txt)!
    private int                           version = 1;
    private String                        username, password, db;
    private String[]                      animals = new String[] {};
    private String[]                      monsters = new String[] {};
    private String[]                      waterAnimals = new String[] {};
    private int                           mobSpawnRate = 100;
    public boolean                        deathMessages = true;
    private boolean                       crow = false;
    private boolean                       allowNether = true;
    //CanaryMod: Allow End
    private boolean                       allowEnd = true;
    // Playerlist options (tab)
    private boolean                       playerList_autoupdate = false;
    private int                           playerList_ticks = 500;
    private boolean                       playerList_colors = true;
    private boolean                       playerList_enabled = true;
    
    //Connection Manager
    private ConnectionService cs;

    private etc() {
        load();
    }

    private void loadIds(Collection<Integer> storage, String rawData) {
        for (String id : rawData.split(",")) {
            id = id.trim();
            if (id.length() > 0) {
                try {
                    storage.add(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    log.log(Level.SEVERE, "While parsing the config: '" + id + "' is not a number");
                }
            }
        }
    }

    /**
     * Loads or reloads the mod
     */
    public final void load() {
        if (configDir == null) {
            configDir = "config/";
        }
        if (properties == null) {
            properties = new PropertiesFile("server.properties");
        } else {
            try {
                properties.load();
            } catch (IOException e) {
                log.log(Level.SEVERE, "Exception while reading from server.properties", e);
            }
        }

        try {
            dataSourceType = properties.getString("data-source", "flatfile");
            loadIds(allowedItems, properties.getString("alloweditems", ""));
            loadIds(disallowedItems, properties.getString("disalloweditems", ""));
            loadIds(itemSpawnBlacklist, properties.getString("itemspawnblacklist", ""));
            playerList_autoupdate = properties.getBoolean("playerlist-autoupdate", false);
            playerList_ticks = properties.getInt("playerlist-ticks", 500);
            playerList_colors = properties.getBoolean("playerlist-usecolors", true);
            playerList_enabled = properties.getBoolean("playerlist-enabled", true);
            motd = properties.getString("motd", "My Canary Server.");
            playerLimit = properties.getInt("max-players", 20);
            saveHomes = properties.getBoolean("save-homes", true);
            hideSeed = properties.getBoolean("hide-seed", false);
            whitelistEnabled = properties.getBoolean("whitelist", false);
            properties.removeKey("white-list"); // delete Notchian white-list properties entry
            whitelistMessage = properties.getString("whitelist-message", "Not on whitelist.");
            configDir = properties.getString("config-directory", "config/");
            motdLoc = properties.getString("motdtxtlocation", "config/motd.txt");
            reservelistEnabled = properties.getBoolean("reservelist", false);
            if (dataSourceType.equalsIgnoreCase("flatfile")) {
                usersLoc = properties.getString("admintxtlocation", "config/users.txt");
                kitsLoc = properties.getString("kitstxtlocation", "config/kits.txt");
                homeLoc = properties.getString("homelocation", "config/homes.txt");
                warpLoc = properties.getString("warplocation", "config/warps.txt");
                itemLoc = properties.getString("itemstxtlocation", "config/items.txt");
                groupLoc = properties.getString("group-txt-location", "config/groups.txt");
                whitelistLoc = properties.getString("whitelist-txt-location", "config/whitelist.txt");
                reservelistLoc = properties.getString("reservelist-txt-location", "config/reservelist.txt");
                antiXRayBlocksLoc = properties.getString("antixray-txt-location", "config/antixray.txt");
                muteListLoc = properties.getString("muted-players-location", "config/muted-players.txt");
            } else {
                PropertiesFile sql = new PropertiesFile("mysql.properties");

                sql.getString("driver", "com.mysql.jdbc.Driver");
                username = sql.getString("user", "root");
                password = sql.getString("pass", "root");
                db = sql.getString("db", "jdbc:mysql://localhost:3306/minecraft");
                //Note: The pool size should not be set by users. It might have performance implications
                cs = new ConnectionService(db, username, password, 10);
            }
            spawnProtectionSize = properties.getInt("spawn-protection-size", 16);
            logging = properties.getBoolean("logging", false);
            allowNether = properties.getBoolean("allow-nether", true);
            allowEnd = properties.getBoolean("allow-end", true);
            enableHealth = properties.getBoolean("enable-health", true);
            enableExperience = properties.getBoolean("enable-experience", true);
            enableAntiXRay = properties.getBoolean("enable-antixray", false);
            enableAntiXRayLighting = properties.getBoolean("enable-antixray-lighting", false);
            deathMessages = properties.getBoolean("death-message", true);

            animals = properties.getString("natural-animals", "Sheep,Pig,Chicken,Cow").split(",");
            if (animals.length == 1 && (animals[0].equals(" ") || animals[0].equals(""))) {
                animals = new String[] {};
            }
            validateMobGroup(animals, "natural-animals", new String[] { "Sheep", "Pig", "Chicken", "Cow", "Wolf" });

            monsters = properties.getString("natural-monsters", "Spider,Zombie,Skeleton,Creeper,Slime,Enderman,CaveSpider,Silverfish").split(",");
            if (monsters.length == 1 && (monsters[0].equals(" ") || monsters[0].equals(""))) {
                monsters = new String[] {};
            }
            validateMobGroup(monsters, "natural-monsters", new String[] { "PigZombie", "Ghast", "Slime", "Giant", "Spider", "Zombie", "Skeleton", "Creeper", "Enderman", "CaveSpider", "Silverfish" });

            waterAnimals = properties.getString("natural-wateranimals", "Squid").split(",");
            if (waterAnimals.length == 1 && (waterAnimals[0].equals(" ") || waterAnimals[0].equals(""))) {
                waterAnimals = new String[] {};
            }
            validateMobGroup(waterAnimals, "natural-wateranimals", new String[] { "Squid" });

            mobSpawnRate = properties.getInt("natural-spawn-rate", mobSpawnRate);

            String autoHealString = properties.getString("auto-heal", "default");

            if (autoHealString.matches("(?i:true|on)")) {
                autoHeal = PluginLoader.HookResult.ALLOW_ACTION;
            } else if (autoHealString.matches("(?i:false|off)")) {
                autoHeal = PluginLoader.HookResult.PREVENT_ACTION;
            }
            
            String protectSpamString = properties.getString("protect-spam", "default");
            if (protectSpamString.matches("(?i:true|on|always)")) {
                protectFromSpam = PluginLoader.HookResult.ALLOW_ACTION;
            } else if (protectSpamString.matches("(?i:false|off|never)")) {
                protectFromSpam = PluginLoader.HookResult.PREVENT_ACTION;
            }

            showUnknownCommand = properties.getBoolean("show-unknown-command", true);
            File file = new File("version.txt");

            if (file.exists()) {
                InputStreamReader ins = new InputStreamReader(file.toURI().toURL().openStream());
                BufferedReader bufferedReader = new BufferedReader(ins);
                String versionParam = bufferedReader.readLine();

                if (versionParam.startsWith("git-")) { // recommended
                    // version.txt for git
                    // builds:
                    // git-<gituser>-<shorttag>
                    // example: git-sk89q-591c662cf4afc8e3e09a
                    version = -1;
                    versionStr = versionParam;
                    tainted = true;
                } else if (versionParam.startsWith("crow-")) {
                    crow = true;
                    versionStr = versionParam.substring(5); // and back to a string.
                    tainted = false; // looks official. We hope.
                } else {
                    version = Integer.parseInt(versionParam);
                    versionStr = Integer.toString(version); // and back to a string.
                    tainted = false; // looks official. We hope.
                }
                ins.close();
                bufferedReader.close();
            } else {
                // I'm a tainted build, probably.
                version = -1;
                versionStr = "Undefined version";
                tainted = true;
                // If any mods check the version.. #@!$
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception while reading from server.properties", e);
            // Just in case...
            motd = "My Canary Server.";
        }
    }

    /**
     * 
     * @return true if we want the playerlist update automaticaly
     */
    public boolean isPlayerList_autoupdate() {
        return playerList_autoupdate;
    }

    /**
     *  
     * @return the interval between playerlist autoupdates
     */
    public int getPlayerList_ticks() {
        return playerList_ticks;
    }

    /**
     *  
     * 
     * @return true if we want player colors on playerlist
     */
    public boolean isPlayerList_colors() {
        return playerList_colors;
    }

    /**
     * returns true if we want enable the [tab]Playerlist
     * @return
     */
    public boolean isPlayerList_enabled() {
        return playerList_enabled;
    }

    /**
     * Loads or reloads the data source
     */
    public void loadData() {
        if (dataSourceType.equalsIgnoreCase("flatfile") && dataSource == null) {
            dataSource = new FlatFileSource();
        } else if (dataSourceType.equalsIgnoreCase("mysql") && dataSource == null) {
            dataSource = new MySQLSource();
        }

        dataSource.initialize();
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    /**
     * Returns the instance
     * 
     * @return
     */
    public static etc getInstance() {
        return instance;
    }

    /**
     * Sets the server to be used.
     * 
     * @param s
     */
    public static void setServer(MinecraftServer s) {
        server = s;
    }

    /**
     * Returns the minecraft server
     * 
     * @return
     */
    public static MinecraftServer getMCServer() {
        return server;
    }

    /**
     * Returns the data source
     * 
     * @return
     */
    public static DataSource getDataSource() {
        return etc.getInstance().getSource();
    }

    /**
     * Returns the minecraft server interface
     * 
     * @return
     */
    public static Server getServer() {
        return etc.getLoader().getServer();
    }

    /**
     * Returns the plugin loader
     * 
     * @return
     */
    public static PluginLoader getLoader() {
        if (instance.loader == null) {
            instance.loader = new PluginLoader(server);
        }

        return instance.loader;
    }

    /**
     * Returns the default group
     * 
     * @return default group
     */
    public Group getDefaultGroup() {
        Group group = dataSource.getDefaultGroup();

        if (group == null) {
            log.log(Level.SEVERE, "No default group! Expect lots of errors!");
        }
        return group;
    }

    /**
     * Adds or modifies the home.
     * 
     * @param home
     */
    public void changeHome(Warp home) {
        if (dataSource.getHome(home.Name) == null) {
            dataSource.addHome(home);
        } else {
            dataSource.changeHome(home);
        }
    }

    /**
     * Adds or modifies the warp
     * 
     * @param warp
     */
    public void setWarp(Warp warp) {
        if (dataSource.getWarp(warp.Name) == null) {
            dataSource.addWarp(warp);
        } else {
            dataSource.changeWarp(warp);
        }
    }

    /**
     * Returns true if the item is on the blacklist
     * 
     * @param id
     * @return
     */
    public boolean isOnItemBlacklist(int id) {
        return itemSpawnBlacklist.contains(id);
    }

    /**
     * Returns the data source
     * 
     * @return
     */
    public DataSource getSource() {
        return dataSource;
    }

    /**
     * Returns true if we're logging commands and such
     * 
     * @return
     */
    public boolean isLogging() {
        return logging;
    }

    /**
     * Returns true if we want health to be enabled.
     * 
     * @return
     */
    public boolean isHealthEnabled() {
        return enableHealth;
    }
    
    /**
     * Returns true if we want experience to be enabled.
     * 
     * @return
     */
    public boolean isExpEnabled() {
        return enableExperience;
    }
    
    /**
     * Returns true if anti-xray mechanism is working
     * 
     * @return
     */
    public boolean isAntiXRayEnabled() {
        return enableAntiXRay;
    }
    
    /**
     * Returns true if anti-xray lighting mechanism is working
     * 
     * @return
     */
    public boolean isAntiXRayLightingEnabled() {
        return enableAntiXRayLighting;
    }
    
    /**
     * Returns true if an anti xray block is opaque.
     * @param id - The id of the block to check.
     * 
     * @return
     */
    public boolean isOpaqueAntiXRayBlock(int id) {
        for (int i = 0; i < this.opaqueAntiXRayBlocks.length; i += 1) {
            if (this.opaqueAntiXRayBlocks[i] == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the status of auto-heal.
     * 
     * @return
     */
    public PluginLoader.HookResult autoHeal() {
        return autoHeal;
    }

    /**
     * Adds command to the /help list
     * 
     * @param command
     * @param description
     */
    public void addCommand(String command, String description) {
        commands.put(command, description);
    }

    /**
     * Removes command from /help list
     * 
     * @param command
     */
    public void removeCommand(String command) {
        commands.remove(command);
    }

    /**
     * Toggles the whitelist (doesn't persist)
     * 
     * @return
     */
    public boolean toggleWhitelist() {
        whitelistEnabled = !whitelistEnabled;
        return whitelistEnabled;
    }

    /**
     * Callback object for notifications sent by executed ServerCommands. so
     * that they appear in server log.
     */
    private MessageReceiver serverConsole = new MessageReceiver() {
        @Override
        public String getName() {
            return "<Server>";
        }

        @Override
        public void notify(String message) {
            // Strip the colors.
            // message = message.replaceAll("\\u00A7[a-f0-9]", "");
            if (message != null) {
                log.info(message);
            }
        }
    };

    /**
     * Parses a console command
     * 
     * @param command
     * @param server
     * @return
     */
    public boolean parseConsoleCommand(String command, MinecraftServer server) {
        if (getMCServer() == null) {
            setServer(server);
        }
        String[] split = command.split(" ");

        if ((Boolean) getLoader().callHook(PluginLoader.Hook.SERVERCOMMAND, new Object[] { split })) {
            return true;
        }
        if (split.length == 0) {
            return false;
        }

        boolean dontParseRegular = true;

        if (split[0].equalsIgnoreCase("save-all")) {
            dontParseRegular = false;
            getServer().saveInventories();
        } else if (split[0].equalsIgnoreCase("help") || split[0].equalsIgnoreCase("mod-help")) {
            if (split[0].equalsIgnoreCase("help")) {
                dontParseRegular = false;
            }
            log.info("Server mod help:");
            log.info("help          Displays this mod's and server's help");
            log.info("mod-help      Displays this mod's help");
            log.info("version       Displays the server version");
            log.info("reload        Reloads the config");
            log.info("modify        Type modify for more info");
            log.info("whitelist     Type whitelist for more info");
            log.info("reservelist   Type reservelist for more info");
            log.info("listplugins   Lists all plugins");
            log.info("enableplugin  Enables a plugin");
            log.info("disableplugin Disables a plugin");
            log.info("reloadplugin  Reloads a plugin");
            log.info("gamemode  Set's the player's gamemode");
        } else {
            dontParseRegular = ServerConsoleCommands.parseServerConsoleCommand(serverConsole, split[0], split);
        }

        return dontParseRegular;
    }

    /**
     * Returns compass direction according to your rotation
     * 
     * @param degrees
     * @return
     */
    public static String getCompassPointForDirection(double degrees) {
        if (0 <= degrees && degrees < 22.5) {
            return "E";
        } else if (22.5 <= degrees && degrees < 67.5) {
            return "SE";
        } else if (67.5 <= degrees && degrees < 112.5) {
            return "S";
        } else if (112.5 <= degrees && degrees < 157.5) {
            return "SW";
        } else if (157.5 <= degrees && degrees < 202.5) {
            return "W";
        } else if (202.5 <= degrees && degrees < 247.5) {
            return "NW";
        } else if (247.5 <= degrees && degrees < 292.5) {
            return "N";
        } else if (292.5 <= degrees && degrees < 337.5) {
            return "NE";
        } else if (337.5 <= degrees && degrees < 360.0) {
            return "E";
        } else {
            return "ERR";
        }
    }

    /**
     * Combines the string array into a string at the specified start with the
     * separator separating each string.
     * 
     * @param startIndex
     * @param string
     * @param seperator
     * @return combined string
     */
    public static String combineSplit(int startIndex, String[] string, String seperator) {
        StringBuilder builder = new StringBuilder();

        for (int i = startIndex; i < string.length; i++) {
            builder.append(string[i]);
            builder.append(seperator);
        }
        if (builder.length() > 0) { // Skye's fix for OutOfBounds exception.
            builder.deleteCharAt(builder.length() - seperator.length());
        } // remove
        // the
        // extra
        // seperator
        return builder.toString();
    }

    /**
     * Returns a list of allowed items for /item
     * 
     * @return list of allowed items
     */
    public Set<Integer> getAllowedItems() {
        return allowedItems;
    }

    /**
     * Returns the list of commands
     * 
     * @return
     */
    public LinkedHashMap<String, String> getCommands() {
        return commands;
    }

    /**
     * Returns a list of disallowed items for /item
     * 
     * @return
     */
    public Set<Integer> getDisallowedItems() {
        return disallowedItems;
    }

    /**
     * Returns the location of groups.txt
     * 
     * @return
     */
    public String getGroupLocation() {
        return groupLoc;
    }

    /**
     * Returns the location of homes.txt
     * 
     * @return
     */
    public String getHomeLocation() {
        return homeLoc;
    }

    /**
     * Returns the location of items.txt
     * 
     * @return
     */
    public String getItemLocation() {
        return itemLoc;
    }

    /**
     * Returns list of banned blocks
     * 
     * @return
     */
    public Set<Integer> getItemSpawnBlacklist() {
        return itemSpawnBlacklist;
    }

    /**
     * Returns the location of kits.txt
     * 
     * @return
     */
    public String getKitsLocation() {
        return kitsLoc;
    }
    
    /**
     * Returns the location of endermanblocks.txt
     * 
     * @return
     */
    public String getEnderBlocksLocation() {
        return enderBlocksLoc;
    }

    /**
     * Returns the MOTD.
     * 
     * @param caller 
     * @return
     */
    public String getMotd(MessageReceiver caller) {
        return Motd.getMotd(caller);
    }
    
    /**
     * Returns the player limit
     * 
     * @return
     */
    public int getPlayerLimit() {
        return playerLimit;
    }

    /**
     * Return reservelist enabled.
     * 
     * @return true if enabled.
     */
    public boolean isReservelistEnabled() {
        return reservelistEnabled;
    }

    /**
     * Returns the location of reservelist.txt
     * 
     * @return
     */
    public String getReservelistLocation() {
        return reservelistLoc;
    }
    
    /**
     * Returns the location of antixray.txt
     * 
     * @return
     */
    public String getAntiXRayBlocksLocation() {
        return antiXRayBlocksLoc;
    }
    
    public String getMuteListLocation() {
        return muteListLoc;
    }

    /**
     * Returns true if the server is saving homes
     * 
     * @return true if server can save homes
     */
    public boolean canSaveHomes() {
        return saveHomes;
    }

    /**
     * Returns true if the server is hiding it's seed.
     *
     * @return true
     */
     
    public boolean getHideSeed() {
        return hideSeed;
    }
    
    /**
     * If true, the server will hide the seed from other players
     *
     * @param hideSeed
     */
     
    public void setHideSeed(boolean hideSeed) {
        this.hideSeed = hideSeed;
    }
     
    /**
     * Returns the spawn protection size
     * 
     * @return
     */
    public int getSpawnProtectionSize() {
        return spawnProtectionSize;
    }

    /**
     * Returns the location of users.txt
     * 
     * @return
     */
    public String getUsersLocation() {
        return usersLoc;
    }

    /**
     * Returns the location of warps.txt
     * 
     * @return
     */
    public String getWarpLocation() {
        return warpLoc;
    }

    /**
     * Returns true if the whitelist is enabled
     * 
     * @return
     */
    public boolean isWhitelistEnabled() {
        return whitelistEnabled;
    }
    
//    public boolean isPlayerOnMuteList(String player) {
//    	if(this.getDataSource().get)
//    }

    /**
     * Returns the location of whitelist.txt
     * 
     * @return
     */
    public String getWhitelistLocation() {
        return whitelistLoc;
    }

    /**
     * Returns the message the kick will show if a player isn't on the whitelist
     * 
     * @return
     */
    public String getWhitelistMessage() {
        return whitelistMessage;
    }

    /**
     * Sets the list of allowed items
     * 
     * @param allowedItems
     */
    public void setAllowedItems(int[] allowedItems) {
        this.allowedItems.clear();
        // this.allowedItems.addAll(Arrays.asList(allowedItems)); <-- if only
        // java was smart >.>
        for (int item : allowedItems) {
            this.allowedItems.add(item);
        }
    }

    /**
     * Sets the list of disallowed items
     * 
     * @param disallowedItems
     */
    public void setDisallowedItems(int[] disallowedItems) {
        this.disallowedItems.clear();
        // this.allowedItems.addAll(Arrays.asList(allowedItems)); <-- if only
        // java was smart >.>
        for (int item : disallowedItems) {
            this.disallowedItems.add(item);
        }
    }

    /**
     * Sets the location of groups.txt
     * 
     * @param groupLoc
     */
    public void setGroupLocation(String groupLoc) {
        this.groupLoc = groupLoc;
    }

    /**
     * Sets the location of homes.txt
     * 
     * @param homeLoc
     */
    public void setHomeLocation(String homeLoc) {
        this.homeLoc = homeLoc;
    }

    /**
     * Sets the location of items.txt
     * 
     * @param itemLoc
     */
    public void setItemLocation(String itemLoc) {
        this.itemLoc = itemLoc;
    }

    /**
     * Sets the item spawn blacklist
     * 
     * @param itemSpawnBlacklist
     */
    public void setItemSpawnBlacklist(int[] itemSpawnBlacklist) {
        this.itemSpawnBlacklist.clear();
        // this.allowedItems.addAll(Arrays.asList(allowedItems)); <-- if only
        // java was smart >.>
        for (int item : itemSpawnBlacklist) {
            this.itemSpawnBlacklist.add(item);
        }
    }

    /**
     * Sets the location of kits.txt
     * 
     * @param kitsLoc
     */
    public void setKitsLocation(String kitsLoc) {
        this.kitsLoc = kitsLoc;
    }

    /**
     * If set to true the server will log all commands used.
     * 
     * @param logging
     */
    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    /**
     * Set the MOTD
     * 
     * @param motd
     */
    public void setMotd(String motd) {
        this.motd = motd;
    }

    /**
     * Set the player limit
     * 
     * @param playerLimit
     */
    public void setPlayerLimit(int playerLimit) {
        this.playerLimit = playerLimit;
    }

    /**
     * Set the location of reservelist.txt
     * 
     * @param reservelistLoc
     */
    public void setReservelistLocation(String reservelistLoc) {
        this.reservelistLoc = reservelistLoc;
    }
    
    /**
     * Set the location of antixray.txt
     * 
     * @param antiXRayLoc
     */
    public void setAntiXRayLocation(String antiXRayLoc) {
        this.antiXRayBlocksLoc = antiXRayLoc;
    }

    /**
     * If true the server will save homes. If false homes won't be saved and
     * will be wiped the next server restart.
     * 
     * @param saveHomes
     */
    public void setSaveHomes(boolean saveHomes) {
        this.saveHomes = saveHomes;
    }

    /**
     * Set the spawn protection size (def: 16)
     * 
     * @param spawnProtectionSize
     */
    public void setSpawnProtectionSize(int spawnProtectionSize) {
        this.spawnProtectionSize = spawnProtectionSize;
    }

    /**
     * Sets the location of users.txt
     * 
     * @param usersLoc
     */
    public void setUsersLocation(String usersLoc) {
        this.usersLoc = usersLoc;
    }

    /**
     * Sets the location of warps.txt
     * 
     * @param warpLoc
     */
    public void setWarpLocation(String warpLoc) {
        this.warpLoc = warpLoc;
    }

    /**
     * If true the whitelist is enabled
     * 
     * @param whitelistEnabled
     */
    public void setWhitelistEnabled(boolean whitelistEnabled) {
        this.whitelistEnabled = whitelistEnabled;
    }

    /**
     * Sets the location of whitelist.txt
     * 
     * @param whitelistLoc
     */
    public void setWhitelistLocation(String whitelistLoc) {
        this.whitelistLoc = whitelistLoc;
    }

    /**
     * Sets the whitelist message to show when it kicks someone
     * 
     * @param whitelistMessage
     */
    public void setWhitelistMessage(String whitelistMessage) {
        this.whitelistMessage = whitelistMessage;
    }

    /**
     * Returns true if "Unknown command" is shown to a player when they enter an
     * unknown command (For wrappers and such)
     * 
     * @return show unknown command
     */
    public boolean showUnknownCommand() {
        return showUnknownCommand;
    }

    /**
     * Sets whether or not to show "Unknown command" to players.
     * 
     * @param showUnknownCommand
     *            whether or not to show it
     */
    public void setShowUnknownCommand(boolean showUnknownCommand) {
        this.showUnknownCommand = showUnknownCommand;
    }

    /**
     * Return the current build of the mod
     * 
     * @return build/version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Return whether this build is "tainted"
     * 
     * @return tainted
     */
    public boolean getTainted() {
        return tainted;
    }

    /**
     * Return the specified string version of the build
     * 
     * @return build/version
     */
    public String getVersionStr() {
        return versionStr;
    }

    /**
     * Returns a list of animals that are allowed to spawn naturally
     * 
     * @return a list of animals
     */
    public String[] getAnimals() {
        return animals;
    }

    /**
     * Sets a list of animals that are allowed to spawn naturally
     * 
     * @param animals
     *            a list of animals
     */
    public void setAnimals(String[] animals) {
        this.animals = animals;
    }

    /**
     * Returns a list of animals that are allowed to spawn naturally
     * 
     * @return a list of animals
     */
    public String[] getWaterAnimals() {
        return waterAnimals;
    }

    /**
     * Sets a list of animals that are allowed to spawn naturally
     * 
     * @param animals
     *            a list of animals
     */
    public void setWaterAnimals(String[] animals) {
        waterAnimals = animals;
    }

    /**
     * Returns a list of mobs that are allowed to spawn naturally
     * 
     * @return a list of mobs
     */
    public String[] getMonsters() {
        return monsters;
    }

    public List getMonstersClass(OBiomeGenBase biomeGen) {
        List<OSpawnListEntry> toRet = biomeGen.H;
        List<String> allowed = Arrays.asList(getMonsters());
        
        Iterator<OSpawnListEntry> it = toRet.iterator();
        if (!it.hasNext())
            return toRet;
        for (OSpawnListEntry en = it.next(); it.hasNext(); en = it.next()) {
            if (!allowed.contains(OEntityList.getName(en.a)))
                it.remove();
        }
        
        return toRet;
    }

    public List getAnimalsClass(OBiomeGenBase biomeGen) {
        List<OSpawnListEntry> toRet = biomeGen.I;
        List<String> allowed = Arrays.asList(getAnimals());
        
        Iterator<OSpawnListEntry> it = toRet.iterator();
        if (!it.hasNext())
            return toRet;
        for (OSpawnListEntry en = it.next(); it.hasNext(); en = it.next()) {
            if (!allowed.contains(OEntityList.getName(en.a)))
                it.remove();
        }
        
        return toRet;
    }

    public List getWaterAnimalsClass(OBiomeGenBase biomeGen) {
        List<OSpawnListEntry> toRet = biomeGen.J;
        List<String> allowed = Arrays.asList(getWaterAnimals());
        
        Iterator<OSpawnListEntry> it = toRet.iterator();
        if (!it.hasNext())
            return toRet;
        for (OSpawnListEntry en = it.next(); it.hasNext(); en = it.next()) {
            if (!allowed.contains(OEntityList.getName(en.a)))
                it.remove();
        }
        
        return toRet;
    }

    /**
     * Sets a list of mobs that are allowed to spawn naturally
     * 
     * @param monsters
     *            a list of mobs
     */
    public void setMonsters(String[] monsters) {
        this.monsters = monsters;
    }

    /**
     * Returns the % from 0 to 100 that a mob or animal will spawn
     * 
     * @return a percentage from 0 to 100
     */
    public int getMobSpawnRate() {
        return mobSpawnRate;
    }

    /**
     * Sets the % from 0 to 100 that a mob or animal will spawn
     * 
     * @param rate
     *            a percentage from 0 to 100
     */
    public void setMobSpawnRate(int rate) {
        mobSpawnRate = rate;
    }

    @Deprecated
    private Connection _getSQLConnection() {
        try {
            return DriverManager.getConnection(db + "?autoReconnect=true&user=" + username + "&password=" + password);
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "Unable to retreive connection", ex);
        }
        return null;
    }
    
    private CanaryConnection _getConnection() throws SQLException {
        return cs.getConnection();
    }

    /**
     * Returns a SQL connection
     * 
     * @return sql connection
     * @deprecated Use {@link #getConnection() } instead.
     */
    @Deprecated
    public static Connection getSQLConnection() {
        return getInstance()._getSQLConnection();
    }
    
    /**
     * Return a connection from the connection pool
     * @return A {@link CanaryConnection}
     * @throws SQLException
     */
    public static CanaryConnection getConnection() throws SQLException {
        return etc.getInstance()._getConnection();
    }

    public static int floor(float paramFloat) {
        int i = (int) paramFloat;

        return paramFloat < i ? i - 1 : i;
    }

    public static int floor(double paramDouble) {
        int i = (int) paramDouble;

        return paramDouble < i ? i - 1 : i;
    }

    private static void validateMobGroup(String[] mobs, String groupname, String[] allowed) {
        lb1:
        for (String i : mobs) {
            for (String al : allowed) {
                if (al.equals(i)) {
                    continue lb1;
                }
            }
            log.warning("Invalid mobType '" + i + "' in group '" + groupname + "', please remove it from your config file!");
            System.exit(0);
        }
    }

    public static boolean isInValidLivingGroup(String classname, Class<?> objectgroup) {
        Class<?> entity = OEntityList.getEntity(classname);

        if (entity != null) {
            return objectgroup.isAssignableFrom(entity);
        } else {
            return false;
        }
    }

    /**
     * Returns config directory
     *
     * @return String configDir
     */
    public String getConfigFolder() {
        return configDir;
    }
    
    /**
     * Returns if current build is a crow build
     *
     * @return boolean crow
     */
    public boolean isCrow() {
        return crow;
    }
    
    /**
     * Returns if nether is enabled
     *
     * @return
     */
    public boolean isNetherEnabled() {
        return allowNether;
    }
    
    /**
     * Returns if the end is enabled
     * 
     * @return
     */
     public boolean isEndEnabled() {
         return allowEnd;
     }
    
    /**
     * Returns the location of motd.txt
     *
     * @return
     */
    public String getMotdLocation() {
        return motdLoc;
    }
        
    /**
     * Returns the server message
     *
     * @return
     */
    public String getServerMessage() {
        return motd;
    }

    /**
     * Returns whether to protect from spam.
     * @return {@link PluginLoader.HookResult#ALLOW_ACTION} if it should be
     *          protected for all messages,
     *          {@link PluginLoader.HookResult#DEFAULT_ACTION} if it should be
     *          protected only for chat messages, or
     *          {@link PluginLoader.HookResult#PREVENT_ACTION} to turn it off.
     */
    public PluginLoader.HookResult getProtectFromSpam() {
        return protectFromSpam;
    }

    /**
     * Sets whether to protect from spam. This is not persistent.
     * @param protectFromSpam {@link PluginLoader.HookResult#ALLOW_ACTION} if it
     *          should be protected for all messages,
     *          {@link PluginLoader.HookResult#DEFAULT_ACTION} if it should be
     *          protected only for chat messages, or
     *          {@link PluginLoader.HookResult#PREVENT_ACTION} to turn it off.
     */
    public void setProtectFromSpam(PluginLoader.HookResult protectFromSpam) {
        this.protectFromSpam = protectFromSpam;
    }
}
