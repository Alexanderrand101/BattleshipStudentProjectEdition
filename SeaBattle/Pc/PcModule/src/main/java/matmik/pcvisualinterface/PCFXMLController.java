
package matmik.pcvisualinterface;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.simpleframework.xml.core.Persister;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import matmik.*;

/**
 *
 * @author 
 */
public class PCFXMLController implements Initializable,View {
 
    @FXML
    private Button saveGameBtn;
    @FXML
    private Spinner<Integer> maxTimeSpinner;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label label;
    @FXML
    private Label myNameLabel;
    @FXML
    private Label opponentNameLabel;
    @FXML
    private ImageView placementImage;
    @FXML
    private Tab titleTab;
    @FXML
    private Tab setDifficultyTab;
    @FXML
    private Tab placementTab;
    @FXML
    private Tab inputIpTab;
    @FXML
    private TextField inputIP;
    @FXML
    private Tab hostOrGuestTab;
    @FXML
    private Tab hostWaitTab;
    @FXML
    private Tab hostTab;
    @FXML
    private Tab readyWaitTab;
    @FXML
    private AnchorPane mainAnchor; 
    @FXML
    private Label timerLabel;
    @FXML 
    private Label quantity1;
    @FXML
    private Label quantity2;
    @FXML 
    private Label quantity3;
    @FXML
    private Label quantity4;
    @FXML
    private TextField nameTB;
    
    private Image ver1;
    private Image hor1;
    private Image ver2;
    private Image hor2;
    private Image ver3;
    private Image hor3;
    private Image ver4;
    private Image hor4;
    
    private boolean isHost;
    private HumanOpponent hopponent;
    private ShipControl selectedShip;
    private PlacementController placementController;
    private BattleController battleController;
    private GlobalStateMachine globalStateMachine;
    
    private GlobalDisplayConstants globalDisplayConstants;

    @FXML
    private ImageView gameImage;
    @FXML
    private Tab gameTab;
    
    private int sizex, sizey;
    
    private void loadImages(){
        int cellSize = globalDisplayConstants.getShipCellSize();
        ver1 = new Image("1ver.png", cellSize, cellSize, true, true);
        ver2 = new Image("2ver.png", cellSize, cellSize * 2, true, true);
        ver3 = new Image("3ver.png", cellSize, cellSize * 3, true, true);
        ver4 = new Image("4ver.png", cellSize, cellSize * 4, true, true);
        hor1 = new Image("1hor.png", cellSize, cellSize, true, true);
        hor2 = new Image("2hor.png", cellSize * 2, cellSize, true, true);
        hor3 = new Image("3hor.png", cellSize * 3, cellSize, true, true);
        hor4 = new Image("4hor.png", cellSize * 4, cellSize, true, true);
    }
    
    private void reDrawView(){
        globalStateMachine = GlobalStateMachine.getInstance(this);
        globalDisplayConstants = GlobalDisplayConstants.getInstanceAndUpdate();
//        placementImage.setFitHeight(sizey * 0.8);
//        placementImage.setFitWidth(sizex);
//        gameImage.setFitHeight(sizey * 0.8);
//        gameImage.setFitWidth(sizex);
        globalDisplayConstants.CalcConstants((int)placementImage.fitWidthProperty().get(),
                (int)(placementImage.fitHeightProperty().get() * 0.9));
        gameImage.setFitHeight(placementImage.fitHeightProperty().get());
        gameImage.setFitWidth(placementImage.fitWidthProperty().get());
        myNameLabel.setLayoutX(gameImage.layoutXProperty().get() + globalDisplayConstants.getPlayerNameLabelBounds().getLeftBound());
        myNameLabel.setLayoutY(gameImage.layoutYProperty().get() + globalDisplayConstants.getPlayerNameLabelBounds().getTopBound() - 12);
        myNameLabel.setMinWidth(globalDisplayConstants.getPlayerNameLabelBounds().getRightBound() - 
                globalDisplayConstants.getPlayerNameLabelBounds().getLeftBound());
        opponentNameLabel.setLayoutX(gameImage.layoutXProperty().get() + globalDisplayConstants.getOpponentNameLabelBounds().getLeftBound());
        opponentNameLabel.setLayoutY(gameImage.layoutYProperty().get() + globalDisplayConstants.getOpponentNameLabelBounds().getTopBound() - 12);
        opponentNameLabel.setMinWidth(globalDisplayConstants.getOpponentNameLabelBounds().getRightBound() - 
                globalDisplayConstants.getOpponentNameLabelBounds().getLeftBound());
        timerLabel.setLayoutX(gameImage.layoutXProperty().get() + globalDisplayConstants.getTimerLabelBounds().getLeftBound());
        timerLabel.setLayoutY(gameImage.layoutYProperty().get() + globalDisplayConstants.getTimerLabelBounds().getTopBound() - 12);
        timerLabel.setMinWidth(globalDisplayConstants.getTimerLabelBounds().getRightBound() - 
                globalDisplayConstants.getTimerLabelBounds().getLeftBound());
    }
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        initialize();
    }    
    
//    public PCFXMLController(int x, int y){
//        sizex = x;
//        sizey = y;
//    }
    
    @FXML
    public void initialize(){   
        reDrawView();
        loadImages();
        nameTB.focusedProperty().addListener(
                new ChangeListener<Boolean>(){
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                       if(!t1){
                           globalStateMachine.setPlayerName(nameTB.getText());
                           nameTB.setText(globalStateMachine.getPlayerName());
                       }
                    }                 
                }
        );
    }

    @FXML
    private void openConnection(MouseEvent event) throws IOException{
        if(globalStateMachine.playerNameValid())
            globalStateMachine.setUpAsHost(new SocketHostConnector(4444), maxTimeSpinner.getValue());
        else
            showError("Представьтесь. Введите имя от 0 до 15 символов");
    }
    
    @FXML
    private void asHost(MouseEvent event){
//    	isHost = true;
//    	hopponent = new GuestOpponent(new SocketHostConnector(4444).open());
//    	tabPane.getSelectionModel().select(placementTab);
////        placementController = new PlacementController(
////                (int)placementImage.fitWidthProperty().get(),
////                (int)placementImage.fitHeightProperty().get());
//        globalDisplayConstants = placementController.getDisplayConstants();
//        drawPlacementBoard();
//    	tabPane.getSelectionModel().select(placementTab);
        globalStateMachine.pickedAsHost();
    }
    
    @FXML
    private void asGuest(MouseEvent event){
    	globalStateMachine.pickedAsGuest();
    }
    
    @FXML
    private void connectTo(MouseEvent event) throws IOException{
//    	hopponent = new HostOpponent(new SocketConnector(inputIP.getText(), 4444));
//    	tabPane.getSelectionModel().select(placementTab);
//        placementController = new PlacementController(
//                (int)placementImage.fitWidthProperty().get(),
//                (int)placementImage.fitHeightProperty().get());
//        globalDisplayConstants = placementController.getDisplayConstants();
//        drawPlacementBoard();
//        tabPane.getSelectionModel().select(placementTab);
        if(globalStateMachine.playerNameValid())
            globalStateMachine.connectAsGuest(new SocketConnector(inputIP.getText(), 4444));
        else
            showError("Представьтесь. Введите имя от 0 до 15 символов");
    }
    
    @FXML
    private void initForPC(MouseEvent event) {
        globalStateMachine.pickedComputer();
    }
    
    private void drawPlacementBoard(){
        int cellSize = globalDisplayConstants.getShipCellSize();
        Image cell = new Image("clean1.png", cellSize, cellSize, true, true);
        Image shipCell = new Image("shipCell.png", cellSize, cellSize, false, true);
        Image candidate = new Image("candidate.png", cellSize, cellSize, true, true);
        Image intersect = new Image("intersect.png", cellSize, cellSize, true, true);
        Image nearshiparea = new Image("nearshiparea.png", cellSize, cellSize, true, true);
        WritableImage placementField = new WritableImage((int)placementImage.fitWidthProperty().get(),
                (int)placementImage.fitHeightProperty().get());
        int baseOffsetX = globalDisplayConstants.getPlayerFieldBounds().getLeftBound();
        int baseOffsetY = globalDisplayConstants.getPlayerFieldBounds().getTopBound();
        //compilation failure
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                transferImage(cell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
//                if(placementController.getGrid()[i][j].getState() == CellState.BUSY)
//                   transferImage(shipCell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(placementController.getGrid()[i][j].getState() == CellState.CANDIDATE)
                   transferImage(candidate, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(placementController.getGrid()[i][j].getState() == CellState.INTERSECTION)
                   transferImage(intersect, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(placementController.getGrid()[i][j].getState() == CellState.NEAR_SHIP_AREA)
                   transferImage(nearshiparea, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        for(Ship ship: placementController.getField().getShips()){
            if(ship.isRotated()){
                switch(ship.getShipLength()){
                    case 1: transferImage2(ver1, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 2: transferImage2(ver2, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 3: transferImage2(ver3, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 4: transferImage2(ver4, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                } 
            }
            else{
                switch(ship.getShipLength()){
                    case 1 :transferImage2(hor1, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 2: transferImage2(hor2, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;    
                    case 3: transferImage2(hor3, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 4: transferImage2(hor4, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                }
            }
        }
        //compilation failure
        Bounds bankBounds;
        List<Bounds> shipBounds;
        List<Bounds> labelBounds;
        if (!placementController.bankRotated()){
            bankBounds = globalDisplayConstants.getShipBankBoundsRotated();
            shipBounds = globalDisplayConstants.getShipsInBank();
            labelBounds = globalDisplayConstants.getLabelBounds();
        }
        else{
            bankBounds = globalDisplayConstants.getShipBankBoundsRotated();
            shipBounds = globalDisplayConstants.getShipsInBankRotated();
            labelBounds = globalDisplayConstants.getLabelBoundsRotated();
        }
        int bankHeight = bankBounds.getBottomBound() - bankBounds.getTopBound();
        int bankWidth = bankBounds.getRightBound() - bankBounds.getLeftBound();
        Image bank = new Image("clean1.png", bankWidth, bankHeight, false, true);
        transferImage(bank, placementField, bankBounds.getLeftBound(),
                bankBounds.getTopBound());
        for(int i = 0; i < 4; i++){
            Image shipImage = null;
            if(placementController.bankRotated()){
                switch(i + 1){
                    case 1: shipImage = ver1;break;
                    case 2: shipImage = ver2;break;
                    case 3: shipImage = ver3;break;
                    case 4: shipImage = ver4;break;
                } 
            }
            else{
                switch(i + 1){
                    case 1: shipImage = hor1;break;
                    case 2: shipImage = hor2;break;
                    case 3: shipImage = hor3;break;
                    case 4: shipImage = hor4;break;
                }
            }
            transferImage2(shipImage, placementField, shipBounds.get(i).getLeftBound(),
                    shipBounds.get(i).getTopBound());
        }
        quantity1.setText(Integer.toString(placementController.getShipBank().getShipAmountOfLength(1)));
        quantity1.setLayoutX(labelBounds.get(0).getLeftBound() + placementImage.layoutXProperty().get());
        quantity1.setLayoutY(labelBounds.get(0).getTopBound()+ placementImage.layoutYProperty().get());
        quantity2.setText(Integer.toString(placementController.getShipBank().getShipAmountOfLength(2)));
        quantity2.setLayoutX(labelBounds.get(1).getLeftBound() + placementImage.layoutXProperty().get());
        quantity2.setLayoutY(labelBounds.get(1).getTopBound()+ placementImage.layoutYProperty().get());
        quantity3.setText(Integer.toString(placementController.getShipBank().getShipAmountOfLength(3)));
        quantity3.setLayoutX(labelBounds.get(2).getLeftBound() + placementImage.layoutXProperty().get());
        quantity3.setLayoutY(labelBounds.get(2).getTopBound()+ placementImage.layoutYProperty().get());
        quantity4.setText(Integer.toString(placementController.getShipBank().getShipAmountOfLength(4)));
        quantity4.setLayoutX(labelBounds.get(3).getLeftBound() + placementImage.layoutXProperty().get());
        quantity4.setLayoutY(labelBounds.get(3).getTopBound() + placementImage.layoutYProperty().get());
        if (selectedShip != null)
        {
            transferImage2(selectedShip.getShipImage(), placementField, selectedShip.getX(), selectedShip.getY());
        }
        placementImage.setImage(placementField);
    }
    
    private void transferImage(Image source, WritableImage scene, int xoffset, int yoffset){
        PixelReader sourceReader = source.getPixelReader();
        PixelWriter sceneWriter = scene.getPixelWriter();
        for(int y = 0; y < source.getHeight(); y++)
            for(int x = 0; x < source.getWidth(); x++)
            {
                Color color = sourceReader.getColor(x, y);
                if (Color.WHITE.equals(color))
                    continue;
                sceneWriter.setColor(xoffset + x, yoffset + y, sourceReader.getColor(x, y));
            }
    }
    
    private void transferImage2(Image source, WritableImage scene, int xoffset, int yoffset){
        PixelReader sourceReader = source.getPixelReader();
        PixelWriter sceneWriter = scene.getPixelWriter();
        for(int y = 0; y < source.getHeight(); y++)
            for(int x = 0; x < source.getWidth(); x++)
            {
                Color color = sourceReader.getColor(x, y);
                if ((color.getRed() > 0.85) && (color.getBlue() > 0.85) && (color.getGreen() > 0.85))
                    continue;
                sceneWriter.setColor(xoffset + x, yoffset + y, sourceReader.getColor(x, y));
            }
    }

    @FXML
    private void initForHuman(MouseEvent event) {
    	globalStateMachine.pickedHuman();
    }

    @FXML
    private void pickDifficultyEasy(MouseEvent event) {
        globalStateMachine.pickedDifficulty(OpponentSubType.MACHINE_DUMB);
    }
    
    @FXML
    private void pickDifficultyNormal(ActionEvent e){
        globalStateMachine.pickedDifficulty(OpponentSubType.MACHINE_NOT_SO_DUMB);
    }
    
    @FXML
    private void pickDifficultyHard(ActionEvent e){
        globalStateMachine.pickedDifficulty(OpponentSubType.MACHINE_OKAY);
    }

    @FXML
    private void clearBoard(MouseEvent event) {
        placementController.clearField();
        drawPlacementBoard();
    }

    @FXML
    private void dropShip(MouseEvent event) {
        if(selectedShip != null)
            placementController.dropShip((int)event.getX(), (int)event.getY());
        selectedShip = null;
        drawPlacementBoard();
    }

    @FXML 
    private void back(MouseEvent event){
        globalStateMachine.back();
    }
    
    @FXML
    private void moveShip(MouseEvent event) {
        if(selectedShip != null){
            selectedShip.setX((int)event.getX());
            selectedShip.setY((int)event.getY());
            placementController.highlight((int)event.getX(), (int)event.getY());
            drawPlacementBoard();
        }
    }

    @FXML
    private void pickShip(MouseEvent event) {
        Ship pickedShip = placementController.pickShip((int)event.getX(), (int)event.getY());
        if(pickedShip != null){
            Image image = null;
            if(pickedShip.isRotated()){
                switch(pickedShip.getShipLength()){
                    case 1: image = ver1; break;
                    case 2: image = ver2; break;   
                    case 3: image = ver3; break;
                    case 4: image = ver4; break;
                } 
            }
            else{
                switch(pickedShip.getShipLength()){
                    case 1: image = hor1; break;
                    case 2: image = hor2; break;   
                    case 3: image = hor3; break;
                    case 4: image = hor4; break;
                }
            }
            selectedShip = new ShipControl((int)event.getX(), (int)event.getY(), image);
        }
    }
    
    private boolean checkBounds(int x, int y, int ox, int oy, int width, int height)
    {
        return (ox <= x) && (ox + width >= x) && (oy <= y) && (oy + height >= y);
    }

    @FXML
    private void placeShips(MouseEvent event) {
        placementController.autoPlaceShips();
        drawPlacementBoard();
    }

    @FXML
    private void rotateBank(MouseEvent event) {
        placementController.rotateBank();
        drawPlacementBoard();
    }

    @FXML
    private void toBattle(MouseEvent event) {
//    	Opponent opponent = null;
//    	if (hopponent == null) {
//    		opponent = new ZeroBrainsMachineOpponent();
//    	}else {
//    		if(isHost) {
//    			try {
//					((GuestOpponent)hopponent).acceptReady();
//				} catch (Exception e) {
//					Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, null, e);
//				}
//    		}
//    		else ((HostOpponent)hopponent).ready();
//                opponent = hopponent;
//    	}
//        Field opponentsField = new Field();
//        battleController = new BattleController(placementController.getField(), 
//                opponentsField, opponent, this ,isHost);
//        tabPane.getSelectionModel().select(gameTab); 
//        drawGameBoard();
//        battleController.gameStart();
        globalStateMachine.toBattle();
    }

    public void animate(Map<Coordinates,Cell> cellsToAnimate) {
        Platform.runLater(
                new Thread()
                {
                    @Override
                    public void run(){
                        drawGameBoard();
                    }
                }
        );
    }
	
    public void gameEnd(final boolean result){
	Platform.runLater(
                new Thread()
                {
                    @Override
                    public void run(){
                        if (result) 
                            new Alert(Alert.AlertType.NONE, "Вы победили", ButtonType.OK).showAndWait();
                        else
                            new Alert(Alert.AlertType.NONE, "Вы проиграли", ButtonType.OK).showAndWait();
                         GlobalStateMachine.getInstance().reset();
                    }
                }
        );	
    }

    private void drawGameBoard() {
        int cellSize = globalDisplayConstants.getShipCellSize();
        Image cell = new Image("clean1.png", cellSize, cellSize, true, true);
        Image shipCell = new Image("shipCell.png", cellSize, cellSize, false, true);
        Image candidate = new Image("missed.png", cellSize, cellSize, true, true);
        Image intersect = new Image("damaged2.png", cellSize, cellSize, true, true);
        Image nearshiparea = new Image("destroyed.png", cellSize, cellSize, true, true);
        Image myTurn = new Image("myTurn.png", cellSize, cellSize, true, true);
        Image opponentTurn = new Image("opponentTurn.png", cellSize, cellSize, true, true);
        WritableImage placementField = new WritableImage((int)placementImage.fitWidthProperty().get(),
                (int)placementImage.fitHeightProperty().get());
        int baseOffsetX = globalDisplayConstants.getPlayerFieldBounds().getLeftBound();
        int baseOffsetY = globalDisplayConstants.getPlayerFieldBounds().getTopBound();
        int arrowOffsetX = globalDisplayConstants.getTurnArrowBounds().getLeftBound();
        int arrowOffsetY = globalDisplayConstants.getTurnArrowBounds().getTopBound();
        if(battleController.getMoveOrder()) 
            transferImage(myTurn, placementField, arrowOffsetX, arrowOffsetY);
        else
            transferImage(opponentTurn, placementField, arrowOffsetX, arrowOffsetY);
        //compilation failure
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                //if(battleController.getPlayerGrid()[i][j].isFree())
                    transferImage(cell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        for(Ship ship: battleController.getPlayerField().getShips()){
            if(ship.isRotated()){
                switch(ship.getShipLength()){
                    case 1: transferImage2(ver1, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 2: transferImage2(ver2, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;   
                    case 3: transferImage2(ver3, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 4: transferImage2(ver4, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                } 
            }
            else{
                switch(ship.getShipLength()){
                    case 1 :transferImage2(hor1, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 2: transferImage2(hor2, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;    
                    case 3: transferImage2(hor3, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 4: transferImage2(hor4, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                }
            }
        }
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                if(battleController.getPlayerGrid()[i][j].getState() == CellState.HIT_MISSED)
                   transferImage2(candidate, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getPlayerGrid()[i][j].getState() == CellState.HIT_DAMAGED)
                   transferImage2(intersect, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getPlayerGrid()[i][j].getState() == CellState.DESTROYED)
                   transferImage2(nearshiparea, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        baseOffsetX = globalDisplayConstants.getOpponentFieldBounds().getLeftBound();
        baseOffsetY = globalDisplayConstants.getOpponentFieldBounds().getTopBound();
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                //if(battleController.getOpponentGrid()[i][j].isFree())
                    transferImage(cell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        for(Ship ship: battleController.getOpponentField().getShips()){
            if(ship.isRotated()){
                switch(ship.getShipLength()){
                    case 1: transferImage2(ver1, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 2: transferImage2(ver2, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;   
                    case 3: transferImage2(ver3, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 4: transferImage2(ver4, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                } 
            }
            else{
                switch(ship.getShipLength()){
                    case 1 :transferImage2(hor1, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 2: transferImage2(hor2, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;    
                    case 3: transferImage2(hor3, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                    case 4: transferImage2(hor4, placementField, baseOffsetX + ship.getBow().getJ() * cellSize, baseOffsetY + ship.getBow().getI() * cellSize);break;
                }
            }
        }
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                if(battleController.getOpponentGrid()[i][j].getState() == CellState.HIT_MISSED)
                   transferImage2(candidate, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getOpponentGrid()[i][j].getState() == CellState.HIT_DAMAGED)
                   transferImage2(intersect, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getOpponentGrid()[i][j].getState() == CellState.DESTROYED)
                   transferImage2(nearshiparea, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        gameImage.setImage(placementField);
        
    }

    @FXML
    private void hitPosition(MouseEvent event) {
        battleController.hitAttempt((int)event.getX(), (int)event.getY());
    }

    @FXML
    private void placementFileManager(ActionEvent event){
        globalStateMachine.loadPlacementsTransition("placements/");
    }
    public void stateTransition(final ViewState state){
        Platform.runLater(new Runnable(){

            public void run() {
               uiStateTransition(state);
            }
                    
        });
    }
    
    private void uiStateTransition(ViewState state) {
        switch(state){
            case START_PAGE:
                tabPane.getSelectionModel().select(titleTab);
                break;
            case LOAD_SAVE:
                break;
            case LOAD_PLACEMENT:
                showPlacementLoaderWindow();
                break;
            case PLACEMENT:
                placementController = globalStateMachine.getPlacementController();
                drawPlacementBoard();
                tabPane.getSelectionModel().select(placementTab);
                break;
            case DIFFICULTY_SELECTOR:
                tabPane.getSelectionModel().select(setDifficultyTab);
                break;
            case HOST_OR_GUEST:
                tabPane.getSelectionModel().select(hostOrGuestTab);
                break;
            case GUEST_PAGE:
                tabPane.getSelectionModel().select(inputIpTab);
                break;
            case HOST_PAGE:
                maxTimeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,60,10));
                tabPane.getSelectionModel().select(hostTab);
                break;
            case HOST_WAITING_PAGE:
                tabPane.getSelectionModel().select(hostWaitTab);
                break;
            case READY_WAITING_PAGE:
                tabPane.getSelectionModel().select(readyWaitTab);
                break;
            case GAME_PAGE:
                battleController = globalStateMachine.getBattleController();
                drawGameBoard();
                setNames();
                if(globalStateMachine.getOpponentType() == OpponentType.HUMAN){
                    saveGameBtn.setVisible(false);
                }
                else{
                    saveGameBtn.setVisible(true);
                }
                battleController.gameStart();
                tabPane.getSelectionModel().select(gameTab);
                break;
            default:;
        }
    }
    


    private void showPlacementLoaderWindow() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PlacementLoaderFXML.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tabPane.getScene().getWindow());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PCFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showError(final String errorMessage) {
        Platform.runLater(
                new Thread()
                {
                    @Override
                    public void run(){
                            new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK).showAndWait();
                    }
                }
        );	
    }

    public void setNames(){
        myNameLabel.setText(globalStateMachine.getPlayerInGameName());
        opponentNameLabel.setText(globalStateMachine.getOpponnetName());
    }
    
    public void drawRemaningTime(final int seconds) {
        Platform.runLater(
                new Thread()
                {
                    @Override
                    public void run(){
                            timerLabel.setText(Integer.toString(seconds));
                    }
                }
        );	
    }
    
    @FXML
    private void loadFromFile(MouseEvent e){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить игру");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файлы игрового сеанса", "*.game"));
        File file = fileChooser.showOpenDialog(tabPane.getScene().getWindow());
        if (file != null){
            globalStateMachine.loadGame(file.getAbsolutePath());
        }
    }
    
    @FXML 
    private void saveGame(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("save Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game files", "*.game"));
        File file = fileChooser.showSaveDialog(tabPane.getScene().getWindow());
        if (file != null){
            battleController.saveBattle(file.getAbsolutePath());
        }
    }
    
    @FXML
    private void settingsDialog(ActionEvent e){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/OptionsFXML.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tabPane.getScene().getWindow());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PCFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
