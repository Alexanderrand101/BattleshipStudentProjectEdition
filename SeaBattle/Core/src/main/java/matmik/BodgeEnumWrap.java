package matmik;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class BodgeEnumWrap {
	@Element
	private CellState state;

	public CellState getState() {
		return state;
	}

	public void setState(CellState state) {
		this.state = state;
	}
	
	public BodgeEnumWrap() {}
	
	public BodgeEnumWrap(CellState state) {
		this.state = state;
	}
}
