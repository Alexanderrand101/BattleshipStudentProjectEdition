package matmik.view.display;

public class Bounds {
	private int leftBound;
	private int rightBound;
	private int topBound;
	private int bottomBound;
	
	public Bounds() {}
	
	public int getRightBound() {
		return rightBound;
	}
	public void setRightBound(int rightBound) {
		this.rightBound = rightBound;
	}
	public int getTopBound() {
		return topBound;
	}
	public void setTopBound(int topBound) {
		this.topBound = topBound;
	}
	public int getBottomBound() {
		return bottomBound;
	}
	public void setBottomBound(int bottomBound) {
		this.bottomBound = bottomBound;
	}
	public int getLeftBound() {
		return leftBound;
	}
	public void setLeftBound(int leftBound) {
		this.leftBound = leftBound;
	}

        public boolean inBounds(int x, int y){
            return (x >= leftBound) && (x < rightBound) && (y >= topBound) && (y < bottomBound);
        }
}
