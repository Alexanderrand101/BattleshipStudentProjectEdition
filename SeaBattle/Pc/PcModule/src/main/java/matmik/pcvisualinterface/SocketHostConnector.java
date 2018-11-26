/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.pcvisualinterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import matmik.AbstractConnector;
import matmik.AbstractHostConnector;

/**
 *
 * @author Алескандр
 */
public class SocketHostConnector implements AbstractHostConnector{
    ServerSocket hostSocket;
    
    public SocketHostConnector(int port){
        try {
            hostSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(SocketHostConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public AbstractConnector open() {
        try {
            return new SocketConnector(hostSocket.accept());
        } catch (IOException ex) {
            Logger.getLogger(SocketHostConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
