/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Алескандр
 */
public abstract class HumanOpponent implements Opponent {
    
    AbstractConnector connector;
    
    public Coordinates makeMove() {
        try {
            return new Persister().read(Coordinates.class, connector.in());
        } catch (Exception ex) {
            Logger.getLogger(HumanOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public CellState checkMove(Coordinates move) {
        try {
            Serializer sc = new Persister();
            sc.write(move, connector.out());
            return sc.read(CellState.class, connector.in());
        } catch (Exception ex) {
            Logger.getLogger(HumanOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void responseDelivery(Coordinates coords, CellState result) {
        try {
            new Persister().write(result, connector.out());
        } catch (Exception ex) {
            Logger.getLogger(HumanOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Ship destroyedShip() {
        try {
            return (Ship)new Persister().read(Ship.class, connector.in());
        } catch (Exception ex) {
            Logger.getLogger(HumanOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sendDestroyedShip(Ship ship) {
        try {
            new Persister().write(ship, connector.out());
        } catch (Exception ex) {
            Logger.getLogger(HumanOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
