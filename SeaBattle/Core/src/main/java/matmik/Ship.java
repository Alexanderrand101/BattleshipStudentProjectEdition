package matmik;

public class Ship {
    private int shipLength;
    private int hits = 0;
    private boolean isRotated = false;
    private boolean isDestroyed = false;
    //private ShipContainer shipContainer;
    private Coordinates bow;

    public Coordinates getBow() {
        return bow;
    }

    
    public void setIsRotated(boolean isRotated) {
        this.isRotated = isRotated;
    }
    
    public Coordinates getStern(){
        return new Coordinates(getBow().getI() + 
                                    (isRotated() ? getShipLength() - 1 : 0)
                                , getBow().getJ() + 
                                    (!isRotated() ? getShipLength() - 1 : 0));
    }

    public final void setBow(Coordinates bow) {
        this.bow = bow;
    }

    public Ship(){}

    public Ship(int length){
        setShipLength(length);
    }

    public int getShipLength() {
        return shipLength;
    }


    public final void setShipLength(int shipLength) {
        this.shipLength = shipLength;
    }


    public void rotate() {
        isRotated = !isRotated;
    }

    public boolean isRotated() {
        return isRotated;
    }

    public void hit() {
        hits++;
        if (hits >= shipLength)
                isDestroyed = true;
    }

    public boolean destroyed() {
        return isDestroyed;
    }


//    public ShipContainer getShipContainer() {
//        return shipContainer;
//    }
//
//
//    public void setShipContainer(ShipContainer shipContainer) {
//        this.shipContainer = shipContainer;
//    }

    public boolean inBounds(Coordinates coords){
        if (bow == null) return false;
        return (coords.getI() >= bow.getI()) && (coords.getI() <= getStern().getI()) 
                && (coords.getJ() >= bow.getJ()) && (coords.getJ() <= getStern().getJ());
    } 
}