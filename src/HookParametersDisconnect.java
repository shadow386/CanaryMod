/**
 * HookParamtersDisconnect.java - send/receive parameters from hooks
 * 
 * @author James
 */

public class HookParametersDisconnect extends HookParameters {
    private String leaveMessage;
    private String reason;
    private boolean Hidden = false;

    public boolean isHidden() {
        return Hidden;
    }

    public void setHidden(boolean hidden) {
        Hidden = hidden;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public String getReason() {
        return reason;
    }

    public HookParametersDisconnect(String leaveMessage, String reason) {
        super();
        this.leaveMessage = leaveMessage;
        this.reason = reason;
    }
    
}
