import java.util.ArrayList;
import java.util.List;

/**
 * HookParametersEnchant - Passed to the ENCHANT hook. Contains the enchanting player, enchanted item and a list of enchantments to apply on it.
 * Changing the list will change the enchantments applied.
 * 
 * @author YLivay
 */
public class HookParametersEnchant extends HookParameters {
   
    private Player player;
    private int itemId;
    private List<Enchantment> enchantments;
    
    private boolean isCanceled;
    
    /**
     * Creates an instance of HookParametersEnchant
     * @param player The enchanting player
     * @param itemId The enchanted item ID
     * @param enchantments A list of OEnchantmentData objects
     */
    public HookParametersEnchant(Player player, int itemId, List enchantments)
    {
        this.player = player;
        this.itemId = itemId;
        this.enchantments = new ArrayList<Enchantment>();
        for (Object enchantObject : enchantments)
        {
           if (enchantObject instanceof OEnchantmentData)
           {
              this.enchantments.add(new Enchantment(Enchantment.Type.fromId(((OEnchantmentData)enchantObject).a.x), ((OEnchantmentData)enchantObject).b));
           }
        }
        this.isCanceled = false;
    }
    
    /**
     * Gets the enchanting player
     * @return
     */
    public Player getPlayer()
    {
        return this.player;
    }
    
    /**
     * Gets the enchanted item id.
     * @return
     */
    public int getItemId()
    {
        return this.itemId;
    }
    
    /**
     * Gets a list of enchantments to apply on the item.
     * Changing this list will change the enchantments that will be applied.
     * @return
     */
    public List<Enchantment> getEnchantments()
    {
        return this.enchantments;
    }
    
    /**
     * Validates the enchantment list
     * @param checkStackable Check if enchantments stack legally
     * @return true if valid
     */
    public boolean isValid(boolean checkStackable)
    {
        Enchantment[] enchantmentsArray = this.enchantments.toArray(new Enchantment[this.enchantments.size()]);
        for (int i = 0; i < enchantmentsArray.length; i += 1)
        {
            if (enchantmentsArray[i].isValid())
            {
                for (int j = i + 1; j < enchantmentsArray.length; j += 1)
                {
                    if (enchantmentsArray[j].isValid())
                    {
                        if (checkStackable && !enchantmentsArray[i].canStack(enchantmentsArray[j]))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            else
            {
                return false;
            }
        }
        return enchantmentsArray.length > 0;
    }
    
    /**
     * See if enchantment is cancelled
     * @return
     */
    public boolean isCanceled()
    {
       return this.isCanceled;
    }
    
    /**
     * Cancels the enchantment
     */
    public void cancel()
    {
       this.isCanceled = true;
    }
}
