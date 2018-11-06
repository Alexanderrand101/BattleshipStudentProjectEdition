package matmik;

public class Ship {
	private int shipLength;
	private int hits = 0;
	private boolean isRotated = false;
	private boolean isDestroyed = false;
	private ShipContainer shipContainer;
	private Point top;//bridge or whatever
	
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
