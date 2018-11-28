/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Алескандр
 */
public class HostOpponent extends HumanOpponent{
    
    public HostOpponent(AbstractConnector connector){
        this.connector = connector;
    }
    
    public void ready(){
        try {
            syncWrite();
            new Persister().write(new Coordinates(-1, -1), connector.out());
        } catch (Exception ex) {
            Logger.getLogger(HostOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
