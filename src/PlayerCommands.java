
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class PlayerCommands {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static PlayerCommands instance;
    private final LinkedHashMap<String, BaseCommand> commands = new LinkedHashMap<String, BaseCommand>();

    public PlayerCommands() {
        for (Field field : getClass().getDeclaredFields())
            if (field.isAnnotationPresent(Command.class))
                for (String command : field.getAnnotation(Command.class).value())
                    try {
                        add(command.equals("") ? field.getName() : command, (BaseCommand) field.get(null));
                    } catch (IllegalAccessException e) {
                    } //impossible
    }

    /**
     * Add a command to the player list.
     *
     * @param name
     * @param cmd
     */
    public void add(String name, BaseCommand cmd) {
        if (name != null && cmd != null) {
            if (!commands.containsValue(cmd))
                etc.getInstance().addCommand("/" + name, cmd.tooltip);
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
     * Performs a lookup for a command of the given name and executes it if
     * found. Returns false if command not found.
     *
     * @param command The command to run
     * @param caller The {@link MessageReceiver} to send messages back to (assumed to be the caller)
     * @param args The arguments to {@code command}
     * @return true if {@code command} was found, false otherwise
     */
    public static boolean parsePlayerCommand(MessageReceiver caller, String command, String[] args) {
        if (instance == null)
            instance = new PlayerCommands();

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
     * @param command The command to search for
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
            for (Entry<String, String> entry : etc.getInstance().getCommands().entrySet())
                if ((caller instanceof Player) && ((Player) caller).canUseCommand(entry.getKey())) {
                    if (entry.getKey().equals("/kit") && !etc.getDataSource().hasKits())
                        continue;
                    if (entry.getKey().equals("/listwarps") && !etc.getDataSource().hasWarps())
                        continue;

                    availableCommands.add(entry.getKey() + " " + entry.getValue());
                }

            caller.notify(Colors.Blue + "Available commands (" + (split.length > 1 ? (split[1].matches("\\d+") ? "Page " + split[1] + " of " + (int) ((double) availableCommands.size() / (double) 7 + 1) : "Matching " + etc.combineSplit(1, split, " ")) : "Page 1 of " + (int) ((double) availableCommands.size() / (double) 7 + 1)) + ") [] = required <> = optional:");
            if (split.length > 1)
                if (split[1].matches("\\d+"))
                    try {
                        int amount = Integer.parseInt(split[1]);

                        if (amount > 0)
                            amount = (amount - 1) * 7;
                        else
                            amount = 0;

                        for (int i = amount; i < amount + 7; i++)
                            if (availableCommands.size() > i)
                                caller.notify(availableCommands.get(i));
                    } catch (NumberFormatException ex) {
                        caller.notify("Not a valid page number.");
                    }
                else
                    try {
                        int count = 0;
                        for (String command : availableCommands) {
                            if (command.matches(etc.combineSplit(1, split, " "))) {
                                caller.notify(command);
                                count += 1;
                            }
                            if (count > 6)
                                break;
                        }
                        if (count == 0)
                            caller.notify("No matches found");
                    } catch (java.util.regex.PatternSyntaxException e) {
                        caller.notify("Invalid pattern.");
                    }
            else
                for (int i = 0; i < 7; i++)
                    if (availableCommands.size() > i)
                        caller.notify(availableCommands.get(i));
        }
    };
    @Command
    public static final BaseCommand mute = new BaseCommand("[Player] - Mutes the player", "Correct usage is: /mute [player]", 2, 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null)
                if (player.toggleMute())
                    caller.notify("Player was muted");
                else
                    caller.notify("Player was unmuted");
            else
                caller.notify("Can't find player " + split[1]);
        }
    };
    @Command({"tell", "msg", "m"})
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
                caller.notify(Colors.White + "(MSG) " + (caller instanceof Player ? ((Player) caller).getColor() : "") + "<" + caller.getName() + "> " + Colors.White + etc.combineSplit(2, split, " "));
            } else
                caller.notify("Couldn't find player " + split[1]);
        }
    };
    @Command
    public static final BaseCommand kit = new BaseCommand("[Kit] - Gives a kit. To get a list of kits type /kit", "Available kits (overridden)", 2, 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;
            Player toGive = (Player) caller;
            if (split.length > 2)
                if (caller instanceof Player && !((Player) caller).canIgnoreRestrictions())
                    onBadSyntax(caller, split);
                else
                    toGive = etc.getServer().matchPlayer(split[2]);

            Kit kit = etc.getDataSource().getKit(split[1]);
            if (toGive != null)
                if (kit != null)
                    if (!((Player) caller).isInGroup(kit.Group) && !kit.Group.equals(""))
                        caller.notify("That kit does not exist.");
                    else if (((Player) caller).getOnlyOneUseKits().contains(kit.Name))
                        caller.notify("You can only get this kit once per login.");
                    else if (MinecraftServer.b.containsKey(caller.getName() + " " + kit.Name))
                        caller.notify("You can't get this kit again for a while.");
                    else {
                        if (!((Player) caller).canIgnoreRestrictions())
                            if (kit.Delay >= 0)
                                MinecraftServer.b.put(caller.getName() + " " + kit.Name, kit.Delay);
                            else
                                ((Player) caller).getOnlyOneUseKits().add(kit.Name);

                        log.info(caller.getName() + " got a kit!");
                        toGive.notify("Enjoy this kit!");
                        for (Entry<String, Integer> entry : kit.IDs.entrySet())
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
                else
                    caller.notify("That kit does not exist.");
            else
                caller.notify("That user does not exist.");
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] parameters) {
            if (caller instanceof Player)
                caller.notify("Available kits" + Colors.White + ": " + etc.getDataSource().getKitNames((Player) caller));
        }
    };
    @Command
    public static final BaseCommand tp = new BaseCommand("[Player] - Teleports to player.", "Correct usage is: /tp [player]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;

            Player player = etc.getServer().matchPlayer(split[1]);
            if (player == null) {
                caller.notify("Can't find user " + split[1] + ".");
                return;
            }
            if (!player.getWorld().equals(((Player) caller).getWorld()))
                if (((Player) caller).canIgnoreRestrictions()) {
                    if (player != null) {
                        if (caller.equals(player)) {
                            caller.notify("You can't do that...");
                            return;
                        }
                        log.info(caller.getName() + " shifted worlds and teleported to " + player.getName());

                        caller.notify("You feel the world shifting...");
                        ((Player) caller).switchWorlds();
                        caller.notify("The worlds suddenly snap back.");
                        if (!((Player) caller).getWorld().equals(player.getWorld())) {
                            caller.notify("The veil between worlds is still too strong.");
                            return;
                        }
                    }
                } else {

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
    @Command({"tphere", "s"})
    public static final BaseCommand tphere = new BaseCommand("[Player] - Teleports the player to you", "Correct usage is: /tphere [player]", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;

            Player player = etc.getServer().matchPlayer(split[1]);
            if (player == null) {
                caller.notify("Can't find user " + split[1] + ".");
                return;
            }
            if (!player.getWorld().equals(((Player) caller).getWorld()))
                if (((Player) caller).canIgnoreRestrictions()) {
                    if (player != null) {
                        if (caller.equals(player)) {
                            caller.notify("You can't do that...");
                            return;
                        }
                        log.info(caller.getName() + " caused the worlds to shift around " + player.getName());

                        player.notify("You feel the world shifting...");
                        player.switchWorlds();
                        if (!player.getWorld().equals(((Player) caller).getWorld())) {
                            player.notify("The veil between worlds keeps you in your place.");
                            return;
                        }
                    }
                } else {

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
    @Command({"playerlist", "who"})
    public static final BaseCommand playerlist = new BaseCommand("- Shows a list of players") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            caller.notify("Player list (" + etc.getServer().getPlayerList().size() + "/" + etc.getInstance().getPlayerLimit() + "): " + Colors.White + etc.getServer().getPlayerNames());
        }
    };
    @Command({"item", "i", "give"})
    public static final BaseCommand item = new BaseCommand("[ID] <Amount> <Damage> <Player> - Gives items", "Correct usage is: /item [itemid] <amount> <damage> <player>", 2, 5) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;
            Player player = (Player) caller;
            Player toGive = (Player) caller;
            int itemId = 0, amount = 1, damage = 0;
            try {
                if (split.length > 1)
                    if (split[1].matches("\\d+"))
                        itemId = Integer.parseInt(split[1]);
                    else
                        itemId = etc.getDataSource().getItem(split[1]);
                if (split.length > 2) {
                    amount = Integer.parseInt(split[2]);
                    if (amount <= 0 && !player.isAdmin())
                        amount = 1;

                    if (amount > 64 && !player.canIgnoreRestrictions())
                        amount = 64;
                    if (amount > 1024)
                        amount = 1024;
                }
                if (split.length == 4) {
                    int temp = -1;
                    if (split[3].matches("\\d+"))
                        temp = Integer.parseInt(split[3]);
                    else if (player.canIgnoreRestrictions())
                        toGive = etc.getServer().matchPlayer(split[3]);
                    if (temp > -1 && temp < 50)
                        damage = temp;
                } else if (split.length == 5) {
                    damage = Integer.parseInt(split[3]);
                    if (damage < 0 && damage > 49)
                        damage = 0;
                    if (player.canIgnoreRestrictions())
                        toGive = etc.getServer().matchPlayer(split[4]);
                }

            } catch (NumberFormatException localNumberFormatException) {
                caller.notify("Improper ID and/or amount.");
                return;
            }

            if (toGive != null) {

                boolean allowedItem = etc.getInstance().getAllowedItems().isEmpty() || etc.getInstance().getAllowedItems().contains(itemId);

                if (!etc.getInstance().getDisallowedItems().isEmpty() && etc.getInstance().getDisallowedItems().contains(itemId))
                    allowedItem = false;

                if (Item.isValidItem(itemId)) {
                    if (allowedItem || player.canIgnoreRestrictions()) {
                        Item i = new Item(itemId, amount, -1, damage);
                        log.info("Giving " + toGive.getName() + " some " + i.toString());
                        // toGive.giveItem(itemId, amount);
                        Inventory inv = toGive.getInventory();
                        ArrayList<Item> list = new ArrayList<Item>();
                        for (Item it : inv.getContents())
                            if (it != null && it.getItemId() == i.getItemId() && it.getDamage() == i.getDamage())
                                list.add(it);

                        for (Item it : list)
                            if (it.getAmount() < 64)
                                if (amount >= 64 - it.getAmount()) {
                                    amount -= 64 - it.getAmount();
                                    it.setAmount(64);
                                    toGive.giveItem(it);
                                } else {
                                    it.setAmount(it.getAmount() + amount);
                                    amount = 0;
                                    toGive.giveItem(it);
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
                        if (toGive.getName().equalsIgnoreCase(caller.getName()))
                            caller.notify("There you go " + caller.getName() + ".");
                        else {
                            caller.notify("Gift given! :D");
                            toGive.notify("Enjoy your gift! :D");
                        }
                    } else if (!allowedItem && !player.canIgnoreRestrictions())
                        caller.notify("You are not allowed to spawn that item.");
                } else
                    caller.notify("No item with ID " + split[1]);

            } else
                caller.notify("Can't find user " + split[3]);
        }
    };
    @Command({"cloth", "dye"})
    public static final BaseCommand clothdye = new BaseCommand("[Color] [Amount] - Gives you the specified dye/cloth", "Overridden", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            try {
                String color = split[1];

                int amountIndex = split[2].matches("\\d+") || split.length < 4 ? 2 : 3;
                if (amountIndex == 3)
                    color += " " + split[2];

                Cloth.Color c = Cloth.Color.getColor(color);
                if (c == null) {
                    caller.notify("Invalid color name!");
                    return;
                }
                Item i = c.getItem();

                int amount = Integer.parseInt(split[amountIndex]);
                if (amount <= 0 && ((caller instanceof Player) && !((Player) caller).isAdmin()))
                    amount = 1;

                if (amount > 64 && ((caller instanceof Player) && !((Player) caller).canIgnoreRestrictions()))
                    amount = 64;
                if (amount > 1024)
                    amount = 1024; // 16 stacks worth. More than enough.

                // If caller is not a Player and no player specified, bail (We can't go giving items to non-players, can we?)
                if (!(caller instanceof Player) && split.length < amountIndex + 3)
                    return;

                Player toGive = (Player) caller;
                if ((!(caller instanceof Player) || ((Player) caller).canIgnoreRestrictions()) && split.length > amountIndex + 2)
                    toGive = etc.getServer().matchPlayer(split[amountIndex + 1]);

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
                for (Item it : inv.getContents())
                    if (it != null && it.getItemId() == i.getItemId() && it.getDamage() == i.getDamage())
                        list.add(it);

                for (Item it : list)
                    if (it.getAmount() < 64)
                        if (amount >= 64 - it.getAmount()) {
                            amount -= 64 - it.getAmount();
                            it.setAmount(64);
                            toGive.giveItem(it);
                        } else {
                            it.setAmount(it.getAmount() + amount);
                            amount = 0;
                            toGive.giveItem(it);
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
                if (toGive.getName().equalsIgnoreCase(caller.getName()))
                    caller.notify("There you go " + caller.getName() + ".");
                else {
                    caller.notify("Gift given! :D");
                    toGive.notify("Enjoy your gift! :D");
                }
            } catch (NumberFormatException localNumberFormatException) {
                caller.notify("Improper ID and/or amount.");
            }
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] parameters) {
            if (caller instanceof Player && !((Player) caller).canIgnoreRestrictions())
                caller.notify("Correct usage is: " + parameters[0] + " [color] [amount]");
            else
                caller.notify("Correct usage is: " + parameters[0] + " [color] [amount] <player> (Optional)");
        }
    };
    @Command
    public static final BaseCommand banlist = new BaseCommand("<IP or bans> - Gives a list of bans") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            boolean ips = false;
            if (split.length == 2 && split[1].equalsIgnoreCase("ips"))
                ips = true;

            if (!ips)
                caller.notify(Colors.Blue + "Ban list:" + Colors.White + " " + etc.getMCServer().f.getBans());
            else
                caller.notify(Colors.Blue + "IP Ban list:" + Colors.White + " " + etc.getMCServer().f.getIpBans());
        }
    };
    @Command
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
                etc.getMCServer().f.c(player.getIP());
                etc.getLoader().callHook(PluginLoader.Hook.IPBAN, new Object[]{this, player, split.length >= 3 ? etc.combineSplit(2, split, " ") : ""});

                log.info("IP Banning " + player.getName() + " (IP: " + player.getIP() + ")");
                caller.notify("IP Banning " + player.getName() + " (IP: " + player.getIP() + ")");

                if (split.length > 2)
                    player.kick("IP Banned by " + caller.getName() + ": " + etc.combineSplit(2, split, " "));
                else
                    player.kick("IP Banned by " + caller.getName() + ".");
            } else
                caller.notify("Can't find user " + split[1] + ".");
        }
    };
    @Command
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
                etc.getServer().ban(player.getName());

                etc.getLoader().callHook(PluginLoader.Hook.BAN, new Object[]{this, player, split.length >= 3 ? etc.combineSplit(2, split, " ") : ""});

                if (split.length > 2)
                    player.kick("Banned by " + caller.getName() + ": " + etc.combineSplit(2, split, " "));
                else
                    player.kick("Banned by " + caller.getName() + ".");
                log.info("Banning " + player.getName());
                caller.notify("Banning " + player.getName());
            } else {
                etc.getServer().ban(split[1]);
                log.info("Banning " + split[1]);
                caller.notify("Banning " + split[1]);
            }
        }
    };
    @Command
    public static final BaseCommand unban = new BaseCommand("[Player] - Unbans the player", "Correct usage is: /unban [player]", 2, 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            etc.getServer().unban(split[1]);
            caller.notify("Unbanned " + split[1]);
        }
    };
    @Command
    public static final BaseCommand unbanip = new BaseCommand("[IP] - Unbans the IP", "Correct usage is: /unbanip [ip]", 2, 2) {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            etc.getMCServer().f.d(parameters[1]);
            caller.notify("Unbanned " + parameters[1]);
        }
    };
    @Command
    public static final BaseCommand kick = new BaseCommand("[Player] <Reason> - Kicks player", "Correct usage is: /kick [player] <reason> (optional)", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player player = etc.getServer().matchPlayer(split[1]);

            if (player != null) {
                if ((caller instanceof Player) && !((Player) caller).hasControlOver(player)) {
                    caller.notify("You can't kick that user.");
                    return;
                }

                etc.getLoader().callHook(PluginLoader.Hook.KICK, new Object[]{this, player, split.length >= 3 ? etc.combineSplit(2, split, " ") : ""});

                if (split.length > 2)
                    player.kick("Kicked by " + caller.getName() + ": " + etc.combineSplit(2, split, " "));
                else
                    player.kick("Kicked by " + caller.getName() + ".");
                log.info("Kicking " + player.getName());
                caller.notify("Kicking " + player.getName());
            } else
                caller.notify("Can't find user " + split[1] + ".");
        }
    };
    @Command({"me", "emote"})
    public static final BaseCommand me = new BaseCommand("[Message] - * hey0 says hi!") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (caller instanceof Player && ((Player) caller).isMuted()) {
                caller.notify("You are currently muted.");
                return;
            }
            if (split.length == 1)
                return;
            String paramString2 = "* " + (caller instanceof Player ? ((Player) caller).getColor() : "") + caller.getName() + Colors.White + " " + etc.combineSplit(1, split, " ");
            log.info("* " + caller.getName() + " " + etc.combineSplit(1, split, " "));
            etc.getServer().messageAll(paramString2);
        }
    };
    @Command
    public static final BaseCommand sethome = new BaseCommand("- Sets your home") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player) && parameters.length < 2)
                return;

            Player player;
            if (parameters.length == 2 && (!(caller instanceof Player) || ((Player) caller).isAdmin()))
                player = etc.getServer().matchPlayer(parameters[1]);
            else
                player = (Player) caller;

            if (player == null) {
                caller.notify("Could not find player.");
                return;
            }
            if ((player.getWorld().getType().getId()) != 0) {
                caller.notify("You cannot set a home in the Nether, mortal.");
                return;
            }

            Warp home = new Warp();
            home.Location = player.getLocation();
            home.Group = ""; // no group neccessary, lol.
            home.Name = player.getName();
            etc.getInstance().changeHome(home);

            if (player == caller)
                caller.notify("Your home has been set.");
            else
                caller.notify(player.getName() + "'s home has been set.");
        }
    };
    @Command
    public static final BaseCommand spawn = new BaseCommand("- Teleports you to spawn") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player) && parameters.length < 2)
                return;

            Player toMove;
            if (parameters.length == 2 && (!(caller instanceof Player) || ((Player) caller).isAdmin()))
                toMove = etc.getServer().matchPlayer(parameters[1]);
            else
                toMove = (Player) caller;

            if (toMove == null) {
                caller.notify("Could not find player.");
                return;
            }
            if (toMove.getWorld().getType().getId() != 0)
                if (toMove.canIgnoreRestrictions()) {
                    toMove.sendMessage("§4You feel the worlds start to shift...");
                    int derp = toMove.getWorld().getType().getId();
                    toMove.switchWorlds();
                    toMove.sendMessage("§4The worlds suddenly snap back into focus.");
                    if (derp == toMove.getWorld().getType().getId()) {
                        toMove.sendMessage("§4The veil is still too strong.");
                        return;
                    }
                } else {
                    toMove.sendMessage("§4The veil between the worlds keeps you bound to the Nether...");
                    return;
                }

            toMove.teleportTo(toMove.getWorld().getSpawnLocation());

        }
    };
    @Command
    public static final BaseCommand setspawn = new BaseCommand("- Sets the spawn point to your position.") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player) && parameters.length < 2)
                return;

            Player player;
            if (parameters.length == 2 && (!(caller instanceof Player) || ((Player) caller).canIgnoreRestrictions()))
                player = etc.getServer().matchPlayer(parameters[1]);
            else
                player = (Player) caller;

            if (player == null) {
                caller.notify("Could not find player.");
                return;
            }

            if ((player.getWorld().getType().getId()) != 0) {
                caller.notify("You cannot set the spawn point in the Nether, mortal.");
                return;
            }

            OWorldInfo info = player.getWorld().getWorld().x;
            info.a((int) player.getX(), (int) player.getY(), (int) player.getZ());

            log.info("Spawn position changed.");
            if (player == caller)
                caller.notify("You have set the spawn to your current position.");
            else
                caller.notify("You have set the spawn to" + player.getName() + "'s current position.");
        }
    };
    @Command
    public static final BaseCommand home = new BaseCommand("- Teleports you home") {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;
            Player player = (Player) caller;

            Warp home;
            if (split.length > 1 && ((Player) caller).isAdmin())
                home = etc.getDataSource().getHome(split[1]);
            else
                home = etc.getDataSource().getHome(caller.getName());

            if (player.getWorld().getType() != World.Type.NORMAL)
                if (player.canIgnoreRestrictions()) {
                    player.notify("You feel the worlds start to shift...");
                    World derp = player.getWorld();
                    player.switchWorlds();
                    player.notify("The worlds suddenly come back into focus.");
                    if (derp.equals(player.getWorld())) {
                        player.notify("The veil is still too strong.");
                        return;
                    }
                } else {
                    player.notify("The veil between the worlds keeps you in the Nether...");
                    return;
                }

            if (home != null)
                player.teleportTo(home.Location);
            else if (split.length > 1 && player.isAdmin())
                caller.notify("That player home does not exist");
            else
                player.teleportTo(player.getWorld().getSpawnLocation());

        }
    };
    @Command
    public static final BaseCommand warp = new BaseCommand("[Warp] - Warps to the specified warp.", "Correct usage is: /warp [warpname]", 2, 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            Player toWarp;
            Warp warp = etc.getDataSource().getWarp(split[1]);

            if (split.length == 3)
                if (!(caller instanceof Player) || ((Player) caller).canIgnoreRestrictions())
                    toWarp = etc.getServer().matchPlayer(split[2]);
                else {
                    onBadSyntax(caller, split);
                    return;
                }
            else if (!(caller instanceof Player))
                return;
            else
                toWarp = (Player) caller;



            if (toWarp != null)
                if (warp != null)
                    if ((caller instanceof Player) && !((Player) caller).isInGroup(warp.Group) && !warp.Group.equals(""))
                        caller.notify("Warp not found.");
                    else {
                        if (toWarp.getWorld().getType() != World.Type.NORMAL)
                            if (toWarp.canIgnoreRestrictions()) {
                                toWarp.notify("You feel the worlds start to shift...");
                                World derp = toWarp.getWorld();
                                toWarp.switchWorlds();
                                toWarp.notify("The worlds suddenly snap back into focus...");
                                if (derp.equals(toWarp.getWorld())) {
                                    toWarp.notify("The veil is still too strong.");
                                    return;
                                }
                            } else {
                                toWarp.sendMessage("The veil between the worlds keeps you in the Nether...");
                                return;
                            }

                        toWarp.teleportTo(warp.Location);
                        toWarp.sendMessage(Colors.Rose + "Woosh!");
                    }
                else
                    caller.notify("Warp not found");
            else
                caller.notify("Player not found.");
        }
    };
    @Command
    public static final BaseCommand listwarps = new BaseCommand("- Gives a list of available warps") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player))
                return;
            Player player = (Player) caller;

            DataSource ds = etc.getDataSource();
            if (!ds.hasWarps() || ds.getWarpNames(player).equals(""))
                caller.notify("No warps available.");
            else
                caller.notify("Available warps: " + Colors.White + ds.getWarpNames(player));

        }
    };
    @Command
    public static final BaseCommand setwarp = new BaseCommand("[Warp] - Sets the warp to your current position.", "Overridden", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;

            Warp warp = new Warp();
            warp.Name = split[1];
            warp.Location = ((Player) caller).getLocation();
            if (split.length == 3)
                warp.Group = split[2];
            else
                warp.Group = "";
            etc.getInstance().setWarp(warp);
            caller.notify("Created warp point " + split[1] + ".");
        }

        @Override
        public void onBadSyntax(MessageReceiver caller, String[] parameters) {
            if (caller instanceof Player && ((Player) caller).canIgnoreRestrictions())
                caller.notify("Correct usage is: /setwarp [warpname] [group]");
            else
                caller.notify("Correct usage is: /setwarp [warpname]");
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
            } else
                caller.notify("That warp does not exist");
        }
    };
    @Command
    public static final BaseCommand time = new BaseCommand("[time|'day|night|check|raw'] (rawtime) - Changes or checks the time", "Correct usage is: /time [time|'day|night|check|raw'] (rawtime)", 2, 3) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            World world;
            if (caller instanceof Player)
                world = ((Player) caller).getWorld();
            else
                world = etc.getServer().getMCServer().a(0).world;

            if (split.length == 2)
                if (split[1].equalsIgnoreCase("day"))
                    world.setRelativeTime(0);
                else if (split[1].equalsIgnoreCase("night"))
                    world.setRelativeTime(13000);
                else if (split[1].equalsIgnoreCase("check"))
                    caller.notify("The time is " + world.getRelativeTime() + "! (RAW: " + world.getTime() + ")");
                else if (split[1].matches("\\d+"))
                    world.setRelativeTime(Long.parseLong(split[1]));
                else
                    caller.notify("Please enter numbers, not letters.");
            else if (split[1].equalsIgnoreCase("raw"))
                if (split[2].matches("\\d+"))
                    world.setTime(Long.parseLong(split[2]));
                else
                    caller.notify("Please enter numbers, not letters.");
        }
    };
    @Command
    public static final BaseCommand getpos = new BaseCommand("- Displays your current position.") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player))
                return;
            Player p = (Player) caller;

            p.sendMessage("Pos X: " + p.getX() + " Y: " + p.getY() + " Z: " + p.getZ());
            p.sendMessage("Rotation: " + p.getRotation() + " Pitch: " + p.getPitch());

            double degreeRotation = ((p.getRotation() - 90) % 360);
            if (degreeRotation < 0)
                degreeRotation += 360.0;
            p.sendMessage("Compass: " + etc.getCompassPointForDirection(degreeRotation) + " (" + (Math.round(degreeRotation * 10) / 10.0) + ")");
        }
    };
    @Command
    public static final BaseCommand compass = new BaseCommand("- Gives you a compass reading.") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            if (!(caller instanceof Player))
                return;

            double degreeRotation = ((((Player) caller).getRotation() - 90) % 360);
            if (degreeRotation < 0)
                degreeRotation += 360.0;

            caller.notify("Compass: " + etc.getCompassPointForDirection(degreeRotation));
        }
    };
    @Command
    public static final BaseCommand motd = new BaseCommand("- Displays the MOTD") {

        @Override
        void execute(MessageReceiver caller, String[] parameters) {
            for (String line : etc.getInstance().getMotd())
                caller.notify(Colors.White + line);
        }
    };
    @Command
    public static final BaseCommand spawnmob = new BaseCommand("[Name] <Amount> - Spawns a mob at the looked-at position", "Correct usage is: /spawnmob [name] <amount>", 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;
            Player p = (Player) caller;

            if (!Mob.isValid(split[1])) {
                caller.notify("Invalid mob. Name has to start with a capital like so: Pig");
                return;
            }
            if (p.getWorld().getType().getId() != 0) {
                caller.notify("That creature's soul cannot be cast here.");
                return;
            }

            Location loc;
            Block t = new HitBlox(p).getTargetBlock();
            if (t == null)
                loc = p.getLocation();
            else
                loc = new Location(t.getX() + .5D, t.getY() + 1.5D, t.getZ() + .5D);

            if (split.length == 2) {
                Mob mob = new Mob(split[1], loc);
                mob.spawn();
            } else if (split.length == 3)
                if (split[2].matches("\\d+")) {
                    int mobnumber = Integer.parseInt(split[2]);
                    for (int i = 0; i < mobnumber; i++) {
                        Mob mob = new Mob(split[1], loc);
                        mob.spawn();
                    }
                } else if (!Mob.isValid(split[2])) {
                    caller.notify("Invalid mob name or number of mobs.");
                    caller.notify("Mob names have to start with a capital like so: Pig");
                } else {
                    Mob mob = new Mob(split[1], loc);
                    mob.spawn(new Mob(split[2]));
                }
            else if (split.length == 4)
                try {
                    int mobnumber = Integer.parseInt(split[3]);
                    if (!Mob.isValid(split[2]))
                        caller.notify("Invalid rider. Name has to start with a capital like so: Pig");
                    else
                        for (int i = 0; i < mobnumber; i++) {
                            Mob mob = new Mob(split[1], loc);
                            mob.spawn(new Mob(split[2]));
                        }
                } catch (NumberFormatException nfe) {
                    caller.notify("Invalid number of mobs.");
                }
        }
    };
    @Command
    public static final BaseCommand clearinventory = new BaseCommand("- Clears your inventory") {

        @Override
        void execute(MessageReceiver caller, String[] split) {

            Player target;
            if (split.length >= 2)
                if (!(caller instanceof Player) || ((Player) caller).isAdmin())
                    target = etc.getServer().matchPlayer(split[1]);
                else
                    return;
            else
                target = (Player) caller;
            if (target != null) {
                Inventory inv = target.getInventory();
                inv.clearContents();
                inv.update();
                if (!target.getName().equals(caller.getName()))
                    caller.notify("Cleared " + target.getName() + "'s inventory.");
            }
        }
    };
    @Command
    public static final BaseCommand mspawn = new BaseCommand("[Mob] - Change the looked at mob spawner's mob", "You must specify what to change the mob spawner to.", 2, 2) {

        @Override
        void execute(MessageReceiver caller, String[] split) {
            if (!(caller instanceof Player))
                return;

            if (!Mob.isValid(split[1])) {
                caller.notify("Invalid mob specified.");
                return;
            }

            HitBlox hb = new HitBlox((Player) caller);
            Block block = hb.getTargetBlock();
            if (block != null && block.getType() == 52) { // mob spawner
                MobSpawner ms = (MobSpawner) ((Player) caller).getWorld().getComplexBlock(block.getX(), block.getY(), block.getZ());
                if (ms != null)
                    ms.setSpawn(split[1]);
            } else
                caller.notify("You are not targeting a mob spawner.");
        }
    };
    /*@Command
    public static final BaseCommand weather = new BaseCommand("[on|off]", null, 1) {

    @Override
    void execute(MessageReceiver caller, String[] parameters) {
    throw new UnsupportedOperationException("Not supported yet.");
    }
    }*/
}
