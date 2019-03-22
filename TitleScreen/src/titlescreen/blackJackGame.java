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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

public class blackJackGame {
    static int closeEverything;
    static String userName;
    public static int display(MenuBar menuBar){
        Stage window = new Stage();
        Pane rootBJ = new Pane();
        
        
        window.setTitle("Black Jack");
        menuBar.prefWidthProperty().bind(window.widthProperty());
        Button btnPlay = new Button("Play");
        Button btnRules = new Button("Rules");
        Button btnQuit = new Button("Quit");
        
        btnPlay.setOnAction(e -> {
            btnPlay.setVisible(false);
            btnRules.setVisible(false);
            btnQuit.setVisible(false);
            playGame(rootBJ);
            
        });
        
        
        btnRules.setOnAction(e ->{
            blackJackRules.display();
        });
       
        btnQuit.setOnAction(e -> {
            closeEverything = exitWindow.callExit();
            if (closeEverything != 2){ 
                window.close(); 
            }
           
        });
        
        rootBJ.setOnKeyPressed(new EventHandler<KeyEvent>(){                      //Check if a key was pressed
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ESCAPE)){
                    closeEverything = 0;
                    window.close();
                }
            }
        });
        window.setOnCloseRequest(e -> {
            e.consume();
            closeEverything = exitWindow.callExit();
            if (closeEverything !=2){
                window.close();
            }
        });
        
        btnPlay.setLayoutX(400);
        btnPlay.setLayoutY(150);
        
        btnRules.setLayoutX(400);
        btnRules.setLayoutY(300);
        
        btnQuit.setLayoutX(400);
        btnQuit.setLayoutY(450);
        
        
        rootBJ.getChildren().addAll(menuBar, btnPlay, btnRules, btnQuit);
        
        Scene scene = new Scene(rootBJ, 800, 600);
        window.setScene(scene);
        window.showAndWait();
        return closeEverything;
    }
    
    
    public static void playGame(Pane thePane){
        Label lblWelcome = new Label("Welcome Player! Are you a new player? Or are you a returning player?");
        lblWelcome.setLayoutX(250);
        lblWelcome.setLayoutY(200);
        
        Button newUser = new Button("New User");
        Button returningUser = new Button("Returning User");
        
        newUser.setPrefSize(120, 70);
        returningUser.setPrefSize(120, 70);
        
        newUser.setLayoutX(250);
        newUser.setLayoutY(400);
        returningUser.setLayoutX(450);
        returningUser.setLayoutY(400);
        thePane.getChildren().addAll(lblWelcome, newUser, returningUser);
        
        double money = 100;
        newUser.setOnAction(e -> {
            lblWelcome.setVisible(false);
            newUser.setVisible(false);
            returningUser.setVisible(false);
            TextField txtUserName = new TextField();
            Label lblEnterName = new Label("Enter Username");
            Button submitUserName = new Button("Submit");
            
            lblEnterName.setLayoutX(200);
            lblEnterName.setLayoutY(300);
            txtUserName.setLayoutX(250);
            txtUserName.setLayoutY(300);
            submitUserName.setLayoutX(500);
            submitUserName.setLayoutY(300);
            
            
            thePane.getChildren().addAll(txtUserName, lblEnterName, submitUserName);
            submitUserName.setOnAction(f -> {
                userName = txtUserName.getText();
                submitUserName.setVisible(false);
                txtUserName.setVisible(false);
                lblEnterName.setVisible(false);
            });
            
        });
    }
    
}
