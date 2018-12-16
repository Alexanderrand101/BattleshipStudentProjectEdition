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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import matmik.controller.global.GlobalSettings;
import matmik.controller.placement.PlacementStrategy;
import javafx.scene.web.WebView;
import  java.io.File;

/**
 *
 * @author Алескандр
 */
public class OptionsController implements Initializable {
    
    @FXML
    RadioButton randomRB;
    @FXML
    RadioButton shoreRB;
    @FXML
	WebView help;
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
        File f = new File("/../help.html");
        help.getEngine().load(getClass().getResource("/help.html").toExternalForm());
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
		((Stage)randomRB.getScene().getWindow()).close();
    }
 
}
