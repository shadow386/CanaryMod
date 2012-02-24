
public class ItemEntity extends BaseEntity {

    /**
     * Constructor
     */
    public ItemEntity() {}

    /**
     * Constructor
     * 
     * @param item
     */
    public ItemEntity(OEntityItem item) {
        super(item);
    }

    /**
     * Returns the entity we're wrapping.
     * 
     * @return
     */
    @Override
    public OEntityItem getEntity() {
        return (OEntityItem) entity;
    }

    public void setAge(int age) {
        getEntity().b = age;
    }

    public int getAge() {
        return getEntity().b;
    }

    public void setDelayBeforeCanPickUp(int time) {
        getEntity().c = time;
    }

    public int getDelayBeforeCanPickUp() {
        return getEntity().c;
    }

    public Item getItem() {
        return new Item(getEntity().a);
    }

    @Override
    public World getWorld() {
        return getEntity().bi.world;
    }

}
