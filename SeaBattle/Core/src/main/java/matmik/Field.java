/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алескандр
 */
@Root
public class Field implements ShipContainer, Cloneable{
        
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 10;
    @ElementList
    private List<Ship> ships = new LinkedList<Ship>();
    private Cell[][] grid = new Cell[GRID_HEIGHT][GRID_WIDTH];
    private boolean initializedForGame = false;

    @ElementList(required = false)
    public List<CellWithCoords> getCellWithCoordsList(){
        List<CellWithCoords> listofstuff = new LinkedList<CellWithCoords>();
        for(int i = 0; i < GRID_HEIGHT; i++)
            for(int j = 0; j < GRID_WIDTH; j++){
                if(!grid[i][j].isFree())
                    listofstuff.add(new CellWithCoords(grid[i][j], new Coordinates(i,j)));
            }
        return listofstuff;
    }
    
    @ElementList(required = false)
    public void setCellWithCoordsList(List<CellWithCoords> listofstuff){
        for(CellWithCoords cellwc : listofstuff){
            grid[cellwc.getCoords().getI()][cellwc.getCoords().getJ()] = cellwc.getCell();
        }
    }
    
    private void gridInit(){
        grid = new Cell[GRID_HEIGHT][GRID_WIDTH];
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

    public boolean possiblePosition(Ship shipToPlace)
    {
        //check if it is in bounds. If it is, highlight its potential placement.
        //as only top left is tracked, we don't need to check left oob, or top oob
        if ((shipToPlace.getStern().getI() > GRID_WIDTH - 1) ||
                (shipToPlace.getStern().getJ() > GRID_HEIGHT - 1))
            return false;
        //draw potential placement
        
        //scan area around ship to determine whenever it touches others or not.

        int startI = shipToPlace.getBow().getI();
        int startJ = shipToPlace.getBow().getJ();

        int endI = shipToPlace.getStern().getI();
        int endJ = shipToPlace.getStern().getJ();

        for(int  i = startI; i <= endI; i++)
            for(int j = startJ; j <= endJ; j++){
                if(grid[i][j].getState() != CellState.FREE)
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

        //gridPaint(shipToPlace.getBow(), shipToPlace.getStern(), CellState.CANDIDATE);
        for(int  i = startI; i <= endI; i++)
            for(int j = startJ; j <= endJ; j++){
                if(grid[i][j].getState() == CellState.BUSY)
                {
                    doesNotIntersect = false;
                    grid[i][j].setState(CellState.INTERSECTION);
                }
                else if(!shipToPlace.inBounds(new Coordinates(i,j)))
                    grid[i][j].setState(CellState.NEAR_SHIP_AREA);
                else
                    grid[i][j].setState(CellState.CANDIDATE);
            }
        
        return doesNotIntersect;
    }

    public void gridRefresh(){
        gridClear();
        for(Ship ship: ships)
            gridPaint(ship.getBow(), ship.getStern(), CellState.BUSY);
    }
    
    public void gridExtendedRefresh(){
        gridClear();
        for(Ship ship: ships){
            int startI = (ship.getBow().getI() - 1 < 0) ? 0 : ship.getBow().getI() - 1;
            int startJ = (ship.getBow().getJ() - 1 < 0) ? 0 : ship.getBow().getJ() - 1;

            int endI = (ship.getStern().getI() + 1 > GRID_WIDTH - 1) 
                ? ship.getStern().getI() : ship.getStern().getI() + 1;
            int endJ = (ship.getStern().getJ() + 1 > GRID_HEIGHT - 1) 
                ? ship.getStern().getJ() : ship.getStern().getJ() + 1;

        //gridPaint(shipToPlace.getBow(), shipToPlace.getStern(), CellState.CANDIDATE);
        for(int  i = startI; i <= endI; i++)
            for(int j = startJ; j <= endJ; j++){
                grid[i][j].setState(CellState.BUSY);
            }
        }
    }
    
    private void embedShip(Ship ship){
        for(int i = ship.getBow().getI(); i <= ship.getStern().getI(); i++)
            for(int j = ship.getBow().getJ(); j <= ship.getStern().getJ(); j++)
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
    
    public int shipsDestroyed(){
        int destroyedCount = 0;
        for(Ship ship:ships){
            if (ship.destroyed()) destroyedCount++;
        }
        return destroyedCount;
    }
    
    public void setMiss(Coordinates cellToMiss){
        grid[cellToMiss.getI()][cellToMiss.getJ()].setState(CellState.HIT_MISSED);
    }
    
    public void setHit(Coordinates cellToHit){
        grid[cellToHit.getI()][cellToHit.getJ()].setState(CellState.HIT_DAMAGED);
    }
    
    public Cell cellAt(Coordinates cell){
        return grid[cell.getI()][cell.getJ()];
    }
    
    public Map<Coordinates,Cell> destroy(Ship shipToDestroy){
        Map<Coordinates,Cell> cells = new HashMap<Coordinates, Cell>();
        int startI = (shipToDestroy.getBow().getI() - 1 < 0) ? 0 : shipToDestroy.getBow().getI() - 1;
        int startJ = (shipToDestroy.getBow().getJ() - 1 < 0) ? 0 : shipToDestroy.getBow().getJ() - 1;

        int endI = (shipToDestroy.getStern().getI() + 1 > GRID_WIDTH - 1) 
                ? shipToDestroy.getStern().getI() : shipToDestroy.getStern().getI() + 1;
        int endJ = (shipToDestroy.getStern().getJ() + 1 > GRID_HEIGHT - 1) 
                ? shipToDestroy.getStern().getJ() : shipToDestroy.getStern().getJ() + 1;

        for(int  i = startI; i <= endI; i++)
            for(int j = startJ; j <= endJ; j++){
                if(shipToDestroy.inBounds(new Coordinates(i, j))){
                    grid[i][j].setState(CellState.DESTROYED);
                    cells.put(new Coordinates(i, j), grid[i][j]);
                }
                else if(grid[i][j].getState() == CellState.FREE)//осторожно тут
                {
                    grid[i][j].setState(CellState.HIT_MISSED);
                    cells.put(new Coordinates(i, j), grid[i][j]);
                }
            }
        return cells;
    }
    
    public boolean isPlayValid(){
        return ships.size() == 10;
    }
    
    public boolean isOnLoadValid(){
        List<Ship> ships = removeAll();
        gridRefresh();
        int s1 = 0;
        int s2 = 0;
        int s3 = 0; 
        int s4 = 0;
        for(Ship ship: ships){
            switch(ship.getShipLength()){
                case 1: s1++; break;
                case 2: s2++; break;
                case 3: s3++; break;
                case 4: s4++; break;
            }
            if(nonPaintingValidate(ship)){
                add(ship);
                gridRefresh();
            }
            else 
                return false;
        }
        return (s1 + s2 + s3 + s4 == ships.size()) && (s1 <= 4) && (s2 <= 3) && (s3 <= 2) && (s4 <= 1);
    }
    
    @Override
    public Object clone(){
        Field field = null;
        try {
            field = (Field)super.clone();
            field.gridInit();
            List<Ship> shipsCloned = new LinkedList<Ship>();
            for(Ship ship:ships){
                shipsCloned.add((Ship)ship.clone());
            }
            field.ships = shipsCloned;
        } catch (CloneNotSupportedException ex) {
            //toDo logFiles
            Logger.getLogger(Coordinates.class.getName()).log(Level.SEVERE, null, ex);
        }
        return field;
    }
}
