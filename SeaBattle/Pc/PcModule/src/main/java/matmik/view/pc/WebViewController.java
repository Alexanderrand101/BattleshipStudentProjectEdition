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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Алескандр
 */
public class WebViewController implements Initializable {
   
    @FXML
    private WebView webView;
    
    private String pathToLoad;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        initialize();
    }
    
    public WebViewController(String pathToLoad){
        this.pathToLoad = pathToLoad;
    }
    
    @FXML
    public void initialize() {
        try{
            webView.getEngine().load(pathToLoad);
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR, "не удалось загрузить файл по адресу " + pathToLoad, ButtonType.OK).showAndWait();
        }
    }
    
}
