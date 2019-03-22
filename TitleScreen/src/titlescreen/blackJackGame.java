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
import java.util.Random;
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
    static double money = 100;
    static boolean readyToPlay = false;
    static double currentBet = 0;
    static int[] cards = new int[52];
    static int[] cardValue = {2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,1,1,1,1};
    static Random randomGen = new Random();
    static int score = 0;
    static int cardsUsed = 0;
    
    static int WIDTH = 800;
    static int HEIGHT = 600;
    
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
            getUserInfo(rootBJ);
           
            
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
        
        checkIfForceClose(rootBJ, window);
        
        

            
            
        
        
        
        window.setOnCloseRequest(e -> {
            e.consume();
            closeEverything = exitWindow.callExit();
            if (closeEverything !=2){
                window.close();
            }
        });
        
        btnPlay.setLayoutX(WIDTH/2);
        btnPlay.setLayoutY(HEIGHT-450);
        
        btnRules.setLayoutX(WIDTH/2);
        btnRules.setLayoutY(HEIGHT/2);
        
        btnQuit.setLayoutX(WIDTH/2);
        btnQuit.setLayoutY(HEIGHT-150);
        
        
        rootBJ.getChildren().addAll(menuBar, btnPlay, btnRules, btnQuit);
        //800,600
        Scene scene = new Scene(rootBJ, WIDTH, HEIGHT);
        window.setScene(scene);
        window.showAndWait();
        return closeEverything;
    }
    
    
    public static void getUserInfo(Pane thePane){
        //checkIfForceClose(thePane, theStage);
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
        
        
        newUser.setOnAction(e -> {
            lblWelcome.setVisible(false);
            newUser.setVisible(false);
            returningUser.setVisible(false);
            TextField txtUserName = new TextField();
            Label lblEnterName = new Label("Enter Username");
            Button submitUserName = new Button("Submit");
            
            lblEnterName.setLayoutX(200);
            lblEnterName.setLayoutY(300);
            txtUserName.setLayoutX(300);
            txtUserName.setLayoutY(300);
            submitUserName.setLayoutX(500);
            submitUserName.setLayoutY(300);
            
            
            thePane.getChildren().addAll(txtUserName, lblEnterName, submitUserName);
            submitUserName.setOnAction(f -> {
                userName = txtUserName.getText();
                submitUserName.setVisible(false);
                txtUserName.setVisible(false);
                lblEnterName.setVisible(false);
                playGame(thePane);
            });
           
            
            
        });
    }
    
    public static void checkIfForceClose(Pane thePane, Stage theStage){
          thePane.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ESCAPE)){
                closeEverything = 0;
                theStage.close();
            }
        });
      
    }
    
    
    public static void playGame(Pane thePane){
                    //playGame();
            
            
            Label lblBetP = new Label("Enter bet: ");
            Label lblMon = new Label();
            Label lblCurrentBet = new Label();
            TextField txtBet = new TextField();
            lblMon.setText("Current Money: " + Double.toString(money));
            Button btnSubmit = new Button("Submit Bet");
            Button btnHit = new Button("Hit me");
            Button btnFreeze = new Button("Freeze");
            
            //Setting coordinates for each node
            lblMon.setLayoutX(10);
            lblMon.setLayoutY(30);
            lblCurrentBet.setLayoutX(10);
            lblCurrentBet.setLayoutY(50);
            lblBetP.setLayoutX(10);
            lblBetP.setLayoutY(HEIGHT-50);
            txtBet.setLayoutX(100);
            txtBet.setLayoutY(HEIGHT-50);
            btnSubmit.setLayoutX(300);
            btnSubmit.setLayoutY(HEIGHT-50);
            btnHit.setLayoutX(550);
            btnHit.setLayoutY(HEIGHT-50);
            btnFreeze.setLayoutX(700);
            btnFreeze.setLayoutY(HEIGHT-50);
            
            //Setting default visibility for Buttons (Hit/Freeze)
            btnHit.setVisible(false);
            btnFreeze.setVisible(false);
            
            thePane.getChildren().addAll(lblMon,lblCurrentBet,lblBetP,txtBet,btnSubmit, btnHit, btnFreeze);
            
            btnSubmit.setOnAction(e->{
                
                currentBet = Double.parseDouble(txtBet.getText());
                if(currentBet <0){ 
                    txtBet.setText("Bet must be over 0$.");
                }
                else if (money - currentBet < 0){
                    txtBet.setText("Not enough funds.");
                }
                else{
                    score = 0;
                    btnSubmit.setVisible(false);
                    lblBetP.setVisible(false);
                    txtBet.setVisible(false);
                    btnHit.setVisible(true);
                    btnFreeze.setVisible(true);
                    money = money - currentBet;
                    lblMon.setText("Current Money: "+ Double.toString(money));
                    lblCurrentBet.setText("Current Bet: " + Double.toString(currentBet));
                }  
            });
            
            
            btnHit.setOnAction(e -> {
               int chosenCard;
               while(true){
                  chosenCard = getRandomNumber();
                  if (cards[chosenCard] == 0){
                      cards[chosenCard] = 1;
                      cardsUsed+=1;
                      if(cardsUsed==52){
                          for(int j =0; j<52; j++){
                              cards[j] = 0;
                          }
                          cardsUsed =0;
                      }
                      break;
                  }
               }
               score += cardValue[chosenCard];
               System.out.println(score);
            });
            
            btnFreeze.setOnAction(e -> {
                System.out.println("Froze on score: " + score);
                btnHit.setVisible(false);
                btnFreeze.setVisible(false);
                btnSubmit.setVisible(true);
                lblBetP.setVisible(true);
                txtBet.setVisible(true);
                
            });
            
    }
    
    public static int getRandomNumber(){
        //Get number between 0 and 51
        int randomNum = randomGen.nextInt(52);
        return randomNum;
    }
    
    
}
