/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlescreen;

/**
 *
 * @author ivonu
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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

public class blackJackGame {
    public static void display(MenuBar menuBar){
        Stage window = new Stage();
        
        window.setTitle("Black Jack");
        menuBar.prefWidthProperty().bind(window.widthProperty());
        Button btnPlay = new Button("Play");
        Button btnRules = new Button("Rules");
        Button btnQuit = new Button("Quit");
        
        btnPlay.setOnAction(e -> {
            btnPlay.setVisible(false);
            btnRules.setVisible(false);
            btnQuit.setVisible(false);
        });
        
        
        btnRules.setOnAction(e ->{
            blackJackRules.display();
        });
       
        btnQuit.setOnAction(e -> {
           window.close(); 
        });
        
        btnPlay.setLayoutX(400);
        btnPlay.setLayoutY(150);
        
        btnRules.setLayoutX(400);
        btnRules.setLayoutY(300);
        
        btnQuit.setLayoutX(400);
        btnQuit.setLayoutY(450);
        
        Pane rootBJ = new Pane();
        rootBJ.getChildren().addAll(menuBar, btnPlay, btnRules, btnQuit);
        
        Scene scene = new Scene(rootBJ, 800, 600);
        window.setScene(scene);
        window.showAndWait();
    }
}
