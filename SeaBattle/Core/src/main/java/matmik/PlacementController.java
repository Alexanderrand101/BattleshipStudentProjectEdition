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
public class PlacementController {
    private ShipBank shipBank;
    private Field field;
    
    public PlacementController(int sizex, int sizey){
        leftFieldBound = sizex / 10;
        rightFieldBound = sizex / 2;
        topFieldBound = sizey / 10;
        botFieldBound = topFieldBound + rightFieldBound;
        field = new Field();
        rightBankBound = sizex - (sizex / 10);
        leftBankBound = sizex - (sizex / 4);
        topBankBound = sizey / 10;
        botBankBound = topFieldBound + rightFieldBound;
        shipBank = new ShipBank();
        shipBank.addRange(ShipUtils.defaultShipList());
    }
    
    public Ship pickShip(int x, int y){
    }
}
