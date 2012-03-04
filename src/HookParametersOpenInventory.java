public class HookParametersOpenInventory extends HookParameters {
    private Player player;
    private Inventory inventory;
    private boolean isSilenced;
    
    public HookParametersOpenInventory(Player player, Inventory inventory, boolean isSilenced)
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
    
    public void setSilenced(boolean newSilenced)
    {
        this.isSilenced = newSilenced;
    }
}
