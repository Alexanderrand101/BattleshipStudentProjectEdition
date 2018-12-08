/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Алескандр
 */
public class ZeroBrainsMachineOpponent extends MachineOpponent{

    public ZeroBrainsMachineOpponent()
    {
        setFleshbagsField(new Field());
        Field emptyField = new Field();
        AutoPlacer.placeShips(emptyField, ShipUtils.defaultShipList());
        setMyField(emptyField);
        myField.gridRefresh();
        myField.gameInit();
    }
    
    @Override
    public Coordinates makeMove() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ZeroBrainsMachineOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return AutoTurn.makeMove(fleshbagsField);
    }
    
}
