import java.util.List;

/**
 * HookParamtersChat.java - send/receive parameters for onChat
 * 
 * @author DarkDiplomat
 */

public class HookParametersChat extends HookParameters {
    Player player;
    StringBuilder prefix;
    StringBuilder message;
    List<Player> receivers;
    boolean cancel = false;
    
    public HookParametersChat(Player player, StringBuilder prefix, StringBuilder message, List<Player> receivers){
        this.player = player;
        this.prefix = prefix;
        this.message = message;
        this.receivers = receivers;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public StringBuilder getPrefix(){
        return prefix;
    }
    
    public StringBuilder getMessage(){
        return message;
    }
    
    public List<Player> getReceivers(){
        return receivers;
    }
    
    public void setPrefix(StringBuilder prefix){
        this.prefix = prefix;
    }
    
    public void setMessage(StringBuilder message){
        this.message = message;
    }
    
    public void setReceivers(List<Player> receivers){
        this.receivers = receivers;
    }
    
    public boolean isCanceled(){
        return cancel;
    }
    
    public void setCanceled(boolean cancel){
        this.cancel = cancel;
    }
}
