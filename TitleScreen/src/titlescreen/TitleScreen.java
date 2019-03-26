/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlescreen;

//Imports

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 *
 * @author ivonu
 */
public class TitleScreen extends Application {
    //Variable used to determine the closure protocal
    static int doIStay;
    //Variable used in thread, will have changing amount of !'s
    private String title = "Welcome to the Arcade!!!";
    @Override
    public void start(Stage primaryStage) {
        
        //New Label that will be used in the thread, will be constantly updated
        Label titleLabel = new Label("");

        //This thread is used as a title for the Arcade Menu, with 
        //The amount of "!"'s changing to show enthusiasm, and get the user
        //pumped up for the games
        Thread theThread = new Thread(() -> {
            try {
                while (true) {
                    if (titleLabel.getText().trim().length() == 23){
                        //If there are currently 2 !'s, update it to 1 !
                        title = "Welcome to the Arcade!";
                    }
                    else if (titleLabel.getText().trim().length() == 24){
                        //If there are 3 !'s, update it to 2 !'s
                        title = "Welcome to the Arcade!!";
                    }
                    else{
                        //If there are not 2 or 3 !'s, update it to 3 !'s
                        title = "Welcome to the Arcade!!!";
                    }
                
                    Platform.runLater(() -> {
                        //Update the titleLabel to the new string value
                        titleLabel.setText(title);
                    });
                    //Use a 200 ms delay
                    Thread.sleep(200);
                }
            }
            catch (InterruptedException ex) {
                //If theres any errors, print them out
                System.out.println(ex);
            }
        });
        
        //Start the thread
        theThread.start();

      primaryStage.setOnCloseRequest(e -> {
            //If the user closes the window with the X button, stop the thread before terminating.
            e.consume();
            theThread.stop();
            primaryStage.close();
        });

        //Creates a new Button for Black Jack game
        Button blackJack = new Button("Blackjack");
        blackJack.setOnAction(e -> {
            //Pause the thread
            theThread.yield();
            //Hide the window for the menu
            primaryStage.hide();
            
            //Do I stay will be used to determine proper return protocal
            //More details in exitWindow.java
            doIStay = blackJackGame.display();
            
            //If doIStay is 0, stop the thread and close the game
            if(doIStay == 0){
                theThread.stop();
                primaryStage.close();
            }
            //If doIStay is not 0, reopen the menu
            else{
                primaryStage.show();
            }
        });
        
        //New button for game 2 (24)
        Button find24 = new Button("Find 24");
        
        find24.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Pause the thread
                theThread.stop();
                //Hides the main menu window
                primaryStage.close();
                //Starts 24
                Game4_24.display();
            }
        });
        
        Button hangman = new Button("Hangman");
        hangman.setOnAction(e-> {
             //Pause the thread
            theThread.stop();
            //Hide the window for the menu
            primaryStage.close();
            HangMan.display();
           
        });
        
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Elephant",FontWeight.BOLD,40));
        VBox layout = new VBox(17);  
        layout.getChildren().addAll(titleLabel, blackJack, find24,hangman );
        layout.setPadding(new Insets(150,0,0,165));
        
        blackJack.setPrefSize(475, 75);
        blackJack.setFont(Font.font("Elephant",FontWeight.BOLD,50));
        blackJack.setId("blackjack");
        blackJack.setTextFill(Color.WHITE);
        
        find24.setPrefSize(475, 75);
        find24.setFont(Font.font("Elephant",FontWeight.BOLD,50));
        find24.setId("find24");
        find24.setTextFill(Color.WHITE);

        hangman.setPrefSize(475, 75);
        hangman.setFont(Font.font("Elephant",FontWeight.BOLD,50));
        hangman.setId("hangman");
        hangman.setTextFill(Color.WHITE);

        layout.setId("openpane");
        //Adds the pane to the scene
        Scene scene = new Scene(layout, 800, 700);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

        //Sets the title of the stage to "Games Collection"
        //Sets the scene of the stage to be the scene, and shows the stage
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
