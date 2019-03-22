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
public class blackJackRules {
    public static void display(){
        
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Rules");
        
        Label labelIntro = new Label();
        labelIntro.setText("Rules for BlackJack: ");
        
        Label lblRule1 = new Label("1. The goal is to get the highest possible score without going over 21.");
        Label lblRule2 = new Label("2. You will start with 2 cards, you may choose to keep your current hand or add an additional card to your hand");
        Label lblRule3 = new Label("3. Adding an aditional card to your hand increases your hands value, you want to have a higher score than your opponent. ");
        Label lblRule4 = new Label("4. However, if the card you pull from the deck is plus your current total is greater than 21, you automatically lose.");
        Label lblRule5 = new Label("5. Each card has a specific value.");
        Label lblRule51 = new Label("5.1. Non-face cards are worth whatever number they are.");
        Label lblRule52 = new Label("5.2. Face cards are worth 10;");
        Label lblRule53 = new Label("5.3. Ace is either worth 1 or 11;");
        Label lblRule54 = new Label("5.4. Jokers are not included.");
        Label lblRule6 = new Label("6. Once a card has been pulled, it cannot be pulled again until the deck runs out of cards, and it needs to be reshuffled.");
        Label lblRule7 = new Label("7. You can bet however much money you like. Each time you win, you double your money. But once you are out of money, it's game over!");
        
        Button btnGotIt = new Button("Got it!");
        btnGotIt.setOnAction(e -> {
           window.close();
        });
        
        
        labelIntro.setLayoutX(10);
        labelIntro.setLayoutY(10);
        
        lblRule1.setLayoutX(10);
        lblRule1.setLayoutY(40);
        
        lblRule2.setLayoutX(10);
        lblRule2.setLayoutY(70);
        
        lblRule3.setLayoutX(10);
        lblRule3.setLayoutY(100);
        
        lblRule4.setLayoutX(10);
        lblRule4.setLayoutY(130);
        
        lblRule5.setLayoutX(10);
        lblRule5.setLayoutY(160);
        
        lblRule51.setLayoutX(20);
        lblRule51.setLayoutY(190);
        
        lblRule52.setLayoutX(20);
        lblRule52.setLayoutY(220);
        
        lblRule53.setLayoutX(20);
        lblRule53.setLayoutY(250);
        
        lblRule54.setLayoutX(20);
        lblRule54.setLayoutY(280);
        
        lblRule6.setLayoutX(10);
        lblRule6.setLayoutY(310);
        
        lblRule7.setLayoutX(10);
        lblRule7.setLayoutY(340);
        
        
        btnGotIt.setLayoutX(750/2);
        btnGotIt.setLayoutY(370);
        Pane rulesBJPane = new Pane();
        rulesBJPane.getChildren().addAll(labelIntro,lblRule1, lblRule2, lblRule3, lblRule4, lblRule5, lblRule51, lblRule52, lblRule53, lblRule54, lblRule6, lblRule7, btnGotIt);
        
        Scene scene = new Scene(rulesBJPane, 750, 400);
        window.setScene(scene);
        window.showAndWait();
    
    
        
    }
}
