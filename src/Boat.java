/**
 * Boat - Used for manipulating boats
 * 
 * @author James
 */
public class Boat extends BaseVehicle {

    /**
     * Interface for boats.
     * 
     * @param boat
     */
    public Boat(OEntityBoat boat) {
        super(boat);
    }

    /**
     * Create a new Boat at the given position.
     * 
     * @param x
     * @param y
     * @param z
     * @deprecated Use {@link #Boat(World, double, double, double)} instead.
     */
    @Deprecated
    public Boat(double x, double y, double z) {
        super(new OEntityBoat(etc.getMCServer().a(0), x, y, z));
        etc.getMCServer().a(0).b(entity);
    }
    
    /**
     * Create a new Boat at the given position.
     * 
     * @param world The world for the new boat
     * @param x The x coordinate for the new boat
     * @param y The y coordinate for the new boat
     * @param z The z coordinate for the new boat
     */
    public Boat(World world, double x, double y, double z) {
        super(new OEntityBoat(world.getWorld(), x, y, z));
        world.getWorld().b(entity);
    }

    /**
     * Returns the entity we're wrapping.
     * 
     * @return
     */
    @Override
    public OEntityBoat getEntity() {
        return (OEntityBoat) entity;
    }
}
