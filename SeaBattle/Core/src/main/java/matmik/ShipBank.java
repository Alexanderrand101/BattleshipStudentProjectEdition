package matmik;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ShipBank implements ShipContainer{	
    
    private List<Ship> ships = new LinkedList<Ship>();

    private boolean rotated = false;

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
