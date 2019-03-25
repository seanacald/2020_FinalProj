/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlescreen;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.stage.FileChooser;


/**
 *
 * @author ivonu
 */
public class TitleScreen extends Application {
    static int doIStay;
    private String title = "Welcome to the Arcade!!!";
    @Override
    public void start(Stage primaryStage) {

        Button btnBlackJack = new Button("Black Jack");
        
        Label titleLabel = new Label("");
        titleLabel.setLayoutX(300/2);
        titleLabel.setLayoutY(20);

        
        
        
        Thread theThread = new Thread(() -> {
            try {
                while (true) {
                    if (titleLabel.getText().trim().length() == 23){
                        title = "Welcome to the Arcade!";
                    }
                    else if (titleLabel.getText().trim().length() == 24){
                        title = "Welcome to the Arcade!!";
                    }
                    else{
                        title = "Welcome to the Arcade!!!";
                    }
                
                    Platform.runLater(() -> {
                        titleLabel.setText(title);
                    });
                    Thread.sleep(200);
                }
            }
            catch (InterruptedException ex) {
                System.out.println(ex);
            }
        });
        
        theThread.start();

      primaryStage.setOnCloseRequest(e -> {
            e.consume();
            theThread.stop();
            primaryStage.close();
        });
      
        //MenuBar menuBar = new MenuBar();
        //menuBar = defineMenuBar.menuCreation(menuBar, primaryStage);
        //menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        
        btnBlackJack.setOnAction(e -> {
            theThread.yield();
            primaryStage.hide();
            doIStay = blackJackGame.display();
            if(doIStay == 0){
                primaryStage.close();
            }
            else{
                primaryStage.show();
            }
            
        });
        
        Button btnGame2 = new Button("Game2");
        Button btnGame3 = new Button("Game3");

        btnBlackJack.setLayoutX(20);
        btnBlackJack.setLayoutY(30);
        
        
        
        Pane root = new Pane();
        root.getChildren().addAll(/*menuBar, */btnBlackJack, titleLabel);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Games Collection!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
