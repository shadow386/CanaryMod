/**
 * HookParamtersConnect.java - send/receive parameters from hooks
 * 
 * @author James
 */

public class HookParametersDisconnect extends HookParameters {
    private String leaveMessage;
    private boolean Hidden = false;

    public boolean isHidden() {
        return Hidden;
    }

    public void setHidden(boolean hidden) {
        Hidden = hidden;
    }

    public void setLeaveMessage(String joinMessage) {
        this.leaveMessage = joinMessage;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public HookParametersDisconnect(String leaveMessage) {
        super();
        this.leaveMessage = leaveMessage;
    }
    
}
