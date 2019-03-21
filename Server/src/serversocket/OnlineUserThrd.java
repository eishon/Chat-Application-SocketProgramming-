package serversocket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EISHON
 */
public class OnlineUserThrd extends Thread{
    String msgout = "";
    
    @Override
    public void run() {
        try {
            while(true){
                msgout = "system>>>users>>>";
                msgout += Server.onlineUserNames();
                Server.sendToAll(msgout);

                OnlineUserThrd.sleep(2000);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    }