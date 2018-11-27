/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public class BattleController {
    private final Semaphore semaphore1 = new Semaphore(1);
    private final Semaphore semaphore2 = new Semaphore(1);
    private GlobalDisplayConstants displayConstants;
    private Field playerField;
    private Field opponentField;
    private Opponent opponent;
    private Coordinates hitCoordinates;
    private BattleView battleView;
    private boolean moveOrder;
    
    public BattleController(Field playerField, Field opponentField, 
            Opponent opponent, BattleView battleView, boolean initialMoveOrder) {
        this.playerField = playerField;
        playerField.gameInit();
        this.opponentField = opponentField;
        this.opponent = opponent;
        this.battleView = battleView;
        this.moveOrder = initialMoveOrder;
        this.displayConstants = GlobalDisplayConstants.getInstance();
        try {
            semaphore2.acquire();
            semaphore1.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(BattleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gameStart(){
        new Thread(new Runnable(){

            public void run() {
                try{
                    while(playerField.shipsDestroyed() < 10 && opponentField.shipsDestroyed() < 10){
                        Map<Coordinates,Cell> toAnimate = null;
                        if(moveOrder){
                            semaphore2.release();
                            semaphore1.acquire();
                            CellState res = opponent.checkMove(hitCoordinates);
                            switch(res){
                                case HIT_MISSED: 
                                    moveOrder = false;
                                    opponentField.setMiss(hitCoordinates);
                                    toAnimate = new HashMap<Coordinates, Cell>();
                                    toAnimate.put(hitCoordinates,opponentField.cellAt(hitCoordinates));
                                    break;
                                case HIT_DAMAGED:
                                    opponentField.setHit(hitCoordinates);
                                    toAnimate = new HashMap<Coordinates, Cell>();
                                    toAnimate.put(hitCoordinates,opponentField.cellAt(hitCoordinates));
                                    break;
                                case DESTROYED:
                                    Ship destroyedShip = opponent.destroyedShip();
                                    opponentField.add(destroyedShip);
                                    toAnimate = opponentField.destroy(destroyedShip); 
                                    break;
                                default:break;//todo error
                            }
                        }
                        else{
                            Coordinates opponentHitCoordinates = opponent.makeMove();
                            switch(playerField.hit(opponentHitCoordinates)){
                                case HIT_MISSED: 
                                    opponent.responseDelivery(opponentHitCoordinates, CellState.HIT_MISSED);
                                    moveOrder = true;
                                    toAnimate = new HashMap<Coordinates, Cell>();
                                    toAnimate.put(opponentHitCoordinates,
                                            playerField.cellAt(opponentHitCoordinates));
                                    break;
                                case HIT_DAMAGED:
                                    opponent.responseDelivery(opponentHitCoordinates, CellState.HIT_DAMAGED);
                                    toAnimate = new HashMap<Coordinates, Cell>();
                                    toAnimate.put(opponentHitCoordinates,
                                            playerField.cellAt(opponentHitCoordinates));
                                    break;
                                case DESTROYED:
                                    opponent.responseDelivery(opponentHitCoordinates, CellState.DESTROYED);
                                    Ship destroyedShip = playerField.shipAt(opponentHitCoordinates);
                                    opponent.sendDestroyedShip(destroyedShip);
                                    toAnimate = playerField.destroy(destroyedShip); 
                                    break;
                                default:break;//todo error
                            }
                        }
                        battleView.animate(toAnimate);
                    } 
                }
                catch(Exception e){
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, null, e);
                }
            }
            
        }).start();
    }
    
    private void hit(Coordinates hitCoordinates){
        if (semaphore2.tryAcquire()){
            if(opponentField.isHittable(hitCoordinates)){
                this.hitCoordinates = hitCoordinates;
                semaphore1.release();
            }
            else{
                semaphore2.release();
            }
        }
    }
    
    public void hitAttempt(int x, int y){
        if (displayConstants.getOpponentFieldBounds().inBounds(x, y)){
            hit(displayConstants.cellAtOpponentField(x, y));
        }
    }

    public Cell[][] getPlayerGrid() {
        return playerField.getGrid();
    }

    public Cell[][] getOpponentGrid() {
        return opponentField.getGrid();
    }
    
    
}
