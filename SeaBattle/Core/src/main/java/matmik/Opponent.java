/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

/**
 *
 * @author Алескандр
 */
public interface Opponent {
    
    Coordinates makeMove();
    CellState checkMove(Coordinates move);
    
}
