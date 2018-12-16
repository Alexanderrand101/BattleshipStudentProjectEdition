/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.util.packet;

import matmik.model.Cell;
import matmik.model.Coordinates;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алескандр
 */
@Root
public class CellWithCoords {
    @Element
    public Cell cell;
    @Element
    public Coordinates coords;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public void setCoords(Coordinates coords) {
        this.coords = coords;
    }

    public CellWithCoords(Cell cell, Coordinates coords) {
        this.cell = cell;
        this.coords = coords;
    }

    public CellWithCoords() {
    }
  
}
