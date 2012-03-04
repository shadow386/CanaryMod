/**
 * BaseEntity.java - Class for accessing things that all entities share - W, X,
 * Y, health.
 * 
 * @author James
 */
public class BaseEntity {
    OEntity entity;

    /**
     * Creates an interface for an entity
     * 
     * @param entity
     */
    public BaseEntity(OEntity entity) {
        this.entity = entity;
    }

    /**
     * Interface for entities.
     */
    public BaseEntity() {}

    /**
     * Returns the ID for this mob
     * 
     * @return id
     */
    public int getId() {
        return entity.bd;
    }

    /**
     * Teleports to the provided location
     * 
     * @param x
     * @param rotation
     * @param y
     * @param z
     * @param pitch
     */
    public void teleportTo(double x, double y, double z, float rotation, float pitch) {
        entity.b(x, y, z, rotation, pitch);
    }

    /**
     * Teleports to the other entity
     * 
     * @param ent
     *            entity to teleport to
     */
    public void teleportTo(BaseEntity ent) {
        teleportTo(ent.getX(), ent.getY(), ent.getZ(), ent.getRotation(), ent.getPitch());
    }

    /**
     * Teleports to the provided location
     * 
     * @param location
     *            location to teleport to
     */
    public void teleportTo(Location location) {
        teleportTo(location.x, location.y, location.z, location.rotX, location.rotY);
    }

    /**
     * Returns the entity's W
     * 
     * @return x
     */
    public double getX() {
        return entity.bm;
    }

    /**
     * Sets the entity's W
     * 
     * @param x
     *            x to set
     */
    public void setX(double x) {
        teleportTo(x, getY(), getZ(), getRotation(), getPitch());
    }

    /**
     * Returns the entity's X
     * 
     * @return y
     */
    public double getY() {
        return entity.bn;
    }

    /**
     * Sets the entity's X
     * 
     * @param y
     *            y to set
     */
    public void setY(double y) {
        teleportTo(getX(), y, getZ(), getRotation(), getPitch());
    }

    /**
     * Returns the entity's Y
     * 
     * @return z
     */
    public double getZ() {
        return entity.bo;
    }

    /**
     * Sets the entity's Y
     * 
     * @param z
     *            z to set
     */
    public void setZ(double z) {
        teleportTo(getX(), getY(), z, getRotation(), getPitch());
    }

    /**
     * Returns the entity's pitch
     * 
     * @return pitch
     */
    public float getPitch() {
        return entity.bt;
    }

    /**
     * Sets the entity's pitch
     * 
     * @param pitch
     *            pitch to set
     */
    public void setPitch(float pitch) {
        teleportTo(getX(), getY(), getZ(), getRotation(), pitch);
    }

    /**
     * Returns the entity's rotation
     * 
     * @return rotation
     */
    public float getRotation() {
        return entity.bs;
    }

    /**
     * Sets the entity's rotation
     * 
     * @param rotation
     *            rotation to set
     */
    public void setRotation(float rotation) {
        teleportTo(getX(), getY(), getZ(), rotation, getPitch());
    }

    /**
     * Returns the entity we're wrapping.
     * 
     * @return
     */
    public OEntity getEntity() {
        return entity;
    }

    /**
     * Returns whether or not this entity is a mob
     * 
     * @return true if mob
     */
    public boolean isMob() {
        return entity instanceof OIMob;
    }

    public static boolean isMob(OEntity entity) {
        return entity instanceof OIMob;
    }

    /**
     * Returns whether or not this entity is an animal
     * 
     * @return true if animal
     */
    public boolean isAnimal() {
        return entity instanceof OIAnimals;
    }

    public static boolean isAnimal(OEntity entity) {
        return entity instanceof OIAnimals;
    }

    /**
     * Returns true if this entity is a player
     * 
     * @return true if player
     */
    public boolean isPlayer() {
        return entity instanceof OEntityPlayerMP;
    }

    public static boolean isPlayer(OEntity entity) {
        return entity instanceof OEntityPlayerMP;
    }

    /**
     * Returns whether or not this entity is alive
     * 
     * @return true if living entity
     */
    public boolean isLiving() {
        return entity instanceof OEntityLiving;
    }

    public static boolean isLiving(OEntity entity) {
        return entity instanceof OEntityLiving;
    }

    /**
     * Returns whether or not this entity is an item entity
     * 
     * @return true if item entity
     */
    public boolean isItem() {
        return entity instanceof OEntityItem;
    }

    public static boolean isItem(OEntity entity) {
        return entity instanceof OEntityItem;
    }

    /**
     * Returns the player for this entity
     * 
     * @return player
     */
    public Player getPlayer() {
        if (!isPlayer()) {
            return null;
        }

        OEntityPlayerMP p = (OEntityPlayerMP) entity;

        return p.getPlayer();
    }

    /**
     * Get the default amount of AirTicks for this entity 20 ticks per second.
     * 
     * @return 300
     * @deprecated It doesn't exist anymore P:
     */
    @Deprecated
    public int getBaseAirTicks() {
        return 300;
    }

    /**
     * Set the default amount of AirTicks for this entity 20 ticks per second.
     * 
     * @param ticks
     * @deprecated It doesn't exist anymore
     * @throws UnsupportedOperationException because it doesn't exist anymore
     */
    @Deprecated
    public void setBaseAirTicks(int ticks) {
        throw new UnsupportedOperationException("BaseAirTicks has been abolished by Notch.");
    }

    /**
     * Get the current NoDamageTicks for this entity.
     * 
     * This gets lowered every game tick, until its smaller than half the
     * BaseNoDamageTicks it only registers any damage more than
     * {@link LivingEntity#getLastDamage()}. 20 ticks per second.
     * 
     * @return
     */
    public int getNoDamageTicks() {
        return getEntity().bW;
    }

    /**
     * Set the current NoDamageTicks for this entity.
     * 
     * This gets lowered every game tick, until its smaller than half the
     * BaseNoDamageTicks it only registers any damage more than
     * {@link LivingEntity#getLastDamage()}. 20 ticks per second.
     * 
     * @param ticks
     */
    public void setNoDamageTicks(int ticks) {
        getEntity().bW = ticks;
    }

    /**
     * Get the amount of AirTicks left.
     * 
     * This gets lowered every game tick when you are under water. 20 ticks per
     * second.
     * 
     * @return
     */
    public int getAirTicks() {
        return getEntity().aZ();
    }

    /**
     * Set the amount of AirTicks left.
     * 
     * This gets lowered every game tick when you are under water. 20 ticks per
     * second.
     * 
     * @param ticks the number of ticks you have air
     */
    public void setAirTicks(int ticks) {
        getEntity().k(ticks);
    }

    /**
     * Get the amount of FireTicks left.
     * 
     * This gets lowered every game tick when you are on fire. 20 ticks per
     * second.
     * 
     * @return
     */
    public int getFireTicks() {
        return getEntity().c;
    }

    /**
     * Set the amount of FireTicks left.
     * 
     * This gets lowered every game tick when you are on fire. 20 ticks per
     * second.
     * 
     * @param ticks the amount of fire ticks
     */
    public void setFireTicks(int ticks) {
        getEntity().c = ticks;
    }

    /**
     * Gets the World object from this entity.
     * @return the World this entity is in
     */
    public World getWorld() {
        return getEntity().bi.world;
    }

    /**
     * Returns the x-motion of this entity
     *
     * @return x-motion
     */
    public double getMotionX() {
        return entity.bp;
    }

    /**
     * Returns the y-motion of this entity
     *
     * @return y-motion
     */
    public double getMotionY() {
        return entity.bq;
    }

    /**
     * Returns the z-motion of this wntity
     *
     * @return z-motion
     */
    public double getMotionZ() {
        return entity.br;
    }

    /**
     * Set entity motion
     *
     * @param motionX
     * @param motionY
     * @param motionZ
     */
    public void setMotion(double motionX, double motionY, double motionZ) {
        setMotionX(motionX);
        setMotionY(motionY);
        setMotionZ(motionZ);
    }

    /**
     * Sets the x-motion of this entity
     *
     * @param motion
     * motion to set
     */
    public void setMotionX(double motion) {
        entity.bp = motion;
        entity.bA = true;
    }

    /**
     * Sets the y-motion of this entity
     *
     * @param motion
     * motion to set
     */
    public void setMotionY(double motion) {
        entity.bq = motion;
        entity.bA = true;
    }

    /**
     * Sets the z-motion of this entity
     *
     * @param motion
     * motion to set
     */
    public void setMotionZ(double motion) {
        entity.br = motion;
        entity.bA = true;
    }

    /**
     * Destroys this entity
     */
    public void destroy() {
        entity.W();
    }

    /**
     * Returns this mob's name
     *
     * @return name
     */
    public String getName() {
        return OEntityList.b(entity);
    }

    /**
     * Returns whether this entity is sprinting.
     * @return the sprinting state
     */
    public boolean getSprinting() {
        return entity.aY();
    }

    /**
     * Set whether this entity is sprinting.
     * Note: for players, this may not make them go faster.
     * @param sprinting 
     */
    public void setSprinting(boolean sprinting) {
        entity.g(sprinting);
    }

}
