/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алескандр
 */
@Root
public class Coordinates implements Cloneable{
    @Element
    private int i;
    @Element
    private int j;

    public int getI() {
        return i;
    }

    public final void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public final void setJ(int j) {
        this.j = j;
    }
    
    public Coordinates(){};
    
    public Coordinates(int i, int j){
        setI(i);
        setJ(j);
    }
    
    @Override
    public Object clone(){
        Coordinates coord = null;
        try {
            coord = (Coordinates)super.clone();
        } catch (CloneNotSupportedException ex) {
            //toDo logFiles
            Logger.getLogger(Coordinates.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coord;
    }
}
