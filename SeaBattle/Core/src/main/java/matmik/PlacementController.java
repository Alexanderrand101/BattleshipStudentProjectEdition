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
    private GlobalDisplayConstants displayConstants;
    private Ship selectedShip;
    private Coordinates shipStartPos;
    private ShipContainer shipStartContainer;
    
    public PlacementController(){
        field = new Field();
        shipBank = new ShipBank();
        shipBank.addRange(ShipUtils.defaultShipList());
        displayConstants = GlobalDisplayConstants.getInstanceAndUpdate();
    }
    
    public PlacementController(Field field){
        this.field = field;
        shipBank = new ShipBank();
        shipBank.addRange(ShipUtils.remainingShipList(field.getShips()));
        displayConstants = GlobalDisplayConstants.getInstanceAndUpdate();
    }
    
    public Cell[][] getGrid(){
        return field.getGrid();
    }
    
    public boolean bankRotated(){
        return shipBank.isRotated();
    }
    
    public GlobalDisplayConstants getDisplayConstants() {
        return displayConstants;
    } 
    
    public void highlight(int x, int y){
        field.gridRefresh();
        if (displayConstants.getPlayerFieldBounds().inBounds(x, y))
        {
            Coordinates shipAt = displayConstants.cellAtPlayerField(x, y);
            selectedShip.setBow(shipAt);
            field.validate(selectedShip);
        }
    }
    
    public void dropShip(int x, int y){
        field.gridRefresh();
        if (displayConstants.getPlayerFieldBounds().inBounds(x, y))
        {
            Coordinates shipAt = displayConstants.cellAtPlayerField(x, y);
            selectedShip.setBow(shipAt);
            if(field.validate(selectedShip)){
                field.add(selectedShip);
            }
            else {
                selectedShip.setBow(shipStartPos);
                shipStartContainer.add(selectedShip);
            }
        }
        else if (!shipBank.isRotated() && displayConstants.getShipBankBounds().inBounds(x, y))
        {
            shipBank.add(selectedShip);
        }
        else if (shipBank.isRotated() && displayConstants.getShipBankBoundsRotated().inBounds(x, y))
        {
            shipBank.add(selectedShip);
        }
        else {
            selectedShip.setBow(shipStartPos);
            shipStartContainer.add(selectedShip);
        }
        selectedShip = null;
        field.gridRefresh();
    }
    
    public Ship pickShip(int x, int y){
        Ship pickedShip = null;
        if (displayConstants.getPlayerFieldBounds().inBounds(x, y))
        {
            Coordinates shipAt = displayConstants.cellAtPlayerField(x, y);
            pickedShip = field.shipAt(shipAt);
            if(pickedShip != null){
                field.remove(pickedShip);
                selectedShip = pickedShip;
                shipStartPos = (Coordinates)pickedShip.getBow().clone();
                shipStartContainer = field;
            }
        }
        else if (!shipBank.isRotated() && displayConstants.getShipBankBounds().inBounds(x, y))
        {
            int shipLength = displayConstants.lengthOfShipInBank(x, y);
            if (shipLength != -1){
                pickedShip = shipBank.getShipOfLength(shipLength);
                if(pickedShip != null){
                    shipBank.remove(pickedShip);
                    selectedShip = pickedShip;
                    shipStartContainer = shipBank;
                }
            }
        }
        else if (shipBank.isRotated() && displayConstants.getShipBankBoundsRotated().inBounds(x, y))
        {
            int shipLength = displayConstants.lengthOfShipInBankRotated(x, y);
            if (shipLength != -1){
                pickedShip = shipBank.getShipOfLength(shipLength);
                if(pickedShip != null){
                    shipBank.remove(pickedShip);
                    selectedShip = pickedShip;
                    shipStartContainer = shipBank;
                }
            }
        }
        return pickedShip;
    }

    public void clearField() {
        shipBank.addRange(field.removeAll());
        field.gridRefresh();
    }

    public void autoPlaceShips() {
        RandomAutoPlacer.placeShips(field, shipBank.removeAll());
        field.gridRefresh();
    }

    public void rotateBank() {
        shipBank.rotate();
    }
    
    public Field getField(){
        return field;
    }
}
