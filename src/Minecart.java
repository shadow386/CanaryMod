import java.util.HashMap;
import java.util.Map;


/**
 * Minecart - Used for manipulating minecarts
 * 
 * @author tw1nk
 */
public class Minecart extends BaseVehicle {

    /**
     * Type of minecart
     */
    public enum Type {

        /**
         * Base minecart.
         */
        Minecart(0),
        /**
         * Storage minecart. Has storage for items.
         */
        StorageCart(1),
        /**
         * Powered minecart. Has storage for fuel.
         */
        PoweredMinecart(2);

        private final int                       id;
        private static final Map<Integer, Type> lookup = new HashMap<Integer, Type>();

        static {
            for (Type t : Type.values()) {
                lookup.put(t.getType(), t);
            }
        }

        private Type(int id) {
            this.id = id;
        }

        public int getType() {
            return id;
        }

        public static Type fromId(final int type) {
            return lookup.get(type);
        }
    }

    /**
     * Creates an interface for minecart.
     * 
     * @param o
     */
    public Minecart(OEntityMinecart o) {
        super(o);
    }

    /**
     * Create a new Minecart at the given position
     * 
     * @param x
     * @param y
     * @param z
     * @param type
     *            0=Minecart, 1=StorageCart, 2=PoweredMinecart
     * @deprecated Use {@link #Minecart(World, double, double, double, Minecart.Type)} instead.
     */
    @Deprecated
    public Minecart(double x, double y, double z, Type type) {
        super(new OEntityMinecart(etc.getMCServer().a(0), x, y, z, type.getType()));
        etc.getMCServer().a(0).b(entity);
    }

    /**
     * Create a new Minecart at the given position
     * 
     * @param world The world for the new minecart
     * @param x The x coordinate for the new minecart
     * @param y The y coordinate for the new minecart
     * @param z The z coordinate for the new minecart
     * @param type The type for the new minecart
     * 
     */
    public Minecart(World world, double x, double y, double z, Type type) {
        super(new OEntityMinecart(world.getWorld(), x, y, z, type.getType()));
        world.getWorld().b(entity);
    }

    /**
     * Returns the entity we're wrapping.
     * 
     * @return
     */
    @Override
    public OEntityMinecart getEntity() {
        return (OEntityMinecart) entity;
    }

    /**
     * Set damage on Mineentity
     * 
     * @param damage
     *            over 40 and you "kill" the mineentity
     */
    public void setDamage(int damage) {
        getEntity().b(damage);
    }

    /**
     * Returns damage for mineentity
     * 
     * @return returns current damage
     */
    public int getDamage() {
        return getEntity().k();
    }

    /**
     * Returns the type of this minecart.
     * 
     * @return type
     */
    public Type getType() {
        return Type.fromId(getEntity().a);
    }

    /**
     * Returns the storage for this minecart. Returns null if minecart is not a
     * storage or powered minecart.
     * 
     * @return storage
     */
    public StorageMinecart getStorage() {
        if (getType() == Type.StorageCart || getType() == Type.PoweredMinecart) {
            return new StorageMinecart(getEntity());
        }
        return null;
    }
}
