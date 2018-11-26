/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Алескандр
 */
public interface AbstractConnector {
    
    InputStream in();
    OutputStream out();
}
