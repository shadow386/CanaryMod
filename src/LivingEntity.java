/**
 * Interface for living entities
 * 
 * @author
 */
public class LivingEntity extends BaseEntity {

    /**
     * Interface for living entities
     */
    public LivingEntity() {}

    /**
     * Interface for living entities
     * 
     * @param livingEntity
     */
    public LivingEntity(OEntityLiving livingEntity) {
        super(livingEntity);
    }

    /**
     * Returns the entity we're wrapping.
     * 
     * @return
     */
    @Override
    public OEntityLiving getEntity() {
        return (OEntityLiving) entity;
    }

    /**
     * Returns the entity's health.
     * 
     * @return health
     */
    public int getHealth() {
        return getEntity().ap;
    }

    /**
     * Increase entity health.
     * 
     * @param health
     *            amount of health to increase the players health with.
     */
    public void increaseHealth(int health) {
        getEntity().d(health);
    }

    /**
     * Sets the entity's health. 20 = max health 1 = 1/2 heart 2 = 1 heart
     * 
     * @param health
     */
    public void setHealth(int health) {
        if (health < -1) {
            health = -1;
        }
        if (health > 20) {
            health = 20;
        }
        getEntity().ap = health;
    }

    /**
     * Get the amount of ticks this entity is dead. 20 ticks per second.
     * 
     * @return
     */
    public int getDeathTicks() {
        return getEntity().av;
    }

    /**
     * Set the amount of ticks this entity is dead. 20 ticks per second.
     * 
     * @param ticks
     */
    public void setDeathTicks(int ticks) {
        getEntity().av = ticks;
    }

    /**
     * Get the amount of ticks this entity will not take damage. (unless it
     * heals) 20 ticks per second.
     * 
     * @return
     */
    public int getBaseNoDamageTicks() {
        return getEntity().S;
    }

    /**
     * Set the amount of ticks this entity will not take damage. (until it
     * heals) 20 ticks per second.
     * 
     * @param ticks
     */
    public void setBaseNoDamageTicks(int ticks) {
        getEntity().S = ticks;
    }

    /**
     * Get the current maximum damage taken during this NoDamageTime
     * 
     * @return
     */
    public int getLastDamage() {
        return getEntity().aU;
    }

    /**
     * Set the current maximum damage taken during this NoDamageTime (if any
     * damage is higher than this number the difference will be added)
     * 
     * @param amount
     */
    public void setLastDamage(int amount) {
        getEntity().aU = amount;
    }
    
    /**
     * Drops this mob's loot. Automatically called if health is set to 0.
     */
    public void dropLoot() {
        getEntity().a(true, 0);
    }
    
    /**
     * Gets the entity's mob spawner.
     * @return MobSpawner of the entity, or null if it wasn't spawned with a mob spawner.
     */
    public MobSpawner getSpawner() {
        return getEntity().spawner;
    }
}
