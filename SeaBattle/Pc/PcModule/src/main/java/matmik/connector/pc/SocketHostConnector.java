/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.connector.pc;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import matmik.connector.AbstractConnector;
import matmik.connector.AbstractHostConnector;

/**
 *
 * @author Алескандр
 */
public class SocketHostConnector implements AbstractHostConnector{
    ServerSocket hostSocket;
    
    public SocketHostConnector(int port) throws IOException{
        hostSocket = new ServerSocket(port);
    }
    
    public AbstractConnector open() throws IOException {
        AbstractConnector connector = new SocketConnector(hostSocket.accept());
        hostSocket.close();
        return connector;
    }

    public void close() throws Exception {
        hostSocket.close();
    }
    
}
