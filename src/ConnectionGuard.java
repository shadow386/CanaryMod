import java.sql.SQLException;
import java.util.logging.Logger;


/**
 * That guy runs to clean connections that are not used anymore in the connection pool.
 * @author Chris
 *
 */
public class ConnectionGuard extends Thread {
	private ConnectionService cp;
	private long sleep = 30000; //half the timeout time for a connection
	
	public ConnectionGuard(ConnectionService cp) {
		Logger.getLogger("Minecraft").info("CanaryMod: Starting connection cleanup thread.");
		this.cp = cp;
	}
	
	@Override
	  public void run() {
	    while (true) {
	      try {
	        Thread.sleep(sleep);
	      } catch (InterruptedException e) {
	    	  //oh shit oh shit
	      }
	      try {
			cp.clearConnections();
		} catch (SQLException e) {
			// oh shit
			e.printStackTrace();
		}
	    }
	  }
}
