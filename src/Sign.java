

/**
 * Sign.java - Interface to signs
 * 
 * @author James
 */
public class Sign implements ComplexBlock {

    private OTileEntitySign sign;

    /**
     * Creates a sign interface
     * 
     * @param localSign
     */
    public Sign(OTileEntitySign localSign) {
        sign = localSign;
    }

    /**
     * Sets the line of text at specified index
     * 
     * @param index
     *            line
     * @param text
     *            text
     */
    public void setText(int index, String text) {
        if (index >= 0 && sign.a.length > index) {
            sign.a[index] = text;
        }
    }

    /**
     * Returns the line of text
     * 
     * @param index
     *            line of text
     * @return text
     */
    public String getText(int index) {
        if (index >= 0 && sign.a.length > index) {
            return sign.a[index];
        }
        return "";
    }

    public int getX() {
        return sign.l;
    }

    public int getY() {
        return sign.m;
    }

    public int getZ() {
        return sign.n;
    }

    public Block getBlock() {
        return getWorld().getBlockAt(getX(), getY(), getZ());
    }

    public World getWorld() {
        return this.sign.k.world;
    }

    public void update() {
        getWorld().getWorld().h(getX(), getY(), getZ());
    }

    /**
     * Returns a String value representing this Block
     * 
     * @return String representation of this block
     */
    @Override
    public String toString() {
        return String.format("Sign [x=%d, y=%d, z=%d]", getX(), getY(), getZ());
    }

    /**
     * Tests the given object to see if it equals this object
     * 
     * @param obj
     *            the object to test
     * @return true if the two objects match
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sign other = (Sign) obj;

        if (getX() != other.getX()) {
            return false;
        }
        if (getY() != other.getY()) {
            return false;
        }
        if (getZ() != other.getZ()) {
            return false;
        }
        return true;
    }

    /**
     * Returns a semi-unique hashcode for this block
     * 
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;

        hash = 97 * hash + getX();
        hash = 97 * hash + getY();
        hash = 97 * hash + getZ();
        return hash;
    }
}
