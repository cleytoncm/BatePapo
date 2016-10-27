/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.Control;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1502727
 */
public class Chat {
    
    public String recuperaIP() {
        String ip = "";
                
        try {
            ServerSocket servidor = new ServerSocket(12345);
            
            ip = servidor.getInetAddress().getHostAddress();
            
            servidor.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ip;
    }

}
