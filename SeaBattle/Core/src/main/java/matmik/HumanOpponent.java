/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public abstract class HumanOpponent implements Opponent {
    
    AbstractConnector connector;
    
    public Coordinates makeMove() throws Exception {
            return new Persister().read(Coordinates.class, connector.in());
    }

    public CellState checkMove(Coordinates move) throws Exception {
            Serializer sc = new Persister();
            sc.write(move, connector.out());
            return sc.read(BodgeEnumWrap.class, connector.in()).getState();
    }

    public void responseDelivery(Coordinates coords, CellState result) throws Exception {
            new Persister().write(new BodgeEnumWrap(result), connector.out());
    }

    public Ship destroyedShip() throws Exception {
            return (Ship)new Persister().read(Ship.class, connector.in());
    }

    public void sendDestroyedShip(Ship ship) throws Exception {
            new Persister().write(ship, connector.out());
    }
    
}
