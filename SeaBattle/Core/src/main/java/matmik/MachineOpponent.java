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
public abstract class MachineOpponent implements Opponent{

    protected Field myField; 
    protected Field fleshbagsField; 
    protected Ship lastDestroyedShip;
    
    public Field getMyField() {
        return myField;
    }

    public void setMyField(Field myField) {
        this.myField = myField;
    }

    public Field getFleshbagsField() {
        return fleshbagsField;
    }

    public void setFleshbagsField(Field fleshbagsField) {
        this.fleshbagsField = fleshbagsField;
    }

    public abstract Coordinates makeMove();

    public CellState checkMove(Coordinates move) {
        CellState hitResult = myField.hit(move);
        if(hitResult == CellState.DESTROYED)
            lastDestroyedShip = myField.shipAt(move);
        return hitResult;
    }
    
    public Ship destroyedShip(){
        return lastDestroyedShip;
    }
    
    public void sendDestroyedShip(Ship ship){
        fleshbagsField.add(ship);
    }
    
    public void responseDelivery(Coordinates coord, CellState state){
        fleshbagsField.getGrid()[coord.getI()][coord.getJ()].setState(state);
    }
}
