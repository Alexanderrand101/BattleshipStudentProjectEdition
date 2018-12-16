/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.view.pc;

import javafx.scene.image.Image;

/**
 *
 * @author Алескандр
 */
public class ShipControl {

    public ShipControl(int x, int y, Image shipImage) {
        this.x = x;
        this.y = y;
        this.shipImage = shipImage;
    }

    private int x;
    private int y;
    private Image shipImage;
    
    public Image getShipImage() {
        return shipImage;
    }

    public void setShipImage(Image shipImage) {
        this.shipImage = shipImage;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
