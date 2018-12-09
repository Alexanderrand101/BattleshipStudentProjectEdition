/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алескандр
 */
@Root
public class GamePack {
    
    @Element
    private Field myField;
    @Element
    private Field opponentField;
    @ElementUnion({
        @Element(name = "ZeroBrainsMachineOpponent", type = ZeroBrainsMachineOpponent.class)
    })
    private MachineOpponent machineOpponent;
    @Element
    private boolean turnOrder;

    public Field getMyField() {
        return myField;
    }

    public void setMyField(Field myField) {
        this.myField = myField;
    }

    public Field getOpponentField() {
        return opponentField;
    }

    public void setOpponentField(Field opponentField) {
        this.opponentField = opponentField;
    }

    public MachineOpponent getMachineOpponent() {
        return machineOpponent;
    }

    public void setMachineOpponent(MachineOpponent machineOpponent) {
        this.machineOpponent = machineOpponent;
    }

    public boolean isTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(boolean turnOrder) {
        this.turnOrder = turnOrder;
    }

    public GamePack(Field myField, Field opponentField, MachineOpponent machineOpponent, boolean turnOrder) {
        this.myField = myField;
        this.opponentField = opponentField;
        this.machineOpponent = machineOpponent;
        this.turnOrder = turnOrder;
    }

    public GamePack() {
    }
    
    
}
