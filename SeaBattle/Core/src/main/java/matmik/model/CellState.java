/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.model;

import org.simpleframework.xml.Root;

/**
 *
 * @author Алескандр
 */
@Root
public enum CellState {
    FREE, HIT_MISSED, HIT_DAMAGED, DESTROYED, BUSY, NEAR_SHIP_AREA, INTERSECTION, CANDIDATE
}
