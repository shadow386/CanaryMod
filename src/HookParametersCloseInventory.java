public class HookParametersCloseInventory extends HookParameters {
    private Player player;
    private Inventory inventory;
    private boolean isSilenced;
    
    public HookParametersCloseInventory(Player player, Inventory inventory, boolean isSilenced)
    {
        this.player = player;
        this.inventory = inventory;
        this.isSilenced = isSilenced;
    }
    
    public Player getPlayer()
    {
        return this.player;
    }
    
    public Inventory getInventory()
    {
        return this.inventory;
    }
    
    public boolean isSilenced()
    {
        return this.isSilenced;
    }
}
