package matmik;

public class Ship {
    
    private int shipLength;
    private int hits = 0;
    private boolean isRotated = false;
    private boolean isDestroyed = false;
    private ShipContainer shipContainer;
    private Coordinates Bow;

    public Coordinates getBow() {
        return Bow;
    }

    public Coordinates getStern(){
        return new Coordinates(getBow().getX() + 
                                    (!isRotated() ? getShipLength() - 1 : 0)
                                , getBow().getY() + 
                                    (isRotated() ? getShipLength() - 1 : 0));
    }

    public void setBow(Coordinates Bow) {
        this.Bow = Bow;
    }

    public Ship()
    {
    }


    public int getShipLength() {
        return shipLength;
    }


    public void setShipLength(int shipLength) {
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


    public ShipContainer getShipContainer() {
        return shipContainer;
    }


    public void setShipContainer(ShipContainer shipContainer) {
        this.shipContainer = shipContainer;
    }

}
