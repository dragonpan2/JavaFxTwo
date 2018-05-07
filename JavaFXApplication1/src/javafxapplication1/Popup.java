/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author panbe
 */
public class Popup {
    
    public static void popup(String title, String message) {
        Button button = new Button("Confirm");
        Label label = new Label(message);
        
        
        
        Pane rootPane = new Pane();
        Scene scene = new Scene(rootPane, 240,120);
        Stage stage = new Stage();
        
        
        rootPane.getChildren().addAll(button,label);
        button.setLayoutX(150);
        button.setLayoutY(90);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        
        
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.showAndWait(); //
        
    }
}
