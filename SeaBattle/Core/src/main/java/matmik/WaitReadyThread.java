/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Алескандр
 */
public class WaitReadyThread extends Thread{
    private HumanOpponent opponent;
    private Semaphore lock;
    
    public WaitReadyThread(HumanOpponent opponent, Semaphore lock){
        this.opponent = opponent;
        this.lock = lock;
    }
    
    @Override
    public void run(){
        try{
            opponent.ready();
            lock.acquire();
            GlobalStateMachine.getInstance().asyncToBattle();
            lock.release();
        }catch(Exception e){
            GlobalStateMachine.getInstance().disconnectTransition();
        }
    }
    
    public void crashConnection() throws Exception{
        opponent.leave();
    }
}
