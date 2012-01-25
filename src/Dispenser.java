/**
 * 
 * @author Meaglin
 */
public class Dispenser extends BaseContainerBlock implements ComplexBlock {

    public Dispenser(OTileEntityDispenser disp) {
        super(disp, "Trap");
    }
    
    public void fire() {
        OWorld oworld = this.getWorld().getWorld();
        
        // OBlock.R = OBlockDispenser, oworld.w = Random
        OBlock.R.a(oworld, this.getX(), this.getY(), this.getZ(), oworld.w);
    }
}
