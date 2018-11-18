/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Алескандр
 */
public class Field implements ShipContainer{
        
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 10;
    private List<Ship> ships = new LinkedList<Ship>();
    private Cell[][] grid = new Cell[GRID_HEIGHT][GRID_WIDTH];
    private boolean initializedForGame = false;

    private void gridInit(){
        for(int i = 0; i < GRID_HEIGHT; i++)
            for(int j = 0; j < GRID_WIDTH; j++)
                grid[i][j] = new Cell();
    }
    

    public int getGRID_WIDTH() {
        return GRID_WIDTH;
    }

    public int getGRID_HEIGHT() {
        return GRID_HEIGHT;
    }
    
    public Field(){
        gridInit();
        gridClear();
    }
    
    private void gridClear(){
        for(Cell[] row: grid)
            for(Cell cell: row)
                cell.setState(CellState.FREE);
    }

    public void remove(Ship toRemove) {
        gridPaint(toRemove.getBow(), toRemove.getStern(), CellState.FREE);
        ships.remove(toRemove);
    }

    public void add(Ship toAdd) {
        ships.add(toAdd); 
    }

    public List<Ship> removeAll() {
        List<Ship> temp = new LinkedList<Ship>();
        temp.addAll(ships);
        ships.clear();
        return temp;
    }

    public void addRange(List<Ship> ships){
        this.ships.addAll(ships);
    }

    public List<Ship> getShips() {
        return ships;
    }

    private void gridPaint(Coordinates topLeft, Coordinates bottomRight, CellState state){
        for(int i = topLeft.getI(); i <= bottomRight.getI(); i++)
            for(int j = topLeft.getJ(); j <= bottomRight.getJ(); j++)
                grid[i][j].setState(state);
    }
    
    public boolean nonPaintingValidate(Ship shipToPlace)
    {
        //check if it is in bounds. If it is, highlight its potential placement.
        //as only top left is tracked, we don't need to check left oob, or top oob
        if ((shipToPlace.getStern().getI() > GRID_WIDTH - 1) ||
                (shipToPlace.getStern().getJ() > GRID_HEIGHT - 1))
            return false;
        //draw potential placement
        
        //scan area around ship to determine whenever it touches others or not.

        int startI = (shipToPlace.getBow().getI() - 1 < 0) ? 0 : shipToPlace.getBow().getI() - 1;
        int startJ = (shipToPlace.getBow().getJ() - 1 < 0) ? 0 : shipToPlace.getBow().getJ() - 1;

        int endI = (shipToPlace.getStern().getI() + 1 > GRID_WIDTH - 1) 
                ? shipToPlace.getStern().getI() : shipToPlace.getStern().getI() + 1;
        int endJ = (shipToPlace.getStern().getJ() + 1 > GRID_HEIGHT - 1) 
                ? shipToPlace.getStern().getJ() : shipToPlace.getStern().getJ() + 1;

        for(int  i = startI; i <= endI; i++)
            for(int j = startJ; j <= endJ; j++){
                if(grid[i][j].getState() == CellState.BUSY)
                {
                    return false;
                }
            }
        return true;
    }

    public boolean validate(Ship shipToPlace)
    {
        //check if it is in bounds. If it is, highlight its potential placement.
        //as only top left is tracked, we don't need to check left oob, or top oob
        if ((shipToPlace.getStern().getI() > GRID_WIDTH - 1) ||
                (shipToPlace.getStern().getJ() > GRID_HEIGHT - 1))
            return false;
        //draw potential placement
        
        //scan area around ship to determine whenever it touches others or not.
        boolean doesNotIntersect = true;

        int startI = (shipToPlace.getBow().getI() - 1 < 0) ? 0 : shipToPlace.getBow().getI() - 1;
        int startJ = (shipToPlace.getBow().getJ() - 1 < 0) ? 0 : shipToPlace.getBow().getJ() - 1;

        int endI = (shipToPlace.getStern().getI() + 1 > GRID_WIDTH - 1) 
                ? shipToPlace.getStern().getI() : shipToPlace.getStern().getI() + 1;
        int endJ = (shipToPlace.getStern().getJ() + 1 > GRID_HEIGHT - 1) 
                ? shipToPlace.getStern().getJ() : shipToPlace.getStern().getJ() + 1;

        for(int  i = startI; i <= endI; i++)
            for(int j = startJ; j <= endJ; j++){
                if(grid[i][j].getState() == CellState.BUSY)
                {
                    doesNotIntersect = false;
                    grid[i][j].setState(CellState.INTERSECTION);
                }
                else 
                    grid[i][j].setState(CellState.NEAR_SHIP_AREA);
            }
        gridPaint(shipToPlace.getBow(), shipToPlace.getStern(), CellState.CANDIDATE);
        return doesNotIntersect;
    }

    public void gridRefresh(){
        gridClear();
        for(Ship ship: ships)
            gridPaint(ship.getBow(), ship.getStern(), CellState.BUSY);
    }
    
    private void embedShip(Ship ship){
        for(int i = ship.getBow().getJ(); i <= ship.getStern().getJ(); i++)
            for(int j = ship.getBow().getI(); j <= ship.getStern().getI(); j++)
                grid[i][j].setShip(ship);
    }
    //todo validation for file loading for game and for placement
    
    public void gameInit()
    {
        for(Ship ship: ships)
        {
            embedShip(ship);
        }
        initializedForGame = true;
    }
    
    public int freeCellCount(){
        int freeCellCount = 0;
        for(Cell[] row: grid)
            for(Cell cell: row)
                if(cell.isFree()) freeCellCount++;
        return freeCellCount;
    }
    
    public boolean isHittable(Coordinates cellToHit){
        return grid[cellToHit.getI()][cellToHit.getJ()].isFree();
    }
    
    public CellState hit(Coordinates cellToHit){
        return grid[cellToHit.getI()][cellToHit.getJ()].hit();
    }
    
    public Ship shipAt(Coordinates shipLoc){
        for(Ship ship:ships){
            if(ship.inBounds(shipLoc)) return ship;
        }
        return null;
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
