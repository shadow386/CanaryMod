public class DoubleChest extends ItemArray<OInventoryLargeChest> implements ComplexBlock, Inventory {
    private final Block block;
    private String      name = "Large Chest";

    public DoubleChest(OInventoryLargeChest chest) {
        super(chest);
        block = chest.getChestBlock();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String value) {
        name = value;
    }

    @Override
    public int getX() {
        return block.getX();
    }

    @Override
    public int getY() {
        return block.getY();
    }

    @Override
    public int getZ() {
        return block.getZ();
    }

    @Override
    public void update() {
        container.H_();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public World getWorld() {
        return block.getWorld();
    }

}
