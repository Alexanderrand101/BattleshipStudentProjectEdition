/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.view;

import java.util.Map;
import matmik.model.Cell;
import matmik.model.Coordinates;

/**
 *
 * @author Алескандр
 */
public interface View {
    void stateTransition(ViewState state);
    void showError(String errorMessage);
    void drawRemaningTime(int seconds);
    void animate(Map<Coordinates,Cell> cellsToAnimate);
    void gameEnd(boolean result);
}
