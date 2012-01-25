

/**
 * Mob.java - Interface for mobs
 * 
 * @author James
 */
public class Mob extends LivingEntity {

    /**
     * Creates a mob interface
     * 
     * @param locallb
     *            name of mob
     */
    public Mob(OEntityLiving locallb) {
        super(locallb);
        
    }

    /**
     * Creates a mob interface
     * 
     * @param mob
     *            name of mob
     * @deprecated Use {@link #Mob(java.lang.String, World)} instead.
     */
    public Mob(String mob) {
        this((OEntityLiving) OEntityList.a(mob, etc.getMCServer().a(0)));
    }
    
    /**
     * Creates a mob interface
     * @param mob name of the mob
     * @param world World for the mob
     */
    public Mob(String mob, World world) {
        this((OEntityLiving) OEntityList.a(mob, world.getWorld()));
    }

    /**
     * Creates a mob interface
     * 
     * @param mobName
     *            name of mob
     * @param location
     *            location of mob
     */
    public Mob(String mobName, Location location) {
        this(mobName,  location.getWorld());
        teleportTo(location);
    }

    /**
     * Spawns this mob
     */
    public void spawn() {
        spawn(null);
    }

    /**
     * Spawns this mob with a rider
     * 
     * @param rider
     */
    public void spawn(Mob rider) {
        OWorld world = etc.getMCServer().a(0);

        entity.c(getX() + 0.5d, getY(), getZ() + 0.5d, getRotation(), 0f);
        world.b(entity);

        if (rider != null) {
            OEntityLiving mob2 = rider.getMob();

            mob2.c(getX(), getY(), getZ(), getRotation(), 0f);
            world.b(mob2);
            mob2.a(entity);
        }
    }

    /**
     * Returns this mob's name
     * 
     * @return name
     */
    @Override
    public String getName() {
        return OEntityList.b(entity);
    }

    /**
     * Returns the current target of the mob
     * 
     * @return OEntity
     */
    public OEntity getTarget() {
        if (getEntity() instanceof OEntityGhast) {
            OEntityGhast var1 = (OEntityGhast) getEntity();

            return var1.getTarget();
        }
        return ((OEntityCreature) getEntity()).d;
    }
    
    /**
     * Sets the mobs target
     * 
     * @param target the entity to target
     */
    public void setTarget(OEntity target) {
        if (getEntity() instanceof OEntityGhast) {
            OEntityGhast var1 = (OEntityGhast) getEntity();

            var1.setTarget(target);
            return;
        }
        ((OEntityCreature) getEntity()).d = target; 
    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        if (health <= 0) {
            dropLoot();
        }
    }

    /**
     * Returns the actual mob
     * 
     * @return
     */
    public OEntityLiving getMob() {
        return getEntity();
    }

    /**
     * Checks to see if the mob is a valid mob
     * 
     * @param mob
     *            the mob to check
     * @return true of mob is valid
     */
    public static boolean isValid(String mob) {
        if (mob == null) {
            return false;
        }
        OEntity c = OEntityList.a(mob, etc.getServer().getWorld(0).getWorld());

        return c instanceof OIMob || c instanceof OIAnimals;
    }
	
    /**
     * Returns Mob location
     * @return this mob's location
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

}
