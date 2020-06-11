/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpserver;

import GUI.VistaPrincipal ;
import Sockets.Server;
import java.io.IOException;
/**
 *
 * @author Eyleen
 */
public class FTPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        VistaPrincipal vp = new VistaPrincipal ();
        vp.setVisible(true);
        server.start(5555);        
    }
    
}
