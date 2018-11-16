/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.pcvisualinterface;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import matmik.GlobalDisplayConstants;

/**
 *
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public class PCFXMLController implements Initializable {
    
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
    
    private ShipControl selectedShip;
    
    private GlobalDisplayConstants globalDisplayConstants;
    @FXML
    private Label stateLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void initForPC(MouseEvent event) {
        tabPane.getSelectionModel().select(setDifficultyTab); 
    }
    
    private void drawPlacementBoard(){
        Image cell = new Image("cell.png", 25, 25, false, true);
        Image shipCell = new Image("shipCell.png", 50, 25, false, true);
        WritableImage placementField = new WritableImage(600, 300);
        int baseOffsetX = 50;
        int baseOffsetY = 50;
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                transferImage(cell, placementField, baseOffsetX + j * 25, baseOffsetY + i * 25);
            }
        transferImage(shipCell, placementField, 100, 400);
        if (selectedShip != null)
        {
            stateLabel.setText("drawing selected ship");
            transferImage(selectedShip.getShipImage(), placementField, selectedShip.getY(), selectedShip.getX());
        }
        placementImage.setImage(placementField);
    }
    
    private void transferImage(Image source, WritableImage scene, int yoffset, int xoffset){
        PixelReader sourceReader = source.getPixelReader();
        PixelWriter sceneWriter = scene.getPixelWriter();
        for(int y = 0; y < Math.round(source.getHeight()); y++)
            for(int x = 0; x < Math.round(source.getWidth()); x++)
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
        globalDisplayConstants = GlobalDisplayConstants.getInstanceAndUpdate();
        globalDisplayConstants.CalcConstants(600, 300);
        drawPlacementBoard();
    }

    @FXML
    private void clearBoard(MouseEvent event) {
    }

    @FXML
    private void dropShip(MouseEvent event) {
        selectedShip = null;
    }

    @FXML
    private void moveShip(MouseEvent event) {
        if(selectedShip != null){
            selectedShip.setX((int)event.getX());
            selectedShip.setY((int)event.getY());
            drawPlacementBoard();
        }
    }

    @FXML
    private void pickShip(MouseEvent event) {
        if (checkBounds((int)event.getX(), (int)event.getY(), 400, 100, 50, 25))
        {
            Image image = new Image("shipCell.png", 50, 25, false, true);
            selectedShip = new ShipControl(400, 100, image);
        }
    }
    
    private boolean checkBounds(int x, int y, int ox, int oy, int width, int height)
    {
        return (ox <= x) && (ox + width >= x) && (oy <= y) && (oy + height >= y);
    }
}
