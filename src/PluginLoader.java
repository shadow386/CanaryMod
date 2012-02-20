
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.MinecraftServer;


/**
 * PluginLoader.java - Used to load plugins, toggle them, etc.
 * 
 * @author James
 */
public class PluginLoader {

    /**
     * Hook - Used for adding a listener to listen on specific hooks
     */
    public enum Hook {

        /**
         * Calls {@link PluginListener#onLoginChecks(java.lang.String) }
         *///
        LOGINCHECK, //
        /**
         * Calls {@link PluginListener#onLogin(Player) }
         *///
        LOGIN, //
        /**
         * Calls {@link PluginListener#onChat(HookParametersChat parametersChat) }
         *///
        CHAT, //
        /**
         * Calls {@link PluginListener#onCommand(Player, java.lang.String[]) }
         *///
        COMMAND, //
        /**
         * Calls {@link PluginListener#onConsoleCommand(java.lang.String[]) }
         *///
        SERVERCOMMAND, //
        /**
         * Calls {@link PluginListener#onBan(Player, Player, java.lang.String) }
         *///
        BAN, //
        /**
         * Calls {@link PluginListener#onIpBan(Player, Player, java.lang.String) }
         *///
        IPBAN, //
        /**
         * Calls {@link PluginListener#onKick(Player, Player, java.lang.String) }
         *///
        KICK, //
        /**
         * Calls {@link PluginListener#onBlockCreate(Player, Block, Block, int) }
         *///
        BLOCK_CREATED, //
        /**
         * Calls {@link PluginListener#onBlockDestroy(Player, Block) }
         *///
        BLOCK_DESTROYED, //
        /**
         * Calls {@link PluginListener#onDisconnect(Player) }
         *///
        DISCONNECT, //
        /**
         * Calls {@link PluginListener#onPlayerMove(Player, Location, Location) }
         *///
        PLAYER_MOVE, //
        /**
         * Calls {@link PluginListener#onArmSwing(Player) }
         *///
        ARM_SWING, //
        /**
         * Calls {@link PluginListener#onItemDrop(Player, ItemEntity) }
         *///
        ITEM_DROP, //
        /**
         * Calls {@link PluginListener#onItemPickUp(Player, ItemEntity) }
         *///
        ITEM_PICK_UP, //
        /**
         * Calls {@link PluginListener#onTeleport(Player, Location, Location) }
         *///
        TELEPORT, //
        /**
         * Calls {@link PluginListener#onBlockBreak(Player, Block) }
         *///
        BLOCK_BROKEN, //
        /**
         * Calls {@link PluginListener#onIgnite(Block, Player) }
         *///
        IGNITE, //
        /**
         * Calls {@link PluginListener#onFlow(Block, Block) }
         *///
        FLOW, //
        /**
         * Calls {@link PluginListener#onExplode(Block, OEntity, HashSet) }
         *///
        EXPLODE, //
        /**
         * Calls {@link PluginListener#onMobSpawn(Mob) }
         *///
        MOB_SPAWN, //
        /**
         * Calls {@link PluginListener#onDamage(PluginLoader.DamageType, BaseEntity, BaseEntity, int) }
         *///
        DAMAGE, //
        /**
         * Calls {@link PluginListener#onHealthChange(Player, int, int) }
         *///
        HEALTH_CHANGE, //
        /**
         * Calls {@link PluginListener#onRedstoneChange(Block, int, int) }
         *///
        REDSTONE_CHANGE, //
        /**
         * Calls {@link PluginListener#onPistonExtend(Block, Boolean) }
         *///
        PISTON_EXTEND, //
        /**
         * Calls {@link PluginListener#onPistonRetract(Block, Boolean) }
         *///
        PISTON_RETRACT, //
        /**
         * Calls {@link PluginListener#onBlockPhysics(Block, boolean) }
         *///
        BLOCK_PHYSICS, //
        /**
         * Calls {@link PluginListener#onVehicleCreate(BaseVehicle) }
         *///
        VEHICLE_CREATE, //
        /**
         * Calls {@link PluginListener#onVehicleUpdate(BaseVehicle) }
         *///
        VEHICLE_UPDATE, //
        /**
         * Calls {@link PluginListener#onVehicleDamage(BaseVehicle, BaseEntity, int) }
         *///
        VEHICLE_DAMAGE, //
        /**
         * Calls {@link PluginListener#onVehicleCollision(BaseVehicle, BaseEntity) }
         *///
        VEHICLE_COLLISION, //
        /**
         * Calls {@link PluginListener#onVehicleDestroyed(BaseVehicle) }
         *///
        VEHICLE_DESTROYED, //
        /**
         * Calls {@link PluginListener#onVehicleEnter(BaseVehicle, HumanEntity) }
         *///
        VEHICLE_ENTERED, //
        /**
         * Calls {@link PluginListener#onVehiclePositionChange(BaseVehicle, int, int, int) }
         *///
        VEHICLE_POSITIONCHANGE, //
        /**
         * Calls {@link PluginListener#onItemUse(Player, Block, Block, Item) }
         *///
        ITEM_USE, //
        /**
         * Calls {@link PluginListener#onBlockPlace(Player, Block, Block, Item) }
         *///
        BLOCK_PLACE, //
        /**
         * Calls {@link PluginListener#onBlockRightClicked(Player, Block, Item) }
         *///
        BLOCK_RIGHTCLICKED, //
        /**
         * Calls {@link PluginListener#onLiquidDestroy(PluginLoader.HookResult, int, Block) }
         *///
        LIQUID_DESTROY, //
        /**
         * Calls {@link PluginListener#onAttack(LivingEntity, LivingEntity, java.lang.Integer) }
         *///
        ATTACK, //
        /**
         * Calls {@link PluginListener#onOpenInventory(Player, Inventory) }
         *///
        OPEN_INVENTORY, //
        /**
         * Calls {@link PluginListener#onOpenInventory(Player, Inventory) }
         *///
        CLOSE_INVENTORY, //
        /**
         * Calls {@link PluginListener#onSignShow(Player, Sign) }
         *///
        SIGN_SHOW, //
        /**
         * Calls {@link PluginListener#onSignChange(Player, Sign) }
         *///
        SIGN_CHANGE, //
        /**
         * Calls {@link PluginListener#onLeafDecay(Block) }
         *///
        LEAF_DECAY, //
        /**
         * Calls {@link PluginListener#onTame(Player, Mob) }
         *///
        TAME, //
        /**
         * Calls {@link PluginListener#onLightningStrike(BaseEntity) }
         *///
        LIGHTNING_STRIKE, //
        /**
         * Calls {@link PluginListener#onWeatherChange(boolean) }
         *///
        WEATHER_CHANGE, //
        /**
         * Calls {@link PluginListener#onThunderChange(boolean) }
         *///
        THUNDER_CHANGE, //
        /**
         * Calls {@link PluginListener#onPortalUse(Player, World) }
         *///
        PORTAL_USE, //
        /**
         * Calls {@link PluginListener#onChunkCreate(int, int, World) }
         *///
        CHUNK_CREATE, //
        /**
         * Calls {@link PluginListener#onSpawnpointCreate(World) }
         *///
        SPAWNPOINT_CREATE, //
        /**
         * Calls {@link PluginListener#onChunkCreated(Chunk chunk) }
         *///
        CHUNK_CREATED, //
        /**
         * Calls {@link PluginListener#onChunkLoaded(Chunk chunk) }
         *///
        CHUNK_LOADED, //
        /**
         * Calls {@link PluginListener#onChunkUnload(Chunk chunk) }
         *///
        CHUNK_UNLOAD, //
        /**
         * Calls {@link PluginListener#onTimeChange(World, long) }
         *///
        TIME_CHANGE, //
        /**
         * Calls {@link PluginListener#canPlayerUseCommand(Player, String) }
         *///
        COMMAND_CHECK, //
        /**
         * Class {@link PluginListener#onPortalCreate(Block[][]) }
         *///
        PORTAL_CREATE, //
        /**
         * Class {@link PluginListener#onPortalDestroy(Block[][]) }
         *///
        PORTAL_DESTROY, //
        /**
         * Class {@link PluginListener#onPlayerRespawn(Player) }
         *///
        PLAYER_RESPAWN, //
        /**
         * Class {@link PluginListener#onEntityDespawn(BaseEntity) }
         *///
        ENTITY_DESPAWN, //
        /**
         * Class {@link PluginListener#onEndermanPickup(Enderman, Block) }
         *///
        ENDERMAN_PICKUP, //
        /**
         * Class {@link PluginListener#onEndermanDrop(Enderman, Block) }
         *///
        ENDERMAN_DROP, //
        /**
         * Class {@link PluginListener#onCowMilk(Player, Mob) }
         *///
        COW_MILK, //
        /**
         * Calls {@link PluginListener#onEat(Player, Item) }
         *///
        EAT, //
        /**
         * Calls {@link PluginListener#onFoodLevelChange(Player, oldLevel, newLevel) }
         *///
        FOODLEVEL_CHANGE, //
        /**
         * Calls (@link PluginListener#onFoodExahustionChange(Player, oldLevel, newLevel) }
         *///
        FOODEXHAUSTION_CHANGE, //
        /**
         * Calls (@link PluginListener#onFoodSaturationChange(Player, oldLevel, newLevel) }
         *///
        FOODSATURATION_CHANGE, //
        /**
         * Calls (@link PluginListener#onPotionEffect(Player,PotionEffect)
         *///
        POTION_EFFECT, //
        /**
         * Class {@link PluginListener#onExpChange(Player, oldExp, newExp) }
         *///
        EXPERIENCE_CHANGE, //
        /**
         * Class {@link PluginListener#onLevelUp(Player) }
         *///
        LEVEL_UP, //
        /**
         * Calls {@link PluginListener#onPlayerListNameGet(Player, defaultName) }
         *///
        GET_PLAYERLISTENTRY, //
        /**
         * Calls {@link PluginListener#onPlayerConnect(Player,HookParametersConnect) }
         *///
        PLAYER_CONNECT, //
        /**
         * Calls {@link PluginListener#onPlayerDisconnect(Player,HookParametersDisconnect) }
         *///
        PLAYER_DISCONNECT, //
        /**
         * Calls {@link PluginListener#onEntityRightClick(Player,BaseEntity) }
         *///
        ENTITY_RIGHTCLICKED, //
        /**
         * Calls {@Link PluginListener#onMobTarget(Player,LivingEntity) }
         *///
        MOB_TARGET, //
        /**
         * Calls {@Link PluginListener#onBlockUpdate(Block) }
         *///
        BLOCK_UPDATE, //
        /**
         * Calls {@Link PluginListener#onEnchant(HookParametersEnchant) }
         */
        ENCHANT, //
        /**
         * Calls{@Link PluginListener#onDispense(Block,Item) }
         */
        DISPENSE, //
        /**
         * For internal use only.
         *///
        NUM_HOOKS;
    }


    /**
     * HookResult - Used where returning a boolean isn't enough.
     */
    public enum HookResult {

        /**
         * Prevent the action
         *///
        PREVENT_ACTION, //
        /**
         * Allow the action
         *///
        ALLOW_ACTION, //
        /**
         * Do whatever it would normally do, continue processing
         *///
        DEFAULT_ACTION
    }


    public enum DamageType {

        /**
         * Creeper explosion
         *///
        CREEPER_EXPLOSION, //
        /**
         * Damage dealt by another entity
         *///
        ENTITY, //
        /**
         * Damage caused by explosion
         *///
        EXPLOSION, //
        /**
         * Damage caused from falling (fall distance - 3.0)
         *///
        FALL, //
        /**
         * Damage caused by fire (1)
         *///
         FIRE, //
        /**
         * Low periodic damage caused by burning (1)
         *///
         FIRE_TICK, //
        /**
         * Damage caused from lava (4)
         *///
         LAVA, //
        /**
         * Damage caused from drowning (2)
         *///
         WATER, //
        /**
         * Damage caused by cactus (1)
         *///
         CACTUS, //
        /**
         * Damage caused by suffocating(1)
         *///
         SUFFOCATION, //
        /**
         * Damage caused by lightning (5)
         *///
         LIGHTNING, //
        /**
         * Damage caused by starvation (1)
         *///
         STARVATION, //
        /**
         * Damage caused by poison (1) (Potions, Poison)
         *///
         POTION;
         
         public static DamageType fromDamageSource(ODamageSource source) {
             if (source == ODamageSource.b)
                 return FIRE; // Can also be lightning
             else if (source == ODamageSource.c)
                 return FIRE_TICK;
             else if (source == ODamageSource.d)
                 return LAVA;
             else if (source == ODamageSource.e)
                 return SUFFOCATION;
             else if (source == ODamageSource.f)
                 return WATER;
             else if (source == ODamageSource.g)
                 return STARVATION;
             else if (source == ODamageSource.h)
                 return CACTUS;
             else if (source == ODamageSource.i)
                 return FALL;
             else if (source == ODamageSource.j)
                 return FALL; // Out of world
             else if (source == ODamageSource.k)
                 return null; // Vanilla's /kill, we don't have this.
             else if (source == ODamageSource.l)
                 return EXPLOSION; // Can also be a creeper.
             else if (source == ODamageSource.m)
                 return POTION;
             else if (source instanceof OEntityDamageSource)
                 return ENTITY;
             else if (source instanceof OEntityDamageSourceIndirect)
                 return ENTITY; // Still an entity, albeit indirect.
             else
                 return null; // Not a valid ODamageSource
         }
    }

    private static final Logger log = Logger.getLogger("Minecraft");
    private static final Object lock = new Object();
    private List<Plugin> plugins = new ArrayList<Plugin>();
    private List<List<PluginRegisteredListener>> listeners = new ArrayList<List<PluginRegisteredListener>>();
    private HashMap<String, PluginInterface> customListeners = new HashMap<String, PluginInterface>();
    private Server server;
    private PropertiesFile properties;
    private volatile boolean loaded = false;
    private volatile boolean loadedpreload = false;

    /**
     * Creates a plugin loader
     * 
     * @param server
     *            server to use
     */
    public PluginLoader(MinecraftServer server) {
        properties = new PropertiesFile("server.properties");
        this.server = new Server(server);

        for (int h = 0; h < Hook.NUM_HOOKS.ordinal(); ++h) {
            listeners.add(new ArrayList<PluginRegisteredListener>());
        }
    }

    /**
     * Loads all plugins.
     */
    public void loadPlugins() {
        if (loaded) {
            return;
        }
        log.info("CanaryMod: Loading plugins...");
        String[] classes = properties.getString("plugins", "").split(",");

        for (String sclass : classes) {
            if (sclass.equals("")) {
                continue;
            }
            loadPlugin(sclass.trim());
        }
        log.info("CanaryMod: Loaded " + plugins.size() + " plugins.");
        loaded = true;
    }

    /**
     * Loads the plugins that shall be loaded before generating the spawn area.
     */
    public void loadPreloadPlugins() {
        if (loadedpreload) {
            return;
        }
        log.info("CanaryMod: Loading preload plugins...");
        String[] classes = properties.getString("preloadplugins", "").split(",");

        for (String sclass : classes) {
            if (sclass.equals("")) {
                continue;
            }
            loadPlugin(sclass.trim());
        }
        log.info("CanaryMod: Loaded " + plugins.size() + " plugins.");
        loadedpreload = true;
    }

    /**
     * Loads the specified plugin
     * 
     * @param fileName
     *            file name of plugin to load
     * @return if the operation was successful
     */
    public Boolean loadPlugin(String fileName) {
        if (getPlugin(fileName) != null) {
            return false; // Already exists.
        }
        return load(fileName);
    }

    /**
     * Reloads the specified plugin
     * 
     * @param fileName
     *            file name of plugin to reload
     * @return if the operation was successful
     */
    public Boolean reloadPlugin(String fileName) {

        /* Not sure exactly how much of this is necessary */
        Plugin toNull = getPlugin(fileName);

        if (toNull != null) {
            if (toNull.isEnabled()) {
                toNull.disable();
            }
        }
        synchronized (lock) {
            plugins.remove(toNull);
            for (List<PluginRegisteredListener> regListeners : listeners) {
                Iterator<PluginRegisteredListener> iter = regListeners.iterator();

                while (iter.hasNext()) {
                    if (iter.next().getPlugin() == toNull) {
                        iter.remove();
                    }
                }
            }
        }
        toNull = null;

        return load(fileName);
    }

    private Boolean load(String fileName) {
        try {
            File file = new File("plugins/" + fileName + ".jar");

            if (!file.exists()) {
                log.log(Level.SEVERE, "Failed to find plugin file: plugins/" + fileName + ".jar. Please ensure the file exists");
                return false;
            }
            URLClassLoader child = null;

            try {
                child = new MyClassLoader(new URL[] { file.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
            } catch (MalformedURLException ex) {
                log.log(Level.SEVERE, "Exception while loading class", ex);
                return false;
            }
            Class<?> c = child.loadClass(fileName);

            Plugin plugin = (Plugin) c.newInstance();

            plugin.setName(fileName);
            plugin.enable();
            synchronized (lock) {
                plugins.add(plugin);
                plugin.initialize();
            }
        } catch (Throwable ex) {
            log.log(Level.SEVERE, "Exception while loading plugin", ex);
            return false;
        }
        return true;
    }

    /**
     * Returns the specified plugin
     * 
     * @param name
     *            name of plugin
     * @return plugin
     */
    public Plugin getPlugin(String name) {
        synchronized (lock) {
            for (Plugin plugin : plugins) {
                if (plugin.getName().equalsIgnoreCase(name)) {
                    return plugin;
                }
            }
        }
        return null;
    }

    /**
     * Returns a string list of plugins
     * 
     * @return String of plugins
     */
    public String getPluginList() {
        StringBuilder sb = new StringBuilder();

        synchronized (lock) {
            for (Plugin plugin : plugins) {
                sb.append(plugin.getName());
                sb.append(" ");
                sb.append(plugin.isEnabled() ? "(E)" : "(D)");
                sb.append(",");
            }
        }
        String str = sb.toString();

        if (str.length() > 1) {
            return str.substring(0, str.length() - 1);
        } else {
            return "Empty";
        }
    }

    /**
     * Enables the specified plugin (Or adds and enables it)
     * 
     * @param name
     *            name of plugin to enable
     * @return whether or not this plugin was enabled
     */
    public boolean enablePlugin(String name) {
        Plugin plugin = getPlugin(name);

        if (plugin != null) {
            if (!plugin.isEnabled()) {
                plugin.toggleEnabled();
                plugin.enable();
            }
        } else { // New plugin, perhaps?
            File file = new File("plugins/" + name + ".jar");

            if (file.exists()) {
                return loadPlugin(name);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Disables specified plugin
     * 
     * @param name
     *            name of the plugin to disable
     */
    public void disablePlugin(String name) {
        Plugin plugin = getPlugin(name);

        if (plugin != null) {
            if (plugin.isEnabled()) {
                plugin.toggleEnabled();
                plugin.disable();
            }
        }
    }

    /**
     * Returns the server
     * 
     * @return server
     */
    public Server getServer() {
        return server;
    }

    private boolean debug_hooks = true;
    private Hook lasthook;
    private int lasthook_count = 0;
    List ignore_hooks = Arrays.asList(new Hook[] { Hook.IGNITE, Hook.TIME_CHANGE, Hook.FLOW, Hook.LIQUID_DESTROY});
    
    public void debugHook(Hook h) {
        if (debug_hooks && !ignore_hooks.contains(h)) {
            if (h == lasthook) {
                lasthook_count++;
            } else {
                if (lasthook_count > 0) {
                    log.log(Level.INFO, String.format("Hook %s called %d times", lasthook, lasthook_count));
                }
                lasthook = h;
                lasthook_count = 1;
            }
        }        
    }

    /**
     * Calls a plugin hook.
     * 
     * @param h
     *            Hook to call
     * @param parameters
     *            Parameters of call
     * @return Object returned by call
     */
    @SuppressWarnings("deprecation")
    public Object callHook(Hook h, Object... parameters) {

        /*
         * Debug hooks block
         */
        // debugHook(h);
        
        Object toRet;

        switch (h) {
        case LIQUID_DESTROY:
        case TAME:
        case ENTITY_RIGHTCLICKED:
        case COMMAND_CHECK:
            toRet = HookResult.DEFAULT_ACTION;
            break;

        case CHUNK_CREATE:
        case SPAWNPOINT_CREATE:
            toRet = null;
            break;

        case ENCHANT:
        case CHAT:
            toRet = parameters[0];
            break;
            
        case REDSTONE_CHANGE:
        case FOODLEVEL_CHANGE:
        case FOODEXHAUSTION_CHANGE:
        case FOODSATURATION_CHANGE:
            toRet = parameters[2];
            break;

        case POTION_EFFECT:
        case GET_PLAYERLISTENTRY:
        case PLAYER_CONNECT:
        case PLAYER_DISCONNECT:
            toRet = parameters[1];
            break;

        default:
            toRet = false;
            break;
        }

        if (!(loaded || loadedpreload)) {
            return toRet;
        }

        synchronized (lock) {
            PluginListener listener = null;

            try {
                List<PluginRegisteredListener> registeredListeners = listeners.get(h.ordinal());

                for (PluginRegisteredListener regListener : registeredListeners) {
                    if (!regListener.getPlugin().isEnabled()) {
                        continue;
                    }

                    listener = regListener.getListener();

                    try {
                        switch (h) {
                        case LOGINCHECK:
                            String result = listener.onLoginChecks((String) parameters[0], (String) parameters[1]);

                            if (result != null) {
                                toRet = result;
                            }
                            break;

                        case LOGIN:
                            listener.onLogin((Player) parameters[0]);
                            break;

                        case DISCONNECT:
                            listener.onDisconnect((Player) parameters[0]);
                            break;

                        case CHAT:
                            toRet = listener.onChat((HookParametersChat) parameters[0]);
                            break;

                        case COMMAND:
                            if (listener.onCommand((Player) parameters[0], ((String[]) parameters[1]).clone())) {
                                toRet = true;
                            }
                            break;

                        case SERVERCOMMAND:
                            if (listener.onConsoleCommand(((String[]) parameters[0]).clone())) {
                                toRet = true;
                            }
                            break;

                        case BAN:
                            listener.onBan((Player) parameters[0], (Player) parameters[1], (String) parameters[2]);
                            break;

                        case IPBAN:
                            listener.onIpBan((Player) parameters[0], (Player) parameters[1], (String) parameters[2]);
                            break;

                        case KICK:
                            listener.onKick((Player) parameters[0], (Player) parameters[1], (String) parameters[2]);
                            break;

                        case BLOCK_CREATED:
                            if (listener.onBlockCreate((Player) parameters[0], (Block) parameters[1], (Block) parameters[2], (Integer) parameters[3])) {
                                toRet = true;
                            }
                            break;

                        case BLOCK_DESTROYED:
                            if (listener.onBlockDestroy((Player) parameters[0], (Block) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case PLAYER_MOVE:
                            listener.onPlayerMove((Player) parameters[0], (Location) parameters[1], (Location) parameters[2]);
                            break;

                        case ARM_SWING:
                            listener.onArmSwing((Player) parameters[0]);
                            break;

                        case ITEM_DROP:
                            if (listener.onItemDrop((Player) parameters[0], (ItemEntity) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case ITEM_PICK_UP:
                            if (listener.onItemPickUp((Player) parameters[0], (ItemEntity) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case TELEPORT:
                            if (listener.onTeleport((Player) parameters[0], (Location) parameters[1], (Location) parameters[2])) {
                                toRet = true;
                            }
                            break;

                        case BLOCK_BROKEN:
                            if (listener.onBlockBreak((Player) parameters[0], (Block) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case FLOW:
                            if (listener.onFlow((Block) parameters[0], (Block) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case IGNITE:
                            if (listener.onIgnite((Block) parameters[0], (parameters[1] == null ? null : (Player) parameters[1]))) {
                                toRet = true;
                            }
                            break;

                        case EXPLODE:
                            if (listener.onExplode((Block) parameters[0], (OEntity) parameters[1], (HashSet) parameters[2])) {
                                toRet = true;
                            }
                            break;

                        case MOB_SPAWN:
                            if (listener.onMobSpawn((Mob) parameters[0])) {
                                toRet = true;
                            }
                            break;

                        case DAMAGE:
                            if (listener.onDamage((DamageType) parameters[0], (BaseEntity) parameters[1], (BaseEntity) parameters[2], (Integer) parameters[3])) {
                                toRet = true;
                            }
                            break;

                        case HEALTH_CHANGE:
                            if (listener.onHealthChange((Player) parameters[0], (Integer) parameters[1], (Integer) parameters[2])) {
                                toRet = true;
                            }
                            break;

                        case REDSTONE_CHANGE:
                            toRet = listener.onRedstoneChange((Block) parameters[0], (Integer) parameters[1], (Integer) toRet);
                            break;

                        case PISTON_EXTEND:
                            toRet = listener.onPistonExtend((Block) parameters[0], (((Block) parameters[0]).getType() == Block.Type.StickyPiston.getType()));
                            break;

                        case PISTON_RETRACT:
                            toRet = listener.onPistonRetract((Block) parameters[0], (((Block) parameters[0]).getType() == Block.Type.StickyPiston.getType()));
                            break;

                        case BLOCK_PHYSICS:
                            if (listener.onBlockPhysics((Block) parameters[0], (Boolean) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case VEHICLE_CREATE:
                            listener.onVehicleCreate((BaseVehicle) parameters[0]);
                            break;

                        case VEHICLE_UPDATE:
                            listener.onVehicleUpdate((BaseVehicle) parameters[0]);
                            break;

                        case VEHICLE_DAMAGE:
                            if (listener.onVehicleDamage((BaseVehicle) parameters[0], (BaseEntity) parameters[1], (Integer) parameters[2])) {
                                toRet = true;
                            }
                            break;

                        case VEHICLE_COLLISION:
                            if (listener.onVehicleCollision((BaseVehicle) parameters[0], (BaseEntity) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case VEHICLE_DESTROYED:
                            listener.onVehicleDestroyed((BaseVehicle) parameters[0]);
                            break;

                        case VEHICLE_ENTERED:
                            listener.onVehicleEnter((BaseVehicle) parameters[0], (HumanEntity) parameters[1]);
                            break;

                        case VEHICLE_POSITIONCHANGE:
                            listener.onVehiclePositionChange((BaseVehicle) parameters[0], (Integer) parameters[1], (Integer) parameters[2], (Integer) parameters[3]);
                            break;

                        case ITEM_USE:
                            if (listener.onItemUse((Player) parameters[0], (Block) parameters[1], (Block) parameters[2], (Item) parameters[3])) {
                                toRet = true;
                            }
                            break;

                        case BLOCK_RIGHTCLICKED:
                            if (listener.onBlockRightClick((Player) parameters[0], (Block) parameters[1], (Item) parameters[2])) {
                                toRet = true;
                            }
                            break;

                        case BLOCK_PLACE:
                            if (listener.onBlockPlace((Player) parameters[0], (Block) parameters[1], (Block) parameters[2], (Item) parameters[3])) {
                                toRet = true;
                            }
                            break;

                        case LIQUID_DESTROY:
                            HookResult ret = listener.onLiquidDestroy((HookResult) toRet, (Integer) parameters[0], (Block) parameters[1]);

                            if (ret != HookResult.DEFAULT_ACTION && (HookResult) toRet == HookResult.DEFAULT_ACTION) {
                                toRet = ret;
                            }
                            break;

                        case ATTACK:
                            if (listener.onAttack((LivingEntity) parameters[0], (LivingEntity) parameters[1], (Integer) parameters[2])) {
                                toRet = true;
                            }
                            break;

                        case OPEN_INVENTORY:
                            if (listener.onOpenInventory((HookParametersOpenInventory)parameters[0])) {
                                toRet = true;
                            }
                            break;
                            
                        case CLOSE_INVENTORY:
                            listener.onCloseInventory((HookParametersCloseInventory)parameters[0]);
                            break;

                        case SIGN_SHOW:
                            listener.onSignShow((Player) parameters[0], (Sign) parameters[1]);
                            break;

                        case SIGN_CHANGE:
                            if (listener.onSignChange((Player) parameters[0], (Sign) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case LEAF_DECAY:
                            if (listener.onLeafDecay((Block) parameters[0])) {
                                toRet = true;
                            }
                            break;

                        case TAME:
                            ret = listener.onTame((Player) parameters[0], (Mob) parameters[1], (Boolean) parameters[2]);
                            if (ret != HookResult.DEFAULT_ACTION && (HookResult) toRet == HookResult.DEFAULT_ACTION) {
                                toRet = ret;
                            }
                            break;

                        case LIGHTNING_STRIKE:
                            if (listener.onLightningStrike((BaseEntity) parameters[0])) {
                                toRet = true;
                            }
                            break;

                        case WEATHER_CHANGE:
                            if (listener.onWeatherChange((World) parameters[0], (Boolean) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case THUNDER_CHANGE:
                            if (listener.onThunderChange((World) parameters[0], (Boolean) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case PORTAL_USE:
                            if (listener.onPortalUse((Player) parameters[0], (World) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case TIME_CHANGE:
                            if (listener.onTimeChange((World) parameters[0], (long) (Long) parameters[1])) {
                                toRet = true;
                            }
                            break;

                        case COMMAND_CHECK:
                            ret = listener.canPlayerUseCommand((Player) parameters[0], (String) parameters[1]);
                            if (ret != HookResult.DEFAULT_ACTION) {
                                toRet = ret;
                            }
                            break;

                        case CHUNK_CREATE:
                            byte[] chunk = listener.onChunkCreate((Integer) parameters[0], (Integer) parameters[1], (World) parameters[2]);

                            if (chunk != null) {
                                toRet = chunk;
                            }
                            break;

                        case SPAWNPOINT_CREATE:
                            Location point = listener.onSpawnpointCreate((World) parameters[0]);

                            if (point != null) {
                                toRet = point;
                            }
                            break;

                        case CHUNK_CREATED:
                            listener.onChunkCreated((Chunk) parameters[0]);
                            break;

                        case CHUNK_LOADED:
                            listener.onChunkLoaded((Chunk) parameters[0]);
                            break;

                        case CHUNK_UNLOAD:
                            listener.onChunkUnload((Chunk) parameters[0]);
                            break;

                        case PORTAL_CREATE:
                            toRet = listener.onPortalCreate(((Block[][]) parameters[0]).clone());
                            break;

                        case PORTAL_DESTROY:
                            toRet = listener.onPortalDestroy(((Block[][]) parameters[0]).clone());
                            break;

                        case PLAYER_RESPAWN:
                            listener.onPlayerRespawn((Player) parameters[0], (Location) parameters[1]);
                            break;

                        case ENTITY_DESPAWN:
                            toRet = listener.onEntityDespawn((BaseEntity) parameters[0]);
                            break;

                        case ENDERMAN_PICKUP:
                            toRet = listener.onEndermanPickup((Enderman) parameters[0], (Block) parameters[1]);
                            break;

                        case ENDERMAN_DROP:
                            toRet = listener.onEndermanDrop((Enderman) parameters[0], (Block) parameters[1]);
                            break;

                        case COW_MILK:
                            toRet = listener.onCowMilk((Player) parameters[0], (Mob) parameters[1]);
                            break;

                        case EAT:
                            toRet = listener.onEat((Player) parameters[0], (Item) parameters[1]);
                            break;

                        case FOODLEVEL_CHANGE:
                            toRet = (Integer) listener.onFoodLevelChange((Player) parameters[0], (Integer) parameters[1], (Integer) parameters[2]);
                            break;

                        case FOODEXHAUSTION_CHANGE:
                            toRet = (Float) listener.onFoodExhaustionChange((Player) parameters[0], (Float) parameters[1], (Float) parameters[2]);
                            break;

                        case FOODSATURATION_CHANGE:
                            toRet = (Float) listener.onFoodSaturationChange((Player) parameters[0], (Float) parameters[1], (Float) parameters[2]);
                            break;

                        case POTION_EFFECT:
                            toRet = listener.onPotionEffect((LivingEntity) parameters[0], (PotionEffect) parameters[1]);
                            break;

                        case EXPERIENCE_CHANGE:
                            if (listener.onExpChange((Player) parameters[0], (Integer) parameters[1], (Integer) parameters[2])) {
                                toRet = true;
                            }
                            break;

                        case LEVEL_UP:
                            if (listener.onLevelUp((Player) parameters[0])) {
                                toRet = true;
                            }
                            break;

                        case GET_PLAYERLISTENTRY:
                            toRet = listener.onGetPlayerlistEntry((Player) parameters[0], (PlayerlistEntry) parameters[1]);
                            break;

                        case PLAYER_CONNECT:
                            toRet = listener.onPlayerConnect((Player) parameters[0], (HookParametersConnect) parameters[1]);
                            break;
                            
                        case PLAYER_DISCONNECT:
                            toRet = listener.onPlayerDisconnect((Player) parameters[0], (HookParametersDisconnect) parameters[1]);
                            break;

                        case ENTITY_RIGHTCLICKED:
                            ret = listener.onEntityRightClick((Player) parameters[0], (BaseEntity) parameters[1], (Item) parameters[2]);
                            if (ret != HookResult.DEFAULT_ACTION && (HookResult) toRet == HookResult.DEFAULT_ACTION) {
                                toRet = ret;
                            }
                            break;

                        case MOB_TARGET:
                            if (listener.onMobTarget((Player) parameters[0], (LivingEntity) parameters[1])) {
                                toRet = true;
                            }
                            break;
                            
                        case BLOCK_UPDATE:
                            if(listener.onBlockUpdate((Block) parameters[0], (Integer) parameters[1])){
                                toRet = true;
                            }
                            break;
                            
                        case ENCHANT:
                            toRet = listener.onEnchant((HookParametersEnchant) parameters[0]);
                            break;
                            
                        case DISPENSE:
                            toRet = listener.onDispense((Dispenser) parameters[0], (BaseEntity) parameters[1]);
                            break;
                        }
                       } catch (UnsupportedOperationException ex) {}
                }
            } catch (Exception ex) {
                String listenerString = listener == null ? "null(unknown listener)" : listener.getClass().toString();

                log.log(Level.SEVERE, "Exception while calling plugin function in '" + listenerString + "' while calling hook: '" + h.toString() + "'.", ex);
            } catch (Throwable ex) { // The 'exception' thrown is so severe it's
                // not even an exception!
                log.log(Level.SEVERE, "Throwable while calling plugin (Outdated?)", ex);
            }
        }

        return toRet;
    }

    /**
     * Calls a custom hook
     * 
     * @param name
     *            name of hook
     * @param parameters
     *            parameters for the hook
     * @return object returned by call
     */
    public Object callCustomHook(String name, Object[] parameters) {
        Object toRet = false;

        synchronized (lock) {
            try {
                PluginInterface listener = customListeners.get(name);

                if (listener == null) {
                    log.log(Level.SEVERE, "Cannot find custom hook: " + name);
                    return false;
                }

                String msg = listener.checkParameters(parameters);

                if (msg != null) {
                    log.log(Level.SEVERE, msg);
                    return false;
                }

                toRet = listener.run(parameters);
            } catch (Exception ex) {
                log.log(Level.SEVERE, "Exception while calling custom plugin function", ex);
            }
        }
        return toRet;
    }

    /**
     * Calls a plugin hook.
     * 
     * @param hook
     *            The hook to call on
     * @param listener
     *            The listener to use when calling
     * @param plugin
     *            The plugin of this listener
     * @param priorityEnum
     *            The priority of this listener
     * @return PluginRegisteredListener
     */
    public PluginRegisteredListener addListener(Hook hook, PluginListener listener, Plugin plugin, PluginListener.Priority priorityEnum) {
        int priority = priorityEnum.ordinal();
        PluginRegisteredListener reg = new PluginRegisteredListener(hook, listener, plugin, priority);

        synchronized (lock) {
            List<PluginRegisteredListener> regListeners = listeners.get(hook.ordinal());

            int pos = 0;

            for (PluginRegisteredListener other : regListeners) {
                if (other.getPriority() < priority) {
                    break;
                }
                ++pos;
            }

            regListeners.add(pos, reg);
        }

        return reg;
    }

    /**
     * Adds a custom listener
     * 
     * @param listener
     *            listener to add
     */
    public void addCustomListener(PluginInterface listener) {
        synchronized (lock) {
            if (customListeners.get(listener.getName()) != null) {
                log.log(Level.SEVERE, "Replacing existing listener: " + listener.getName());
            }
            customListeners.put(listener.getName(), listener);
            log.info("Registered custom hook: " + listener.getName());
        }
    }

    /**
     * Removes the specified listener from the list of listeners
     * 
     * @param reg
     *            listener to remove
     */
    public void removeListener(PluginRegisteredListener reg) {
        List<PluginRegisteredListener> regListeners = listeners.get(reg.getHook().ordinal());

        synchronized (lock) {
            regListeners.remove(reg);
        }
    }

    /**
     * Removes a custom listener
     * 
     * @param name
     *            name of listener
     */
    public void removeCustomListener(String name) {
        synchronized (lock) {
            customListeners.remove(name);
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

}
