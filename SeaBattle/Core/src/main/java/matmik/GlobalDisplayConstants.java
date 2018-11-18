/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public class GlobalDisplayConstants {

    public static GlobalDisplayConstants getInstance() {
        return instance;
    }

    public int getShipCellSize() {
        return shipCellSize;
    }

    public List<Bounds> getShipsInBank() {
        return shipsInBank;
    }

    public List<Bounds> getShipsInBankRotated() {
        return shipsInBankRotated;
    }

    public Bounds getShipBankBounds() {
        return shipBankBounds;
    }

    public Bounds getShipBankBoundsRotated() {
        return shipBankBoundsRotated;
    }

    public Bounds getPlayerFieldBounds() {
        return playerFieldBounds;
    }

    public Bounds getOpponentFieldBounds() {
        return opponentFieldBounds;
    }
    private static GlobalDisplayConstants instance;
    private int shipCellSize;
    private List<Bounds> shipsInBank;
    private List<Bounds> shipsInBankRotated;
    private Bounds shipBankBounds;
    private Bounds shipBankBoundsRotated;
    private Bounds playerFieldBounds;
    private Bounds opponentFieldBounds;
    
    public static GlobalDisplayConstants getInstanceAndUpdate(){//todo a better Singleton
        if (instance == null){
            instance = new GlobalDisplayConstants();
        }
        return instance;
    }
    
    private GlobalDisplayConstants(){
        
    }
    
    public Coordinates cellAtPlayerField(int x, int y){
        return new Coordinates((y - playerFieldBounds.getTopBound()) * 10 /
                (playerFieldBounds.getBottomBound() - playerFieldBounds.getTopBound()),
                (x - playerFieldBounds.getLeftBound()) * 10 /
                (playerFieldBounds.getRightBound() - playerFieldBounds.getLeftBound())  
                );
    }
    
    public int lengthOfShipInBank(int x, int y)
    {
        for(int i = 0; i < 4; i++){
            if (shipsInBank.get(i).inBounds(x, y)) return i + 1;
        }
        return -1;
    }
    
    public int lengthOfShipInBankRotated(int x, int y)
    {
        for(int i = 0; i < 4; i++){
            if (shipsInBankRotated.get(i).inBounds(x, y)) return i + 1;
        }
        return -1;
    }
    
    public void CalcConstants(int sizex, int sizey){
        playerFieldBounds = new Bounds();
        opponentFieldBounds = new Bounds();
        shipBankBounds = new Bounds();
        shipBankBoundsRotated = new Bounds();
        shipsInBank = new ArrayList<Bounds>();
        shipsInBankRotated = new ArrayList<Bounds>();
        for(int i = 0; i < 4; i++) {
        	shipsInBankRotated.add(new Bounds());
        	shipsInBank.add(new Bounds());
        }
        shipCellSize = sizey / 9 * 15 / 20;
        playerFieldBounds.setTopBound(sizey / 9);
        playerFieldBounds.setBottomBound(playerFieldBounds.getTopBound() + shipCellSize * 10);
        playerFieldBounds.setLeftBound(sizex / 16);
        playerFieldBounds.setRightBound(playerFieldBounds.getLeftBound() + shipCellSize * 10);
        opponentFieldBounds.setTopBound(sizey / 9);
        opponentFieldBounds.setBottomBound(opponentFieldBounds.getTopBound() + shipCellSize * 10);
        opponentFieldBounds.setLeftBound(sizex / 16 + playerFieldBounds.getRightBound());
        opponentFieldBounds.setBottomBound(opponentFieldBounds.getTopBound() + shipCellSize * 10);
        shipBankBounds.setTopBound(sizey / 9);
        shipBankBounds.setBottomBound(shipBankBounds.getTopBound() + shipCellSize * 8);
        shipBankBounds.setLeftBound(sizex / 16 + playerFieldBounds.getRightBound());
        shipBankBounds.setRightBound(shipBankBounds.getLeftBound() + shipCellSize * 5);
        shipBankBoundsRotated.setTopBound(sizey / 9);
        shipBankBoundsRotated.setBottomBound(shipBankBounds.getTopBound() + shipCellSize * 7);
        shipBankBoundsRotated.setLeftBound(sizex / 16 + playerFieldBounds.getRightBound());
        shipBankBoundsRotated.setRightBound(shipBankBounds.getLeftBound() + shipCellSize * 7);
        for(int i = 0; i < 4; i++) {
        	Bounds shipInBank = shipsInBank.get(i);
        	shipInBank.setTopBound(shipBankBounds.getTopBound() + shipCellSize / 2 * (i + 1) + shipCellSize * i);
        	shipInBank.setBottomBound(shipInBank.getTopBound() + shipCellSize);
        	shipInBank.setLeftBound(shipCellSize / 2 + shipBankBounds.getLeftBound());
        	shipInBank.setRightBound(shipInBank.getLeftBound() + shipCellSize * (i + 1));
        	Bounds shipInBankRotated = shipsInBankRotated.get(i);
        	shipInBankRotated.setTopBound(shipBankBoundsRotated.getTopBound() + shipCellSize / 2);
        	shipInBankRotated.setBottomBound(shipInBankRotated.getTopBound() + shipCellSize * (i + 1));
        	shipInBankRotated.setLeftBound(shipCellSize / 2 * (i + 1) + shipCellSize * i + shipBankBoundsRotated.getLeftBound());
        	shipInBankRotated.setRightBound(shipInBankRotated.getLeftBound() + shipCellSize);
        }
    }
}
