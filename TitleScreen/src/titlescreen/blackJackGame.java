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
import java.util.ArrayList;
import java.util.Random;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class blackJackGame {
    
  //Basic declarations used for program
  static int closeEverything;
  static String userName;
  static double money;
  static boolean readyToPlay = false;
  static double currentBet = 0;
  static int[] cards = new int[52];
  static int[] cardValue = {2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7,8,8,8,8,9,9,9,9,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,1,1,1,1};
  static Random randomGen = new Random();
  static int score = 0;
  static int enemyScore = 0;
  static int cardsUsed = 0;
  
  static int WIDTH = 800;
  static int HEIGHT = 600;
  static ArrayList<Image> deck = new ArrayList<>();
  static ImageView[] view = new ImageView[24];        //You can get a maximum of 12 cards each per side.
  static ImageView[] censoredCards = new ImageView[12];   //Enemy can have maximum of 12 cards.
  static int howManyCards = 0;
  static int howManyEnemyCards = 12;
  static boolean firstPlay = true;
  static Font Helvetica = Font.font("Helvetica", FontWeight.BOLD, 33);
  
  
  static MediaPlayer mediaPlayer;
  static boolean mediaStarted = false;
  
  
  public static int display(){
      
    //Sets a new stage (with title Black Jack) and new pane
    Stage window = new Stage();
    Pane rootBJ = new Pane();
    window.setTitle("Black Jack");
    
    //Declares locations of image that will be used in ImageView as a background
    String backgroundLocation = new File("src/titlescreen/background").getAbsolutePath();

    //Defines an image
    ImageView theBackgroundView = new ImageView(new Image(new File(backgroundLocation).toURI().toString()+"/"+"background.jpg"));

    //Sets the inital Y position to be 15(so it doesn't mess with menubar)
    theBackgroundView.setLayoutY(15);

    //Make the background span the entire width/height(-15) of the pane
    theBackgroundView.setFitHeight(HEIGHT-15);
    theBackgroundView.setFitWidth(WIDTH);

    //send theBackgroundView to be behind the nodes
    theBackgroundView.toBack();
    rootBJ.getChildren().add(theBackgroundView);

    //Defines new buttons
    Button btnPlay = new Button("Play");
    Button btnRules = new Button("Rules");
    Button btnQuit = new Button("Quit");

    btnPlay.setOnAction(e -> {
      //Set the money equal to 100 by default
      money = 100;
      
      //Hide the buttons
      btnPlay.setVisible(false);
      btnRules.setVisible(false);
      btnQuit.setVisible(false);
      
      //Setup cards
      initalizeCards(rootBJ);
      
      //Find out who is playing
      getUserInfo(rootBJ, window);
    });


    btnRules.setOnAction(e ->{
      //Display the rules for blackjack
      blackJackRules.display();
    });

    btnQuit.setOnAction(e -> {
      //Close the game, and check if there will be a return to the main menu
      closeEverything = exitWindow.callExit();
      if (closeEverything != 2){
        if(mediaStarted == true){
            //If there is audio playing, suspend it.
            mediaPlayer.stop();
        }
        window.close(); 
      }

    });

    //Check if the user presses ESCAPE to close the game.
    checkIfForceClose(rootBJ, window);


    window.setOnCloseRequest(e -> {
      //If the user clicks the X button at the top right of the screen
      //Check if they want to return to the main menu or not.
      e.consume();
      closeEverything = exitWindow.callExit();
      if (closeEverything !=2){
        if(mediaStarted == true){
            mediaPlayer.stop();
        }
        window.close();
      }
    });

    //Defines locations for the buttons
    btnPlay.setLayoutX(WIDTH/2);
    btnPlay.setLayoutY(HEIGHT-450);

    btnRules.setLayoutX(WIDTH/2);
    btnRules.setLayoutY(HEIGHT/2);

    btnQuit.setLayoutX(WIDTH/2);
    btnQuit.setLayoutY(HEIGHT-150);

    //Add the nodes to the pane
    rootBJ.getChildren().addAll(btnPlay, btnRules, btnQuit);
    
    //Add the pane to the scene, and then add the scene to the window
    Scene scene = new Scene(rootBJ, WIDTH, HEIGHT);
    window.setScene(scene);
    window.showAndWait();
    
    //When the program closes, check if there is going to be a return to the main menu or not.
    return closeEverything;
  }


  public static void getUserInfo(Pane thePane, Stage window){
    //Check if the user presses ESCAPE
    checkIfForceClose(thePane, window);
    
    //Ask the user if they are a new or a returning player
    Label lblWelcome = new Label("Welcome Player! Are you a new player? Or are you a returning player?");
    lblWelcome.setLayoutX(250);
    lblWelcome.setLayoutY(200);

    Button newUser = new Button("New User");
    Button returningUser = new Button("Returning User");

    //Sets dimensions of buttons
    newUser.setPrefSize(120, 70);
    returningUser.setPrefSize(120, 70);

    //Sets location of buttons, and adds them to the pane
    newUser.setLayoutX(250);
    newUser.setLayoutY(400);
    returningUser.setLayoutX(450);
    returningUser.setLayoutY(400);
    thePane.getChildren().addAll(lblWelcome, newUser, returningUser);

    //If the user selects "New User"
    newUser.setOnAction(e -> {
      //Remove/hide the previous nodes
      lblWelcome.setVisible(false);
      newUser.setVisible(false);
      returningUser.setVisible(false);
      
      //Sets up new nodes that and defines their coordinates
      TextField txtUserName = new TextField();
      Label lblEnterName = new Label("Enter Username");
      Button submitUserName = new Button("Submit");
      lblEnterName.setLayoutX(200);
      lblEnterName.setLayoutY(300);
      txtUserName.setLayoutX(300);
      txtUserName.setLayoutY(300);
      submitUserName.setLayoutX(500);
      submitUserName.setLayoutY(300);
      
      //Add the new nodes to the pane
      thePane.getChildren().addAll(txtUserName, lblEnterName, submitUserName);
      
      //When the user submits their username
      submitUserName.setOnAction(f -> {
        //Save the name they entered in userName
        userName = txtUserName.getText();
        
        //Hide all the other nodes
        submitUserName.setVisible(false);
        txtUserName.setVisible(false);
        lblEnterName.setVisible(false);
        
        //Start the actual game
        playGame(thePane,window);
      });



    });
    
    //If the user selects Returning User
    returningUser.setOnAction(e -> {
        //Hides all the nodes
        lblWelcome.setVisible(false);
        newUser.setVisible(false);
        returningUser.setVisible(false);
        
        //Opens a new stage that will be used to select the users save
        Stage theFileOpener = new Stage();
        
        //Creates a filechooser and sets the title of it to "Open your save"
        FileChooser fc = new FileChooser();
        fc.setTitle("Open your save");
        
        //Get the file the user chooses
        File file = fc.showOpenDialog(theFileOpener);
        
        //Read through the file, and get the username and their money values
        //from the file
        try (BufferedReader theReader = new BufferedReader(new FileReader (file))){
            String allText;
            String[] nameAndScore = new String[2];
            while((allText = theReader.readLine()) != null){
                nameAndScore = allText.split(" ");
                userName = nameAndScore[0];
                money = Double.parseDouble(nameAndScore[1]);
            }
        }
        
        //Catch any errors, and print them out.
        catch (Exception ex) {
            Logger.getLogger(defineMenuBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Start the actual game
        playGame(thePane,window);
    });
    
    
    
  }

  public static void checkIfForceClose(Pane thePane, Stage theStage){
      //Check what key the user pressed
      thePane.setOnKeyPressed((KeyEvent ke) -> {
      
      //If the user presses escape
      if (ke.getCode().equals(KeyCode.ESCAPE)){
        //Force close the program
        closeEverything = 0;
        theStage.close();
      }
    });

  }


  public static void playGame(Pane thePane, Stage window){
      
    //Check if user closes the game  
    checkIfForceClose(thePane, window);
    
    //Defines path for audio file
    String whereIsMP3 = new File("src/titlescreen/Casino.wav").getAbsolutePath();
    Media media = new Media(new File(whereIsMP3).toURI().toString());
    
    //Allows music to be played
    mediaPlayer = new MediaPlayer(media);
    mediaStarted = true;
    
    //Has music loop
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    MediaView mediaViewer = new MediaView(mediaPlayer);
    thePane.getChildren().add(mediaViewer);

    Label lblBetP = new Label("Enter bet: ");
    Label lblMon = new Label();
    Label lblCurrentBet = new Label();
    Label lblWelcome = new Label();
    TextField txtBet = new TextField();
    lblMon.setText("Current Money: " + Double.toString(money));
    Button btnSubmit = new Button("Submit Bet");
    Button btnHit = new Button("Hit me");
    Button btnFreeze = new Button("Freeze");
    
    Button btnRestart = new Button("Restart");
    btnRestart.setVisible(false);
    //btnRestart.setLayoutX(195);
    btnRestart.setLayoutY(HEIGHT-90);
    btnRestart.setPrefWidth((WIDTH/2) - 0.25);
    btnRestart.setPrefHeight(90);
    
    Button btnGOQuit = new Button("Quit");
    btnGOQuit.setVisible(false);
    btnGOQuit.setLayoutX((WIDTH/2) + 0.25);
    btnGOQuit.setLayoutY(HEIGHT-90);
    btnGOQuit.setPrefWidth((WIDTH/2) - 0.25);
    btnGOQuit.setPrefHeight(90);
    
    Label lblGameOver = new Label("Uh oh! It appears you are out of funds. Play again?"); 
    lblGameOver.setFont(Helvetica);
    lblGameOver.setTextFill(Color.YELLOW);
    lblGameOver.setLayoutX(5.5);
    lblGameOver.setLayoutY(HEIGHT-155);
    lblGameOver.setVisible(false);
    
    
    lblWelcome.setText("Welcome " +userName + "!");
    lblWelcome.setFont(Helvetica);
    lblWelcome.setTextFill(Color.YELLOW);
    
    lblMon.setFont(Helvetica);
    lblMon.setTextFill(Color.YELLOW);        //Color.LIGHTCYAN       Color.TURQUOISE
    lblBetP.setFont(Helvetica);                 //CYAN AND TURQUOISE
    lblBetP.setTextFill(Color.YELLOW);
    lblCurrentBet.setFont(Helvetica);
    lblCurrentBet.setTextFill(Color.YELLOW);
    
    //Setting coordinates for each node
    lblWelcome.setLayoutX(10);
    lblWelcome.setLayoutY(30);
    lblMon.setLayoutX(10);
    lblMon.setLayoutY(80);
    lblCurrentBet.setLayoutX(10);
    lblCurrentBet.setLayoutY(130);
    lblBetP.setLayoutX(10);
    lblBetP.setLayoutY(HEIGHT-65);
    txtBet.setLayoutX(173);
    txtBet.setLayoutY(HEIGHT-50);
    btnSubmit.setLayoutX(330);
    btnSubmit.setLayoutY(HEIGHT-50);
    btnHit.setLayoutX(550);
    btnHit.setLayoutY(HEIGHT-50);
    btnFreeze.setLayoutX(700);
    btnFreeze.setLayoutY(HEIGHT-50);

    //Setting default visibility for Buttons (Hit/Freeze)
    btnHit.setVisible(false);
    btnFreeze.setVisible(false);

    thePane.getChildren().addAll(lblWelcome, lblMon,lblCurrentBet,lblBetP,txtBet,btnSubmit, btnHit, btnFreeze, btnRestart, btnGOQuit, lblGameOver/*, lblmyScore, lblenemyScore*/);

    btnSubmit.setOnAction(e->{
      if(firstPlay !=true){
        resetCardPosition(HEIGHT-120, 30);
        resetCards();
      }

      firstPlay = false;
      howManyCards = 0;
      howManyEnemyCards = 12;
      currentBet = Double.parseDouble(txtBet.getText());
      if(currentBet <=0){
        txtBet.setText("Bet must be over 0$.");
      }
      else if (money - currentBet < 0){
        txtBet.setText("Not enough funds.");
      }
      else{
        score = 0;
        enemyScore = 0;
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
      view[howManyCards].setImage(deck.get(chosenCard));
      howManyCards+=1;
      if(score >21){
        System.out.println("You bust! Please Freeze.");
        btnHit.setVisible(false);

      }
      //Enemy must play on anything under 16.
      if (enemyScore < 16){
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
        System.out.println("Enemy got: " + cardValue[chosenCard]);
        enemyScore += cardValue[chosenCard];
        view[howManyEnemyCards].setImage(deck.get(chosenCard));
        if(howManyEnemyCards-12 >0){
          System.out.println(howManyEnemyCards-12);
          censoredCards[howManyEnemyCards-12].setVisible(true);
          censoredCards[howManyEnemyCards-12].toFront();
        }
        howManyEnemyCards+=1;
      }

      System.out.println(score);
      howManyCards+=1;
    });

    btnFreeze.setOnAction(e -> {
      System.out.println("Froze on score: " + score);
      btnHit.setVisible(false);
      btnFreeze.setVisible(false);
      btnSubmit.setVisible(true);
      lblBetP.setVisible(true);
      txtBet.setVisible(true);
      int chosenCard;
      if (enemyScore < 16){
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
            System.out.println("Enemy got: " + cardValue[chosenCard]);
            enemyScore += cardValue[chosenCard];
            view[howManyEnemyCards].setImage(deck.get(chosenCard));
            if(howManyEnemyCards-12 >0){
              System.out.println(howManyEnemyCards-12);
              censoredCards[howManyEnemyCards-12].setVisible(true);
              censoredCards[howManyEnemyCards-12].toFront();
            }
            howManyEnemyCards+=1;
            if(enemyScore >16){
              break;
            }
          }
        }

      }
      //runThread(score, enemyScore, thePane);
      //Print to console users score, and the enemys score
      System.out.println("Your score was: " + score);
      System.out.println("Enemy score was: " + enemyScore);

      //Determines a winner, and gives appropriate reward
      if(score >21 ){
        if(enemyScore >21){
          System.out.println("Tie.");
          //Tie.
          money+=currentBet;
        }
        else{
          //You lose.
          System.out.println("You lose.");
          if(money == 0){
            btnRestart.setVisible(true);
            btnGOQuit.setVisible(true);
            lblGameOver.setVisible(true);
            btnSubmit.setVisible(false);
            txtBet.setVisible(false);
            lblBetP.setVisible(false);
            btnRestart.setOnAction(f -> {
                gameOver();
                btnRestart.setVisible(false);
                btnGOQuit.setVisible(false);
                lblGameOver.setVisible(false);
                btnSubmit.setVisible(true);
                txtBet.setVisible(true);
                lblBetP.setVisible(true);
                lblMon.setText("Current Money: "+ Double.toString(money));
                lblCurrentBet.setText("Current Bet: 0.0" );
            });
            
            btnGOQuit.setOnAction(g -> {
                closeEverything = exitWindow.callExit();
                if (closeEverything != 2){
                    mediaPlayer.stop();
                    window.close();
                }
            });
          }
        }
      }

      else if (enemyScore > 21){
        System.out.println("You win!");
        money +=currentBet*2;
      }

      else if(score > enemyScore){
        //You win a profit!
        System.out.println("You win!");
        money += currentBet*2;
      }
      else if (score == enemyScore){
        System.out.println("Tie.");
        //Get your money back
        money += currentBet;
      }
      else{
        System.out.println("You lose.");
        if(money == 0){
            btnRestart.setVisible(true);
            btnGOQuit.setVisible(true);
            lblGameOver.setVisible(true);
            btnSubmit.setVisible(false);
            txtBet.setVisible(false);
            lblBetP.setVisible(false);
            btnRestart.setOnAction(f -> {
                gameOver();
                btnRestart.setVisible(false);
                btnGOQuit.setVisible(false);
                lblGameOver.setVisible(false);
                btnSubmit.setVisible(true);
                txtBet.setVisible(true);
                lblBetP.setVisible(true);
                lblMon.setText("Current Money: "+ Double.toString(money));
                lblCurrentBet.setText("Current Bet: 0.0" );
            });
            btnGOQuit.setOnAction(g -> {
                closeEverything = exitWindow.callExit();
                if (closeEverything != 2){
                    mediaPlayer.stop();
                    window.close();
                }
            });
            
        }
      }

      //Updates the money in the display at the top left
      lblMon.setText("Current Money: "+ Double.toString(money));

      //Removes the censor, and displays all the cards for the users to see
      resetCensor();
      resetCardPosition(HEIGHT/2, HEIGHT/2);
    });

  }

  public static int getRandomNumber(){
    //Get number between 0 and 51
    int randomNum = randomGen.nextInt(52);
    return randomNum;
  }

  public static void resetCensor(){
    //This function removes the censor,
    //so that censor cards won't be seen when not needed.
    for (int j = 1; j<howManyEnemyCards-12; j++){
      censoredCards[j].setVisible(false);
    }
  }

  public static void resetCards(){
    //Resets all pictures of cards.
    for(int i =0; i<24; i++){
      view[i].setImage(null);
    }
  }

  public static void resetCardPosition(int ourPos, int enemyPos){
    //This function is used after the cards are put to the center of the screen.
    //This function puts the cards back in their original location
    for (int i =0; i<12; i++){
      view[i].setLayoutY(ourPos);
      view[i+12].setLayoutY(enemyPos);
    }
  }

  public static void initalizeCards(Pane thePane){
    //Declares locations of images that will be used in ImageViews
    String photoLocation = new File("src/titlescreen/cards").getAbsolutePath();
    String backOfCardLocation = new File("src/titlescreen/cards/back").getAbsolutePath();

    //Defines an image
    Image backOfCard = new Image(new File(backOfCardLocation).toURI().toString()+"/"+"redback.jpg");

    //Loops through the entire deck of cards
    for (int i = 0; i<52; i++){
      //Adds the images of the cards to the deck
      int j= i+1;
      deck.add(new Image (new File(photoLocation).toURI().toString()+"/" + j +".jpg"));

      //Sets the position for the users and the enemys cards
      if (i<24){
        view[i] = new ImageView();
        view[i].setFitHeight(120);
        view[i].setFitWidth(95);
        if(i<12){
          //Sets the users cards
          view[i].setLayoutX(i*7);
          view[i].setLayoutY(HEIGHT-120);
          //The censored cards (so user can't see what enemy is holding)
          censoredCards[i] = new ImageView();
          censoredCards[i].setFitHeight(120);
          censoredCards[i].setFitWidth(95);
          censoredCards[i].setLayoutX(WIDTH-95-(15*(i)));
          censoredCards[i].setLayoutY(30);
          censoredCards[i].setImage(backOfCard);

          //Initally false, when the enemy draws a second+ card,
          //will be changed to true.
          censoredCards[i].setVisible(false);

          //Adds the card to the pane
          thePane.getChildren().add(censoredCards[i]);


        }
        else{
          //Sets the enemys cards
          view[i].setLayoutX(WIDTH-95-(15*(i-12)));
          view[i].setLayoutY(30);
        }
        //Add users and enemys cards to the pane
        thePane.getChildren().add(view[i]);

      }
    }
    
    
    
  }
  
  public static void gameOver(){
      //Reset user to have their old money value
      money = 100;
      //potential loss counter
  }




}
