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
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public class GuestOpponent extends HumanOpponent{
    
    public GuestOpponent(AbstractConnector connector){
        this.connector = connector;
    }
    
    public void acceptReady() throws Exception{
            syncRead();
            Coordinates x = new Persister().read(Coordinates.class, connector.in());
    }
}
