/**
 * BaseVehicle - Base class for interfacing boats and minecarts
 * 
 * @author James
 */
public class BaseVehicle extends BaseEntity {

    /**
     * Creates an interface for a vehicle
     * 
     * @param entity
     */
    public BaseVehicle(OEntity entity) {
        this.entity = entity;
    }

    /**
     * Interface for vehicles.
     */
    public BaseVehicle() {}

    /**
     * Checks if this vehicle is empty (unoccupied)
     * 
     * @return true if unoccupied.
     */
    public boolean isEmpty() {
        if (entity.bg == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the passenger. If there is no passenger this function returns
     * null.
     * 
     * @return passenger
     */
    public Player getPassenger() {
        if (entity.bg != null) {
            if (isPlayer(entity.bg)) {
                return ((OEntityPlayerMP) entity.bg).getPlayer();
            }
        }

        return null;
    }
}
