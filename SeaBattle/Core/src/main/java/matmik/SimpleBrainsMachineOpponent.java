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
public class SimpleBrainsMachineOpponent extends MachineOpponent {

    private boolean hunt = false;
    private List<Coordinates> hitCoords = new LinkedList<Coordinates>();
    
     public SimpleBrainsMachineOpponent()
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
            Logger.getLogger(SimpleBrainsMachineOpponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!hunt){
            return AutoTurn.makeMove(fleshbagsField);
        }
        else{
            List<Coordinates> hitCandidates = new LinkedList<Coordinates>();
            for(Coordinates hitCoord : hitCoords){
                Coordinates toLeft = new Coordinates(hitCoord.getI(), hitCoord.getJ() - 1);
                Coordinates toRight = new Coordinates(hitCoord.getI(), hitCoord.getJ() + 1);
                Coordinates toTop = new Coordinates(hitCoord.getI() - 1, hitCoord.getJ());
                Coordinates toBottom = new Coordinates(hitCoord.getI() + 1, hitCoord.getJ());
                if(validateCoordinates(toLeft) && fleshbagsField.isHittable(toLeft)) hitCandidates.add(toLeft);
                if(validateCoordinates(toRight) && fleshbagsField.isHittable(toRight)) hitCandidates.add(toRight);
                if(validateCoordinates(toTop) && fleshbagsField.isHittable(toTop)) hitCandidates.add(toTop);
                if(validateCoordinates(toBottom) && fleshbagsField.isHittable(toBottom)) hitCandidates.add(toBottom);
            }
            Random r = new Random();
            return hitCandidates.get(r.nextInt(hitCandidates.size()));
        }
    }
    
    private boolean validateCoordinates(Coordinates coords){
        if((coords.getI() > -1) && (coords.getI() < Field.GRID_HEIGHT) && (coords.getJ() > -1) && (coords.getJ() < Field.GRID_WIDTH)){
            if(hitCoords.size() > 2){
                if(hitCoords.get(0).getI() == hitCoords.get(1).getI()){
                    if(hitCoords.get(0).getI() == coords.getI()) 
                        return true;
                    else
                        return false;
                }
                else {
                    if(hitCoords.get(0).getJ() == coords.getJ()) 
                        return true;
                    else
                        return false;
                }
            }
            else
                return true;
        }
        else
            return false;
                
    }
    
    @Override
    public void responseDelivery(Coordinates coord, CellState state){
        fleshbagsField.getGrid()[coord.getI()][coord.getJ()].setState(state);
        if(state == CellState.HIT_DAMAGED) {
            hunt = true;
            hitCoords.add(coord);
        }
        if(state == CellState.DESTROYED) {
            hunt = false;
            hitCoords.clear();
        }
    }
    
}
