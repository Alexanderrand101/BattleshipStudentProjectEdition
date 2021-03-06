/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.controller.global;

import matmik.view.ViewState;
import matmik.view.View;
import matmik.connector.AbstractConnector;
import matmik.connector.AbstractHostConnector;
import java.io.File;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import matmik.controller.battle.BattleController;
import matmik.model.Field;
import matmik.util.packet.GamePack;
import matmik.opponent.human.GuestOpponent;
import matmik.opponent.human.HostOpponent;
import matmik.opponent.human.HumanOpponent;
import matmik.opponent.machine.OkayBrainsMachineOpponent;
import matmik.opponent.Opponent;
import matmik.opponent.OpponentSubType;
import matmik.opponent.OpponentType;
import matmik.controller.placement.PlacementController;
import matmik.controller.placement.loader.PlacementFileManager;
import matmik.opponent.machine.SimpleBrainsMachineOpponent;
import matmik.opponent.machine.ZeroBrainsMachineOpponent;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Алескандр
 */
public class GlobalStateMachine {
    private static GlobalStateMachine stateMachine;
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
    private String playerName = "Игрок";
    private Random turnOrderGenerator;
    
    
    //arguments
    public OpponentType getOpponentType() {
        return opponentType;
    }
    
    public static GlobalStateMachine getInstance(View view){
        if (stateMachine == null){
            stateMachine = new GlobalStateMachine(view);
        }
        return stateMachine;
    }
    
    public static GlobalStateMachine getInstance(){
        return stateMachine;
    }
    
    public void setPlayerName(String playerName){
        //add vaidation
        this.playerName = playerName;
    }
    
    public String getPlayerName(){
        return playerName;
    }
    
    private GlobalStateMachine(View view){
        this.view = view;
        stateLock = new Semaphore(1);
        currentViewState = ViewState.START_PAGE;
        turnOrderGenerator = new Random();
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
            case MACHINE_NOT_SO_DUMB:
                opponent = new SimpleBrainsMachineOpponent();
                break;
            case MACHINE_OKAY:
                opponent = new OkayBrainsMachineOpponent();
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
            ((HostOpponent)opponent).setMyName(playerName);
            opponentSubType = OpponentSubType.HUMAN_HOST;
            placementController = new PlacementController();
            view.stateTransition(ViewState.PLACEMENT);
            currentViewState = ViewState.PLACEMENT;
    }
    
    public boolean playerNameValid(){
        return !playerName.isEmpty() && playerName.length() <= 15;
    }
    
    public void setUpAsHost(AbstractHostConnector connector, int maxTurnValue){
            view.stateTransition(ViewState.HOST_WAITING_PAGE);
            currentViewState = ViewState.HOST_WAITING_PAGE;
            GuestOpponent guestOpponent = new GuestOpponent();
            guestOpponent.setTurntime(maxTurnValue);
            guestOpponent.setMyName(playerName);
            guestOpponent.setMoveOrder(turnOrderGenerator.nextBoolean());
            waitConnectThread = new WaitConnectThread(guestOpponent, connector, stateLock);
            opponent = guestOpponent;
            opponentSubType = OpponentSubType.HUMAN_GUEST;
            waitConnectThread.setDaemon(true);
            waitConnectThread.start();
    }
    
    public void asyncAcceptedConnection(){
        placementController = new PlacementController();
        view.stateTransition(ViewState.PLACEMENT);
        currentViewState = ViewState.PLACEMENT;
    }
    
    public void toBattle(){
        if(placementController.getField().isPlayValid()){
            if(opponentType == OpponentType.MACHINE){
                gameInit();
                view.stateTransition(ViewState.GAME_PAGE);
                currentViewState = ViewState.GAME_PAGE;
            }
            else if(opponentType == OpponentType.HUMAN){
                view.stateTransition(ViewState.READY_WAITING_PAGE);
                currentViewState = ViewState.READY_WAITING_PAGE;
                waitReadyThread = new WaitReadyThread((HumanOpponent)opponent, stateLock);
                waitReadyThread.setDaemon(true);
                waitReadyThread.start();
            }
        }
        else{
            view.showError("Необходимо расставить все 10 кораблей");
        }
    }
    
    private void gameInit(){
        int maxTurnTime = -1;
        if(OpponentType.HUMAN == opponentType){
            maxTurnTime = ((HumanOpponent)opponent).getTurntime();
            initialMoveOrder = ((HumanOpponent)opponent).isMoveOrder();
        }
        else {
            initialMoveOrder = turnOrderGenerator.nextBoolean();
        }
        battleController = new BattleController(placementController.getField(), new Field(),
        opponent, view, initialMoveOrder, maxTurnTime);
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

    public void disconnectTransition(boolean causedByInternalAction) {
        if(!causedByInternalAction)view.showError("Потеряна связь с противником");
        try {
            hostConnector.close();
            ((HumanOpponent)opponent).leave();
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
                    if(opponentType == OpponentType.HUMAN){
                        battleController.hideErrors();
                        ((HumanOpponent)opponent).leave();
                    }
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

    public void reset() {
        if(OpponentType.HUMAN == opponentType)
            try {
                ((HumanOpponent)opponent).leave();
        } catch (Exception ex) {
            Logger.getLogger(GlobalStateMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
        view.stateTransition(ViewState.START_PAGE);
        currentViewState = ViewState.START_PAGE;
    }

    public String getOpponnetName() {
        if(opponentType == OpponentType.HUMAN){
            return ((HumanOpponent)opponent).getOpponentName();
        }
        else
            return "S.B. AI"; 
    }

    public String getPlayerInGameName() {
        if(opponentType == OpponentType.HUMAN){
            return ((HumanOpponent)opponent).getMyName();
        }
        else
            return playerName; 
    }
    
    public void loadGame(String filename){
        try {
            //validation required
            GamePack gamePack = new Persister().read(GamePack.class, new File(filename));
            if(!gamePack.validate()) throw new Exception("validation exception"); 
            gamePack.getMachineOpponent().getMyField().gameInit();
            battleController = new BattleController(gamePack.getMyField(), gamePack.getOpponentField(),
            gamePack.getMachineOpponent(), view, gamePack.isTurnOrder(), -1);
            view.stateTransition(ViewState.GAME_PAGE);
            currentViewState = ViewState.GAME_PAGE;
        } catch (Exception ex) {
            view.showError("Не удалось открыть файл");
        }
    }
}
