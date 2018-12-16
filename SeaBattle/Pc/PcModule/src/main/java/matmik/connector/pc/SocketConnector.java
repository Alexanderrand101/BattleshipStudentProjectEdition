/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.connector.pc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import matmik.connector.AbstractConnector;

/**
 *
 * @author Алескандр
 */
public class SocketConnector implements AbstractConnector {
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
