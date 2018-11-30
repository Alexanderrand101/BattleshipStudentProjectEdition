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
    
    public GuestOpponent(){
    }
    
    public void setConnectior(AbstractConnector connector){
        this.connector = connector;
    }
    
    public void ready() throws Exception{
            syncWrite();
            new Persister().write(new Coordinates(-1,-1), connector.out());
            syncRead();
            new Persister().read(Coordinates.class, connector.in());
    }
}
