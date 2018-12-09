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
public class OkayBrainsMachineOpponent extends MachineOpponent{
    
    private boolean hunt = false;
    private List<Coordinates> hitCoords = new LinkedList<Coordinates>();
    
     public OkayBrainsMachineOpponent()
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
            int[][] grid = new int[Field.GRID_HEIGHT][Field.GRID_WIDTH];
            List<Ship> shipList = ShipUtils.remainingShipList(fleshbagsField.getShips());
            shipList.sort(new Ship.ShipComparator());
            Ship currentShip = shipList.get(0);
            List<Coordinates> possibleHits = new LinkedList<Coordinates>();
            for(int i = 0; i < fleshbagsField.getGRID_HEIGHT(); i++)
                for(int j = 0; j < fleshbagsField.getGRID_WIDTH(); j++){
                    Coordinates hitCoodinates = new Coordinates(i,j);
                    currentShip.setBow(hitCoodinates);
                    if(fleshbagsField.isHittable(hitCoodinates) && fleshbagsField.possiblePosition(currentShip)){
                        for(int k = currentShip.getBow().getI(); k <= currentShip.getStern().getI(); k++)
                            for(int l = currentShip.getBow().getJ(); l <= currentShip.getStern().getJ(); l++){
                                grid[k][l]++;
                            }
                    }
                    currentShip.rotate();
                    if(fleshbagsField.isHittable(hitCoodinates) && fleshbagsField.possiblePosition(currentShip)){
                        for(int k = currentShip.getBow().getI(); k <= currentShip.getStern().getI(); k++)
                            for(int l = currentShip.getBow().getJ(); l <= currentShip.getStern().getJ(); l++){
                                grid[k][l]++;
                            }
                    }
                    currentShip.rotate();
                }
            int maxval = grid[0][0];
            for(int i = 0; i < grid.length; i++)
                for(int j = 0; j < grid[i].length; j++){
                    if (maxval < grid[i][j]){
                        maxval = grid[i][j];
                    }
                }
            for(int i = 0; i < grid.length; i++){
                for(int j = 0; j < grid[i].length; j++){
                    if (maxval == grid[i][j]){
                        possibleHits.add(new Coordinates(i, j));
                        
                    }
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            Random r = new Random();
            return possibleHits.get(r.nextInt(possibleHits.size()));
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
            if(hitCoords.size() > 1){
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
