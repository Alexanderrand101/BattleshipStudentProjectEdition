/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.core.Persister;

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
    private int maxTurnTime;
    private Coordinates hitCoordinates;
    private View battleView;
    private boolean moveOrder;
    private boolean hideErrors = false;
    
    public boolean getMoveOrder(){
        return moveOrder;
    }
    
    
    public Field getOpponentField() {
        return opponentField;
    }
    
    public BattleController(Field playerField, Field opponentField, 
            Opponent opponent, View battleView, boolean initialMoveOrder, int maxTurnTime) {
        this.playerField = playerField;
        playerField.gameInit();
        this.opponentField = opponentField;
        this.opponent = opponent;
        this.battleView = battleView;
        this.moveOrder = initialMoveOrder;
        this.displayConstants = GlobalDisplayConstants.getInstance();
        this.maxTurnTime = maxTurnTime;
        try {
            semaphore2.acquire();
            semaphore1.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(BattleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gameStart(){
        final BattleController bc = this;
        Thread game = new Thread(new Runnable(){

            public void run() {
                try{
                    while(playerField.shipsDestroyed() < 10 && opponentField.shipsDestroyed() < 10){
                        Map<Coordinates,Cell> toAnimate = null;
                        if(moveOrder){
                            TimerThread ttread = null;
                            if(maxTurnTime > 0){
                                 ttread = new TimerThread(maxTurnTime, battleView, bc);
                                 ttread.setDaemon(true);
                                 ttread.start();
                            }
                            semaphore2.release();
                            semaphore1.acquire();
                            if(ttread != null)
                                 ttread.terminate();
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
//                                    if (destroyedShip != null)
//                                    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME + "1").log(Level.SEVERE, serialize(destroyedShip));
//                                    else 
//                                    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME + "1").log(Level.SEVERE, "null");    
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
//                                    if (destroyedShip != null)
//                                    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME + "1").log(Level.SEVERE, serialize(destroyedShip));
//                                    else 
//                                    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME + "1").log(Level.SEVERE, "null"); 
                                    opponent.sendDestroyedShip(destroyedShip);
                                    toAnimate = playerField.destroy(destroyedShip); 
                                    break;
                                default:break;//todo error
                            }
                        }
                        battleView.animate(toAnimate);
                    }
                    battleView.gameEnd(playerField.shipsDestroyed() < 10);
                }
                catch(Exception e){
                        if(!hideErrors)
                            battleView.showError("your opponent left");
                        GlobalStateMachine.getInstance().back();
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, null, e);
                }
            }
            
        });
        game.setDaemon(true);//a hack but im in too deep already
        game.start();
    }
    
    public void hideErrors(){
        hideErrors = true;
    }
    
    protected void hit(Coordinates hitCoordinates){
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
    
    private String serialize(Ship ship) throws Exception{
        StringWriter str = new StringWriter();
        new Persister().write(ship, str);
        return str.toString();
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
    
    public void saveBattle(String fileName){
        try {
            File file = new File(fileName);
            new Persister().write(new GamePack(playerField, opponentField, (MachineOpponent)opponent, moveOrder), file);
        } catch (Exception ex) {
            battleView.showError("saving failed");
        }
    }
}
