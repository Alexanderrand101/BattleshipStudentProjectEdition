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
    private int boundTop;
    private int boundBottom;
    private int boundLeft;
    private int boundRight;

    public Field(int boundTop, int boundBottom, int boundLeft, int boundRight) {
        this.boundTop = boundTop;
        this.boundBottom = boundBottom;
        this.boundLeft = boundLeft;
        this.boundRight = boundRight;
    }
 
    public int getBoundTop() {
        return boundTop;
    }

    public void setBoundTop(int boundTop) {
        this.boundTop = boundTop;
    }

    public int getBoundBottom() {
        return boundBottom;
    }

    public void setBoundBottom(int boundBottom) {
        this.boundBottom = boundBottom;
    }

    public int getBoundLeft() {
        return boundLeft;
    }

    public void setBoundLeft(int boundLeft) {
        this.boundLeft = boundLeft;
    }

    public int getBoundRight() {
        return boundRight;
    }

    public void setBoundRight(int boundRight) {
        this.boundRight = boundRight;
    }
    

    public int getGRID_WIDTH() {
        return GRID_WIDTH;
    }

    public int getGRID_HEIGHT() {
        return GRID_HEIGHT;
    }
    
    public Field(){
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
        toAdd.setShipContainer(this);
    }

    public List<Ship> removeAll() {
        List<Ship> temp = new LinkedList<Ship>();
        temp.addAll(ships);
        ships.clear();
        return temp;
    }

    public void addRange(List<Ship> ships){
        ships.addAll(ships);
        for(Ship ship: ships)
            ship.setShipContainer(this);
    }

    public List<Ship> getShips() {
        return ships;
    }

    private void gridPaint(Coordinates topLeft, Coordinates bottomRight, CellState state){
        for(int i = topLeft.getY(); i <= bottomRight.getY(); i++)
            for(int j = topLeft.getX(); j <= bottomRight.getY(); j++)
                grid[i][j].setState(state);
    }
    
    public boolean nonPaintingValidate(Ship shipToPlace)
    {
         if ((shipToPlace.getStern().getX() > GRID_WIDTH - 1) ||
                (shipToPlace.getStern().getY() > GRID_WIDTH - 1))
            return false;
        //scan area around ship to determine whenever it touches others or not.
        boolean doesNotIntersect = true;

        int startX = (shipToPlace.getBow().getX() - 1 < 0) ? 0 : shipToPlace.getBow().getX() - 1;
        int startY = (shipToPlace.getBow().getY() - 1 < 0) ? 0 : shipToPlace.getBow().getY() - 1;

        int endX = (shipToPlace.getStern().getX() + 1 > GRID_WIDTH - 1) 
                ? 0 : shipToPlace.getStern().getX() + 1;
        int endY = (shipToPlace.getStern().getY() + 1 > GRID_WIDTH - 1) 
                ? 0 : shipToPlace.getStern().getY() + 1;

        for(int  i = startY; i <= endY; i++)
            for(int j = startX; j <= endX; j++){
                if(grid[i][j].getState() == CellState.BUSY)
                {
                    doesNotIntersect = false;
                    break;
                }
            }
        return doesNotIntersect;
    }

    public boolean validate(Ship shipToPlace)
    {
        //check if it is in bounds. If it is, highlight its potential placement.
        //as only top left is tracked, we don't need to check left oob, or top oob
        if ((shipToPlace.getStern().getX() > GRID_WIDTH - 1) ||
                (shipToPlace.getStern().getY() > GRID_HEIGHT - 1))
            return false;
        //draw potential placement
        gridPaint(shipToPlace.getBow(), shipToPlace.getStern(), CellState.CANDIDATE);
        //scan area around ship to determine whenever it touches others or not.
        boolean doesNotIntersect = true;

        int startX = (shipToPlace.getBow().getX() - 1 < 0) ? 0 : shipToPlace.getBow().getX() - 1;
        int startY = (shipToPlace.getBow().getY() - 1 < 0) ? 0 : shipToPlace.getBow().getY() - 1;

        int endX = (shipToPlace.getStern().getX() + 1 > GRID_WIDTH - 1) 
                ? 0 : shipToPlace.getStern().getX() + 1;
        int endY = (shipToPlace.getStern().getY() + 1 > GRID_HEIGHT - 1) 
                ? 0 : shipToPlace.getStern().getY() + 1;

        for(int  i = startY; i <= endY; i++)
            for(int j = startX; j <= endX; j++){
                if(grid[i][j].getState() == CellState.BUSY)
                {
                    doesNotIntersect = false;
                    grid[i][j].setState(CellState.INTERSECTION);
                }
                else 
                    grid[i][j].setState(CellState.NEAR_SHIP_AREA);
            }
        return doesNotIntersect;
    }

    public void gridRefresh(){
        gridClear();
        for(Ship ship: ships)
            gridPaint(ship.getBow(), ship.getStern(), CellState.BUSY);
    }
    
    private void embedShip(Ship ship){
        for(int i = ship.getBow().getY(); i <= ship.getStern().getY(); i++)
            for(int j = ship.getBow().getX(); j <= ship.getStern().getX(); j++)
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
        return grid[cellToHit.getX()][cellToHit.getY()].isFree();
    }
    
    public CellState hit(Coordinates cellToHit){
        return grid[cellToHit.getX()][cellToHit.getY()].hit();
    }
}
