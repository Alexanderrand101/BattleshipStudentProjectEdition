package matmik;

import java.util.Collection;

public interface ShipContainer {
	
    void remove(Ship toRemove);
    void add(Ship toAdd);
    Collection<Ship> removeAll();
    void addRange(Collection<Ship> ships);
}
