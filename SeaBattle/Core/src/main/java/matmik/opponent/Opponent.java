/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.opponent;

import matmik.model.CellState;
import matmik.model.Coordinates;
import matmik.model.Ship;

/**
 *
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public interface Opponent {
    
    Coordinates makeMove() throws Exception;
    CellState checkMove(Coordinates move) throws Exception;
    void responseDelivery(Coordinates coords, CellState result) throws Exception;
    Ship destroyedShip() throws Exception;
    void sendDestroyedShip(Ship ship) throws Exception;
}
