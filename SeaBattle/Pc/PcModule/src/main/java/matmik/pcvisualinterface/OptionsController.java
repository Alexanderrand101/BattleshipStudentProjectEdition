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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
import matmik.GlobalSettings;
import matmik.GlobalStateMachine;
import matmik.LoadResult;
import matmik.PlacementFileManager;
import matmik.PlacementLoaderDisplayConstants;
import matmik.PlacementStrategy;

/**
 *
 * @author Алескандр
 */
public class OptionsController implements Initializable {
    
    @FXML
    RadioButton randomRB;
    @FXML
    RadioButton shoreRB;
    
    private final ToggleGroup placerGroup = new ToggleGroup();
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        initialize();
    }
    
    public OptionsController(){}
    
    @FXML
    public void initialize() {
        randomRB.setToggleGroup(placerGroup);
        shoreRB.setToggleGroup(placerGroup);
        switch(GlobalSettings.getInstance().getPlacementStrategy()){
            case RANDOM: randomRB.setSelected(true);break;
            case SHORE: shoreRB.setSelected(true);break;
            default:;
        }
    }
    
    @FXML
    private void close(ActionEvent e){
        ((Stage)randomRB.getScene().getWindow()).close();        
    }
    
    @FXML
    private void saveProps(ActionEvent e){
        if(randomRB.isSelected()){
            GlobalSettings.getInstance().setPlacementStrategy(PlacementStrategy.RANDOM);
        }
        if(shoreRB.isSelected()){
            GlobalSettings.getInstance().setPlacementStrategy(PlacementStrategy.SHORE);
        }
    }
 
}
