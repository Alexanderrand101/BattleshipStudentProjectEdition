/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.pcvisualinterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import matmik.AbstractConnector;

/**
 *
 * @author Алескандр
 */
class SocketConnector implements AbstractConnector {
    Socket socket;

    public SocketConnector(Socket accept) {
        socket = accept;
    }

    public SocketConnector(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException ex) {
            Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public InputStream in() {
        try {
            return socket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public OutputStream out() {
        try {
            return socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
