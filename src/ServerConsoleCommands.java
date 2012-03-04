
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class ServerConsoleCommands {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static ServerConsoleCommands instance;
    private final LinkedHashMap<String, BaseCommand> commands = new LinkedHashMap<String, BaseCommand>();

    private ServerConsoleCommands() {
        add("reload", reload);
        add("listplugins", listplugins);
        add("enableplugin", enableplugin);
        add("reloadplugin", reloadplugin);
        add("disableplugin", disableplugin);
        add("modify", modify);
        add("mp", modify);
        add("reservelist", reservelist);
        add("whitelist", whitelist);
        add("version", version);
        add("banlist", banlist);
        add("ban", ban);
        add("banip", banip);
        add("unban", unban);
        add("unbanip", unbanip);
        add("tempban", tempban);
        add("kick", kick);
        add("kickall", kickall);
    }

    /**
     * Add a command to the server list.
     *
     * @param name
     * @param cmd
     */
    public void add(String name, BaseCommand cmd) {
        if (name != null && cmd != null) {
            if (!commands.containsValue(cmd)) {
                etc.getInstance().addCommand("/" + name, cmd.tooltip);
            }
            commands.put(name, cmd);
        }
    }

    /**
     * Remove a command from the server list.
     *
     * @param name
     */
    public void remove(String name) {
        if (name != null) {
            etc.getInstance().removeCommand(name);
            commands.remove(name);
        }
    }

    /**
     * Performs a lookup for a command of the given name and executes it if
     * found. Returns false if command not found.
     *
     * @param command
     * @param caller
     * @param args
     * @return
     */
    public static boolean parseServerConsoleCommand(MessageReceiver caller, String command, String[] args) {
        if (instance == null) {
            instance = new ServerConsoleCommands();
        }

        BaseCommand cmd = instance.getCommand(command);

        if (cmd != null) {
            cmd.parseCommand(caller, args);
            // Inform caller a matching command was found.
            return true;
        }
        return false;
    }

    public BaseCommand getCommand(String command) {
        return commands.get(command);
    }
    public static final BaseCommand reload = new BaseCommand("- Reloads CanaryMod") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            etc.getInstance().load();
            etc.getInstance().loadData();
            for (Player p : etc.getServer().getPlayerList()) {
                p.getUser().reloadPlayer();
            }
            log.info("CanaryMod reloaded by " + caller.getName());
            caller.notify("Successfully reloaded config");
        }
    };
    public static final BaseCommand modify = new BaseCommand("[player] [key] [value] - Type /modify for more info", "Overriden onBadSyntax", 3) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (parameters.length > 2 && parameters[2].contains(":")) {
                for (int i = 3; i < parameters.length; i++) {
                    if (!parameters[i].contains(":")) {
                        onBadSyntax(caller, null);
                        return;
                    }
                }

                Player player = etc.getServer().matchPlayer(parameters[1]);

                if (player == null) {
                    if (!etc.getDataSource().doesPlayerExist(parameters[1])) {
                        caller.notify("Player does not exist.");
                        return;
                    } else {
                        player = etc.getDataSource().getPlayer(parameters[1]);
                        player.setOfflineName(parameters[1]);
                    }
                }

                for (int i = 2; i < parameters.length; i++) {
                    if (parameters[i].split(":").length != 2) {
                        caller.notify("This key:value pair is deformed... " + parameters[i]);
                        return;
                    }
                    String key = parameters[i].split(":")[0];
                    String value = parameters[i].split(":")[1];
                    boolean newUser = false;

                    if (!etc.getDataSource().doesPlayerExist(player.getOfflineName())) {
                        if (!key.equalsIgnoreCase("groups") && !key.equalsIgnoreCase("g")) {
                            caller.notify("When adding a new user, set their group(s) first.");
                            return;
                        }
                        caller.notify("Adding new user.");
                        newUser = true;
                        player.setCanModifyWorld(true);
                    }

                    boolean skip = false;

                    if ((key.equalsIgnoreCase("groups") || key.equalsIgnoreCase("g"))) {
                        for (String groupname : value.split(",")) {
                            if (!etc.getDataSource().doesGroupExist(groupname)) {
                                caller.notify(String.format("Group %s does not exist. Skipping group key.", groupname));
                                skip = true;
                            }
                        }
                    }

                    if (!skip) {
                        updatePlayerValues(player, key, value);
                        saveChanges(player, newUser);
                    }

                    log.info("Modifed user " + player.getOfflineName() + ". " + key + " => " + value + " by " + caller.getName());
                }
                caller.notify("Modified user.");
            } else {
                if (parameters.length < 4) {
                    onBadSyntax(caller, null);
                    return;
                }

                Player player = etc.getServer().matchPlayer(parameters[1]);

                if (player == null) {
                    if (!etc.getDataSource().doesPlayerExist(parameters[1])) {
                        caller.notify("Player does not exist.");
                        return;
                    } else {
                        player = etc.getDataSource().getPlayer(parameters[1]);
                        player.setOfflineName(parameters[1]);
                    }
                }

                String key = parameters[2];
                String value = parameters[3];
                boolean newUser = false;

                if (!etc.getDataSource().doesPlayerExist(player.getOfflineName())) {
                    if (!key.equalsIgnoreCase("groups") && !key.equalsIgnoreCase("g")) {
                        caller.notify("When adding a new user, set their group(s) first.");
                        return;
                    }
                    caller.notify("Adding new user.");
                    newUser = true;
                }

                if ((key.equalsIgnoreCase("groups") || key.equalsIgnoreCase("g"))) {
                    for (String groupname : value.split(",")) {
                        if (!etc.getDataSource().doesGroupExist(groupname)) {
                            caller.notify(String.format("Group %s does not exist. %s groups not modified.", groupname, player.getOfflineName()));
                            return;
                        }
                    }
                }

                updatePlayerValues(player, key, value);
                saveChanges(player, newUser);

                caller.notify("Modified user.");
                // Send to server
                // log too,
                // regardless of
                // caller.
                log.info("Modifed user " + player.getOfflineName() + ". " + key + " => " + value + " by " + caller.getName());
            }
        }

        private void saveChanges(Player player, boolean newUser) {
            if (newUser) {
                etc.getDataSource().addPlayer(player);
            } else {
                etc.getDataSource().modifyPlayer(player);
            }
        }

        private void updatePlayerValues(Player player, String key, String value) {
            if (key.equalsIgnoreCase("prefix") || key.equalsIgnoreCase("p")) {
                player.setPrefix(value);
            } else if (key.equalsIgnoreCase("commands") || key.equalsIgnoreCase("c")) {
                player.setCommands(value.split(","));
            } else if (key.equalsIgnoreCase("groups") || key.equalsIgnoreCase("g")) {
                player.setGroups(value.split(","));
            } else if (key.equalsIgnoreCase("ignoresrestrictions") || key.equalsIgnoreCase("ir")) {
                player.setIgnoreRestrictions(value.equalsIgnoreCase("true") || value.equals("1"));
            } else if (key.equalsIgnoreCase("admin") || key.equalsIgnoreCase("a")) {
                player.setAdmin(value.equalsIgnoreCase("true") || value.equals("1"));
            } else if (key.equalsIgnoreCase("modworld") || key.equalsIgnoreCase("mw")) {
                player.setCanModifyWorld(value.equalsIgnoreCase("true") || value.equals("1"));
            }
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] params) {
            caller.notify("Usage is: /modify [player] [key] [value]");
            caller.notify("Keys:");
            caller.notify("prefix: only the letter the color represents");
            caller.notify("commands: list seperated by comma");
            caller.notify("groups: list seperated by comma");
            caller.notify("ignoresrestrictions: true or false");
            caller.notify("admin: true or false");
            caller.notify("modworld: true or false");
        }
    };
    public final static BaseCommand whitelist = new BaseCommand("[operation (add or remove)] [player]", "whitelist [operation (toggle, add or remove)] <player>", 2) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (parameters[1].equalsIgnoreCase("toggle")) {
                caller.notify((etc.getInstance().toggleWhitelist() ? "Whitelist enabled" : "Whitelist disabled"));
            } else if (parameters.length == 3) {
                if (parameters[1].equalsIgnoreCase("add")) {
                    etc.getDataSource().addToWhitelist(parameters[2]);
                    caller.notify(parameters[2] + " added to whitelist");
                } else if (parameters[1].equalsIgnoreCase("remove")) {
                    etc.getDataSource().removeFromWhitelist(parameters[2]);
                    caller.notify(parameters[2] + " removed from whitelist");
                } else {
                    caller.notify("Invalid operation.");
                }
            } else {
                caller.notify("Invalid operation.");
            }
        }
    };
    public final static BaseCommand reservelist = new BaseCommand("[operation (add or remove)] [player]", "reservelist [operation (add or remove)] [player]", 3, 3) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (parameters[1].equalsIgnoreCase("add")) {
                etc.getDataSource().addToReserveList(parameters[2]);
                caller.notify(parameters[2] + " added to reservelist");
            } else if (parameters[1].equalsIgnoreCase("remove")) {
                etc.getDataSource().removeFromReserveList(parameters[2]);
                caller.notify(parameters[2] + " removed from reservelist");
            } else {
                caller.notify("Invalid operation.");
            }
        }
    };
    public final static BaseCommand listplugins = new BaseCommand("- Lists all plugins") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            caller.notify("Plugins" + Colors.White + ": " + etc.getLoader().getPluginList());
        }
    };
    public final static BaseCommand reloadplugin = new BaseCommand("[plugin] - Reloads plugin", "Correct usage is: /reloadplugin [plugin]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (etc.getLoader().reloadPlugin(parameters[1])) {
                caller.notify("Plugin reloaded.");
            } else {
                caller.notify("Unable to reload plugin. Check capitalization and/or server logfile.");
            }
        }
    };
    public final static BaseCommand enableplugin = new BaseCommand("[plugin] - Enables plugin", "Correct usage is: /enableplugin [plugin]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (etc.getLoader().enablePlugin(parameters[1])) {
                caller.notify("Plugin enabled.");
            } else {
                caller.notify("Unable to enable plugin. Check capitalization and/or server logfile.");
            }
        }
    };
    public final static BaseCommand disableplugin = new BaseCommand("[plugin] - Disables plugin", "Correct usage is: /disableplugin [plugin]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            etc.getLoader().disablePlugin(parameters[1]);
            caller.notify("Plugin disabled.");
        }
    };
    public final static BaseCommand version = new BaseCommand("- Displays the server version") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!etc.getInstance().getTainted()) {
                if (etc.getInstance().isCrow()) {
                    caller.notify(Colors.Gold + "Crow Test Build " + etc.getInstance().getVersionStr());
                } else {
                    caller.notify(Colors.Gold + "CanaryMod Build " + etc.getInstance().getVersionStr());
                }
            } else {
                caller.notify(Colors.Gold + "Tainted Build Information: " + etc.getInstance().getVersionStr());
            }
        }
    };
    public static final BaseCommand banlist = new BaseCommand("<IP or bans> - Gives a list of bans") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            boolean ips = false;

            if (split.length == 2 && split[1].equalsIgnoreCase("ips")) {
                ips = true;
            }

            if (!ips) {
                caller.notify(Colors.Blue + "Ban list:" + Colors.White + " " + etc.getMCServer().h.getBans());
            } else {
                caller.notify(Colors.Blue + "IP Ban list:" + Colors.White + " " + etc.getMCServer().h.getIpBans());
            }
        }
    };
    public static final BaseCommand banip = new BaseCommand("[Player] <Reason> - Bans the player's IP", "Correct usage is: /banip [player] <reason> (optional) NOTE: this permabans IPs.", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null) {
                if (caller instanceof Player && !((Player) caller).hasControlOver(player)) {
                    caller.notify("You can't ban that user.");
                    return;
                }

                // adds player to ban list
                if (split.length > 2) {
                    BanSystem.fileIpBan(player, etc.combineSplit(2, split, " "));
                } else {
                    BanSystem.fileIpBan(player);
                }

                etc.getLoader().callHook(PluginLoader.Hook.IPBAN, new Object[]{(caller instanceof Player) ? (Player) caller : null, player, split.length >= 3 ? etc.combineSplit(2, split, " ") : ""});

                log.info("IP Banning " + player.getName() + " (IP: " + player.getIP() + ")");
                caller.notify("IP Banning " + player.getName() + " (IP: " + player.getIP() + ")");

                if (split.length > 2) {
                    player.kick("IP Banned by " + caller.getName() + ": " + etc.combineSplit(2, split, " "));
                } else {
                    player.kick("IP Banned by " + caller.getName() + ".");
                }
            } else {
                caller.notify("Can't find user " + split[1] + ".");
            }
        }
    };
    public static final BaseCommand ban = new BaseCommand("[Player] <Reason> - Bans the player", "Correct usage is: /ban [player] <reason> (optional)", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null) {
                if (caller instanceof Player && !((Player) caller).hasControlOver(player)) {
                    caller.notify("You can't ban that user.");
                    return;
                }

                // adds player to ban list
                if (split.length > 2) {
                    BanSystem.fileBan(player, etc.combineSplit(2, split, " "));
                } else {
                    BanSystem.fileBan(player);
                }

                etc.getLoader().callHook(PluginLoader.Hook.BAN, new Object[]{(caller instanceof Player) ? (Player) caller : null, player, split.length >= 3 ? etc.combineSplit(2, split, " ") : ""});

                if (split.length > 2) {
                    player.kick("Banned by " + caller.getName() + ": " + etc.combineSplit(2, split, " "));
                } else {
                    player.kick("Banned by " + caller.getName() + ".");
                }
                log.info(caller.getName() + ": banning " + player.getName());
                caller.notify("Banning " + player.getName());
            } else {
                if (!etc.getDataSource().isOnBanList(split[1], "")) {
                    etc.getDataSource().addBan(new Ban(split[1]));
                    log.info(caller.getName() + ": banning " + split[1]);
                    caller.notify("Banning " + split[1]);
                } else {
                    caller.notify(String.format("%s is already banned from this server", split[1]));
                }
            }
        }
    };
    public static final BaseCommand unban = new BaseCommand("[Player] - Unbans the player", "Correct usage is: /unban [player]", 2, 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            etc.getServer().unban(split[1]);
            caller.notify("Unbanned " + split[1]);
        }
    };
    public static final BaseCommand unbanip = new BaseCommand("[IP] - Unbans the IP", "Correct usage is: /unbanip [ip]", 2, 2) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            caller.notify("This command is going to be phased out.");
            caller.notify("For new bans, you can just use /unban to unban IPs.");
            etc.getDataSource().expireBan(new Ban(parameters[1]));
            etc.getMCServer().h.d(parameters[1]);
            caller.notify("Unbanned " + parameters[1]);
        }
    };
    // TODO: add a way to ban by IP, either a new command or an option for /tempban
    public static final BaseCommand tempban = new BaseCommand("<'ip'> [player] [time] <reason> - Bans the player for the specified time", "Overridden because multiline", 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            boolean byIp = split[1].equalsIgnoreCase("ip");
            if (byIp)
                split = (split[0] + " " + etc.combineSplit(2, split, " ")).split(" ");
            
            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null) {
                if (caller instanceof Player && !((Player) caller).hasControlOver(player)) {
                    caller.notify("You can't ban that user.");
                    return;
                }

                if (split.length > 3) {
                    BanSystem.fileBan(player, etc.combineSplit(3, split, " "),
                            (int) (matchFutureDate(split[2]) / 1000), byIp);
                } else {
                    BanSystem.fileBan(player, etc.getInstance().getDefaultBanMessage(),
                            (int) (matchFutureDate(split[2]) / 1000), byIp);
                }

                etc.getLoader().callHook(PluginLoader.Hook.BAN, new Object[]{(caller instanceof Player) ? (Player) caller : null, player, split.length >= 3 ? etc.combineSplit(2, split, " ") : ""});

                if (split.length > 3) {
                    player.kick("Banned by " + caller.getName() + ": " + etc.combineSplit(3, split, " "));
                } else {
                    player.kick("Banned by " + caller.getName() + ".");
                }
                log.info(caller.getName() + ": banning " + player.getName());
                caller.notify("Banning " + player.getName());
            } else if (byIp) {
                if (!etc.getDataSource().isOnBanList("", split[1])) {
                    Ban b = new Ban();
                    b.setIp(split[1]);
                    b.setTimestamp((int) (matchFutureDate(split[2]) / 1000));
                    etc.getDataSource().addBan(new Ban(split[1]));
                    log.info(caller.getName() + ": banning " + split[1]);
                    caller.notify("Banning " + split[1]);
                }
            } else {
                if (!etc.getDataSource().isOnBanList(split[1], "")) {
                    etc.getDataSource().addBan(new Ban(split[1]));
                    log.info(caller.getName() + ": banning " + split[1]);
                    caller.notify("Banning " + split[1]);
                } else {
                    caller.notify(String.format("%s is already banned from this server", split[1]));
                }
            }
        }
        
        // Thanks to sk89q's CommandBook for this parser
        public long matchFutureDate(String filter) throws IllegalArgumentException {
            if (filter.equalsIgnoreCase("now")) return System.currentTimeMillis();
            String[] groupings = filter.split("-");
            if (groupings.length == 0) throw new IllegalArgumentException("Invalid date specified");
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(System.currentTimeMillis());
            for (String str : groupings) {
                int type;
                switch (str.charAt(str.length() - 1)) {
                    case 'm':
                        type = Calendar.MINUTE;
                        break;
                    case 'h':
                        type = Calendar.HOUR;
                        break;
                    case 'd':
                        type = Calendar.DATE;
                        break;
                    case 'w':
                        type = Calendar.WEEK_OF_YEAR;
                        break;
                    case 'y':
                        type = Calendar.YEAR;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown date value specified");
                }
                cal.add(type, Integer.valueOf(str.substring(0, str.length() -1)));
            }
            return cal.getTimeInMillis();
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] parameters) {
            caller.notify("Correct usage: /tempban <'ip'> [player] [time] <reason> (optional)");
            caller.notify("Time is given as a series of [number][m|h|d|w|y] split by -.");
            caller.notify("The characters m, h, d, w, y stand for minutes, hours, days, weeks");
            caller.notify("  and years, respectively");
        }
    };
    public static final BaseCommand kick = new BaseCommand("[Player] <Reason> - Kicks player", "Correct usage is: /kick [player] <reason> (optional)", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null) {
                if ((caller instanceof Player) && !((Player) caller).hasControlOver(player)) {
                    caller.notify("You can't kick that user.");
                    return;
                }

                etc.getLoader().callHook(PluginLoader.Hook.KICK, new Object[]{(caller instanceof Player) ? (Player) caller : null, player, split.length >= 3 ? etc.combineSplit(2, split, " ") : ""});

                if (split.length > 2) {
                    player.kick("Kicked by " + caller.getName() + ": " + etc.combineSplit(2, split, " "));
                } else {
                    player.kick("Kicked by " + caller.getName() + ".");
                }
                log.info("Kicking " + player.getName());
                caller.notify("Kicking " + player.getName());
            } else {
                caller.notify("Can't find user " + split[1] + ".");
            }
        }
    };
    public static final BaseCommand kickall = new BaseCommand("<Reason> - Kicks all players", "Correct usage is: /kickall <reason> (optional)", 1) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            log.info("Kicking all players.");
            Object[] playerObjects = etc.getServer().getPlayerList().toArray();
            for (Object playerObject : playerObjects) {
                Player player = (Player) playerObject;
                if (player != null && player.isConnected()) {
                    etc.getLoader().callHook(PluginLoader.Hook.KICK, new Object[]{(caller instanceof Player) ? (Player) caller : null, player, split.length >= 2 ? etc.combineSplit(1, split, " ") : ""});

                    if (split.length > 1) {
                        player.kick("Kicked by " + caller.getName() + ": " + etc.combineSplit(1, split, " "));
                    } else {
                        player.kick("Kicked by " + caller.getName() + ".");
                    }
                }
            }
            log.info("Kicked all players.");
        }
    };
}
