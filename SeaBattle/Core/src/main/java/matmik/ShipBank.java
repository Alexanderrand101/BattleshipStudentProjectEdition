package matmik;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ShipBank implements ShipContainer{	
    
    private List<Ship> ships = new LinkedList<Ship>();
    private int boundTop;
    private int boundBottom;
    private int boundLeft;
    private int boundRight;
    private boolean rotated = false;

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

    public ShipBank(int boundTop, int boundBottom, int boundLeft, int boundRight) {
        this.boundTop = boundTop;
        this.boundBottom = boundBottom;
        this.boundLeft = boundLeft;
        this.boundRight = boundRight;
    }

    public ShipBank(){};

    public void remove(Ship toRemove) {
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

    public boolean isRotated() {
        return rotated;
    }

    public void rotate(){
        rotated = !rotated;
    }

}
