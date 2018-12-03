/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.pcvisualinterface;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import matmik.Bounds;
import matmik.Cell;
import matmik.CellState;
import matmik.GlobalStateMachine;
import matmik.PlacementFileManager;
import matmik.PlacementLoaderDisplayConstants;

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
    
    private int selectedID = -1;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        PlacementLoaderDisplayConstants.getInstance().calcConstants((int)placementImage1.fitWidthProperty().get(),
                (int)placementImage1.fitHeightProperty().get());
        drawImages();
    }
    
    public PlacementLoaderController(){}
    
    @FXML
    public void initialize() {
        PlacementLoaderDisplayConstants.getInstance().calcConstants((int)placementImage1.getFitWidth(),
                (int)placementImage1.getFitHeight());
        drawImages();
    }
    
    private void drawImages(){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadPlacement(0) != null) drawField(placementImage1, pfm.getGrid(0), selectedID == 0);
        else drawEmpty(placementImage1);
        if (pfm.loadPlacement(1) != null) drawField(placementImage2, pfm.getGrid(1), selectedID == 1);
        else drawEmpty(placementImage2);
        if (pfm.loadPlacement(2) != null) drawField(placementImage3, pfm.getGrid(2), selectedID == 2);
        else drawEmpty(placementImage3);
        if (pfm.loadPlacement(3) != null) drawField(placementImage4, pfm.getGrid(3), selectedID == 3);
        else drawEmpty(placementImage4);
    }
    
    private void drawField(ImageView image, Cell[][] grid, boolean isSelected){
        PlacementLoaderDisplayConstants pldc = PlacementLoaderDisplayConstants.getInstance();
        int cellSize = pldc.getShipCellSize();
        Image cell = new Image("cell.png", cellSize, cellSize, true, true);
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
                if(grid[i][j].getState() == CellState.BUSY)
                   transferImage(shipCell, placementField, baseOffsetX + j * cellSize, baseOffsetY + i * cellSize);
            }
        image.setImage(placementField);
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
        if (pfm.loadPlacement(0) == null) pfm.savePlacement(0);
        else selectedID = 0;
        deleteBTN.setDisable(false);
        pickBTN.setDisable(false);
        drawImages();
    }
    
    @FXML
    private void image2Pick(MouseEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadPlacement(1) == null) pfm.savePlacement(1);
        else selectedID = 1;
        deleteBTN.setDisable(false);
        pickBTN.setDisable(false);
        drawImages();
    }
    
    @FXML
    private void image3Pick(MouseEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadPlacement(2) == null) pfm.savePlacement(2);
        else selectedID = 2;
        deleteBTN.setDisable(false);
        pickBTN.setDisable(false);
        drawImages();
    }
    
    @FXML
    private void image4Pick(MouseEvent e){
        GlobalStateMachine gsm = GlobalStateMachine.getInstance();
        PlacementFileManager pfm = gsm.getPlacementFileManager();
        if (pfm.loadPlacement(3) == null) pfm.savePlacement(3);
        else selectedID = 3;
        deleteBTN.setDisable(false);
        pickBTN.setDisable(false);
        drawImages();
    }
}
