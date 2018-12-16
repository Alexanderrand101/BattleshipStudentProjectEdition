/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.controller.battle;

import matmik.view.View;
import java.util.logging.Level;
import java.util.logging.Logger;
import matmik.util.AutoTurn;

/**
 *
 * @author Алескандр
 */
public class TimerThread extends Thread{
    private int maxTurnTime;
    private View view;
    private boolean isActive = true;
    private BattleController battleController;
    
    public TimerThread(int maxTurnTime, View view, BattleController battleController){
        this.maxTurnTime = maxTurnTime;
        this.view = view;
        this.battleController = battleController;
    }
    
    public void terminate(){
        isActive = false;
    }
    
    @Override
    public void run(){
        int currTurnTime = maxTurnTime;
        while(isActive){
            try {
                Thread.sleep(1000);
                currTurnTime -= 1;
                view.drawRemaningTime(currTurnTime);
                if(currTurnTime == 0)
                {
                    battleController.hit(AutoTurn.makeMove(battleController.getOpponentField()));
                    view.animate(null);
                    break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(TimerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
