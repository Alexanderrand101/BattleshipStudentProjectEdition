/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Алескандр
 */
public class ZeroBrainsMachineOpponent extends MachineOpponent{

    public ZeroBrainsMachineOpponent()
    {
        setFleshbagsField(new Field());
        Field emptyField = new Field();
        RandomAutoPlacer.placeShips(emptyField, ShipUtils.defaultShipList());
        setMyField(emptyField);
        myField.gridRefresh();
        myField.gameInit();
    }
    
    @Override
    public Coordinates makeMove() {
        List<Coordinates> possibleHits = new LinkedList<Coordinates>();
        for(int i = 0; i < fleshbagsField.getGRID_HEIGHT(); i++)
            for(int j = 0; j < fleshbagsField.getGRID_WIDTH(); j++){
                Coordinates hitCoodinates = new Coordinates(i,j);
                if(fleshbagsField.isHittable(hitCoodinates))
                    possibleHits.add(hitCoodinates);
            }
        Random r = new Random();
        return possibleHits.get(r.nextInt(possibleHits.size()));
    }
    
}
