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
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
    TextFlow textFlow;
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
        Text text = new Text("Программу сию ваяли 2 студента, что в бывшем СГАУ учатся. А звали их Матвеев Владислав и Михеев Александр. А проверена эта программа Зеленко Ларисой Сергеевной в рамках лабораторного практикума по дисциплине Технологии программирования. Если вы не один из этих 3 людей, значит прогу кто-то слил(");
        textFlow.getChildren().add(text);
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
    private void loadGuide(ActionEvent e) throws IOException{
        WebViewController controller = new WebViewController(getClass().getResource("/help.html").toExternalForm());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WebPage.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void loadRules(ActionEvent e) throws IOException{
        WebViewController controller = new WebViewController(getClass().getResource("/rules.html").toExternalForm());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WebPage.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
