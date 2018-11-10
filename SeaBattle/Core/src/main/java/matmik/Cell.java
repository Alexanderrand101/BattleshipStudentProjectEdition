/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

/**
 *
 * @author Алескандр
 */
public class Cell {

    private CellState state;
    private Ship ship;
    
    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
    
    public boolean isFree()
    {
        return state == CellState.FREE;
    }
    
    public CellState hit(){
        if(state == CellState.BUSY){
            ship.hit();
            state = ship.destroyed()? CellState.DESTROYED : CellState.HIT_DAMAGED;
        }
        else{
            state = CellState.HIT_MISSED;
        }
        return state;
    }
    
    public Cell(){}
    
    
}
