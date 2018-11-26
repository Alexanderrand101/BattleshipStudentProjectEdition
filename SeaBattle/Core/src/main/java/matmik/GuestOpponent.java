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
public class GuestOpponent extends HumanOpponent{
    
    public GuestOpponent(AbstractConnector connector){
        this.connector = connector;
    }
    
    public void acceptReady(){
        try {
            Coordinates x = (Coordinates)new Persister().read(Coordinates.class, connector.in());
        } catch (Exception ex) {
            Logger.getLogger(GuestOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
