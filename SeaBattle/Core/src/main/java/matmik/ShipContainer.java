package matmik;

import java.util.List;

public interface ShipContainer {
    void remove(Ship toRemove);
    void add(Ship toAdd);
    List<Ship> removeAll();
    void addRange(List<Ship> ships);
}
