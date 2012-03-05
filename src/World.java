
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * World.java - Interface to worlds.
 * Most of the stuff in Server.java was moved here.
 *
 * @author 14mRh4X0r
 */
public class World {
    private final OWorldServer world;

    public enum Type {
        NETHER(-1), //
        NORMAL(0), //
        END(1);
        
        private int id;
        private static Map<Integer, Type> map;

        private Type(int id) {
            this.id = id;
            add(id, this);
        }

        private static void add(int type, Type name) {
            if (map == null) {
                map = new HashMap<Integer, Type>();
            }

            map.put(type, name);
        }

        public int getId() {
            return id;
        }

        public static Type fromId(final int type) {
            return map.get(type);
        }
        
        @Override
        public String toString() {
            String name = this.name();
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

    }

    /**
     * Instantiates this wrapper around {@code world}
     * @param world the OWorldServer to wrap
     */
    public World(OWorldServer world) {
        this.world = world;
    }

    /**
     * Returns the OWorldServer this class wraps around
     * @return the managed worldserver
     */
    public OWorldServer getWorld() {
        return world;
    }

    /**
     * Returns this dimension's type.
     * Currently Nether, End and Normal
     * 
     * @return the dimension type
     */
    public Type getType() {
        return Type.fromId(world.t.g);
    }

    /**
     * Returns actual server time (-2^63 to 2^63-1)
     *
     * @return time server time
     */
    public long getTime() {
        return world.o();
    }

    /**
     * Returns current server time (0-24000)
     *
     * @return time server time
     */
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
     * @param time
     *            time (-2^63 to 2^63-1)
     */
    public void setTime(long time) {
        etc.getServer().getWorld(-1).getWorld().a(time);
        etc.getServer().getWorld(0).getWorld().a(time);
        etc.getServer().getWorld(1).getWorld().a(time);
    }

    /**
     * Sets the current server time
     *
     * @param time
     *            time (0-24000)
     */
    public void setRelativeTime(long time) {
        long margin = (time - getTime()) % 24000;

        // Java modulus is stupid.
        if (margin < 0) {
            margin += 24000;
        }
        setTime(getTime() + margin);
    }

    /**
     * Returns the list of mobs in all open chunks.
     *
     * @return list of mobs
     */
    public List<Mob> getMobList() {
        List<Mob> toRet = new ArrayList<Mob>();

        for (Object o : world.b) {
            if (o instanceof OEntityMob || o instanceof OEntityGhast || o instanceof OEntitySlime || o instanceof OEntityEnderDragon || o instanceof OEntityLavaSlime) {
                toRet.add(new Mob((OEntityLiving) o));
            }
        }
        return toRet;
    }

    /**
     * Returns the list of animals in all open chunks.
     *
     * @return list of animals
     */
    public List<Mob> getAnimalList() {
        List<Mob> toRet = new ArrayList<Mob>();

        for (Object o : world.b) {
            if (o instanceof OEntityAnimal || o instanceof OEntitySquid || o instanceof OEntitySnowMan) {
                toRet.add(new Mob((OEntityLiving) o));
            }
        }
        return toRet;
    }
    
    /**
     * Returns the list of minecarts in all open chunks.
     *
     * @return list of minecarts
     */
    public List<Minecart> getMinecartList() {
        List<Minecart> toRet = new ArrayList<Minecart>();

        for (Object o : world.b) {
            if (o instanceof OEntityMinecart) {
                toRet.add(((OEntityMinecart) o).cart);
            }
        }
        return toRet;
    }

    /**
     * Returns the list of boats in all open chunks.
     *
     * @return list of boats
     */
    public List<Boat> getBoatList() {
        List<Boat> toRet = new ArrayList<Boat>();

        for (Object o : world.b) {
            if (o instanceof OEntityBoat) {
                toRet.add(((OEntityBoat) o).boat);
            }
        }
        return toRet;
    }

    /**
     * Returns the list of all entities in the server in open chunks.
     *
     * @return list of entities
     */
    public List<BaseEntity> getEntityList() {
        List<BaseEntity> toRet = new ArrayList<BaseEntity>();

        for (Object o : world.b) {
            if (o instanceof OEntityMob || o instanceof OEntityGhast || o instanceof OEntityAnimal || o instanceof OEntitySlime || o instanceof OEntityEnderDragon || o instanceof OEntityLavaSlime || o instanceof OEntityVillager || o instanceof OEntitySquid || o instanceof OEntitySnowMan) {
                toRet.add(new Mob((OEntityLiving) o));
            } else if (o instanceof OEntityMinecart) {
                toRet.add(((OEntityMinecart) o).cart);
            } else if (o instanceof OEntityBoat) {
                toRet.add(((OEntityBoat) o).boat);
            } else if (o instanceof OEntityPlayerMP) {
                toRet.add(((OEntityPlayerMP) o).getPlayer());
            } else if (o instanceof OEntityItem) {
                toRet.add(((OEntityItem) o).item);
            }
        }
        return toRet;
    }
	
    /**
     * Returns the list of items in all open chunks.
     *
     * @return list of items
     */
    public List<ItemEntity> getItemList() {
        List<ItemEntity> toRet = new ArrayList<ItemEntity>();

        for (Object o : world.b) {
            if (o instanceof OEntityItem) {
                toRet.add(((OEntityItem) o).item);
            }
        }
        return toRet;
    }

    /**
     * Returns the list of all living entities (players, animals, mobs) in open
     * chunks.
     *
     * @return list of living entities
     */
    public List<LivingEntity> getLivingEntityList() {
        List<LivingEntity> toRet = new ArrayList<LivingEntity>();

        for (Object o : world.b) {
            if (o instanceof OEntityMob || o instanceof OEntityGhast || o instanceof OEntityAnimal || o instanceof OEntitySlime || o instanceof OEntityEnderDragon || o instanceof OEntityLavaSlime || o instanceof OEntityVillager || o instanceof OEntitySquid || o instanceof OEntitySnowMan) {
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
     * @return list of vehicles
     */
    public List<BaseVehicle> getVehicleEntityList() {
        List<BaseVehicle> toRet = new ArrayList<BaseVehicle>();

        for (Object o : world.b) {
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
     * @return Location object for spawn
     */
    public Location getSpawnLocation() {
        // More structure ftw
        OWorldInfo info = world.x;
        Location spawn = new Location();

        spawn.x = info.c() + 0.5D;
        spawn.y = world.f(info.c(), info.e()) + 1.5D;
        spawn.z = info.e() + 0.5D;
        spawn.rotX = 0.0F;
        spawn.rotY = 0.0F;
        return spawn;
    }

    /**
     * Sets the block
     *
     * @param block
     * @return
     */
    public boolean setBlock(Block block) {
        return setBlockAt(block.getType(), block.getX(), block.getY(), block.getZ()) && setBlockData(block.getX(), block.getY(), block.getZ(), block.getData());
    }

    /**
     * Returns the block at the specified location
     *
     * @param x
     * @param y
     * @param z
     * @return block
     */
    public Block getBlockAt(int x, int y, int z) {
        return new Block(this, getBlockIdAt(x, y, z), x, y, z, getBlockData(x, y, z));
    }

    /**
     * Returns the block data at the specified coordinates
     *
     * @param x
     *            x
     * @param y
     *            y
     * @param z
     *            z
     * @return block data
     */
    public int getBlockData(int x, int y, int z) {
        return world.c(x, y, z);
    }

    /**
     * Sets the block data at the specified coordinates
     *
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
    public boolean setBlockData(int x, int y, int z, int data) {
        boolean toRet = world.d(x, y, z, data);

        etc.getMCServer().h.a(new OPacket53BlockChange(x, y, z, world), getType().getId());
        ComplexBlock block = getComplexBlock(x, y, z);

        if (block != null) {
            block.update();
        }
        return toRet;
    }

    /**
     * Sets the block type at the specified location
     *
     * @param blockType
     * @param x
     * @param y
     * @param z
     * @return true if successful
     */
    public boolean setBlockAt(int blockType, int x, int y, int z) {
        return world.e(x, y, z, blockType);
    }

    /**
     * Returns the highest block Y
     *
     * @param x
     * @param z
     * @return highest block altitude
     */
    public int getHighestBlockY(int x, int z) {
        return world.e(x, z);
    }

    /**
     * Returns the block type at the specified location
     *
     * @param x
     * @param y
     * @param z
     * @return block type
     */
    public int getBlockIdAt(int x, int y, int z) {
        return world.a(x, y, z);
    }

    /**
     * Returns the complex block at the specified location. Null if there's no
     * complex block there. This will also find complex-blocks spanning multiple
     * spaces, such as double chests.
     *
     * @param block
     * @return complex block
     */
    public ComplexBlock getComplexBlock(Block block) {
        return getComplexBlock(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Returns the complex block at the specified location. Null if there's no
     * complex block there. This will also find complex-blocks spanning multiple
     * spaces, such as double chests.
     *
     * @param x
     *            x
     * @param y
     *            y
     * @param z
     *            z
     * @return complex block
     */
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
     * @param block
     * @return complex block
     */
    public ComplexBlock getOnlyComplexBlock(Block block) {
        return getOnlyComplexBlock(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Returns the complex block at the specified location. Null if there's no
     * complex block there.
     *
     * @param x
     *            x
     * @param y
     *            y
     * @param z
     *            z
     * @return complex block
     */
    public ComplexBlock getOnlyComplexBlock(int x, int y, int z) {
        OTileEntity localav = world.b(x, y, z);

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
            } else if (localav instanceof OTileEntityNote) {
                return new NoteBlock((OTileEntityNote) localav);
            } else if (localav instanceof OTileEntityBrewingStand) {
                return new BrewingStand((OTileEntityBrewingStand) localav);
            }
        }
        return null;
    }

    /**
     * Drops an item at the specified location
     *
     * @param loc
     * @param itemId
     * 
     * @return returns the ItemEntity that was dropped
     */
    public ItemEntity dropItem(Location loc, int itemId) {
        return dropItem(loc.x, loc.y, loc.z, itemId, 1, 0);
    }

    /**
     * Drops an item at the specified location
     *
     * @param x
     * @param y
     * @param z
     * @param itemId
     *
     * @return returns the ItemEntity that was dropped
     */
    public ItemEntity dropItem(double x, double y, double z, int itemId) {
        return dropItem(x, y, z, itemId, 1, 0);
    }

    /**
     * Drops an item with desired quantity at the specified location
     *
     * @param x
     * @param y
     * @param z
     * @param itemId
     * @param quantity
     * 
     * @return returns the ItemEntity that was dropped
     */
    public ItemEntity dropItem(double x, double y, double z, int itemId, int quantity) {
        return dropItem(x, y, z, itemId, quantity, 0);
    }

    /**
     * Drops an item with desired quantity at the specified location
     *
     * @param loc
     * @param itemId
     * @param quantity
     * 
     * @return returns the ItemEntity that was dropped
     */
    public ItemEntity dropItem(Location loc, int itemId, int quantity) {
        return dropItem(loc.x, loc.y, loc.z, itemId, quantity, 0);
    }

    /**
     * Drops an item with damage data and desired quantity at the specified location
     *
     * @param loc
     * @param itemId
     * @param quantity
     * @param damage
     * 
     * @return returns the ItemEntity that was dropped
     */
    public ItemEntity dropItem(Location loc, int itemId, int quantity, int damage) {
        return dropItem(loc.x, loc.y, loc.z, itemId, quantity, damage);
    }

    /**
     * Drops an item with desired quantity and damage value at the specified location
     *
     * @param x
     * @param y
     * @param z
     * @param itemId
     * @param quantity
     * 
     * @return returns the ItemEntity that was dropped
     */
    public ItemEntity dropItem(double x, double y, double z, int itemId, int quantity, int damage) {
        double d1 = world.r.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double d2 = world.r.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double d3 = world.r.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;

        OEntityItem oei = new OEntityItem(world, x + d1, y + d2, z + d3, new OItemStack(itemId, quantity, damage));

        oei.c = 10;
        world.b(oei);
        return oei.item;
    }

    /**
     * Forces the server to update the physics for blocks around the given block
     *
     * @param block
     *            the block that changed
     */
    public void updateBlockPhysics(Block block) {
        updateBlockPhysics(block.getX(), block.getY(), block.getZ(), block.getData());
    }

    /**
     * Forces the server to update the physics for blocks around the given block
     *
     * @param x
     *            the X coordinate of the block
     * @param y
     *            the Y coordinate of the block
     * @param z
     *            the Z coordinate of the block
     * @param data
     *            the new data for the block
     */
    public void updateBlockPhysics(int x, int y, int z, int data) {
        world.c(x, y, z, data);
    }

    /**
     * Checks to see whether or not the chunk containing the given block is
     * loaded into memory.
     *
     * @param block
     *            the Block to check
     * @return true if the chunk is loaded
     */
    public boolean isChunkLoaded(Block block) {
        return isChunkLoaded(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Checks to see whether or not the chunk containing the given block
     * coordinates is loaded into memory.
     *
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return true if the chunk is loaded
     */
    public boolean isChunkLoaded(int x, int y, int z) {
        return isChunkLoaded(x >> 4, z >> 4);
    }
    
    /**
     * Checks to see whether or not the chunk containing the given chunk
     * coordinates is loaded into memory.
     *
     * @param x
     *            a block x-coordinate
     * @param z
     *            a block z-coordinate
     * @return true if the chunk is loaded
     */
    public boolean isChunkLoaded(int x, int z) {
        return world.G.a(x, z);
    }

    /**
     * Loads the chunk containing the given block. If the chunk does not exist,
     * it will be generated.
     *
     * @param block
     *            the Block to check
     * @return chunk
     */
    public Chunk loadChunk(Block block) {
        return loadChunk(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Loads the chunk containing the given block coordinates. If the chunk does
     * not exist, it will be generated.
     *
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return chunk
     */
    public Chunk loadChunk(int x, int y, int z) {
        return loadChunk(x >> 4, z >> 4);
    }

    /**
     * Loads the chunk containing the given chunk coordinates. If the chunk does
     * not exist, it will be generated.
     *
     * @param x
     *            a chunk x-coordinate
     * @param z
     *            a chunk z-coordinate
     * @return chunk
     */
    public Chunk loadChunk(int x, int z) {
        return world.v.c(x, z).chunk;
    }

    /**
     * Gets the chunk containing the given block. If the chunk is not loaded,
     * the result will be null.
     *
     * @param block
     *            the Block to check
     * @return chunk
     */
    public Chunk getChunk(Block block) {
        return getChunk(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Gets the chunk containing the given block coordinates. If the chunk is not loaded,
     * the result will be null.
     *
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return chunk
     */
    public Chunk getChunk(int x, int y, int z) {
        return getChunk(x >> 4, z >> 4);
    }

    /**
     * Gets the chunk containing the given chunk coordinates. If the chunk is not loaded,
     * the result will be null.
     *
     * @param x
     *            a chunk x-coordinate
     * @param z
     *            a chunk z-coordinate
     * @return chunk
     */
    public Chunk getChunk(int x, int z) {
        if (isChunkLoaded(x, z)) {
            return world.G.b(x, z).chunk;
        } else {
            return null;
        }
    }

    /**
     * Checks if the provided block is being powered through redstone
     *
     * @param block
     *            Block to check
     * @return true if the block is being powered
     */
    public boolean isBlockPowered(Block block) {
        return isBlockPowered(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Checks if the provided block is being powered through redstone
     *
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return true if the block is being powered
     */
    public boolean isBlockPowered(int x, int y, int z) {
        return world.t(x, y, z);
    }

    /**
     * Checks if the provided block is being indirectly powered through redstone
     *
     * @param block
     *            Block to check
     * @return true if the block is being indirectly powered
     */
    public boolean isBlockIndirectlyPowered(Block block) {
        return isBlockIndirectlyPowered(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Checks if the provided block is being indirectly powered through redstone
     *
     * @param x
     *            a block x-coordinate
     * @param y
     *            a block y-coordinate
     * @param z
     *            a block z-coordinate
     * @return true if the block is being indirectly powered
     */
    public boolean isBlockIndirectlyPowered(int x, int y, int z) {
        return world.u(x, y, z);
    }

    /**
     * Set the thunder state
     * @param thundering whether it should thunder
     */
    public void setThundering(boolean thundering) {
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.THUNDER_CHANGE, this, thundering)) {
            return;
        }
        world.x.a(thundering);

        // Thanks to Bukkit for figuring out these numbers
        if (thundering) {
            setThunderTime(world.r.nextInt(12000) + 3600);
        } else {
            setThunderTime(world.r.nextInt(168000) + 12000);
        }
    }

    /**
     * Set the thunder ticks.
     * @param ticks ticks of thunder
     */
    public void setThunderTime(int ticks) {
        world.x.b(ticks);
    }

    /**
     * Sets the rain state.
     * @param raining whether it should rain
     */
    public void setRaining(boolean raining) {
        if ((Boolean) etc.getLoader().callHook(PluginLoader.Hook.WEATHER_CHANGE, this, raining)) {
            return;
        }
        world.x.b(raining);

        // Thanks to Bukkit for figuring out these numbers
        if (raining) {
            setRainTime(world.r.nextInt(12000) + 3600);
        } else {
            setRainTime(world.r.nextInt(168000) + 12000);
        }
    }

    /**
     * Sets the rain ticks.
     * @param ticks ticks of rain
     */
    public void setRainTime(int ticks) {
        world.x.c(ticks);
    }

    /**
     * Returns whether it's thundering
     * @return whether it's thundering
     */
    public boolean isThundering() {
        return world.x.i();
    }

    /**
     * Returns the number of ticks to go till the end of the thunder
     * @return the thunder ticks
     */
    public int getThunderTime() {
        return world.x.j();
    }

    /**
     * Returns whether it's raining
     * @return whether it's raining
     */
    public boolean isRaining() {
        return world.x.k();
    }

    /**
     * Returns the number of ticks to go till the end of the rain
     * @return the rain ticks
     */
    public int getRainTime() {
        return world.x.l();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj instanceof World && ((World) obj).world == world;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 89 * hash + (this.world != null ? this.world.hashCode() : 0);
        return hash;
    }

    /**
     * Creates an explosion with the specified power at the given location.
     * @param exploder The entity causing the explosion.
     * @param x
     * @param y
     * @param z
     * @param power The power of the explosion. TNT has a power of 4.
     */
    public void explode(BaseEntity exploder, double x, double y, double z, float power) {
        world.a(exploder.entity, x, y, z, power);
    }
    
    /**
     * Gets the random seed of the world.
     * @return seed of the world
     */
    public long getRandomSeed() {
        return world.n();
    }
    
    /**
     * Sets a new light level with the specified level at the given location.
     * @param x
     * @param y
     * @param z
     * @param newlevel The light level.
     */
    public void setLightLevel(int x, int y, int z, int newlevel){
      this.getWorld().a(OEnumSkyBlock.b, x, y, z, newlevel);
    }
   
    /**
     * Gets the light level of the given location.
     * @param x
     * @param y
     * @param z
     * @return Light level of the location.
     */
    public float getLightLevel(int x, int y, int z){
        return this.getWorld().m(x, y, z);
    }
    /**
     * Updates the light around the given location.
     * @param x
     * @param y
     * @param z 
     */
    public void updateLight(int x, int y, int z){
        for(int x2 = x-2; x2 <= x+2; x2++){
			for(int y2 = y-2; y2 <= y+2; y2++){
				for(int z2 = z-2; z2 <= z+2; z2++){
				this.getWorld().b(x2,y2,z2,this.getWorld().a(x2,y2,z2),this.getWorld().c(x2,y2,z2));	
				}
			}
		}
    }

}
