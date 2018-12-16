/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.opponent.human;

import matmik.connector.AbstractConnector;
import matmik.util.packet.GuestPack;
import matmik.util.packet.HostPack;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Алескандр
 */
public class HostOpponent extends HumanOpponent{
    
    public HostOpponent(AbstractConnector connector){
        this.connector = connector;
    }
    
    public void ready() throws Exception{       
            syncRead();
            HostPack hp = new Persister().read(HostPack.class, connector.in());
            opponentName = hp.getName();
            turntime = hp.getMaxTurnTime();
            moveOrder = hp.isTurnOrder();
            syncWrite();
            new Persister().write(new GuestPack(myName), connector.out());
    }
}
