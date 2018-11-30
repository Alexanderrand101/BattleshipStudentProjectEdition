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

    public SocketConnector(String ip, int port) throws IOException {
            socket = new Socket(ip, port);
    }
    
    public InputStream in() throws IOException {
            return socket.getInputStream();
    }

    public OutputStream out() throws IOException {
            return socket.getOutputStream();
    }

    public void close() throws Exception{
        socket.close();
    }
    
}
