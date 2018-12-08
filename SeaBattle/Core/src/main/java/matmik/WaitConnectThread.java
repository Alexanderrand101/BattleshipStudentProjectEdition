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
public class WaitConnectThread extends Thread{
    private GuestOpponent opponent;
    private AbstractHostConnector abstractHostConnector;
    private Semaphore lock;
    
    
    public WaitConnectThread(GuestOpponent opponent, 
            AbstractHostConnector abstractHostConnector, Semaphore lock){
        this.abstractHostConnector = abstractHostConnector;
        this.opponent = opponent;
        this.lock = lock;
    }
    
    @Override
    public void run(){
        try{
            opponent.setConnectior(abstractHostConnector.open());
            lock.acquire();
            GlobalStateMachine.getInstance().asyncAcceptedConnection();
            lock.release();
        }catch(Exception e){
            GlobalStateMachine.getInstance().closePortTransition();
        }
    }
    
    public void crashConnection() throws Exception{
        
        abstractHostConnector.close();
    }
}
