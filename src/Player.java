import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.server.MinecraftServer;


/**
 * Player.java - Interface so mods don't have to update often.
 * 
 * @author James
 */
public class Player extends HumanEntity implements MessageReceiver {

    private static final Logger log = Logger.getLogger("Minecraft");
    private int id = -1;
    private String prefix = "";
    private String[] commands = new String[] { "" };
    private ArrayList<String> groups = new ArrayList<String>();
    private String[] ips = new String[] { "" };
    private boolean ignoreRestrictions = false;
    private boolean admin = false;
    private boolean canModifyWorld = false;
    private boolean muted = false;
    private PlayerInventory inventory;
    private List<String> onlyOneUseKits = new ArrayList<String>();
    private Pattern badChatPattern = Pattern.compile("[^ !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ\\[\\\\\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00C7\u00FC\u00E9\u00E2\u00E4\u00E0\u00E5\u00E7\u00EA\u00EB\u00E8\u00EF\u00EE\u00EC\u00C4\u00C5\u00C9\u00E6\u00C6\u00F4\u00F6\u00F2\u00FB\u00F9\u00FF\u00D6\u00DC\u00F8\u00A3\u00D8\u00D7\u0192\u00E1\u00ED\u00F3\u00FA\u00F1\u00D1\u00AA\u00BA\u00BF\u00AE\u00AC\u00BD\u00BC\u00A1\u00AB\u00BB]");
    public ArrayList<Player> modes = new ArrayList<Player>();
    private String offlineName = ""; // Allows modify command to work on offline players
    
    /**
     * Creates an empty player. Add the player by calling {@link #setUser(OEntityPlayerMP)}
     */
    public Player() {}

    public Player(OEntityPlayerMP player) {
        setUser(player);
    }

    /**
     * Returns the entity we're wrapping.
     * 
     * @return
     */
    @Override
    public OEntityPlayerMP getEntity() {
        return (OEntityPlayerMP) entity;
    }

    /**
     * Returns if the player is still connected
     * 
     * @return
     */
    public boolean isConnected() {
        return !getEntity().a.c;
    }

    /**
     * Kicks player with the specified reason
     * 
     * @param reason
     */
    public void kick(String reason) {
        getEntity().a.a(reason);
    }

    /**
     * Sends player a notification
     * 
     * @param message
     */
    public void notify(String message) {
        if (message.length() > 0) {
            sendMessage(Colors.Rose + message);
        }
    }

    /**
     * Sends a message to the player
     * 
     * @param message
     */
    public void sendMessage(String message) {
        getEntity().a.msg(message);
    }

    /**
     * Gives an item to the player
     * 
     * @param item
     */
    public void giveItem(Item item) {
        inventory.addItem(item);
        inventory.update();
    }

    /**
     * Makes player send message.
     * 
     * @param message
     */
    public void chat(String message) {
        if (message.length() > 100) {
            kick("Chat message too long");
            return;
        }
        message = message.trim();
        Matcher m = badChatPattern.matcher(message);

        if (m.find()) {
            kick("Illegal characters '" + m.group() + "' hex: " + Integer.toHexString(message.charAt(m.start())) + " in chat");
            return;
        }
        if (message.startsWith("/")) {
            command(message);
        } else {
            if (isMuted()) {
                sendMessage(Colors.Rose + "You are currently muted.");
                return;
            }
            StringBuilder sbMessage = new StringBuilder(message);
            if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.CHAT, new Object[] { this, sbMessage })) {
                return;
            }

            String chat = "<" + getColor() + getName() + Colors.White + "> " + sbMessage.toString();

            log.log(Level.INFO, "<" + getName() + "> " + sbMessage.toString());
            etc.getServer().messageAll(chat);
        }
    }

    /**
     * Makes player use command.
     * 
     * TODO: redo this in the same way as the server commands.
     * 
     * @param command
     * 
     */
    public void command(String command) {
        try {
            if (etc.getInstance().isLogging()) {
                log.info("Command used by " + getName() + " " + command);
            }

            String[] split = command.split(" ");
            String cmd = split[0];

            if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.COMMAND, this, split)) {
                return;
            } // No need to go on, commands were parsed.
            if (!canUseCommand(cmd) && !cmd.startsWith("/#")) {
                if (etc.getInstance().showUnknownCommand()) {
                    sendMessage(Colors.Rose + "Unknown command.");
                }
                return;
            }
            if (command.startsWith("/#") && this.isOp()) {
                String str = command.substring(2);

                log.info(getName() + " issued server command: " + str);
                etc.getMCServer().a(str, getEntity().a);
                return;
            }

            // Remove '/' before checking.
            if (!ServerConsoleCommands.parseServerConsoleCommand(this, cmd.substring(1), split) && !PlayerCommands.parsePlayerCommand(this, cmd.substring(1), split)) {
                log.info(getName() + " tried command " + command);
                if (etc.getInstance().showUnknownCommand()) {
                    sendMessage(Colors.Rose + "Unknown command");
                }
            }

        } catch (Throwable ex) { // Might as well try and catch big exceptions
            // before the server crashes from a stack
            // overflow or something
            log.log(Level.SEVERE, "Exception in command handler (Report this on github unless you did something dumb like enter letters as numbers):", ex);
            if (isAdmin()) {
                sendMessage(Colors.Rose + "Exception occured. Check the server for more info.");
            }
        }
    }

    /**
     * Gives an item to the player
     * 
     * @param itemId
     * @param amount
     */
    public void giveItem(int itemId, int amount) {
        inventory.giveItem(itemId, amount);
        inventory.update();
    }

    /**
     * Gives the player this item by dropping it in front of them
     * 
     * @param item
     */
    public void giveItemDrop(Item item) {
        giveItemDrop(item.getItemId(), item.getAmount());
    }

    /**
     * Gives the player this item by dropping it in front of them
     * 
     * @param itemId
     * @param amount
     */
    public void giveItemDrop(int itemId, int amount) {
        OEntityPlayerMP player = getEntity();

        if (amount == -1) {
            player.b(new OItemStack(itemId, 255, 0));
        } else {
            int temp = amount;

            do {
                if (temp - 64 >= 64) {
                    player.b(new OItemStack(itemId, 64, 0));
                } else {
                    player.b(new OItemStack(itemId, temp, 0));
                }
                temp -= 64;
            } while (temp > 0);
        }
    }

    /**
     * Returns true if this player can use the specified command
     * 
     * @param command
     * @return
     */
    public boolean canUseCommand(String command) {
        PluginLoader.HookResult res = (PluginLoader.HookResult) etc.getLoader().callHook(PluginLoader.Hook.COMMAND_CHECK, this, command);

        if (res == PluginLoader.HookResult.DEFAULT_ACTION) {
            return canUseCommandByDefault(command);
        }// If someone wants to use
        // false instead, this can be
        // done with low priority
        // plugin.
        if (res == PluginLoader.HookResult.ALLOW_ACTION) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this player can use the specified command This method ignores permission management plugins and shouldn't be used by other plugins.
     * 
     * @param command
     * @return
     */

    public boolean canUseCommandByDefault(String command) {
        for (String str : commands) {
            if (str.equalsIgnoreCase(command)) {
                return true;
            }
        }

        for (String str : groups) {
            Group g = etc.getDataSource().getGroup(str);

            if (g != null) {
                if (recursiveUseCommand(g, command)) {
                    return true;
                }
            }
        }

        if (hasNoGroups()) {
            Group def = etc.getInstance().getDefaultGroup();

            if (def != null) {
                if (recursiveUseCommand(def, command)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean recursiveUseCommand(Group g, String command) {
        for (String str : g.Commands) {
            if (str.equalsIgnoreCase(command) || str.equals("*")) {
                return true;
            }
        }

        if (g.InheritedGroups != null) {
            for (String str : g.InheritedGroups) {
                Group g2 = etc.getDataSource().getGroup(str);

                if (g2 != null) {
                    if (recursiveUseCommand(g2, command)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if this player is in the specified group
     * 
     * @param group
     * @return
     */
    public boolean isInGroup(String group) {
        if (group != null) {
            if (etc.getInstance().getDefaultGroup() != null) {
                if (group.equalsIgnoreCase(etc.getInstance().getDefaultGroup().Name)) {
                    return true;
                }
            }
        }
        for (String str : groups) {
            if (recursiveUserInGroup(etc.getDataSource().getGroup(str), group)) {
                return true;
            }
        }
        return false;
    }

    private boolean recursiveUserInGroup(Group g, String group) {
        if (g == null || group == null) {
            return false;
        }

        if (g.Name.equalsIgnoreCase(group)) {
            return true;
        }

        if (g.InheritedGroups != null) {
            for (String str : g.InheritedGroups) {
                if (g.Name.equalsIgnoreCase(str)) {
                    return true;
                }

                Group g2 = etc.getDataSource().getGroup(str);

                if (g2 != null) {
                    if (recursiveUserInGroup(g2, group)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if this player has control over the other player
     * 
     * @param player
     * @return true if player has control
     */
    public boolean hasControlOver(Player player) {
        boolean isInGroup = false;

        if (player.hasNoGroups()) {
            return true;
        }
        for (String str : player.getGroups()) {
            if (isInGroup(str)) {
                isInGroup = true;
            } else {
                continue;
            }
            break;
        }

        return isInGroup;
    }

    /**
     * Returns the player's current location
     * 
     * @return
     */
    public Location getLocation() {
        Location loc = new Location();

        loc.x = getX();
        loc.y = getY();
        loc.z = getZ();
        loc.rotX = getRotation();
        loc.rotY = getPitch();
        loc.dimension = getWorld().getType().getId();
        return loc;
    }

    /**
     * Returns the IP of this player
     * 
     * @return
     */
    public String getIP() {
        return getEntity().a.b.c().toString().split(":")[0].substring(1);
    }

    /**
     * Returns true if this player is an admin.
     * 
     * @return
     */
    public boolean isAdmin() {
        if (admin) {
            return true;
        }

        for (String str : groups) {
            Group group = etc.getDataSource().getGroup(str);

            if (group != null) {
                if (group.Administrator) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Don't use this! Use isAdmin
     * 
     * @return
     */
    public boolean getAdmin() {
        return admin;
    }

    /**
     * Sets whether or not this player is an administrator
     * 
     * @param admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Returns false if this player can not modify terrain, edit chests, etc.
     * 
     * @return
     */
    public boolean canBuild() {
        if (canModifyWorld) {
            return true;
        }

        for (String str : groups) {
            Group group = etc.getDataSource().getGroup(str);

            if (group != null) {
                if (group.CanModifyWorld) {
                    return true;
                }
            }
        }

        if (hasNoGroups()) {
            if (etc.getInstance().getDefaultGroup().CanModifyWorld) {
                return true;
            }
        }

        return false;
    }

    /**
     * Don't use this, use canBuild()
     * 
     * @return
     */
    public boolean canModifyWorld() {
        return canModifyWorld;
    }

    /**
     * Sets whether or not this player can modify the dimension terrain
     * 
     * @param canModifyWorld
     */
    public void setCanModifyWorld(boolean canModifyWorld) {
        this.canModifyWorld = canModifyWorld;
    }

    /**
     * Set allowed commands
     * 
     * @return
     */
    public String[] getCommands() {
        return commands;
    }

    /**
     * Sets this player's allowed commands
     * 
     * @param commands
     */
    public void setCommands(String[] commands) {
        this.commands = commands;
    }

    /**
     * Returns this player's groups
     * 
     * @return
     */
    public String[] getGroups() {
        String[] strGroups = new String[groups.size()];

        groups.toArray(strGroups);
        return strGroups;
    }

    /**
     * Sets this player's groups
     * 
     * @param groups
     */
    public void setGroups(String[] groups) {
        this.groups.clear();
        for (String s : groups) {
            if (s.length() > 0) {
                this.groups.add(s);
            }
        }
    }

    /**
     * Adds the player to the specified group
     * 
     * @param group
     *            group to add player to
     */
    public void addGroup(String group) {
        groups.add(group);
    }

    /**
     * Removes specified group from list of groups
     * 
     * @param group
     *            group to remove
     */
    public void removeGroup(String group) {
        groups.remove(group);
    }

    /**
     * Returns the sql ID.
     * 
     * @return
     */
    public int getSqlId() {
        return id;
    }

    /**
     * Sets the sql ID. Don't touch this.
     * 
     * @param id
     */
    public void setSqlId(int id) {
        this.id = id;
    }

    /**
     * If the user can ignore restrictions this will return true. Things like item amounts and such are unlimited, etc.
     * 
     * @return
     */
    public boolean canIgnoreRestrictions() {
        if (admin || ignoreRestrictions) {
            return true;
        }

        for (String str : groups) {
            Group group = etc.getDataSource().getGroup(str);

            if (group != null) {
                if (group.Administrator || group.IgnoreRestrictions) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Don't use. Use canIgnoreRestrictions()
     * 
     * @return
     */
    public boolean ignoreRestrictions() {
        return ignoreRestrictions;
    }

    /**
     * Sets ignore restrictions
     * 
     * @param ignoreRestrictions
     */
    public void setIgnoreRestrictions(boolean ignoreRestrictions) {
        this.ignoreRestrictions = ignoreRestrictions;
    }

    /**
     * Returns allowed IPs
     * 
     * @return
     */
    public String[] getIps() {
        return ips;
    }

    /**
     * Sets allowed IPs
     * 
     * @param ips
     */
    public void setIps(String[] ips) {
        this.ips = ips;
    }

    /**
     * Returns the correct color/prefix for this player
     * 
     * @return
     */
    public String getColor() {
        if (prefix != null) {
            if (!prefix.equals("")) {
                return Colors.Marker + prefix;
            }
        }
        if (groups.size() > 0) {
            Group group = etc.getDataSource().getGroup(groups.get(0));

            if (group != null) {
                return Colors.Marker + group.Prefix;
            }
        }
        Group def = etc.getInstance().getDefaultGroup();

        return def != null ? Colors.Marker + def.Prefix : "";
    }

    /**
     * Returns the prefix. NOTE: Don't use this, use getColor() instead.
     * 
     * @return
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the prefix.
     * 
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
	
    /**
     * Get players name.
     * @return The offline name
     */
    public String getOfflineName() {
        if (getEntity() != null) {
            return getName();
        } else { 
            return offlineName; 
        }
    }

    /**
     * Sets offline player name.
     * @param name The offline name
     */
    public void setOfflineName(String name) {
        offlineName = name;
    }

    /**
     * Gets the full name prefix+name.
     * @return The name including prefix
     */
    public String getFullName() {
        return this.getColor() + this.getName();
    }

    /**
     * Gets the name that shows on PlayerList (tab).
     * @param show Whether to show the player
     * @return PlayerListEntry The entry to display.
     */
    public PlayerlistEntry getPlayerlistEntry(boolean show) {
        String name;

        if (etc.getInstance().isPlayerList_colors()) { 
            name = getFullName() + Colors.LightGreen;
        } else {
            name = getName();
        }
        PlayerlistEntry entry = new PlayerlistEntry(name, this.getPing(), show);

        return (PlayerlistEntry) etc.getLoader().callHook(PluginLoader.Hook.GET_PLAYERLISTENTRY, this, entry); 
    }
    
    /**
     * Gets player ping (as shown in playerlist).
     * @return The ping
     */
    public int getPing() {
        return this.getEntity().i;
    }
    
    /**
     * Gets the actual user class.
     * 
     * @return
     */
    public OEntityPlayerMP getUser() {
        return getEntity();
    }

    /**
     * Sets the user. Don't use this.
     * 
     * @param player
     */
    protected void setUser(OEntityPlayerMP player) {
        entity = player;
        inventory = new PlayerInventory(this);
    }

    @Override
    public void teleportTo(double x, double y, double z, float rotation, float pitch) {
        OEntityPlayerMP player = getEntity();

        // If player is in vehicle - eject them before they are teleported.
        if (player.bh != null) {
            player.b(player.bh);
        }
        player.a.a(x, y, z, rotation, pitch);
    }

    /**
     * Returns true if the player is muted.
     * 
     * @return Whether the player is muted.
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Toggles mute.
     * 
     * @return Whether the player is now muted.
     */
    public boolean toggleMute() {
        muted = !muted;
        return muted;
    }

    /**
     * Checks to see if this player is in any groups.
     * 
     * @return true if this player isn't in any group.
     */
    public boolean hasNoGroups() {
        if (groups.isEmpty()) {
            return true;
        }
        if (groups.size() == 1) {
            return groups.get(0).equals("");
        }
        return false;
    }

    /**
     * Returns item id in player's hand
     * 
     * @return item id
     */
    public int getItemInHand() {
        return getEntity().a.getItemInHand();
    }

    /**
     * Returns the item stack in the player's hand.
     * 
     * @return Item
     */
    public Item getItemStackInHand() {
        OItemStack result = getEntity().k.d();

        if (result != null) {
            return new Item(result, getEntity().k.c);
        }
        return null;
    }

    /**
     * Returns this player's inventory.
     * 
     * @return inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Returns whether or not this Player is currently sneaking (crouching).
     * 
     * @return true if sneaking
     */
    public boolean getSneaking() {
        return getEntity().aO();
    }

    /**
     * Force this Player to be sneaking or not
     * 
     * @param sneaking
     *            true if sneaking
     */
    public void setSneaking(boolean sneaking) {
        getEntity().f(sneaking);
        OPacket19EntityAction sneakUpdate = new OPacket19EntityAction();

        sneakUpdate.a = getId();
        sneakUpdate.b = sneaking ? 1 : 2;
        etc.getMCServer().h.a(sneakUpdate, getWorld().getType().getId());
    }

    /**
     * Returns the one-use only kits
     * 
     * @return List of kit names
     */
    public List<String> getOnlyOneUseKits() {
        return new ArrayList(onlyOneUseKits);
    }

    /**
     * Switch to the other dimension at the according position.
     * @deprecated use {@link #switchWorlds(int) } instead.
     */
    @Deprecated
    public void switchWorlds() {
        MinecraftServer mcServer = etc.getMCServer();
        OEntityPlayerMP ent = getEntity();

        // Nether is not allowed, so shush
        if (!mcServer.d.a("allow-nether", true)) {
            return;
        }
        // Dismount first or get buggy
        if (ent.bh != null) {
            ent.c(ent.bh);
        }
        
        if (getWorld().getType().getId() == 0) {
            switchWorlds(-1);
        } else {
            switchWorlds(0);
        }
        // Canary: We don't want a portal created
        // mcServer.h.a(ent, false);
        
        // CanaryMod: Refresh the creative mode
        refreshCreativeMode();
    }
    
    /**
     * Switch to the specified dimension at the according position.
     * @param world The id of the world to swith to.
     */
    public void switchWorlds(int world) {
        MinecraftServer mcServer = etc.getMCServer();
        OEntityPlayerMP ent = getEntity();
        
        // Nether is not allowed, so shush
        if (world == World.Type.NETHER.getId() && !mcServer.d.a("allow-nether", true)) {
            return;
        }
        // The End is not allowed, so shush
        if (world == World.Type.END.getId() && !mcServer.d.a("allow-end", true)) {
            return;
        }
        // Dismount first or get buggy
        if (ent.bh != null) {
            ent.b(ent.bh);
        }

        ent.a((OStatBase) OAchievementList.B);
        OChunkCoordinates var2 = mcServer.a(world).d();

        if (var2 != null) {
            ent.a.a((double) var2.a, (double) var2.b, (double) var2.c, 0.0F, 0.0F);
        }

        mcServer.h.sendPlayerToOtherDimension(ent, world, false);
        
        refreshCreativeMode();
    }
    
    @Override
    public void teleportTo(BaseEntity ent) {
        if (!getWorld().equals(ent.getWorld())) {
            switchWorlds(ent.getWorld().getType().getId());
        }
        super.teleportTo(ent);
    }

    @Override
    public void teleportTo(Location location) {
        if (!getWorld().equals(location.getWorld())) {
            switchWorlds(location.dimension);
        }
        super.teleportTo(location);
    }

    @Override
    public String toString() {
        return String.format("Player[id=%d, name=%s]", id, getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        final Player other = (Player) obj;

        return getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 89 * hash + this.id;
        hash = 89 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        return hash;
    }

    /**
     * Set creative mode for this Player
     * 
     * @param i
     */
    public void setCreativeMode(int i) {
        getEntity().c.a(i);
        getEntity().a.b((OPacket) (new OPacket70Bed(3, i)));
        if (i == 1 && !getPlayer().getMode()) {
            modes.add(this);
        } else {
            modes.remove(this);
        }
    }

    /**
     * Get creative mode for this Player
     * 
     * @return i
     */
    public int getCreativeMode() {
        int i = 0;

        if (getEntity().c.a() != 0) {
            i = getEntity().c.a();
        }
        return i;
    }

    /**
     * Check to see if this Player is in creative mode
     * 
     * @return <tt>true</tt> if the given Player is in creative mode,
     *          <tt>null</tt> otherwise.
     */
    public boolean getMode() {
        if (modes.contains(this) && getEntity().c.b()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Refresh this Player's mode
     */
    public void refreshCreativeMode() {
        if (getMode() || etc.getMCServer().d.a("gamemode", 0) == 1) {
            getEntity().c.a(1);
        } else {
            getEntity().c.a(0);
        }
    }

    /**
     * Get total experience amount for this Player.
     * 
     * @return
     */
    public int getXP() {
        return getEntity().N;
    }

    /**
     * Get experience level for this Player.
     * 
     * @return
     */
    public int getLevel() {
        return getEntity().M;
    }

    /**
     * Add experience points to total for this Player.
     * 
     * @param i the amount of experience points to add.
     */
    public void addXP(int i) {
        getEntity().addXP(i);
        updateXP();
    }

    /**
     * Remove experience points from total for this Player.
     * 
     * @param i the amount of experience points to remove.
     */
    public void removeXP(int i) {
        if (getXP() > 0) {
            getEntity().removeXP(i);
            updateXP();
        } else {
            notify("Cannot decrease XP below 0");
        }
    }

    /**
     * Set total experience points for this Player.
     * 
     * @param i the new amount of experience points.
     */
    public void setXP(int i) {
        if (i >= 0) {
            getEntity().setXP(i);
            updateXP();
        } else {
            notify("XP cannot be set to less than 0");
        }
    }

    /**
     * Send player the updated experience packet.
     *
     */
    public void updateXP() {
        getEntity().a.b((OPacket) (new OPacket43Experience(getEntity().O, getEntity().N, getEntity().M)));
    }

    /**
     * Send update food and health to client
     * 
     */
    public void updateLevels() {
        OEntityPlayerMP entityMP = getEntity();

        entityMP.a.b((OPacket) (new OPacket8UpdateHealth(getHealth(), getFoodLevel(), getFoodSaturationLevel())));
    }
    
    /**
     * Get player Food Level
     * 
     * @return player food level
     */
    public int getFoodLevel() {
        return getEntity().n.a;
    }
    
    /**
     * Set Player food level
     * 
     * @param foodLevel
     *         new food level, between 1 and 20
     */
    public void setFoodLevel(int foodLevel) {
        getEntity().n.a = Math.min(foodLevel, 20);
        updateLevels();
    }
    
    /**
     * Get Players food ExhaustionLevel
     * @return
     */
    public float getFoodExhaustionLevel() {
        return getEntity().n.c;
    }

    /**
     * Set player food exhaustion level
     * 
     * @param foodExhaustionLevel
     */
    public void setFoodExhaustionLevel(float foodExhaustionLevel) {
        getEntity().n.c = Math.min(foodExhaustionLevel, 40F);
        updateLevels();
    }
    
    /**
     * Updates the inventory on the client
     * 
     */
    public void updateInventory() {
        OContainer l = getEntity().l;
        ArrayList var3 = new ArrayList();

        for (int var4 = 0; var4 < l.e.size(); ++var4) {
            var3.add(((OSlot) l.e.get(var4)).b());
        }
        
        getEntity().a(l, var3);
    }

    /**
     * Get Players food saturationLevel
     * @return
     */
    public float getFoodSaturationLevel() {
        return getEntity().n.b;
    }
    
    /**
     * Set player food saturation level
     * 
     * @param foodSaturationLevel
     */
    public void setFoodSaturationLevel(float foodSaturationLevel) {
        getEntity().n.b = Math.min(foodSaturationLevel, getFoodLevel());
        updateLevels();
    }
    
    /**
     * Adds a potion Effect to the player
     * 
     * @param effect the effect to add.
     */
    public void addPotionEffect(PotionEffect effect) {
        getEntity().e(effect.potionEffect);
    }
    
    /**
     * Removes a potion Effect from player
     * 
     * @param effect The potion effect to remove
     */
    public void removePotionEffect(PotionEffect effect) {     
        OPotionEffect var3 = (OPotionEffect) getEntity().aL.get(effect.getType().getId());

        getEntity().aL.remove(Integer.valueOf(effect.getType().getId()));
        getEntity().d(var3);
    }

    /**
     * Returns a Collection of potion effects active on the player
     * 
     * @return List of potion effects 
     */
    public List<PotionEffect> getPotionEffects() {
        Collection ay = getEntity().aD();
        ArrayList<PotionEffect> list = new ArrayList<PotionEffect>();

        for (Iterator<OPotionEffect> iterator = ay.iterator(); iterator.hasNext();) {
            list.add(((OPotionEffect) iterator.next()).potionEffect);
        }
        return list;
    }
    
    /**
     * Returns whether this player can receive damage.
     * @return the disableDamage state
     */
    public boolean isDamageDisabled() {
        return getEntity().L.a;
    }

    /**
     * Sets whether this player can receive damage.
     * @param disabled the new value.
     */
    public void setDamageDisabled(boolean disabled) {
        getEntity().L.a = disabled;
    }

    /**
     * Returns whether the player is flying.
     * @return the flying state
     */
    public boolean isFlying() {
        return getEntity().L.b;
    }
    
    /**
     * Sets whether the player is flying.
     * @param flying the flying state.
     */
    public void setFlying(boolean flying) {
        getEntity().L.b = flying;
    }

    /**
     * Returns whether falling is disabled.
     * @return the disableFalling state
     */
    public boolean isFallingDisabled() {
        return getEntity().L.c;
    }

    /**
     * Sets whether falling is disabled.
     * @param disabled the new value
     */
    public void setFallingDisabled(boolean disabled) {
        getEntity().L.c = disabled;
    }

    /**
     * Returns whether buckets are always full.
     * When set, every bucket that the player holds stays full after emptying.
     * @return whether buckets are always full.
     */
    public boolean isBucketAlwaysFull() {
        return getEntity().L.d;
    }

    /**
     * Sets whether buckets are always full.
     * When set, every bucket that the player holds stays full after emptying.
     * @param alwaysFull the new state
     */
    public void setBucketAlwaysFull(boolean alwaysFull) {
        getEntity().L.d = alwaysFull;
    }
    
    /**
     * Returns whether this player is an op.
     * @return {@code true} if the player is op.
     */
    public boolean isOp() {
        return etc.getMCServer().h.h(getName());
    }
    
    /**
     * Static method to determine whether a player is op.
     * @param playerName The name of the player to check.
     * @return {@code true} if the player is op.
     */
    public static boolean isOp(String playerName) {
        return etc.getMCServer().h.h(playerName);
    }
}
