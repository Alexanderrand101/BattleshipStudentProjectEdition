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
        int leftFieldBound = sizex / 10;
        int rightFieldBound = sizex / 2;
        int topFieldBound = sizey / 10;
        int botFieldBound = topFieldBound + rightFieldBound;
        field = new Field(leftFieldBound, rightFieldBound, topFieldBound, botFieldBound);
        int rightBankBound = sizex - (sizex / 10);
        int leftBankBound = sizex - (sizex / 4);
        int topBankBound = sizey / 10;
        int botBankBound = topFieldBound + rightFieldBound;
        shipBank = new ShipBank(leftBankBound, rightBankBound, topBankBound, botBankBound);
        shipBank.addRange(ShipUtils.defaultShipList());
    }
    
    public Ship pickShip(int x, int y){
    }
}
