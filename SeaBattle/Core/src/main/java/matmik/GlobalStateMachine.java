/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Алескандр
 */
public class GlobalStateMachine {
    private static GlobalStateMachine stateMachine;
    //arguments
    private OpponentType opponentType;
    private OpponentSubType opponentSubType;
    private Opponent opponent;
    private View view;
    private PlacementController placementController;
    private BattleController battleController;
    private boolean initialMoveOrder;
    private WaitConnectThread waitConnectThread;
    private WaitReadyThread waitReadyThread;
    private Semaphore stateLock;
    private ViewState currentViewState;
    private AbstractHostConnector hostConnector;
    private PlacementFileManager placementFileManager;
    
    public static GlobalStateMachine getInstance(View view){
        if (stateMachine == null){
            stateMachine = new GlobalStateMachine(view);
        }
        return stateMachine;
    }
    
    public static GlobalStateMachine getInstance(){
        return stateMachine;
    }
    
    
    private GlobalStateMachine(View view){
        this.view = view;
        stateLock = new Semaphore(1);
    }
    
    public void pickedComputer(){
        opponentType = OpponentType.MACHINE;
        view.stateTransition(ViewState.DIFFICULTY_SELECTOR);
        currentViewState = ViewState.DIFFICULTY_SELECTOR;
    }
    
    public void pickedHuman(){
        opponentType = OpponentType.HUMAN;
        view.stateTransition(ViewState.HOST_OR_GUEST);
        currentViewState = ViewState.HOST_OR_GUEST;
        //
    }
    
    public void pickedDifficulty(OpponentSubType difficultyLVL){
        opponentSubType = difficultyLVL;
        switch(difficultyLVL){
            case MACHINE_DUMB:
                opponent = new ZeroBrainsMachineOpponent();
                break;
            default:;
        }
        placementController = new PlacementController();
        view.stateTransition(ViewState.PLACEMENT);
        currentViewState = ViewState.PLACEMENT;
        //
    }
    
    public void pickedAsHost(){
        view.stateTransition(ViewState.HOST_PAGE);
        currentViewState = ViewState.HOST_PAGE;
    }
    
    public void pickedAsGuest(){
        view.stateTransition(ViewState.GUEST_PAGE);
        currentViewState = ViewState.GUEST_PAGE;
    }
    
    public void connectAsGuest(AbstractConnector connector){ 
        opponent = new HostOpponent(connector);
        opponentSubType = OpponentSubType.HUMAN_HOST;
        placementController = new PlacementController();
        view.stateTransition(ViewState.PLACEMENT);
        currentViewState = ViewState.PLACEMENT;
    }
    
    public void setUpAsHost(AbstractHostConnector connector){
        view.stateTransition(ViewState.HOST_WAITING_PAGE);
        currentViewState = ViewState.HOST_WAITING_PAGE;
        GuestOpponent guestOpponent = new GuestOpponent();
        waitConnectThread = new WaitConnectThread(guestOpponent, connector, stateLock);
        opponent = guestOpponent;
        opponentSubType = OpponentSubType.HUMAN_GUEST;
        waitConnectThread.start();
    }
    
    public void asyncAcceptedConnection(){
        placementController = new PlacementController();
        view.stateTransition(ViewState.PLACEMENT);
        currentViewState = ViewState.PLACEMENT;
    }
    
    public void toBattle(){
        if(opponentType == OpponentType.MACHINE){
            gameInit();
            view.stateTransition(ViewState.GAME_PAGE);
            currentViewState = ViewState.GAME_PAGE;
        }
        else if(opponentType == OpponentType.HUMAN){
            view.stateTransition(ViewState.READY_WAITING_PAGE);
            currentViewState = ViewState.READY_WAITING_PAGE;
            waitReadyThread = new WaitReadyThread((HumanOpponent)opponent, stateLock);
            waitReadyThread.start();
        }
    }
    
    private void gameInit(){
        if(OpponentSubType.HUMAN_GUEST == opponentSubType) initialMoveOrder = true;
        else initialMoveOrder = false;
        battleController = new BattleController(placementController.getField(), new Field(),
        opponent, view, initialMoveOrder);
    }
    
    public PlacementController getPlacementController(){
        return placementController;
    }
    
    public BattleController getBattleController(){
        return battleController;
    }

    public void asyncToBattle() {
        gameInit();
        view.stateTransition(ViewState.GAME_PAGE);
        currentViewState = ViewState.GAME_PAGE;
    }

    public void disconnectTransition() {
        try {
            hostConnector.close();
        } catch (Exception ex) {
            Logger.getLogger(GlobalStateMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
        view.stateTransition(ViewState.START_PAGE);
        currentViewState = ViewState.START_PAGE;
    }

    void closePortTransition() {
        view.stateTransition(ViewState.START_PAGE);
        currentViewState = ViewState.START_PAGE;
    }
    
    public void loadPlacementsTransition(String pathToPlacements){
        placementFileManager = new PlacementFileManager(pathToPlacements, placementController.getField());
        view.stateTransition(ViewState.LOAD_PLACEMENT);
        currentViewState = ViewState.LOAD_PLACEMENT;
    }
    
    public PlacementFileManager getPlacementFileManager(){
        return placementFileManager;
    }
    
    public void buildPlacementController(Field field){
        placementController = new PlacementController(field);
    }
    
    public void back() {
        try {
            stateLock.acquire();
            switch(currentViewState){
                case START_PAGE:
                    break;
                case LOAD_SAVE:
                    break;
                case LOAD_PLACEMENT:
                    view.stateTransition(ViewState.PLACEMENT);
                    currentViewState = ViewState.PLACEMENT;
                    break;
                case PLACEMENT:
                    view.stateTransition(ViewState.START_PAGE);
                    currentViewState = ViewState.START_PAGE;
                    if(opponentType == OpponentType.HUMAN)
                        ((HumanOpponent)opponent).leave();
                    break;
                case DIFFICULTY_SELECTOR:
                    view.stateTransition(ViewState.START_PAGE);
                    currentViewState = ViewState.START_PAGE;
                    break;
                case HOST_OR_GUEST:
                    view.stateTransition(ViewState.START_PAGE);
                    currentViewState = ViewState.START_PAGE;
                    break;
                case GUEST_PAGE:
                    view.stateTransition(ViewState.HOST_OR_GUEST);
                    currentViewState = ViewState.HOST_OR_GUEST;
                    break;
                case HOST_PAGE:
                    view.stateTransition(ViewState.HOST_OR_GUEST);
                    currentViewState = ViewState.HOST_OR_GUEST;
                    break;
                case HOST_WAITING_PAGE:
                    waitConnectThread.crashConnection();
                    break;
                case READY_WAITING_PAGE:
                    waitReadyThread.crashConnection();
                    break;
                case GAME_PAGE:
                    view.stateTransition(ViewState.START_PAGE);
                    currentViewState = ViewState.START_PAGE;
                    if(opponentType == OpponentType.HUMAN)
                        ((HumanOpponent)opponent).leave();
                    break;
                default:;
            }
            stateLock.release();
        } catch (InterruptedException ex) {
            //все ужасно все валим
        } catch (Exception ex) {
            //надо будет эксепшоны попричесать
            Logger.getLogger(GlobalStateMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
