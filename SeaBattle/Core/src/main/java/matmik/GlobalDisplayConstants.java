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

    public List<Bounds> getLabelBounds() {
        return labelBounds;
    }

    public void setLabelBounds(List<Bounds> labelBounds) {
        this.labelBounds = labelBounds;
    }

    public List<Bounds> getLabelBoundsRotated() {
        return labelBoundsRotated;
    }

    public void setLabelBoundsRotated(List<Bounds> labelBoundsRotated) {
        this.labelBoundsRotated = labelBoundsRotated;
    }
    private List<Bounds> labelBounds;
    private List<Bounds> labelBoundsRotated;
    private Bounds shipBankBounds;
    private Bounds shipBankBoundsRotated;
    private Bounds playerFieldBounds;
    private Bounds opponentFieldBounds;
    private Bounds turnArrowBounds;

    public Bounds getPlayerNameLabelBounds() {
        return playerNameLabelBounds;
    }

    public Bounds getOpponentNameLabelBounds() {
        return opponentNameLabelBounds;
    }

    public Bounds getTimerLabelBounds() {
        return timerLabelBounds;
    }
    private Bounds playerNameLabelBounds;
    private Bounds opponentNameLabelBounds;
    private Bounds timerLabelBounds;

    public Bounds getTurnArrowBounds() {
        return turnArrowBounds;
    }
    
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
    
    public Coordinates cellAtOpponentField(int x, int y){
        return new Coordinates((y - opponentFieldBounds.getTopBound()) * 10 /
                (opponentFieldBounds.getBottomBound() - opponentFieldBounds.getTopBound()),
                (x - opponentFieldBounds.getLeftBound()) * 10 /
                (opponentFieldBounds.getRightBound() - opponentFieldBounds.getLeftBound())  
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
        playerNameLabelBounds = new Bounds();
        opponentNameLabelBounds = new Bounds();
        timerLabelBounds = new Bounds();
        shipsInBank = new ArrayList<Bounds>();
        shipsInBankRotated = new ArrayList<Bounds>();
        labelBounds = new ArrayList<Bounds>();
        labelBoundsRotated = new ArrayList<Bounds>();
        turnArrowBounds = new Bounds();
        for(int i = 0; i < 4; i++) {
        	shipsInBankRotated.add(new Bounds());
        	shipsInBank.add(new Bounds());
                labelBounds.add(new Bounds());
                labelBoundsRotated.add(new Bounds());
        }
        shipCellSize = sizey / 9 * 7 / 10;
        playerFieldBounds.setTopBound(sizey / 9);
        playerFieldBounds.setBottomBound(playerFieldBounds.getTopBound() + shipCellSize * 10);
        playerFieldBounds.setLeftBound(sizex / 16);
        playerFieldBounds.setRightBound(playerFieldBounds.getLeftBound() + shipCellSize * 10);
        playerNameLabelBounds.setLeftBound(playerFieldBounds.getLeftBound());
        playerNameLabelBounds.setRightBound(playerFieldBounds.getRightBound());
        playerNameLabelBounds.setTopBound(playerFieldBounds.getTopBound() - 18);
        opponentFieldBounds.setTopBound(sizey / 9);
        opponentFieldBounds.setBottomBound(opponentFieldBounds.getTopBound() + shipCellSize * 10);
        opponentFieldBounds.setLeftBound(sizex / 16 + playerFieldBounds.getRightBound());
        opponentFieldBounds.setRightBound(opponentFieldBounds.getLeftBound() + shipCellSize * 10);
        opponentNameLabelBounds.setLeftBound(opponentFieldBounds.getLeftBound());
        opponentNameLabelBounds.setRightBound(opponentFieldBounds.getRightBound());
        opponentNameLabelBounds.setTopBound(opponentFieldBounds.getTopBound() - 18);
        
        turnArrowBounds.setLeftBound(playerFieldBounds.getRightBound() + sizex / 16 * 3 / 20);
        turnArrowBounds.setTopBound((playerFieldBounds.getBottomBound() - playerFieldBounds.getTopBound() - shipCellSize) / 2 
                + playerFieldBounds.getTopBound());
        timerLabelBounds.setLeftBound(turnArrowBounds.getLeftBound());
        timerLabelBounds.setRightBound(opponentFieldBounds.getLeftBound() - (turnArrowBounds.getLeftBound() - playerFieldBounds.getRightBound()));
        timerLabelBounds.setTopBound(playerFieldBounds.getTopBound() - 18);
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
        for(int i = 0; i < 4; i++){
            Bounds labelBound = labelBounds.get(i);
            labelBound.setLeftBound(shipsInBank.get(3).getRightBound() + shipCellSize);
            labelBound.setTopBound(shipsInBank.get(i).getTopBound());
            Bounds labelBoundRotated = labelBoundsRotated.get(i);
            labelBoundRotated.setTopBound(shipsInBankRotated.get(3).getBottomBound() + shipCellSize);
            labelBoundRotated.setLeftBound(shipsInBankRotated.get(i).getLeftBound());
        }
    }
}
