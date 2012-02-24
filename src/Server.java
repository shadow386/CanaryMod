
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;


/**
 * Server.java - Interface to server stuff
 * Crow build 3.1.9
 *
 * As of Canary b8, a lot of methods have been deprecated because they were
 * dimension-related. Please use the appropriate {@link World} functions instead.
 * 
 * @author James
 */
public class Server {

    private MinecraftServer server;

    /**
     * Creates a server
     * 
     * @param server
     */
    public Server(MinecraftServer server) {
        this.server = server;
    }

    /**
     * Sends a message to all users
     * 
     * @param msg
     *            Message text to send
     */
    public void messageAll(String msg) {
        server.h.a(new OPacket3Chat(msg));
    }

    /**
     * Bans specified player
     * 
     * @param player
     *            Name of the player to ban
     * 
     */
    public void ban(String player) {
        server.h.a(player);
    }

    /**
     * Unbans specified user
     * 
     * @param player
     *            Player name to unban
     * 
     */
    public void unban(String player) {
        server.h.b(player);
    }

    /**
     * Uses the specified console command
     * 
     * @param command
     */
    public void useConsoleCommand(String command) {
        server.a(command, server);
    }

    /**
     * Uses the specified console command
     * 
     * @param command
     *            command to use
     * @param player
     *            player to use command as
     */
    public void useConsoleCommand(String command, Player player) {
        server.a(command, player.getUser().a);
    }

    /**
     * Starts a timer using the built-in timer system.
     * 
     * @param uniqueString
     *            must be unique identifier for this timer
     * @param time
     *            time till it expires (6000 roughly equals 5 minutes)
     */
    public void setTimer(String uniqueString, int time) {
        MinecraftServer.b.put(uniqueString, time);
    }

    /**
     * Check to see if your timer has expired yet.
     * 
     * @param uniqueString
     *            unique identifier
     * @return false if timer has expired
     */
    public boolean isTimerExpired(String uniqueString) {
        return MinecraftServer.b.containsKey(uniqueString);
    }

    /**
     * Returns actual server time (-2^63 to 2^63-1)
     *
     * @deprecated Use {@link World#getTime()} instead.
     * @return time server time
     */
    @Deprecated
    public long getTime() {
        return server.e[0].n();
    }

    /**
     * Returns current server time (0-24000)
     *
     * @deprecated use {@link World#getRelativeTime() } instead.
     * @return time server time
     */
    @Deprecated
    public long getRelativeTime() {
        long time = (getTime() % 24000);

        // Java modulus is stupid.
        if (time < 0) {
            time += 24000;
        }
        return time;
    }

    /**
     * Sets the actual server time
     *
     * @deprecated Use {@link World#setTime(long)} instead.
     * @param time
     *            time (-2^63 to 2^63-1)
     */
    @Deprecated
    public void setTime(long time) {
        server.a(0).a(time);
    }

    /**
     * Sets the current server time
     *
     * @deprecated Use {@link World#setRelativeTime(long) } instead.
     * @param time
     *            time (0-24000)
     */
    @Deprecated
    public void setRelativeTime(long time) {
        long margin = (time - getTime()) % 24000;

        // Java modulus is stupid.
        if (margin < 0) {
            margin += 24000;
        }
        setTime(getTime() + margin);
    }

    /**
     * Returns the actual Minecraft Server
     * 
     * @return
     */
    public MinecraftServer getMCServer() {
        return server;
    }

    /**
     * Tries to match a character's name.
     * 
     * @param name
     * @return
     */
    public Player matchPlayer(String name) {
        Player lastPlayer = null;

        name = name.toLowerCase();

        for (OEntityPlayerMP player : (List<OEntityPlayerMP>) server.h.b) {
            String playerName = player.v;

            if (playerName.toLowerCase().equals(name)) {
                // Perfect match found
                lastPlayer = player.getPlayer();
                break;
            }
            if (playerName.toLowerCase().indexOf(name.toLowerCase()) != -1) {
                // Partial match
                if (lastPlayer != null) {
                    // Found multiple
                    return null;
                }
                lastPlayer = player.getPlayer();
            }
        }

        return lastPlayer;
    }

    /**
     * Returns specified player
     * 
     * @param name
     * @return
     */
    public Player getPlayer(String name) {
        OEntityPlayerMP user = server.h.i(name);

        return user == null ? null : user.getPlayer();
    }

    /**
     * Returns the player list.
     * 
     * @return list of players
     */
    public List<Player> getPlayerList() {
        List<Player> toRet = new ArrayList<Player>();

        for (OEntityPlayerMP oepmp : (List<OEntityPlayerMP>) server.h.b) {
            toRet.add(oepmp.getPlayer());
        }
        return toRet;
    }

    /**
     * Returns a string with comma-seperated player names
     *
     * @return list of player names
     */
    public String getPlayerNames() {
        return server.h.c();
    }

    /**
     * Returns the list of mobs in all open chunks.
     *
     * @deprecated Use {@link World#getMobList() } instead.
     * @return list of mobs
     */
    @Deprecated
    public List<Mob> getMobList() {
        List<Mob> toRet = new ArrayList<Mob>();

        for (Object o : server.a(0).h) {
            if (o instanceof OEntityMob || o instanceof OEntityGhast) {
                toRet.add(new Mob((OEntityLiving) o));
            }
        }
        return toRet;
    }

    /**
     * Returns the list of animals in all open chunks.
     *
     * @deprecated Use {@link World#getAnimalList() } instead.
     * @return list of animals
     */
    @Deprecated
    public List<Mob> getAnimalList() {
        List<Mob> toRet = new ArrayList<Mob>();

        for (Object o : server.a(0).h) {
            if (o instanceof OEntityAnimal) {
                toRet.add(new Mob((OEntityLiving) o));
            }
        }
        return toRet;
    }

    /**
     * Returns the list of minecarts in all open chunks.
     *
     * @deprecated Use {@link World#getMinecartList() } instead.
     * @return list of minecarts
     */
    @Deprecated
    public List<Minecart> getMinecartList() {
        List<Minecart> toRet = new ArrayList<Minecart>();

        for (Object o : server.a(0).h) {
            if (o instanceof OEntityMinecart) {
                toRet.add(((OEntityMinecart) o).cart);
            }
        }
        return toRet;
    }

    /**
     * Returns the list of boats in all open chunks.
     *
     * @deprecated Use {@link World#getBoatList() } instead.
     * @return list of boats
     */
    @Deprecated
    public List<Boat> getBoatList() {
        List<Boat> toRet = new ArrayList<Boat>();

        for (Object o : server.a(0).h) {
            if (o instanceof OEntityBoat) {
                toRet.add(((OEntityBoat) o).boat);
            }
        }
        return toRet;
    }

    /**
     * Returns the list of all entities in the server in open chunks.
     *
     * @deprecated Use {@link World#getEntityList() } instead.
     * @return list of entities
     */
    @Deprecated
    public List<BaseEntity> getEntityList() {
        List<BaseEntity> toRet = new ArrayList<BaseEntity>();

        for (Object o : server.a(0).h) {
            if (o instanceof OEntityMob || o instanceof OEntityGhast || o instanceof OEntityAnimal) {
                toRet.add(new Mob((OEntityLiving) o));
            } else if (o instanceof OEntityMinecart) {
                toRet.add(((OEntityMinecart) o).cart);
            } else if (o instanceof OEntityBoat) {
                toRet.add(((OEntityBoat) o).boat);
            } else if (o instanceof OEntityPlayerMP) {
                toRet.add(((OEntityPlayerMP) o).getPlayer());
            }
        }
        return toRet;
    }

    /**
     * Returns the list of all living entities (players, animals, mobs) in open
     * chunks.
     *
     * @deprecated Use {@link World#getLivingEntityList() } instead.
     * @return list of living entities
     */
    @Deprecated
    public List<LivingEntity> getLivingEntityList() {
        List<LivingEntity> toRet = new ArrayList<LivingEntity>();

        for (Object o : server.a(0).h) {
            if (o instanceof OEntityMob || o instanceof OEntityGhast || o instanceof OEntityAnimal) {
                toRet.add(new Mob((OEntityLiving) o));
            } else if (o instanceof OEntityPlayerMP) {
                toRet.add(((OEntityPlayerMP) o).getPlayer());
            }
        }
        return toRet;
    }

    /**
     * Returns the list of vehicles in open chunks.
     *
     * @deprecated Use {@link World#getVehicleEntityList() } instead.
     * @return list of vehicles
     */
    @Deprecated
    public List<BaseVehicle> getVehicleEntityList() {
        List<BaseVehicle> toRet = new ArrayList<BaseVehicle>();

        for (Object o : server.a(0).h) {
            if (o instanceof OEntityMinecart) {
                toRet.add(((OEntityMinecart) o).cart);
            } else if (o instanceof OEntityBoat) {
                toRet.add(((OEntityBoat) o).boat);
            }
        }
        return toRet;
    }

    /**
     * Get the global spawn location
     *
     * @deprecated Use {@link World#getSpawnLocation() } instead
     * @return Location object for spawn
     */
    @Deprecated
    public Location getSpawnLocation() {
        // More structure ftw
        OWorldInfo info = server.a(0).C;
        Location spawn = new Location();

        spawn.x = info.c() + 0.5D;
        spawn.y = getMCServer().a(0).f(info.c(), info.e());
        spawn.z = info.e() + 0.5D;
        spawn.rotX = 0.0F;
        spawn.rotY = 0.0F;
        return spawn;
    }

    /**
     * Sets the block
     *
     * @deprecated Use {@link World#setBlock(Block) } instead.
     * @param block
     * @return
     */
    @Deprecated
    public boolean setBlock(Block block) {
        return setBlockAt(block.getType(), block.getX(), block.getY(), block.getZ()) && setBlockData(block.getX(), block.getY(), block.getZ(), block.getData());
    }

    /**
     * Returns the block at the specified location
     *
     * @deprecated Use {@link World#getBlockAt(int, int, int) } instead.
     * @param x
     * @param y
     * @param z
     * @return block
     */
    @Deprecated
    public Block getBlockAt(int x, int y, int z) {
        return new Block(getBlockIdAt(x, y, z), x, y, z, getBlockData(x, y, z));
    }

    /**
     * Returns the block data at the specified coordinates
     *
     * @deprecated Use {@link World#getBlockData(int, int, int)} instead.
     * @param x
     *            x
     * @param y
     *            y
     * @param z
     *            z
     * @return block data
     */
    @Deprecated
    public int getBlockData(int x, int y, int z) {
        return server.a(0).c(x, y, z);
    }

    /**
     * Sets the block data at the specified coordinates
     *
     * @deprecated Use {@link World#setBlockData(int, int, int, int) } instead.
     * @param x
     *            x
     * @param y
     *            y
     * @param z
     *            z
     * @param data
     *            data
     * @return true if it was successful
     */
    @Deprecated
    public boolean setBlockData(int x, int y, int z, int data) {
        boolean toRet = server.a(0).d(x, y, z, data);

        etc.getMCServer().h.a(new OPacket53BlockChange(x, y, z, etc.getMCServer().a(0)), 0);
        ComplexBlock block = getComplexBlock(x, y, z);

        if (block != null) {
            block.update();
        }
        return toRet;
    }

    /**
     * Sets the block type at the specified location
     *
     * @deprecated Use {@link World#setBlockAt(int, int, int, int) } instead.
     * @param blockType
     * @param x
     * @param y
     * @param z
     * @return true if successful
     */
    @Deprecated
    public boolean setBlockAt(int blockType, int x, int y, int z) {
        return server.a(0).e(x, y, z, blockType);
    }

    /**
     * Returns the highest block Y
     *
     * @deprecated Use {@link World#getHighestBlockY(int, int) } instead.
     * @param x
     * @param z
     * @return highest block altitude
     */
    @Deprecated
    public int getHighestBlockY(int x, int z) {
        return server.a(0).e(x, z);
    }

    /**
     * Returns the block type at the specified location
     *
     * @deprecated Use {@link World#getBlockIdAt(int, int, int) } instead.
     * @param x
     * @param y
     * @param z
     * @return block type
     */
    @Deprecated
    public int getBlockIdAt(int x, int y, int z) {
        return server.a(0).a(x, y, z);
    }

    /**
     * Returns the complex block at the specified location. Null if there's no
     * complex block there. This will also find complex-blocks spanning multiple
     * spaces, such as double chests.
     *
     * @deprecated Use {@link World#getComplexBlock(Block) } instead.
     * @param block
     * @return complex block
     */
    @Deprecated
    public ComplexBlock getComplexBlock(Block block) {
        return getComplexBlock(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Returns the complex block at the specified location. Null if there's no
     * complex block there. This will also find complex-blocks spanning multiple
     * spaces, such as double chests.
     *
     * @deprecated Use {@link World#getComplexBlock(int, int, int) } instead.
     * @param x
     *            x
     * @param y
     *            y
     * @param z
     *            z
     * @return complex block
     */
    @Deprecated
    public ComplexBlock getComplexBlock(int x, int y, int z) {
        ComplexBlock result = getOnlyComplexBlock(x, y, z);

        if (result != null) {
            if (result instanceof Chest) {
                Chest chest = (Chest) result;

                result = chest.findAttachedChest();

                if (result != null) {
                    return result;
                } else {
                    return chest;
                }
            }
        }

        return result;
    }

    /**
     * Returns the only complex block at the specified location. Null if there's
     * no complex block there.
     *
     * @deprecated Use {@link World#getOnlyComplexBlock(Block) } instead.
     * @param block
     * @return complex block
     */
    @Deprecated
    public ComplexBlock getOnlyComplexBlock(Block block) {
        return getOnlyComplexBlock(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Returns the complex block at the specified location. Null if there's no
     * complex block there.
     *
     * @deprecated Use {@link World#getOnlyComplexBlock(int, int, int)} instead.
     * @param x
     *            x
     * @param y
     *            y
     * @param z
     *            z
     * @return complex block
     */
    @Deprecated
    public ComplexBlock getOnlyComplexBlock(int x, int y, int z) {
        OTileEntity localav = server.a(0).b(x, y, z);

        if (localav != null) {
            if (localav instanceof OTileEntityChest) {
                return new Chest((OTileEntityChest) localav);
            } else if (localav instanceof OTileEntitySign) {
                return new Sign((OTileEntitySign) localav);
            } else if (localav instanceof OTileEntityFurnace) {
                return new Furnace((OTileEntityFurnace) localav);
            } else if (localav instanceof OTileEntityMobSpawner) {
                return new MobSpawner((OTileEntityMobSpawner) localav);
            } else if (localav instanceof OTileEntityDispenser) {
                return new Dispenser((OTileEntityDispenser) localav);
            }
        }
        return null;
    }

    /**
     * Drops an item at the specified location
     *
     * @deprecated Use {@link World#dropItem(Location, int) } instead.
     * @param loc
     * @param itemId
     */
    @Deprecated
    public void dropItem(Location loc, int itemId) {
        dropItem(loc.x, loc.y, loc.z, itemId, 1);
    }

    /**
     * Drops an item at the specified location
     *
     * @deprecated Use {@link World#dropItem(double, double, double, int)}
     * instead.
     * @param x
     * @param y
     * @param z
     * @param itemId
     * 
     * @return returns the ItemEntity that was dropped
     */
    @Deprecated
    public ItemEntity dropItem(double x, double y, double z, int itemId) {
        return dropItem(x, y, z, itemId, 1);
    }

    /**
     * Drops an item with desired quantity at the specified location
     *
     * @deprecated Use {@link World#dropItem(Location, int, int) } instead.
     * @param loc
     * @param itemId
     * @param quantity
     * 
     * @return returns the ItemEntity that was dropped
     */
    @Deprecated
    public ItemEntity dropItem(Location loc, int itemId, int quantity) {
        return dropItem(loc.x, loc.y, loc.z, itemId, quantity);
    }

    /**
     * Drops an item with desired quantity at the specified location
     *
     * @deprecated Use {@link World#dropItem(double, double, double, int, int) }
     * instead.
     * @param x
     * @param y
     * @param z
     * @param itemId
     * @param quantity
     * 
     * @return returns the ItemEntity that was dropped
     */
    @Deprecated
    public ItemEntity dropItem(double x, double y, double z, int itemId, int quantity) {
        OWorldServer ows = server.a(0);
        double d1 = ows.w.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double d2 = ows.w.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double d3 = ows.w.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;

        OEntityItem oei = new OEntityItem(ows, x + d1, y + d2, z + d3, new OItemStack(itemId, quantity, 0));

        oei.c = 10;
        ows.b(oei);
        return oei.item;
    }

    /**
     * Forces the server to update the physics for blocks around the given block
     *
     * @deprecated Use {@link World#updateBlockPhysics(Block) } instead.
     * @param block
     *            the block that changed
     */
    @Deprecated
    public void updateBlockPhysics(Block block) {
        updateBlockPhysics(block.getX(), block.getY(), block.getZ(), block.getData());
    }

    /**
     * Forces the server to update the physics for blocks around the given block
     *
     * @deprecated Use {@link World#updateBlockPhysics(int, int, int, int) }
     * instead.
     * @param x
     *            the X coordinate of the block
     * @param y
     *            the Y coordinate of the block
     * @param z
     *            the Z coordinate of the block
     * @param data
     *            the new data for the block
     */
    @Deprecated
    public void updateBlockPhysics(int x, int y, int z, int data) {
        server.a(0).c(x, y, z, data);
    }

    /**
     * Adds a runnable to the Server Queue, so that it will be executed in the
     * Server Thread.
     * 
     * @param r
     *            - the runnable
     */
    public void addToServerQueue(Runnable r) {
        addToServerQueue(r, 0L);
    }

    /**
     * Executes a runnable in the server thread after a specified delay.
     * 
     * @param r
     *            - the runnable
     * @param delayMillis
     *            - the delay in milliseconds
     */
    public void addToServerQueue(Runnable r, long delayMillis) {
        OEntityTracker.add(r, delayMillis);
    }

    /**
     * Saves all player inventories to file
     */
    public void saveInventories() {
        server.h.g();
    }

    /**
     * Checks to see whether or not the chunk containing the given block is
     * loaded into memory.
     *
     * @deprecated Use {@link World#isChunkLoaded(Block) } instead.
     * @param block
     *            the Block to check
     * @return true if the chunk is loaded
     */
    @Deprecated
    public boolean isChunkLoaded(Block block) {
        return isChunkLoaded(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Checks to see whether or not the chunk containing the given block
     * coordinates is loaded into memory.
     *
     * @deprecated Use {@link World#isChunkLoaded(int, int, int) } instead.
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return true if the chunk is loaded
     */
    @Deprecated
    public boolean isChunkLoaded(int x, int y, int z) {
        return server.a(0).J.a(x >> 4, z >> 4);
    }

    /**
     * Loads the chunk containing the given block. If the chunk does not exist,
     * it will be generated.
     *
     * @deprecated Use {@link World#loadChunk(Block) } instead.
     * @param block
     *            the Block to check
     */
    @Deprecated
    public void loadChunk(Block block) {
        loadChunk(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Loads the chunk containing the given block coordinates. If the chunk does
     * not exist, it will be generated.
     *
     * @deprecated Use {@link World#loadChunk(int, int, int) } instead.
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     */
    @Deprecated
    public void loadChunk(int x, int y, int z) {
        loadChunk(x >> 4, z >> 4);
    }

    /**
     * Loads the chunk containing the given chunk coordinates. If the chunk does
     * not exist, it will be generated.
     *
     * @deprecated Use {@link World#loadChunk(int, int) } instead.
     * @param x
     *            a chunk x-coordinate
     * @param z
     *            a chunk z-coordinate
     */
    @Deprecated
    public void loadChunk(int x, int z) {
        server.a(0).J.a(x, z);
    }

    /**
     * Checks if the provided block is being powered through redstone
     *
     * @deprecated Use {@link World#isBlockPowered(Block) } instead.
     * @param block
     *            Block to check
     * @return true if the block is being powered
     */
    @Deprecated
    public boolean isBlockPowered(Block block) {
        return isBlockPowered(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Checks if the provided block is being powered through redstone
     *
     * @deprecated Use {@link World#isBlockPowered(int, int, int) } instead.
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return true if the block is being powered
     */
    @Deprecated
    public boolean isBlockPowered(int x, int y, int z) {
        return server.a(0).t(x, y, z);
    }

    /**
     * Checks if the provided block is being indirectly powered through redstone
     *
     * @deprecated Use {@link World#isBlockIndirectlyPowered(Block) } instead.
     * @param block
     *            Block to check
     * @return true if the block is being indirectly powered
     */
    @Deprecated
    public boolean isBlockIndirectlyPowered(Block block) {
        return isBlockIndirectlyPowered(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Checks if the provided block is being indirectly powered through redstone
     *
     * @deprecated Use {@link World#isBlockIndirectlyPowered(int, int, int) }
     * instead.
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return true if the block is being indirectly powered
     */
    @Deprecated
    public boolean isBlockIndirectlyPowered(int x, int y, int z) {
        return server.a(0).u(x, y, z);
    }

    /**
     * Set the thunder state
     *
     * @deprecated Use {@link World#setThundering(boolean) } instead;
     * @param thundering whether it should thunder
     */
    @Deprecated
    public void setThundering(boolean thundering) {
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.THUNDER_CHANGE, getDefaultWorld(), thundering)) {
            return;
        }

        OWorldServer ows = server.a(0);

        ows.C.a(thundering);

        // Thanks to Bukkit for figuring out these numbers
        if (thundering) {
            setThunderTime(ows.w.nextInt(12000) + 3600);
        } else {
            setThunderTime(ows.w.nextInt(168000) + 12000);
        }
    }

    /**
     * Set the thunder ticks.
     *
     * @deprecated Use {@link World#setThunderTime(int) } instead.
     * @param ticks ticks of thunder
     */
    @Deprecated
    public void setThunderTime(int ticks) {
        server.a(0).C.b(ticks);
    }

    /**
     * Sets the rain state.
     *
     * @deprecated Use {@link World#setRaining(boolean) } instead.
     * @param raining whether it should rain
     */
    @Deprecated
    public void setRaining(boolean raining) {
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.WEATHER_CHANGE, getDefaultWorld(), raining)) {
            return;
        }

        OWorldServer ows = server.a(0);

        ows.C.b(raining);

        // Thanks to Bukkit for figuring out these numbers
        if (raining) {
            setRainTime(ows.w.nextInt(12000) + 3600);
        } else {
            setRainTime(ows.w.nextInt(168000) + 12000);
        }
    }

    /**
     * Sets the rain ticks.
     *
     * @deprecated Use {@link World#setRainTime(int) } instead.
     * @param ticks ticks of rain
     */
    @Deprecated
    public void setRainTime(int ticks) {
        server.a(0).C.c(ticks);
    }

    /**
     * Returns whether it's thundering
     *
     * @deprecated Use {@link World#isThundering() } instead.
     * @return whether it's thundering
     */
    @Deprecated
    public boolean isThundering() {
        return server.a(0).C.j();
    }

    /**
     * Returns the number of ticks to go till the end of the thunder
     *
     * @deprecated Use {@link World#getThunderTime() } instead.
     * @return the thunder ticks
     */
    @Deprecated
    public int getThunderTime() {
        return server.a(0).C.k();
    }

    /**
     * Returns whether it's raining
     *
     * @deprecated Use {@link World#isRaining() } instead.
     * @return whether it's raining
     */
    @Deprecated
    public boolean isRaining() {
        return server.a(0).C.l();
    }

    /**
     * Returns the number of ticks to go till the end of the rain
     *
     * @deprecated Use {@link World#getRainTime() } instead.
     * @return the rain ticks
     */
    @Deprecated
    public int getRainTime() {
        return server.a(0).C.m();
    }

    /**
     * Returns the dimension new users spawn in
     * @return the default dimension
     */
    public World getDefaultWorld() {
        return server.a(0).world;
    }

    /**
     * Returns the dimension at the given dimension.
     * Due to the way the server works, this returns the Nether's {@link World}
     * if {@code dimension} is -1, the normal dimension's {@link World} otherwise.
     * @param dimension The dimension to return the World for
     * @return {@code dimension}'s World
     */
    public World getWorld(int dimension) {
        return server.a(dimension).world;
    }

    /**
     * Adds a recipe to the crafting manager.
     * Due to deadlines, this documentation isn't written yet, you may want to
     * refer to MCP in the meantime.
     * @param item The item to return
     * @param recipe The recipe to return the item for
     */
    public void addRecipe(Item item, Object... recipe) {
        for (int i = 0; i < recipe.length; i++) {
            if (recipe[i] instanceof Block.Type) {
                recipe[i] = OBlock.m[((Block.Type) recipe[i]).getType()];
            } else if (recipe[i] instanceof Item.Type) {
                recipe[i] = OItem.d[((Item.Type) recipe[i]).getId()];
            }
        }
        OCraftingManager.a().a(item.getBaseItem(), recipe);
    }

    /**
     * Adds a shapeless recipe to the crafting manager.
     * Due to deadlines, this documentation isn't written yet, you may want to
     * refer to MCP in the meantime.
     * @param item The item to return
     * @param recipe The recipes to return the item for.
     */
    public void addShapelessRecipe(Item item, Object... recipe) {
        for (int i = 0; i < recipe.length; i++) {
            if (recipe[i] instanceof Block.Type) {
                recipe[i] = OBlock.m[((Block.Type) recipe[i]).getType()];
            } else if (recipe[i] instanceof Item.Type) {
                recipe[i] = OItem.d[((Item.Type) recipe[i]).getId()];
            }
        }
        OCraftingManager.a().b(item.getBaseItem(), recipe);
    }
    
    /**
     * Adds a smelting recipe to the furnace recipes.
     * {@code from} is the item that is put into the furnace, and should have 
     * amount 1. {@code to} is the result after smelting.
     * @param from The inserted item
     * @param to The resulting item
     * @throws IllegalArgumentException if the amount of {@code from} doesn't
     *          equal 1.
     */
    public void addSmeltingRecipe(Item from, Item to) {
        if (from.getAmount() != 1)
            throw new IllegalArgumentException("The 'from' amount should be 1");
        OFurnaceRecipes.a().a(from.getItemId(), to.getBaseItem());
    }

    /**
     * Returns the list with recipes, as kept by the crafting manager.
     * @return a list containing {@code OIRecipe} instances.
     */
    public List getRecipeList() {
        return OCraftingManager.a().b();
    }
}
