/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Алескандр
 */
public class ShipUtils {
    
    public static List<Ship> defaultShipList(){
        List<Ship> ships = new LinkedList<Ship>();
        ships.add(new Ship(4));
        for(int i = 0; i < 2; i++) ships.add(new Ship(3));
        for(int i = 0; i < 3; i++) ships.add(new Ship(2));
        for(int i = 0; i < 4; i++) ships.add(new Ship(1));
        return ships;
    }
    
    public static List<Ship> remainingShipList(List<Ship> shipList){
        List<Ship> ships = defaultShipList();
        for(Ship ship: shipList){
            for(int i = 0; i < ships.size(); i++){
                if (ships.get(i).getShipLength() == ship.getShipLength()){
                    ships.remove(i);
                    break;
                }
            }
        }
        return ships;
    }
}
