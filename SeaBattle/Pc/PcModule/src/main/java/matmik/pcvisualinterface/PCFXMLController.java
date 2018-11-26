/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.pcvisualinterface;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import matmik.*;

/**
 *
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public class PCFXMLController implements Initializable,BattleView {
    
    @FXML
    private TabPane tabPane;
    @FXML
    private Label label;
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
    
    private ShipControl selectedShip;
    private PlacementController placementController;
    private BattleController battleController;
    
    private GlobalDisplayConstants globalDisplayConstants;
    @FXML
    private Label stateLabel;
    @FXML
    private ImageView gameImage;
    @FXML
    private Tab gameTab;
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void asHost(MouseEvent event){
        SocketHostConnector
    }
    
    @FXML
    private void asGuest(MouseEvent event){
        
    }
    
    @FXML
    private void connectTo(MouseEvent event){
        
    }
    
    @FXML
    private void initForPC(MouseEvent event) {
        tabPane.getSelectionModel().select(setDifficultyTab); 
    }
    
    private void drawPlacementBoard(){
        int cellSize = globalDisplayConstants.getShipCellSize();
        Image cell = new Image("cell.png", cellSize, cellSize, true, true);
        Image shipCell = new Image("shipCell.png", cellSize, cellSize, false, true);
        Image candidate = new Image("candidate.png", cellSize, cellSize, true, true);
        Image intersect = new Image("intersect.png", cellSize, cellSize, true, true);
        Image nearshiparea = new Image("nearshiparea.png", cellSize, cellSize, true, true);
        WritableImage placementField = new WritableImage(600, 300);
        int baseOffsetX = globalDisplayConstants.getPlayerFieldBounds().getLeftBound();
        int baseOffsetY = globalDisplayConstants.getPlayerFieldBounds().getTopBound();
        //поле для расстановки
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                transferImage(cell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(placementController.getGrid()[i][j].getState() == CellState.BUSY)
                   transferImage(shipCell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(placementController.getGrid()[i][j].getState() == CellState.CANDIDATE)
                   transferImage(candidate, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(placementController.getGrid()[i][j].getState() == CellState.INTERSECTION)
                   transferImage(intersect, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(placementController.getGrid()[i][j].getState() == CellState.NEAR_SHIP_AREA)
                   transferImage(nearshiparea, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        //банк икорабли в нем
        Bounds bankBounds;
        List<Bounds> shipBounds;
        if (!placementController.bankRotated()){
            bankBounds = globalDisplayConstants.getShipBankBounds();
            shipBounds = globalDisplayConstants.getShipsInBank();
        }
        else{
            bankBounds = globalDisplayConstants.getShipBankBoundsRotated();
            shipBounds = globalDisplayConstants.getShipsInBankRotated();
        }
        int bankHeight = bankBounds.getBottomBound() - bankBounds.getTopBound();
        int bankWidth = bankBounds.getRightBound() - bankBounds.getLeftBound();
        Image bank = new Image("cell.png", bankWidth, bankHeight, false, true);
        transferImage(bank, placementField, bankBounds.getLeftBound(),
                bankBounds.getTopBound());
        for(int i = 0; i < 4; i++){
            int shipHeight = shipBounds.get(i).getBottomBound() - shipBounds.get(i).getTopBound();
            int shipWidth = shipBounds.get(i).getRightBound() - shipBounds.get(i).getLeftBound();
            Image shipImage = new Image("shipCell.png", shipWidth, shipHeight, false, true);
            transferImage(shipImage, placementField, shipBounds.get(i).getLeftBound(),
                    shipBounds.get(i).getTopBound());
        }
        if (selectedShip != null)
        {
            stateLabel.setText("drawing selected ship");
            transferImage(selectedShip.getShipImage(), placementField, selectedShip.getX(), selectedShip.getY());
        }
        placementImage.setImage(placementField);
    }
    
    private void transferImage(Image source, WritableImage scene, int xoffset, int yoffset){
        PixelReader sourceReader = source.getPixelReader();
        PixelWriter sceneWriter = scene.getPixelWriter();
        for(int y = 0; y < source.getHeight(); y++)
            for(int x = 0; x < source.getWidth(); x++)
            {
                sceneWriter.setColor(xoffset + x, yoffset + y, sourceReader.getColor(x, y));
            }
    }

    @FXML
    private void initForHuman(MouseEvent event) {
    }

    @FXML
    private void pickDifficultyEasy(MouseEvent event) {
        tabPane.getSelectionModel().select(placementTab);
        placementController = new PlacementController(
                (int)placementImage.fitWidthProperty().get(),
                (int)placementImage.fitHeightProperty().get());
        globalDisplayConstants = placementController.getDisplayConstants();
        drawPlacementBoard();
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
            Image image;
            if(!pickedShip.isRotated()) image = new Image("shipCell.png", 
                    globalDisplayConstants.getShipCellSize() * pickedShip.getShipLength(), 
                    globalDisplayConstants.getShipCellSize(), false, true); // todo Scale this
            else image = new Image("shipCell.png", 
                    globalDisplayConstants.getShipCellSize() , 
                    globalDisplayConstants.getShipCellSize()* pickedShip.getShipLength(), false, true);
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
        Opponent opponent = new ZeroBrainsMachineOpponent();
        Field opponentsField = new Field();
        battleController = new BattleController(placementController.getField(), 
                opponentsField, opponent, this ,true);
        tabPane.getSelectionModel().select(gameTab); 
        drawGameBoard();
        battleController.gameStart();
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
	
    public void gameEnd(){
		
    }

    private void drawGameBoard() {
        int cellSize = globalDisplayConstants.getShipCellSize();
        Image cell = new Image("cell.png", cellSize, cellSize, true, true);
        Image shipCell = new Image("shipCell.png", cellSize, cellSize, false, true);
        Image candidate = new Image("candidate.png", cellSize, cellSize, true, true);
        Image intersect = new Image("intersect.png", cellSize, cellSize, true, true);
        Image nearshiparea = new Image("nearshiparea.png", cellSize, cellSize, true, true);
        WritableImage placementField = new WritableImage(600, 300);
        int baseOffsetX = globalDisplayConstants.getPlayerFieldBounds().getLeftBound();
        int baseOffsetY = globalDisplayConstants.getPlayerFieldBounds().getTopBound();
        //поле для расстановки
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                transferImage(cell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getPlayerGrid()[i][j].getState() == CellState.BUSY)
                   transferImage(shipCell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getPlayerGrid()[i][j].getState() == CellState.HIT_MISSED)
                   transferImage(candidate, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getPlayerGrid()[i][j].getState() == CellState.HIT_DAMAGED)
                   transferImage(intersect, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getPlayerGrid()[i][j].getState() == CellState.DESTROYED)
                   transferImage(nearshiparea, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        baseOffsetX = globalDisplayConstants.getOpponentFieldBounds().getLeftBound();
        baseOffsetY = globalDisplayConstants.getOpponentFieldBounds().getTopBound();
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                transferImage(cell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getOpponentGrid()[i][j].getState() == CellState.BUSY)
                   transferImage(shipCell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getOpponentGrid()[i][j].getState() == CellState.HIT_MISSED)
                   transferImage(candidate, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getOpponentGrid()[i][j].getState() == CellState.HIT_DAMAGED)
                   transferImage(intersect, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
                if(battleController.getOpponentGrid()[i][j].getState() == CellState.DESTROYED)
                   transferImage(nearshiparea, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        gameImage.setImage(placementField);
    }

    @FXML
    private void hitPosition(MouseEvent event) {
        battleController.hitAttempt((int)event.getX(), (int)event.getY());
    }
}
