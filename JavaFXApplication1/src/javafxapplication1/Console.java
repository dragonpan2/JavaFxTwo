/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author panbe
 */
public class Console extends Application implements EventHandler<KeyEvent> {

    @Override
    public void start(Stage primaryStage) {
        Pane paneRoot = new Pane();
        Scene scene = new Scene(paneRoot, 1000, 700);
        primaryStage.setScene(scene);
        
        VBox vBox = new VBox();
        
        paneRoot.getChildren().add(vBox);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().toString().equals("ENTER")) {
                    
                }
            }
        });
        
    }
    
    public void addLine(String line) {
        //let's say it has access to vBox
        Label label = new Label(line);
        VBox vBox = new VBox();
        vBox.getChildren().add(vBox.getChildren().size(),label);
    }
    
    
    
    

    @Override
    public void handle(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
