/**
 * HookParamtersConnect.java - send/receive parameters from hooks
 * 
 * @author James
 */

public class HookParametersConnect extends HookParameters {
    private String joinMessage;
    private boolean applyPotionsEffects;
    private boolean Hidden = false;

    public boolean isHidden() {
        return Hidden;
    }

    public void setHidden(boolean hidden) {
        Hidden = hidden;
    }

    public boolean applyPotionsEffects() {
        return applyPotionsEffects;
    }

    public void setApplyPotionsEffects(boolean applyPotionsEffects) {
        this.applyPotionsEffects = applyPotionsEffects;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public HookParametersConnect(String joinMessage, boolean potionEffects) {
        super();
        this.joinMessage = joinMessage;
        this.applyPotionsEffects = potionEffects;
    }
    
}
