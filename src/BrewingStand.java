/**
 * Interface to brewing stands.
 * TODO: add hooks (e.g. getBrewTime)
 * @author willem
 */
public class BrewingStand extends BaseContainerBlock<OTileEntityBrewingStand> implements ComplexBlock {

    public BrewingStand(OTileEntityBrewingStand brewingStand) {
        super(brewingStand, "Brewing Stand");
    }
    
}
