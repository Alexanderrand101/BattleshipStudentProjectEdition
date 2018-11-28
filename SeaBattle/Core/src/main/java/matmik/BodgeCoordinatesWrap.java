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
public class BodgeCoordinatesWrap {
    @Element
    Coordinates coords;

    public BodgeCoordinatesWrap(Coordinates coords){
        setCoords(coords);
    }
    
    public BodgeCoordinatesWrap(){}
    
    public Coordinates getCoords() {
        return coords;
    }

    public final void setCoords(Coordinates coords) {
        this.coords = coords;
    }
}
