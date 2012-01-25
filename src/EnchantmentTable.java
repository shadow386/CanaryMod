/**
 * An interface class to Enchantment Tables.
 * TODO: add hooks.
 * @author willem
 */
public class EnchantmentTable extends ItemArray<OInventoryBasic> implements Inventory {
    private final OContainerEnchantment enchantTable;
    
    public EnchantmentTable(OContainerEnchantment block) {
        super(block.a);
        enchantTable = block;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported. Please use update(Player player)");
        // Same as workbench: ugly, but we kinda need it. :(
    }
    
    public void update(Player player) {
        enchantTable.a((OEntityPlayer) player.getEntity());
    }

    @Override
    public String getName() {
        return container.getName();
    }

    @Override
    public void setName(String value) {
        container.setName(value);
    }
    
}
