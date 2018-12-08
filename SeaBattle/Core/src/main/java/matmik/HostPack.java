/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алескандр
 */
@Root
public class HostPack {
    @Element
    private String Name;
    @Element
    private int maxTurnTime;
    @Element
    private boolean turnOrder;

    public HostPack(){}
    
    public HostPack(String Name, int maxTurnTime, boolean turnOrder) {
        this.Name = Name;
        this.maxTurnTime = maxTurnTime;
        this.turnOrder = turnOrder;
    }

    public boolean isTurnOrder() {
        return turnOrder;
    }

    public void setTurnOrder(boolean turnOrder) {
        this.turnOrder = turnOrder;
    }
    
    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getMaxTurnTime() {
        return maxTurnTime;
    }

    public void setMaxTurnTime(int maxTurnTime) {
        this.maxTurnTime = maxTurnTime;
    }
    
}
