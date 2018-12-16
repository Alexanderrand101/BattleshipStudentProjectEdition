/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.connector;

/**
 *
 * @author Алескандр
 */
public interface AbstractHostConnector {
    
    AbstractConnector open() throws Exception;
    void close() throws Exception;
}
