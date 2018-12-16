/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.view.pc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import matmik.model.Field;
import matmik.controller.global.GlobalStateMachine;
import matmik.controller.placement.loader.LoadResult;
import matmik.controller.placement.loader.PlacementFileManager;
import matmik.view.display.PlacementLoaderDisplayConstants;
import matmik.model.Ship;

/**
 *
 * @author Алескандр
 */
public class PlacementLoaderController implements Initializable {
    
    @FXML
    ImageView placementImage1;
    @FXML
    ImageView placementImage2;
    @FXML
    ImageView placementImage3;
    @FXML
    ImageView placementImage4;
    @FXML 
    AnchorPane anchor;
    @FXML
    Button deleteBTN;
    @FXML
    Button pickBTN;
    
    private Image ver1;
    private Image hor1;
    private Image ver2;
    private Image hor2;
    private Image ver3;
    private Image hor3;
    private Image ver4;
    private Image hor4;
    
    private int selectedID = -1;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        initialize();
    }
    
    public PlacementLoaderController(){}
    
    @FXML
    public void initialize() {
        PlacementLoaderDisplayConstants.getInstance().calcConstants((int)placementImage1.getFitWidth(),
                (int)placementImage1.getFitHeight());
        int cellSize = PlacementLoaderDisplayConstants.getInstance().getShipCellSize();
        ver1 = new Image("1ver.png", cellSize, cellSize, true, true);
        ver2 = new Image("2ver.png", cellSize, cellSize * 2, true, true);
        ver3 = new Image("3ver.png", cellSize, cellSize * 3, true, true);
        ver4 = new Image("4ver.png", cellSize, cellSize * 4, true, true);
        hor1 = new Image("1hor.png", cellSize, cellSize, true, true);
        hor2 = new Image("2hor.png", cellSize * 2, cellSize, true, true);
        hor3 = new Image("3hor.png", cellSize * 3, cellSize, true, true);
        hor4 = new Image("4hor.png", cellSize * 4, cellSize, true, true);
        drawImages();
    }
    
    private void drawImages(){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadResult(0) == LoadResult.VALID) drawField(placementImage1, pfm.loadPlacement(0), selectedID == 0);
        else if(pfm.loadResult(0) == LoadResult.NONE) drawEmpty(placementImage1);
        else drawError(placementImage1);
        if (pfm.loadResult(1) == LoadResult.VALID) drawField(placementImage2, pfm.loadPlacement(1), selectedID == 1);
        else if(pfm.loadResult(1) == LoadResult.NONE) drawEmpty(placementImage2);
        else drawError(placementImage2);
        if (pfm.loadResult(2) == LoadResult.VALID) drawField(placementImage3, pfm.loadPlacement(2), selectedID == 2);
        else if(pfm.loadResult(2) == LoadResult.NONE) drawEmpty(placementImage3);
        else drawError(placementImage3);
        if (pfm.loadResult(3) == LoadResult.VALID) drawField(placementImage4, pfm.loadPlacement(3), selectedID == 3);
        else if(pfm.loadResult(3) == LoadResult.NONE) drawEmpty(placementImage4);
        else drawError(placementImage4);
    }
    
    private void drawField(ImageView image, Field field, boolean isSelected){
        PlacementLoaderDisplayConstants pldc = PlacementLoaderDisplayConstants.getInstance();
        int cellSize = pldc.getShipCellSize();
        Image cell = new Image("clean1.png", cellSize, cellSize, true, true);
        Image border = new Image("shipCell.png", (int)image.fitWidthProperty().get(),
                (int)image.fitHeightProperty().get(), true, true);
        Image shipCell = new Image("shipCell.png", cellSize, cellSize, false, true);
        WritableImage placementField = new WritableImage((int)image.fitWidthProperty().get(),
                (int)image.fitHeightProperty().get());
        int baseOffsetX = pldc.getFieldBounds().getLeftBound();
        int baseOffsetY = pldc.getFieldBounds().getTopBound();
        //Ð¿Ð¾Ð»Ðµ Ð´Ð»Ñ� Ñ€Ð°Ñ�Ñ�Ñ‚Ð°Ð½Ð¾Ð²ÐºÐ¸
        if(isSelected)
            transferImage(border, placementField, 0, 0);
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                transferImage(cell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        for(Ship ship: field.getShips()){
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
        image.setImage(placementField);
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
    
    private void transferImage(Image source, WritableImage scene, int xoffset, int yoffset){
        PixelReader sourceReader = source.getPixelReader();
        PixelWriter sceneWriter = scene.getPixelWriter();
        for(int y = 0; y < source.getHeight(); y++)
            for(int x = 0; x < source.getWidth(); x++)
            {
                sceneWriter.setColor(xoffset + x, yoffset + y, sourceReader.getColor(x, y));
            }
    }
    
    private void drawEmpty(ImageView image){
        Image border = new Image("shipCell.png", (int)image.fitWidthProperty().get(),
                (int)image.fitHeightProperty().get(), true, true);
        image.setImage(border);
    }
    
    private void drawError(ImageView image){
        Image border = new Image("intersect.png", (int)image.fitWidthProperty().get(),
                (int)image.fitHeightProperty().get(), true, true);
        image.setImage(border);
    }
    
    @FXML
    private void close(ActionEvent e){
        GlobalStateMachine.getInstance().back();
        ((Stage)anchor.getScene().getWindow()).close();
        
    }
    
    @FXML
    private void deleteSelected(ActionEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        gsm.getPlacementFileManager().deletePlacement(selectedID);
        drawImages();
    }
    
    @FXML
    private void pickAndClose(ActionEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        gsm.buildPlacementController(gsm.getPlacementFileManager().loadPlacement(selectedID));
        gsm.back();
        ((Stage)anchor.getScene().getWindow()).close();
        
    }
    
    @FXML
    private void image1Pick(MouseEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadResult(0) == LoadResult.NONE) {
            pfm.savePlacement(0);
        }
        else if(pfm.loadResult(0) == LoadResult.VALID){
            selectedID = 0;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(false);
        }
        else {
            selectedID = 0;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(true);
        }
        drawImages();
    }
    
    @FXML
    private void image2Pick(MouseEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadResult(1) == LoadResult.NONE) pfm.savePlacement(1);
        else if(pfm.loadResult(1) == LoadResult.VALID){ 
            selectedID = 1;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(false);
        }
        else {
            selectedID = 1;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(true);
        }
        drawImages();
    }
    
    @FXML
    private void image3Pick(MouseEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadResult(2) == LoadResult.NONE) pfm.savePlacement(2);
        else if(pfm.loadResult(2) == LoadResult.VALID){ 
            selectedID = 2;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(false);
        }
        else {
            selectedID = 2;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(true);
        }
        drawImages();
    }
    
    @FXML
    private void image4Pick(MouseEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadResult(3) == LoadResult.NONE) pfm.savePlacement(3);
        else if(pfm.loadResult(3) == LoadResult.VALID){ 
            selectedID = 3;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(false);
        }
        else {
            selectedID = 3;
            deleteBTN.setDisable(false);
            pickBTN.setDisable(true);
        }
        drawImages();
    }
}
