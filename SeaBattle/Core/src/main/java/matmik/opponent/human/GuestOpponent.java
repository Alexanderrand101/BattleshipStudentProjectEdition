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
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public class GuestOpponent extends HumanOpponent{
    
    public GuestOpponent(){
    }
    
    public void setConnectior(AbstractConnector connector){
        this.connector = connector;
    }
    
    public void setTurntime(int turntime){
        this.turntime = turntime;
    }
    
    public void ready() throws Exception{
            syncWrite();
            new Persister().write(new HostPack(myName, turntime, !moveOrder), connector.out());
            syncRead();
            opponentName = new Persister().read(GuestPack.class, connector.in()).getName();
    }
}
