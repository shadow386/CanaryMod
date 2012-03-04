import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import net.minecraft.server.MinecraftServer;


public class PlayerCommands {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static PlayerCommands instance;
    private final LinkedHashMap<String, BaseCommand> commands = new LinkedHashMap<String, BaseCommand>();

    public PlayerCommands() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Command.class)) {
                for (String command : field.getAnnotation(Command.class).value()) {
                    try {
                        add(command.equals("") ? field.getName() : command, (BaseCommand) field.get(null));
                    } catch (IllegalAccessException e) {}
                }
            }
        } // impossible
    }

    /**
     * Add a command to the player list.
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
     * Remove a command from the player list.
     * 
     * @param name
     */
    public void remove(String name) {
        if (name != null) {
            etc.getInstance().removeCommand("/" + name);
            commands.remove(name);
        }
    }

    /**
     * Performs a lookup for a command of the given name and executes it if found. Returns false if command not found.
     * 
     * @param command
     *            The command to run
     * @param caller
     *            The {@link MessageReceiver} to send messages back to (assumed to be the caller)
     * @param args
     *            The arguments to {@code command}
     * @return true if {@code command} was found, false otherwise
     */
    public static boolean parsePlayerCommand(MessageReceiver caller, String command, String[] args) {
        if (instance == null) {
            instance = new PlayerCommands();
        }

        BaseCommand cmd = instance.getCommand(command);

        if (cmd != null) {
            cmd.parseCommand(caller, args);
            // Inform caller a matching command was found.
            return true;
        }
        return false;
    }

    /**
     * Searches for and returns {@code command} if found, {@code null} otherwise.
     * 
     * @param command
     *            The command to search for
     * @return {@code command} if found, {@code null} otherwise
     */
    public BaseCommand getCommand(String command) {
        return commands.get(command);
    }

    @Command
    public static final BaseCommand help = new BaseCommand("[Page|Pattern] - Shows a list of commands. 7 per page.") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            // Meh, not the greatest way, but not the worst either.
            List<String> availableCommands = new ArrayList<String>();

            for (Entry<String, String> entry : etc.getInstance().getCommands().entrySet()) {
                if ((caller instanceof Player) && ((Player) caller).canUseCommand(entry.getKey())) {
                    if (entry.getKey().equals("/kit") && !etc.getDataSource().hasKits()) {
                        continue;
                    }
                    if (entry.getKey().equals("/listwarps") && !etc.getDataSource().hasWarps()) {
                        continue;
                    }

                    availableCommands.add(entry.getKey() + " " + entry.getValue());
                }
            }

            caller.notify(Colors.Blue + "Available commands (" + (split.length > 1 ? (split[1].matches("\\d+") ? "Page " + split[1] + " of " + (int) ((double) availableCommands.size() / (double) 7 + 1) : "Matching " + etc.combineSplit(1, split, " ")) : "Page 1 of " + (int) ((double) availableCommands.size() / (double) 7 + 1)) + ") [] = required <> = optional:");
            if (split.length > 1) {
                if (split[1].matches("\\d+")) {
                    try {
                        int amount = Integer.parseInt(split[1]);

                        if (amount > 0) {
                            amount = (amount - 1) * 7;
                        } else {
                            amount = 0;
                        }

                        for (int i = amount; i < amount + 7; i++) {
                            if (availableCommands.size() > i) {
                                caller.notify(availableCommands.get(i));
                            }
                        }
                    } catch (NumberFormatException ex) {
                        caller.notify("Not a valid page number.");
                    }
                } else {
                    try {
                        int count = 0;
                        Pattern p = Pattern.compile(etc.combineSplit(1, split, " "));

                        for (String command : availableCommands) {
                            if (p.matcher(command).find()) {
                                caller.notify(command);
                                count += 1;
                            }
                            if (count > 6) {
                                break;
                            }
                        }
                        if (count == 0) {
                            caller.notify("No matches found");
                        }
                    } catch (java.util.regex.PatternSyntaxException e) {
                        caller.notify("Invalid pattern.");
                    }
                }
            } else {
                for (int i = 0; i < 7; i++) {
                    if (availableCommands.size() > i) {
                        caller.notify(availableCommands.get(i));
                    }
                }
            }
        }
    };
    @Command
    public static final BaseCommand mute = new BaseCommand("[Player] - Mutes the player", "Correct usage is: /mute [player]", 2, 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null) {
                if (player.toggleMute()) {
                	//df: adding to mutelist
                	etc.getDataSource().setPlayerToMuteList(player.getName());
                    caller.notify("Player was muted");
                } else {
                	//df: removing from mute list
                	etc.getDataSource().removePlayerFromMuteList(player.getName());
                    caller.notify("Player was unmuted");
                }
            } else {
                caller.notify("Can't find player " + split[1]);
            }
        }
    };
    @Command({ "tell", "msg", "m" })
    public static final BaseCommand tell = new BaseCommand("[Player] [Message] - Sends a message to player", "Correct usage is: /msg [player] [message]", 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if ((caller instanceof Player) && ((Player) caller).isMuted()) {
                caller.notify("You are currently muted.");
                return;
            }

            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null) {
                if (player.getName().equals(caller.getName())) {
                    caller.notify("You can't message yourself!");
                    return;
                }

                player.sendMessage("(MSG) " + (caller instanceof Player ? ((Player) caller).getColor() : "") + "<" + caller.getName() + "> " + Colors.White + etc.combineSplit(2, split, " "));
                caller.notify(Colors.White + "(MSG) " +  "<" + (caller instanceof Player ? ((Player) caller).getColor() : "") +  caller.getName() + Colors.White + "> " + Colors.White + etc.combineSplit(2, split, " "));
            } else {
                caller.notify("Couldn't find player " + split[1]);
            }
        }
    };
    @Command
    public static final BaseCommand kit = new BaseCommand("[Kit] - Gives a kit. To get a list of kits type /kit", "Available kits (overridden)", 2, 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player toGive = (Player) caller;

            if (split.length > 2) {
                if (caller instanceof Player && !((Player) caller).canIgnoreRestrictions()) {
                    onBadSyntax(caller, split);
                } else {
                    toGive = etc.getServer().matchPlayer(split[2]);
                }
            }

            Kit kit = etc.getDataSource().getKit(split[1]);

            if (toGive != null) {
                if (kit != null) {
                    if (!((Player) caller).isInGroup(kit.Group) && !kit.Group.equals("")) {
                        caller.notify("That kit does not exist.");
                    } else if (((Player) caller).getOnlyOneUseKits().contains(kit.Name)) {
                        caller.notify("You can only get this kit once per login.");
                    } else if (MinecraftServer.b.containsKey(caller.getName() + " " + kit.Name)) {
                        caller.notify("You can't get this kit again for a while.");
                    } else { {
                            if (!((Player) caller).canIgnoreRestrictions()) {
                                if (kit.Delay >= 0) {
                                    MinecraftServer.b.put(caller.getName() + " " + kit.Name, kit.Delay);
                                } else {
                                    ((Player) caller).getOnlyOneUseKits().add(kit.Name);
                                }
                            }

                            log.info(caller.getName() + " got a kit!");
                            toGive.notify("Enjoy this kit!");
                            for (Entry<String, Integer> entry : kit.IDs.entrySet()) {
                                try {
                                    int itemId = 0;

                                    try {
                                        itemId = Integer.parseInt(entry.getKey());
                                    } catch (NumberFormatException n) {
                                        itemId = etc.getDataSource().getItem(entry.getKey());
                                    }

                                    toGive.giveItem(itemId, kit.IDs.get(entry.getKey()));
                                } catch (Exception e1) {
                                    log.info("Got an exception while giving out a kit (Kit name \"" + kit.Name + "\"). Are you sure all the Ids are numbers?");
                                    caller.notify("The server encountered a problem while giving the kit :(");
                                }
                            }
                        }
                    }
                } else {
                    caller.notify("That kit does not exist.");
                }
            } else {
                caller.notify("That user does not exist.");
            }
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] parameters) {
            if (caller instanceof Player) {
                caller.notify("Available kits" + Colors.White + ": " + etc.getDataSource().getKitNames((Player) caller));
            }
        }
    };
    @Command
    public static final BaseCommand tp = new BaseCommand("[Player] - Teleports to player.", "Correct usage is: /tp [player]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }

            Player player = etc.getServer().matchPlayer(split[1]);

            if (player == null) {
                caller.notify("Can't find user " + split[1] + ".");
                return;
            }
            if (player.getWorld().getType() != ((Player) caller).getWorld().getType() && !((Player) caller).canIgnoreRestrictions()) {
                caller.notify("That player is in another world.");
                return;
            }
            if (caller.equals(player)) {
                caller.notify("You're already here!");
                return;
            }

            log.info(caller.getName() + " teleported to " + player.getName());
            ((Player) caller).teleportTo(player);
        }
    };
    @Command({ "tphere", "s" })
    public static final BaseCommand tphere = new BaseCommand("[Player] - Teleports the player to you", "Correct usage is: /tphere [player]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }

            Player player = etc.getServer().matchPlayer(split[1]);

            if (player == null) {
                caller.notify("Can't find user " + split[1] + ".");
                return;
            }

            if (player.getWorld().getType() != ((Player) caller).getWorld().getType() && !((Player) caller).canIgnoreRestrictions()) {
                caller.notify("That player is in another world.");
                return;
            }
            if (caller.equals(player)) {
                caller.notify("Wow look at that! You teleported yourself to yourself!");
                return;
            }
            log.info(caller.getName() + " teleported " + player.getName() + " to their self.");
            player.teleportTo((Player) caller);
        }
    };
    @Command({ "playerlist", "who" })
    public static final BaseCommand playerlist = new BaseCommand("- Shows a list of players") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            caller.notify("Player list (" + etc.getServer().getPlayerList().size() + "/" + etc.getInstance().getPlayerLimit() + "): " + Colors.White + etc.getServer().getPlayerNames());
        }
    };
    @Command({ "item", "i", "give" })
    public static final BaseCommand item = new BaseCommand("[ID] <Amount> <Damage> <Player> - Gives items", "Correct usage is: /item [itemid] <amount> <damage> <player>", 2, 5) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player player = (Player) caller;
            Player toGive = (Player) caller;
            int itemId = 0, amount = 1, damage = 0;

            try {
                if (split.length > 1) {
                    if (split[1].matches("\\d+")) {
                        itemId = Integer.parseInt(split[1]);
                    } else {
                        itemId = etc.getDataSource().getItem(split[1]);
                    }
                }
                if (split.length > 2) {
                    amount = Integer.parseInt(split[2]);
                    if (amount <= 0 && !player.isAdmin()) {
                        amount = 1;
                    }

                    if (amount > 64 && !player.canIgnoreRestrictions()) {
                        amount = 64;
                    }
                    if (amount > 1024) {
                        amount = 1024;
                    }
                }
                if (split.length == 4) {
                    int temp = -1;

                    if (split[3].matches("\\d+")) {
                        temp = Integer.parseInt(split[3]);
                    } else if (player.canIgnoreRestrictions()) {
                        toGive = etc.getServer().matchPlayer(split[3]);
                    }
                    if (temp > -1 && temp < 201) {
                    	damage = temp;
                    }
                } else if (split.length == 5) {
                    damage = Integer.parseInt(split[3]);
                    if (itemId == 383)
                    	System.out.println("ItemID: "+itemId);
                   	if (damage < 0 || damage > 200) {
                   		damage = 0;
                   	}
                    if (player.canIgnoreRestrictions()) {
                        toGive = etc.getServer().matchPlayer(split[4]);
                    }
                }

            } catch (NumberFormatException localNumberFormatException) {
                caller.notify("Improper ID and/or amount.");
                return;
            }

            if (toGive != null) {

                boolean allowedItem = etc.getInstance().getAllowedItems().isEmpty() || etc.getInstance().getAllowedItems().contains(itemId);

                if (!etc.getInstance().getDisallowedItems().isEmpty() && etc.getInstance().getDisallowedItems().contains(itemId)) {
                    allowedItem = false;
                }

                if (Item.isValidItem(itemId)) {
                    if (allowedItem || player.canIgnoreRestrictions()) {
                        Item i = new Item(itemId, amount, -1, damage);

                        log.info("Giving " + toGive.getName() + " some " + i.toString());
                        // toGive.giveItem(itemId, amount);
                        Inventory inv = toGive.getInventory();
                        ArrayList<Item> list = new ArrayList<Item>();

                        for (Item it : inv.getContents()) {
                            if (it != null && it.getItemId() == i.getItemId() && it.getDamage() == i.getDamage()) {
                                list.add(it);
                            }
                        }

                        for (Item it : list) {
                            if (it.getAmount() < 64) {
                                if (amount >= 64 - it.getAmount()) {
                                    amount -= 64 - it.getAmount();
                                    it.setAmount(64);
                                    toGive.giveItem(it);
                                } else {
                                    it.setAmount(it.getAmount() + amount);
                                    amount = 0;
                                    toGive.giveItem(it);
                                }
                            }
                        }
                        if (amount != 0) {
                            i.setAmount(64);
                            while (amount > 64) {
                                amount -= 64;
                                toGive.giveItem(i);
                                i.setSlot(-1);
                            }
                            i.setAmount(amount);
                            toGive.giveItem(i);
                        }
                        if (toGive.getName().equalsIgnoreCase(caller.getName())) {
                            caller.notify("There you go " + caller.getName() + ".");
                        } else {
                            caller.notify("Gift given! :D");
                            toGive.notify("Enjoy your gift! :D");
                        }
                    } else if (!allowedItem && !player.canIgnoreRestrictions()) {
                        caller.notify("You are not allowed to spawn that item.");
                    }
                } else {
                    caller.notify("No item with ID " + split[1]);
                }

            } else {
                caller.notify("Can't find user " + split[3]);
            }
        }
    };
    @Command({ "cloth", "dye" })
    public static final BaseCommand clothdye = new BaseCommand("[Color] [Amount] - Gives you the specified dye/cloth", "Overridden", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            try {
                String color = split[1];

                int amountIndex = 2;

                if (split.length > 2 && !split[2].matches("\\d+")) {
                    amountIndex = 3;
                    color += " " + split[2];
                }

                Cloth.Color c = Cloth.Color.getColor(color);

                if (c == null) {
                    caller.notify("Invalid color name!");
                    return;
                }
                Item i = c.getItem();

                int amount = 1;

                if (split.length > amountIndex) {
                    amount = Integer.parseInt(split[amountIndex]);
                    if (amount <= 0 && ((caller instanceof Player) && !((Player) caller).isAdmin())) {
                        amount = 1;
                    }

                    if (amount > 64 && ((caller instanceof Player) && !((Player) caller).canIgnoreRestrictions())) {
                        amount = 64;
                    }
                    if (amount > 1024) {
                        amount = 1024;
                    } // 16 stacks worth. More than enough.
                }

                // If caller is not a Player and no player specified, bail (We
                // can't go giving items to non-players, can we?)
                if (!(caller instanceof Player) && split.length < amountIndex + 2) {
                    return;
                }

                Player toGive = (Player) caller;

                if ((!(caller instanceof Player) || ((Player) caller).canIgnoreRestrictions()) && split.length > amountIndex + 2) {
                    toGive = etc.getServer().matchPlayer(split[amountIndex + 1]);
                }

                if (toGive == null) {
                    caller.notify("Could not find player.");
                    return;
                }

                if (split[0].equalsIgnoreCase("/dye")) {
                    i.setType(Item.Type.InkSack);
                    // some1 had fun inverting this i guess .....
                    i.setDamage(15 - i.getDamage());
                }
                i.setAmount(amount);
                log.info("Giving " + caller.getName() + " some " + i.toString());

                Inventory inv = toGive.getInventory();
                ArrayList<Item> list = new ArrayList<Item>();

                for (Item it : inv.getContents()) {
                    if (it != null && it.getItemId() == i.getItemId() && it.getDamage() == i.getDamage()) {
                        list.add(it);
                    }
                }

                for (Item it : list) {
                    if (it.getAmount() < 64) {
                        if (amount >= 64 - it.getAmount()) {
                            amount -= 64 - it.getAmount();
                            it.setAmount(64);
                            toGive.giveItem(it);
                        } else {
                            it.setAmount(it.getAmount() + amount);
                            amount = 0;
                            toGive.giveItem(it);
                        }
                    }
                }
                if (amount != 0) {
                    i.setAmount(64);
                    while (amount > 64) {
                        amount -= 64;
                        toGive.giveItem(i);
                        i.setSlot(-1);
                    }
                    i.setAmount(amount);
                    toGive.giveItem(i);
                }
                if (toGive.getName().equalsIgnoreCase(caller.getName())) {
                    caller.notify("There you go " + caller.getName() + ".");
                } else {
                    caller.notify("Gift given! :D");
                    toGive.notify("Enjoy your gift! :D");
                }
            } catch (NumberFormatException localNumberFormatException) {
                caller.notify("Improper ID and/or amount.");
            }
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] parameters) {
            if (caller instanceof Player && !((Player) caller).canIgnoreRestrictions()) {
                caller.notify("Correct usage is: " + parameters[0] + " [color] [amount]");
            } else {
                caller.notify("Correct usage is: " + parameters[0] + " [color] [amount] <player> (Optional)");
            }
        }
    };
    
    @Command({ "me", "emote" })
    public static final BaseCommand me = new BaseCommand("[Message] - * hey0 says hi!") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (caller instanceof Player && ((Player) caller).isMuted()) {
                caller.notify("You are currently muted.");
                return;
            }
            if (split.length == 1) {
                return;
            }
            String paramString2 = "* " + (caller instanceof Player ? ((Player) caller).getColor() : "") + caller.getName() + Colors.White + " " + etc.combineSplit(1, split, " ");

            log.info("* " + caller.getName() + " " + etc.combineSplit(1, split, " "));
            etc.getServer().messageAll(paramString2);
        }
    };
    @Command
    public static final BaseCommand sethome = new BaseCommand("- Sets your home") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player) && parameters.length < 2) {
                return;
            }

            Player player;

            if (parameters.length == 2 && (!(caller instanceof Player) || ((Player) caller).isAdmin())) {
                player = etc.getServer().matchPlayer(parameters[1]);
            } else {
                player = (Player) caller;
            }

            if (player == null) {
                caller.notify("Could not find player.");
                return;
            }
            World.Type worldType = player.getWorld().getType();
            if (worldType != World.Type.NORMAL) {
                if (player.canIgnoreRestrictions()) {
                    player.switchWorlds(World.Type.NORMAL.getId());
                } else {
                    player.notify("You cannot set a home in the " + worldType + ", mortal.");
                    return;
                }
            }

            Warp home = new Warp();

            home.Location = player.getLocation();
            home.Group = ""; // no group neccessary, lol.
            home.Name = player.getName();
            etc.getInstance().changeHome(home);

            if (player == caller) {
                caller.notify("Your home has been set.");
            } else {
                caller.notify(player.getName() + "'s home has been set.");
            }
        }
    };
    @Command
    public static final BaseCommand spawn = new BaseCommand("- Teleports you to spawn") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player) && parameters.length < 2) {
                return;
            }

            Player toMove;

            if (parameters.length == 2 && (!(caller instanceof Player) || ((Player) caller).isAdmin())) {
                toMove = etc.getServer().matchPlayer(parameters[1]);
            } else {
                toMove = (Player) caller;
            }

            if (toMove == null) {
                caller.notify("Could not find player.");
                return;
            }
            World.Type worldType = toMove.getWorld().getType();
            if (worldType != World.Type.NORMAL) {
                if (toMove != caller || toMove.canIgnoreRestrictions()) {
                    toMove.switchWorlds(World.Type.NORMAL.getId());
                } else {
                    toMove.sendMessage(Colors.Red + "The veil between the worlds keeps you bound to the " + worldType + "...");
                    return;
                }
            }

            toMove.teleportTo(toMove.getWorld().getSpawnLocation());

        }
    };
    @Command
    public static final BaseCommand setspawn = new BaseCommand("- Sets the spawn point to your position.") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player) && parameters.length < 2) {
                return;
            }

            Player player;

            if (parameters.length == 2 && (!(caller instanceof Player) || ((Player) caller).canIgnoreRestrictions())) {
                player = etc.getServer().matchPlayer(parameters[1]);
            } else {
                player = (Player) caller;
            }

            if (player == null) {
                caller.notify("Could not find player.");
                return;
            }

            if ((player.getWorld().getType().getId()) != 0) {
                caller.notify("You cannot set the spawn point outside of the Overworld, mortal.");
                return;
            }

            for (World.Type type: World.Type.values()) {
                etc.getMCServer().a(type.getId()).s().a((int) player.getX(), (int) player.getY(), (int) player.getZ());
            }

            log.info("Spawn position changed.");
            if (player == caller) {
                caller.notify("You have set the spawn to your current position.");
            } else {
                caller.notify("You have set the spawn to" + player.getName() + "'s current position.");
            }
        }
    };
    @Command
    public static final BaseCommand home = new BaseCommand("- Teleports you home") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player player = (Player) caller;

            Warp home;

            if (split.length > 1 && ((Player) caller).isAdmin()) {
                home = etc.getDataSource().getHome(split[1]);
            } else {
                home = etc.getDataSource().getHome(caller.getName());
            }

            World.Type worldType = player.getWorld().getType();
            if (worldType != World.Type.NORMAL) {
                if (player.canIgnoreRestrictions()) {
                    player.switchWorlds(World.Type.NORMAL.getId());
                } else {
                    player.notify("The veil between the worlds keeps you in the " + worldType + "...");
                    return;
                }
            }

            if (home != null) {
                player.teleportTo(home.Location);
            } else if (split.length > 1 && player.isAdmin()) {
                caller.notify("That player home does not exist");
            } else {
                player.teleportTo(player.getWorld().getSpawnLocation());
            }

        }
    };
    @Command
    public static final BaseCommand warp = new BaseCommand("[Warp] - Warps to the specified warp.", "Correct usage is: /warp [warpname]", 2, 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player toWarp;
            Warp warp = etc.getDataSource().getWarp(split[1]);

            if (split.length == 3) {
                if (!(caller instanceof Player) || ((Player) caller).canIgnoreRestrictions()) {
                    toWarp = etc.getServer().matchPlayer(split[2]);
                } else { {
                        onBadSyntax(caller, split);
                        return;
                    }
                }
            } else if (!(caller instanceof Player)) {
                return;
            } else {
                toWarp = (Player) caller;
            }

            if (toWarp != null) {
                if (warp != null) {
                    if ((caller instanceof Player) && !((Player) caller).isInGroup(warp.Group) && !warp.Group.equals("")) {
                        caller.notify("Warp not found.");
                    } else {
                        World.Type worldType = toWarp.getWorld().getType();
                        if (worldType != World.Type.NORMAL) {
                            if (toWarp != caller || toWarp.canIgnoreRestrictions()) {
                                toWarp.switchWorlds(World.Type.NORMAL.getId());
                            } else {
                                toWarp.sendMessage(Colors.Rose + "The veil between the worlds keeps you in the " + worldType + "...");
                                return;
                            }
                        }

                        toWarp.teleportTo(warp.Location);
                        toWarp.sendMessage(Colors.Rose + "Woosh!");
                    }
                } else {
                    caller.notify("Warp not found");
                }
            } else {
                caller.notify("Player not found.");
            }
        }
    };
    @Command
    public static final BaseCommand listwarps = new BaseCommand("- Gives a list of available warps") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player player = (Player) caller;

            DataSource ds = etc.getDataSource();

            if (!ds.hasWarps() || ds.getWarpNames(player).equals("")) {
                caller.notify("No warps available.");
            } else {
                caller.notify("Available warps: " + Colors.White + ds.getWarpNames(player));
            }

        }
    };
    @Command
    public static final BaseCommand setwarp = new BaseCommand("[Warp] - Sets the warp to your current position.", "Overridden", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }

            Warp warp = new Warp();

            warp.Name = split[1];
            warp.Location = ((Player) caller).getLocation();
            if (split.length == 3) {
                warp.Group = split[2];
            } else {
                warp.Group = "";
            }
            etc.getInstance().setWarp(warp);
            caller.notify("Created warp point " + split[1] + ".");
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] parameters) {
            if (caller instanceof Player && ((Player) caller).canIgnoreRestrictions()) {
                caller.notify("Correct usage is: /setwarp [warpname] [group]");
            } else {
                caller.notify("Correct usage is: /setwarp [warpname]");
            }
        }
    };
    @Command
    public static final BaseCommand removewarp = new BaseCommand("[Warp] - Removes the specified warp.", "Correct usage is: /removewarp [warpname]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Warp warp = etc.getDataSource().getWarp(split[1]);

            if (warp != null) {
                etc.getDataSource().removeWarp(warp);
                caller.notify(Colors.Blue + "Warp removed.");
            } else {
                caller.notify("That warp does not exist");
            }
        }
    };
    @Command
    public static final BaseCommand time = new BaseCommand("[time|'day|night|check|raw'] (rawtime) - Changes or checks the time", "Correct usage is: /time [time|'day|night|check|raw'] (rawtime)", 2, 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            World world;

            if (caller instanceof Player) {
                world = ((Player) caller).getWorld();
            } else {
                world = etc.getServer().getDefaultWorld();
            }

            if (split.length == 2) {
                if (split[1].equalsIgnoreCase("day")) {
                    world.setRelativeTime(0);
                } else if (split[1].equalsIgnoreCase("night")) {
                    world.setRelativeTime(13000);
                } else if (split[1].equalsIgnoreCase("check")) {
                    caller.notify("The time is " + world.getRelativeTime() + "! (RAW: " + world.getTime() + ")");
                } else if (split[1].matches("\\d+")) {
                    world.setRelativeTime(Long.parseLong(split[1]));
                } else {
                    caller.notify("Please enter numbers, not letters.");
                }
            } else if (split[1].equalsIgnoreCase("raw")) {
                if (split[2].matches("\\d+")) {
                    world.setTime(Long.parseLong(split[2]));
                } else {
                    caller.notify("Please enter numbers, not letters.");
                }
            }
        }
    };
    @Command
    public static final BaseCommand mode = new BaseCommand("- Changes your gamemode") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }
            if (split.length == 3 && ((Player) caller).isAdmin()) {
                Player player = etc.getServer().matchPlayer(split[2]);

                if (player == null) {
                    caller.notify("Can't find user " + split[2] + ".");
                    return;
                } else {
                    try {
                        int mode = Integer.parseInt(split[1]);

                        mode = OWorldSettings.a(mode);
                        if (player.getCreativeMode() != mode && !player.getMode()) {
                            caller.notify(Colors.Yellow + "Setting " + player.getName() + " to game mode " + mode);
                            player.setCreativeMode(mode);
                        } else {
                            caller.notify(player.getName() + " already has game mode " + mode);
                        }
                    } catch (NumberFormatException var11) {
                        caller.notify("There\'s no game mode with id " + split[1]);
                    }
                }
            } else if (split.length == 2) {
                if (caller instanceof Player) {
                    try {
                        Player player = ((Player) caller);
                        int mode = Integer.parseInt(split[1]);

                        mode = OWorldSettings.a(mode);
                        if (player.getCreativeMode() != mode) {
                            player.notify(Colors.Yellow + "Setting your game mode to " + mode);
                            player.setCreativeMode(mode);
                        } else {
                            caller.notify("Your game mode is already " + mode);
                        }
                    } catch (NumberFormatException var11) {
                        caller.notify("There\'s no game mode with id " + split[1]);
                    }
                }
            } else {
                Player player = ((Player) caller);

                caller.notify(String.format("Your current gamemode is: %d", player.getCreativeMode()));
            }
        }
    };
    @Command
    public static final BaseCommand getpos = new BaseCommand("- Displays your current position.") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player p = (Player) caller;

            p.sendMessage("Pos X: " + p.getX() + " Y: " + p.getY() + " Z: " + p.getZ());
            p.sendMessage("Rotation: " + p.getRotation() + " Pitch: " + p.getPitch());

            double degreeRotation = ((p.getRotation() - 90) % 360);

            if (degreeRotation < 0) {
                degreeRotation += 360.0;
            }
            p.sendMessage("Compass: " + etc.getCompassPointForDirection(degreeRotation) + " (" + (Math.round(degreeRotation * 10) / 10.0) + ")");
        }
    };
    @Command
    public static final BaseCommand compass = new BaseCommand("- Gives you a compass reading.") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player)) {
                return;
            }

            double degreeRotation = ((((Player) caller).getRotation() - 90) % 360);

            if (degreeRotation < 0) {
                degreeRotation += 360.0;
            }

            caller.notify("Compass: " + etc.getCompassPointForDirection(degreeRotation));
        }
    };
    @Command
    public static final BaseCommand motd = new BaseCommand("- Displays the MOTD") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            etc.getInstance().getMotd(caller);
        }
    };
    @Command
    public static final BaseCommand spawnmob = new BaseCommand("[Name] <Amount> - Spawns a mob at the looked-at position", "Correct usage is: /spawnmob [name] <amount>", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player p = (Player) caller;

            if (!Mob.isValid(split[1])) {
                caller.notify("Invalid mob. Name has to start with a capital like so: Pig");
                return;
            }

            Location loc;
            Block t = new HitBlox(p).getTargetBlock();

            if (t == null) {
                loc = p.getLocation();
            } else {
                loc = new Location(t.getX() + .5D, t.getY() + 1.5D, t.getZ() + .5D);
            }

            if (split.length == 2) {
                Mob mob = new Mob(split[1], loc);

                mob.spawn();
            } else if (split.length == 3) {
                if (split[2].matches("\\d+")) {
                    int mobnumber = Integer.parseInt(split[2]);

                    for (int i = 0; i < mobnumber; i++) {
                        Mob mob = new Mob(split[1], loc);

                        mob.spawn();
                    }
                } else if (!Mob.isValid(split[2])) {
                    caller.notify("Invalid mob name or number of mobs.");
                    caller.notify("Mob names have to start with a capital like so: Pig");
                } else { {
                        Mob mob = new Mob(split[1], loc);

                        mob.spawn(new Mob(split[2]));
                    }
                }
            } else if (split.length == 4) {
                try {
                    int mobnumber = Integer.parseInt(split[3]);

                    if (!Mob.isValid(split[2])) {
                        caller.notify("Invalid rider. Name has to start with a capital like so: Pig");
                    } else {
                        for (int i = 0; i < mobnumber; i++) {
                            Mob mob = new Mob(split[1], loc);

                            mob.spawn(new Mob(split[2]));
                        }
                    }
                } catch (NumberFormatException nfe) {
                    caller.notify("Invalid number of mobs.");
                }
            }
        }
    };
    @Command
    public static final BaseCommand clearinventory = new BaseCommand("- Clears your inventory") {

        @Override
        void execute(MessageReceiver caller, String[] split) {

            Player target;

            if (split.length >= 2) {
                if (!(caller instanceof Player) || ((Player) caller).isAdmin()) {
                    target = etc.getServer().matchPlayer(split[1]);
                } else {
                    return;
                }
            } else {
                target = (Player) caller;
            }
            if (target != null) {
                Inventory inv = target.getInventory();

                inv.clearContents();
                inv.update();
                if (!target.getName().equals(caller.getName())) {
                    caller.notify("Cleared " + target.getName() + "'s inventory.");
                }
            }
        }
    };
    @Command
    public static final BaseCommand mspawn = new BaseCommand("[Mob] - Change the looked at mob spawner's mob", "Correct usage is: /mspawn <name>.", 1, 2) {

        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }

            HitBlox hb = new HitBlox((Player) caller);
            Block block = hb.getTargetBlock();

            if (block != null && block.getType() == 52) { // mob spawner
                MobSpawner ms = (MobSpawner) ((Player) caller).getWorld().getComplexBlock(block.getX(), block.getY(), block.getZ());

                if (ms != null) {
                    if (split.length == 1) {
                        caller.notify(String.format("You are targeting a mob spawner of: %s", ms.getSpawn()));
                    } else {
                        if (!Mob.isValid(split[1])) {
                            caller.notify(String.format("%s is not a valid mob name.", split[1]));
                            return;
                        } else {
                            ms.setSpawn(split[1]);
                        }
                    }
                }

            } else {
                caller.notify("You are not targeting a mob spawner.");
            }
        }
    };
    @Command
    public static final BaseCommand weather = new BaseCommand("<on|off>", "Usage: /weather <on|off>", 1, 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player player = (Player) caller;
            World world = player.getWorld();

            if (split.length == 1) {
                world.setRaining(!world.isRaining());
                caller.notify("Weather toggled.");
            } else if (split[1].equalsIgnoreCase("on")) {
                world.setRaining(true);
                caller.notify(Colors.Yellow + "Weather turned on.");
            } else if (split[1].equalsIgnoreCase("off")) {
                world.setRaining(false);
                caller.notify(Colors.Yellow + "Weather turned off.");
            } else {
                onBadSyntax(caller, split);
            }

        }
    };
    @Command
    public static final BaseCommand thunder = new BaseCommand("<on|off>", "Usage: /thunder <on|off>", 1, 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }
            Player player = (Player) caller;
            World world = player.getWorld();

            if (split.length == 1) {
                world.setThundering(!world.isThundering());
                caller.notify("Thunder toggled.");
            } else if (split[1].equalsIgnoreCase("on")) {
                world.setThundering(true);
                caller.notify(Colors.Yellow + "Thunder turned on.");
            } else if (split[1].equalsIgnoreCase("off")) {
                world.setThundering(false);
                caller.notify(Colors.Yellow + "Thunder turned off.");
            } else {
                onBadSyntax(caller, split);
            }
        }

    };
    @Command
    public static final BaseCommand xp = new BaseCommand("[level|total] - XP status", "Usage: /xp level|total", 2, 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player)) {
                return;
            }

            Player player = (Player) caller;

            if (split.length == 3) {
                Player p = etc.getServer().matchPlayer(split[2]);

                if (p == null) {
                    player.notify(split[2] + " does not exist!");
                    return;
                } else {
                    if (split[1].equalsIgnoreCase("level")) {
                        player.sendMessage(p.getName() + " is level " + Colors.Yellow + p.getLevel());
                    }
                    if (split[1].equalsIgnoreCase("total")) {
                        player.sendMessage(p.getName() + " has " + Colors.Yellow + p.getXP() + Colors.White + " Total EXP");
                    }
                }
            } else if (split.length == 2) {
                if (split[1].equalsIgnoreCase("level")) {
                    player.sendMessage("You are level " + Colors.Yellow + player.getLevel());
                } else if (split[1].equalsIgnoreCase("total")) {
                    player.sendMessage("You have " + Colors.Yellow + player.getXP() + Colors.White + " Total EXP");
                }
            }
        }
    };
    
    @Command
    public static final BaseCommand foodlevel = new BaseCommand("[add|remove|set] <Player> <value> - Sets player food level", "Correct usage is: /foodlevel [add|remove|set] <player> <value>", 2, 4) {
        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player subject = (Player) caller;
            String command = "add";
            int foodLevel = 20;

            try {
                if (split.length == 4) {
                    command = split[1];
                    subject = etc.getServer().matchPlayer(split[2]);
                    foodLevel = Integer.parseInt(split[3]);
                } else if (split.length == 3) {
                    command = split[1];
                    foodLevel = Integer.parseInt(split[2]);
                }
            } catch (NumberFormatException e) {
                caller.notify((split.length == 4 ? split[3] : split[2]) + " is not a valid number.");
            } catch (Exception e) {
                caller.notify("Error on /foodlevel command");
                return;
            }
            
            if (command.equalsIgnoreCase("add")) {
                foodLevel = Math.min(20, subject.getFoodLevel() + foodLevel);
            } else if (command.equalsIgnoreCase("remove")) {
                foodLevel = Math.max(0, subject.getFoodLevel() - foodLevel);
            } else if (command.endsWith("set")) {
                foodLevel = Math.min(20, Math.max(foodLevel, 0));
            }

            if (subject != null) {
                subject.setFoodLevel(foodLevel);
                subject.setFoodExhaustionLevel(1);
            } else {
                caller.notify("Can't find player " + split[1]);
            }
        }
    };
    
    @Command
    public static final BaseCommand god = new BaseCommand("<Player> - Makes player invulnerable", "Correct usage is: /god <player>", 1, 2) {
        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player subject = (Player) caller;
            String info = Colors.Yellow + "You are";

            if (split.length == 2) {
                subject = etc.getServer().matchPlayer(split[1]);
                info = String.format("%s%s is", Colors.Yellow, subject.getName());
            }                       
            if (subject != null) {
                if (subject.getMode()) {
                    caller.notify("Can't apply /god to players in creative mode");
                    return;
                }
                subject.setDamageDisabled(!subject.isDamageDisabled());
                if (subject.isDamageDisabled()) {
                    caller.notify(info + " now invincible!");
                } else {
                    caller.notify(info + " no longer invincible.");
                }
            } else {
                caller.notify("Can't find player " + split[1]);
            }
        }
    };
    @Command
    public static final BaseCommand kill = new BaseCommand("<Player> - Kill the specified player", "Correct usage is: /kill [player]", 1, 2) {
        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player killer = (Player) caller;
            if(split.length == 2) {
                Player victim = etc.getServer().matchPlayer(split[1]);
                if(victim == null) {
                    killer.notify(split[1] + " could not be found (and therefore he has not been killed)");
                    return;
                }
                if(killer.hasControlOver(victim)) {
                    victim.dropInventory();
                    victim.setHealth(0);
                    killer.notify(victim.getName() + " has been killed!");
                }
                else {
                    killer.notify("You cannot kill " + victim.getName());
                }
                
            }
            else {
                killer.dropInventory();
                killer.setHealth(0);
                killer.notify("You suicided.");
            }
            
        }
    };
    
    @Command
    public static final BaseCommand playerinfo = new BaseCommand("<Player> Shows player data", "Correct usage is: /playerinfo <player>", 1, 2) {

        private void sendData(MessageReceiver caller, String caption, Object[] data) {
            caller.notify(Colors.LightGreen + caption + Colors.Gold + Arrays.toString(data));
        }

        private void sendData(MessageReceiver caller, String caption, Object data) {
            caller.notify(Colors.LightGreen + caption + Colors.Gold + String.valueOf(data));
        }

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player subject = null;
            if (split.length == 2) {
                subject = etc.getServer().matchPlayer(split[1]);
            } else if (caller instanceof Player) {
                subject = (Player) caller;
            }
            if (subject != null) {
                
                caller.notify(Colors.Green + subject.getName() + "'s info:");
                sendData(caller, "Prefix: ", subject.getColor());
                sendData(caller, "IP address : ", subject.getIP());
                sendData(caller, "Groups: ", subject.getGroups());
                sendData(caller, "Commands: ", subject.getCommands());
                sendData(caller, "Health: ", subject.getHealth());
                sendData(caller, "Muted: ", subject.isMuted());
                sendData(caller, "Food Level: ", subject.getFoodLevel());
                sendData(caller, "Food Exhaustion: ", String.format("%.2f", subject.getFoodExhaustionLevel()));
                sendData(caller, "Food Saturation: ", String.format("%.2f", subject.getFoodSaturationLevel()));
                sendData(caller, "Experience: ", subject.getXP());
                sendData(caller, "Level: ", subject.getLevel());
                Location l = subject.getLocation();

                sendData(caller, "Position: ", String.format("X: %.2f Y: %.2f Z: %.2f Pitch: %.2f Yawn: %.2f", l.x, l.y, l.z, l.rotX, l.rotY));
                sendData(caller, "World: ", String.format("%s (%d)", subject.getWorld().getType().name(), subject.getWorld().getType().getId()));
                Warp home = etc.getDataSource().getHome(subject.getName());

                if (home != null) {
                    l = home.Location;
                    sendData(caller, "Home: ", String.format("X: %.2f Y: %.2f Z: %.2f", l.x, l.y, l.z));
                } else {
                    sendData(caller, "Home: ", "Not set");
                }
            } else if (split.length == 2) {
                caller.notify(Colors.Yellow + "Can't find player " + split[1]);
            }
        }
    };
}
