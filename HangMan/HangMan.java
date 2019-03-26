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
import javafx.scene.shape.*;
import java.util.Scanner;


public class HangMan extends Application{

  //for multi window purposes
  final static int classValue = 10;


  //for displaying guessed letters
  //private Label guessedLetters = new Label("");


  public static int main(String args[]){
    launch(args);
    return classValue;
  }

  @Override
  public void start(Stage primaryStage){
    int wordsCompleted = 0;

    //makes the gridpane for the title screen
    Pane titlePane = new Pane();


    //send theBackgroundView to be behind the nodes

    //Defines an image
    ImageView backGround = new ImageView(new Image("images//chalkboard.jpg"));
    backGround.setX(0.0f);
    backGround.setY(0.0f);
    backGround.toBack();


    //the title
    Label title = new Label("Hang Man");
    Font font = Font.font("Marker Felt", 60);
    title.setFont(font);
    title.setTextFill(Color.WHITE);
    title.setLayoutX(275.0f);
    title.setLayoutY(150.0f);


    //the button that commences the game
    Button btStart = new Button("Start");
    font = Font.font("Marker Felt", 30);
    btStart.setFont(font);
    btStart.setLayoutX(345.0f);
    btStart.setLayoutY(300.0f);


    //proceeds to the next scene once the button is pressed
    btStart.setOnAction((e) -> {
      categoryScene(primaryStage, wordsCompleted);
    });


    titlePane.getChildren().addAll(backGround, title,btStart);
    Scene scene = new Scene(titlePane, 800, 548);


    primaryStage.setTitle("Hang Man"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }



  //builds the scene to pick a category
  protected static void categoryScene(Stage primaryStage, int words){
    String wordsCompleted = Integer.toString(words);

    Pane categoryPane = new Pane();

    //sets the background to a chalkboard
    ImageView backGround = new ImageView(new Image("images//chalkboard.jpg"));
    backGround.setX(0.0f);
    backGround.setY(0.0f);
    backGround.toBack();

    //sets the big letters
    Label categoryTitle = new Label("Pick A Word Category");
    Font font = Font.font("Marker Felt", 60);
    categoryTitle.setFont(font);
    categoryTitle.setTextFill(Color.WHITE);
    categoryTitle.setLayoutX(150.0f);
    categoryTitle.setLayoutY(150.0f);

    Label wordsCompletedLabel = new Label("You have completed: " + wordsCompleted + " word(s).");
    font = Font.font("Marker Felt", 60);
    wordsCompletedLabel.setFont(font);
    wordsCompletedLabel.setTextFill(Color.WHITE);
    wordsCompletedLabel.setLayoutX(50.0f);
    wordsCompletedLabel.setLayoutY(400.0f);


    //makes the buttons
    Button btAnimal = new Button("Animals");
    font = Font.font("Marker Felt", 25);
    btAnimal.setFont(font);
    btAnimal.setLayoutX(180.0f);
    btAnimal.setLayoutY(300.0f);

    Button btCountries = new Button("Countries");
    font = Font.font("Marker Felt", 25);
    btCountries.setFont(font);
    btCountries.setLayoutX(345.0f);
    btCountries.setLayoutY(300.0f);

    Button btFood = new Button("Food");
    font = Font.font("Marker Felt", 25);
    btFood.setFont(font);
    btFood.setLayoutX(530.0f);
    btFood.setLayoutY(300.0f);


    btAnimal.setOnAction((e) ->{
      mainStage(primaryStage, "animals", words);
    });
    btCountries.setOnAction((e) ->{
      mainStage(primaryStage, "countries", words);
    });
    btFood.setOnAction((e) ->{
      mainStage(primaryStage, "food", words);
    });

    categoryPane.getChildren().addAll(backGround, categoryTitle, wordsCompletedLabel, btAnimal, btCountries, btFood);

    Scene scene = new Scene(categoryPane, 800, 548);


    primaryStage.setTitle("Hang Man"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  protected static void mainStage(Stage primaryStage, String categoryName, int words){
    String wordsCompleted = Integer.toString(words);

    /*
    Borderpane for the entire screen that holds everything in it
    Borderpane breakdown:
    Centre:
    has the stick figure
    and a label that shows how many words have been completed
    Bottom:
    has guessed words and make a guess button and text field
    right has the word and the category
    Right:
    a label that tells the category
    a label that has the word that is being guessed
    */
    BorderPane whole = new BorderPane();


    //CentrePane buidling
    //Holds the stick figure drawing
    Pane figurePane = new Pane();
    figurePane.setPrefWidth(500);
    figurePane.setPrefHeight(548);

    //makes the stick figure in figurePane then makes it invisible until needed
    makeFigure(figurePane);


    /*
    Pane for bottom
    holds: the make a guess label and text field, guessed letters label and
    text field, button for guessing
    */
    Pane guessPane = new Pane();
    Font font = Font.font("Marker Felt", 20);


    //Bottom of the Borderpane building

    //the label for the letter guess
    Label guessLabel = new Label("Guess a letter: ");
    guessLabel.setFont(font);
    guessLabel.setLayoutX(10.0f);
    guessLabel.setLayoutY(30.0f);

    ImageView deskBackGround = new ImageView(new Image("images//desk.jpg"));


    //for guessing a letter
    TextField guessALetter = new TextField();
    guessALetter.setFont(font);
    guessALetter.setLayoutX(150.0f);
    guessALetter.setLayoutY(20.0f);

    //the letters that have been guessed label
    Label guessedLettersLabel = new Label("Guessed Letters: ");
    guessedLettersLabel.setFont(font);
    guessedLettersLabel.setLayoutX(450.0f);
    guessedLettersLabel.setLayoutY(28.0f);

    //the letters that have been guessed
    Label guessedLetters = new Label("");
    guessedLetters.setFont(font);
    guessedLetters.setLayoutX(600.0f);
    guessedLetters.setLayoutY(20.0f);




    //the button for making a guess
    Button btGuess = new Button("Make a Guess!");
    font = Font.font("Marker Felt", 25);

    btGuess.setFont(font);
    btGuess.setLayoutX(350.0f);
    btGuess.setLayoutY(150.0f);


    guessPane.getChildren().addAll(deskBackGround, guessLabel, guessALetter, guessedLettersLabel, guessedLetters, btGuess);






    //Right of the BorderPane Building

    Pane rightPane = new Pane();
    rightPane.setPrefWidth(300);
    rightPane.setPrefHeight(548);

    //calling the function to choose a word from the specific category
    String category = categoryName;
    String mysteryWord = chooseWord(category);



    //two labels one is category one is word

    //Says what category the current word is from
    //makes the font BIG
    font = Font.font("Marker Felt", 50);
    Label categoryLabel = new Label(category);
    categoryLabel.setFont(font);
    categoryLabel.setTextFill(Color.WHITE);
    categoryLabel.setLayoutX(0.0f);
    categoryLabel.setLayoutY(10.0f);


    //label for the word to be guessed
    //Label guessWordLabel = new Label("The Word: ");
    //rightPane.setConstraints(guessWordLabel, 0, 2);

    //label where the word is solved
    //build display makes the blank form of the word
    Label wordToGuess = new Label(buildDisplay(mysteryWord));
    font = Font.font("Marker Felt", 40);
    wordToGuess.setFont(font);
    wordToGuess.setTextFill(Color.WHITE);
    wordToGuess.setLayoutX(0.0f);
    wordToGuess.setLayoutY(100.0f);

    Label wordsCompletedLabel = new Label(wordsCompleted + " word(s)");
    font = Font.font("Marker Felt", 30);
    wordsCompletedLabel.setFont(font);
    wordsCompletedLabel.setTextFill(Color.WHITE);
    wordsCompletedLabel.setLayoutX(0.0f);
    wordsCompletedLabel.setLayoutY(200.0f);



    //rightPane.getChildren().addAll(categoryLabel, guessWordLabel, wordToGuess);
    rightPane.getChildren().addAll(categoryLabel, wordToGuess, wordsCompletedLabel);






    //the button action checker for when they make a guess
    btGuess.setOnAction((e) -> {
      //Gets the letter that has been guessed
      String letterGuessed = guessALetter.getText();

      //makes it lower case
      letterGuessed = letterGuessed.toLowerCase();



      //only continues if the guess was within rules
      /*
      current rules:
      if its only 1 letter long
      if it hasnt been guessed before
      */
      if(legalGuess(letterGuessed, guessedLetters.getText())){

        //checks if the guessed letter is in the mystery word
        if(guessIsCorrect(letterGuessed, mysteryWord)){

          //gets the already guessed letters into a string for appending
          String alreadyGuessed = guessedLetters.getText();
          //puts it on the label
          guessedLetters.setText(alreadyGuessed + letterGuessed);

          //put the letters that have been guessed into the display of the mystery word
          addLetter(letterGuessed, mysteryWord, wordToGuess);

        }else{

          //gets the already guessed letters into a string for appending
          String alreadyGuessed = guessedLetters.getText();
          //puts it on the label
          guessedLetters.setText(alreadyGuessed + letterGuessed);

          guessedWrong(figurePane);

        }

      }else{


      }
      //checks if the game has been won yet
      isWon(primaryStage, wordToGuess, words);

      isLost(primaryStage, figurePane, words);

      guessALetter.clear();
    });




    //if the player guesses the wrong word
    //guessedWrong(figurePane);

    whole.setCenter(figurePane);
    whole.setBottom(guessPane);
    whole.setRight(rightPane);


    //the main scene for the game
    Scene scene = new Scene(whole, 800, 800);



    primaryStage.setTitle("Hang Man"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage


  }

  protected static void winScreen(Stage primaryStage, int words){

    String numberOfWords = Integer.toString(words);
    Pane winPane = new Pane();
    //Defines an image
    ImageView backGround = new ImageView(new Image("images//chalkboard.jpg"));
    backGround.setX(0.0f);
    backGround.setY(0.0f);
    backGround.toBack();

    //add a label that shows the number of words wordscompleted
    //add a button that takes you back to categories
    //add a buttont that

    //label for number of words
    Label numberWordsLabel = new Label("You have completed " + numberOfWords + " word(s) in a row");
    Font font = Font.font("Marker Felt", 40);
    numberWordsLabel.setFont(font);
    numberWordsLabel.setTextFill(Color.WHITE);
    numberWordsLabel.setLayoutX(100.0f);
    numberWordsLabel.setLayoutY(10.0f);


    Label nameLabel= new Label("Enter Name");
    font = Font.font("Marker Felt", 30);
    numberWordsLabel.setFont(font);
    numberWordsLabel.setTextFill(Color.WHITE);
    numberWordsLabel.setLayoutX(150.0f);
    numberWordsLabel.setLayoutY(200.0f);

    TextField name = new TextField();
    name.setFont(font);
    name.setLayoutX(300.0f);
    name.setLayoutY(200.0f);




    font = Font.font("Marker Felt", 40);

    //the button for playing again
    Button btReplay = new Button("Play Again?");
    btReplay.setFont(font);
    btReplay.setLayoutX(150.0f);
    btReplay.setLayoutY(350.0f);

    //the button for submitting your score
    Button btSubmit = new Button("Submit Score?");
    btSubmit.setFont(font);
    btSubmit.setLayoutX(375.0f);
    btSubmit.setLayoutY(350.0f);


    btReplay.setOnAction((e) ->{
      categoryScene(primaryStage, words);
    });
    btSubmit.setOnAction((e) ->{
      submitScore(primaryStage, numberOfWords, name.getText());
    });


    winPane.getChildren().addAll(backGround, numberWordsLabel, nameLabel, name, btReplay, btSubmit);


    Scene scene = new Scene(winPane, 800, 548);

    primaryStage.setTitle("Hang Man"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }


  protected static void loseScreen(Stage primaryStage, int words){

    String numberOfWords = Integer.toString(words);

    Pane losePane = new Pane();

    //Defines an image
    ImageView backGround = new ImageView(new Image("images//chalkboard.jpg"));
    backGround.setX(0.0f);
    backGround.setY(0.0f);
    backGround.toBack();

    //add a label that shows the number of words wordscompleted
    //add a button that takes you back to categories
    //add a buttont that

    //label for number of words
    Label numberWordsLabel = new Label("You lost having completed " + numberOfWords + " word(s) in a row");
    Font font = Font.font("Marker Felt", 40);
    numberWordsLabel.setFont(font);
    numberWordsLabel.setTextFill(Color.WHITE);
    numberWordsLabel.setLayoutX(75.0f);
    numberWordsLabel.setLayoutY(100.0f);


    //the label and textfield for putting a name into the score
    Label nameLabel= new Label("Enter Name");
    font = Font.font("Marker Felt", 30);
    numberWordsLabel.setFont(font);
    numberWordsLabel.setTextFill(Color.WHITE);
    numberWordsLabel.setLayoutX(200.0f);
    numberWordsLabel.setLayoutY(200.0f);

    TextField name = new TextField();
    name.setFont(font);
    name.setLayoutX(300.0f);
    name.setLayoutY(200.0f);


    font = Font.font("Marker Felt", 40);

    //the button for playing again
    Button btReplay = new Button("Play Again?");
    btReplay.setFont(font);
    btReplay.setLayoutX(100.0f);
    btReplay.setLayoutY(275.0f);

    //the button for submitting your score
    Button btSubmit = new Button("Submit Score?");
    btReplay.setFont(font);
    btReplay.setLayoutX(350.0f);
    btReplay.setLayoutY(275.0f);


    btReplay.setOnAction((e) ->{
      categoryScene(primaryStage, words);
    });
    btSubmit.setOnAction((e) ->{

      //function that basically ends the game here
      submitScore(primaryStage, numberOfWords, name.getText());
    });


    losePane.getChildren().addAll(backGround, numberWordsLabel, nameLabel, name, btReplay, btSubmit);
    Scene scene = new Scene(losePane, 800, 548);

    primaryStage.setTitle("Hang Man"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  //builds the stick figure that is in the game
  //takes in the middle pane
  protected static void makeFigure(Pane pane){

    //Make a head, left and right arm and left and right leg
    //head = circle
    //everything else is a line

    //sets the background to a chalkboard
    ImageView backGround = new ImageView(new Image("images//chalkboard.jpg"));
    backGround.setX(0.0f);
    backGround.setY(0.0f);
    backGround.toBack();

    //Circle head = new Circle();
    Circle head = new Circle();
    Line body = new Line();
    Line rArm = new Line();
    Line lArm = new Line();
    Line rLeg = new Line();
    Line lLeg = new Line();

    //place them
    //head
    float headX = 300.0f;
    float headY = 150.0f;


    head.setCenterX(headX);
    head.setCenterY(headY);
    head.setRadius(50.0f);
    head.setFill(Color.TRANSPARENT);
    head.setStroke(Color.WHITE);
    head.setStrokeWidth(5);


    headY = headY + 50.0f;

    // body and arms
    rArm.setStartX(headX);
    rArm.setStartY(headY+75.0f);
    rArm.setEndX(headX + 100.0f);
    rArm.setEndY(headY+75.0f);
    rArm.setStroke(Color.WHITE);
    rArm.setStrokeWidth(5);


    lArm.setStartX(headX-100.0f);
    lArm.setStartY(headY+75.0f);
    lArm.setEndX(headX);
    lArm.setEndY(headY+75.0f);
    lArm.setStroke(Color.WHITE);
    lArm.setStrokeWidth(5);


    headY = headY - 50.0f;
    body.setStartX(headX);
    body.setStartY(headY+50.0f);
    body.setEndX(headX);
    body.setEndY(headY+200.0f);
    body.setStroke(Color.WHITE);
    body.setStrokeWidth(5);


    headY = headY+200.0f;

    rLeg.setStartX(headX);
    rLeg.setStartY(headY);
    rLeg.setEndX(headX+100.0f);
    rLeg.setEndY(headY+100.0f);
    rLeg.setStroke(Color.WHITE);
    rLeg.setStrokeWidth(5);


    lLeg.setStartX(headX);
    lLeg.setStartY(headY);
    lLeg.setEndX(headX-100.0f);
    lLeg.setEndY(headY+100.0f);
    lLeg.setStroke(Color.WHITE);
    lLeg.setStrokeWidth(5);





    //Make them all invisible until they are needed

    head.setVisible(false);
    body.setVisible(false);
    rArm.setVisible(false);
    lArm.setVisible(false);
    rLeg.setVisible(false);
    lLeg.setVisible(false);



    //make the gallows



    Line base = new Line();
    Line height = new Line();
    Line topPart = new Line();
    Line noose = new Line();

    //make the noose part
    noose.setStartX(headX);
    noose.setStartY(100.0f);
    noose.setEndX(headX);
    noose.setEndY(50.0f);
    noose.setStroke(Color.WHITE);
    noose.setStrokeWidth(5);


    //make the top part
    topPart.setStartX(headX);
    topPart.setStartY(50.0f);
    topPart.setEndX(headX-200.0f);
    topPart.setEndY(50.0f);
    topPart.setStroke(Color.WHITE);
    topPart.setStrokeWidth(5);

    //make the big long part
    height.setStartX(headX-200.0f);
    height.setStartY(50.0f);
    height.setEndX(headX-200.0f);
    height.setEndY(400.0f);
    height.setStroke(Color.WHITE);
    height.setStrokeWidth(5);


    //make the base part
    base.setStartX(headX-250.0f);
    base.setStartY(400.0f);
    base.setEndX(headX-150.0f);
    base.setEndY(400.0f);
    base.setStroke(Color.WHITE );
    base.setStrokeWidth(5);



    //put everything in the pane
    pane.getChildren().addAll(backGround, head, body, rArm, lArm, rLeg, lLeg, base, height, topPart, noose
    );
    return;
  }

  //checks that the guess fits all rules, does not check if its a wrong guess
  /*
  current rules:
  has to be a 1 character string
  cant have been guessed before(checked through the global label that has guesses)
  */
  protected static boolean legalGuess(String guess, String alreadyGuessed){
    if(guess.length() != 1){
      return false;
    }



    for(int i = 0; i < alreadyGuessed.length(); ++i){
      if(guess.charAt(0) == alreadyGuessed.charAt(i)){
        return false;
      }
    }
    return true;
  }

  //If the letter guessed is not a correct guess
  //takes the pane as input and adds a part of the stick figure
  //if the stickman is complete and another guess is wrong you lose
  protected static void guessedWrong(Pane pane){

    for(int i = 1; i <= 6; ++i){
      if(!pane.getChildren().get(i).isVisible()){
        pane.getChildren().get(i).setVisible(true);
        return;
      }
    }
  }

  //checks to see if the man is completed if so game is over
  protected static void isLost(Stage primaryStage, Pane figurePane, int words){

    if(figurePane.getChildren().get(6).isVisible()){
      loseScreen(primaryStage, words);
    }

    return;
  }


  //chooses a word from the seleceted category file using a Scanner
  //generates a random number and takes that line(one word per line) from the file
  protected static String chooseWord(String category){
    //the selected word based on the criteria used (category stuff like that)
    String chosenWord = "";

    //try stuff for file io
    try {

      // Create a File instance with the text files in the sub
      java.io.File file = new java.io.File("categories//" + category + ".txt");


      // Create a Scanner for the file
      Scanner wordFinder = new Scanner(file);

      //iterator
      int i = 1;
      //go through file until the word has been reached
      while (wordFinder.hasNext() && i < randomNum()) {
        chosenWord = wordFinder.nextLine();
        ++i;
      }

      // Close the file
      wordFinder.close();

    }
    catch(IOException ex) {
      //System.err.println("Choose word broke");
      System.out.println(ex);
    }


    //returns the word that has been picked
    System.out.println(chosenWord);
    return chosenWord.toLowerCase();
  }

  //returns a random number
  public static int randomNum(){
    Random rand = new Random();
    int n = rand.nextInt(52)+1;
    return n;
  }








  //checks if the guessed letter is in the word
  //inputs of the guessed letter and the actual word outputs a boolean
  //full word is the entire word to be guessed with no blanks
  protected static boolean guessIsCorrect(String guess, String fullWord){
    //if the user gets a single letter right true is returned immediately

    for(int i = 0; i < fullWord.length(); ++i){
      if(guess.charAt(0) == fullWord.charAt(i)){
        return true;
      }
    }
    return false;
  }


  // builds the blank underscore string in the right pane of border pane
  //takes the mystery word as an input and makes a blank with the same length

  protected static String buildDisplay(String word){
    String blanks = "_";
    for(int i = 0; i < word.length()-1; ++i){
      blanks = blanks + " _";
    }




    return blanks;
  }


  //adds the letter that has been guessed to the displayed word(the blanks)
  //takes the letter the actual word and the displayed word(the blanks) as
  //inputs, returns after it has put the letters in the display
  protected static void addLetter(String letter, String actualWord, Label displayedWord){
    String blanks = displayedWord.getText();
    String updatedWord = "";

    //the length of the blanks string which will have n -1 spaces in it
    int n = actualWord.length();


    //if its the first index of the blanks have to do it different
    if(blanks.charAt(0) == '_'){
      if(letter.charAt(0) == actualWord.charAt(0)){
        updatedWord = updatedWord + letter.charAt(0);
      }else{
        updatedWord = updatedWord + "_";
      }
    }else{
      updatedWord = updatedWord + blanks.charAt(0);
    }

    int j = 2;
    //there will always be n-1 spaces in a word of length n
    for(int i = 1; i < n; ++i){
      if(letter.charAt(0) == actualWord.charAt(i)){
        updatedWord = updatedWord + " " + letter.charAt(0);
      }else {
        updatedWord = updatedWord + " " + blanks.charAt(j);
      }

      //iterates through the string that is made of blanks
      j = j+2;
    }

    displayedWord.setText(updatedWord);

  }

  //checks to see if the word has been completed if it has returns true
  protected static void isWon(Stage primaryStage, Label displayedWord, int words){
    String currentWord = displayedWord.getText();

    for(int i = 0; i < currentWord.length(); ++i){
      if(currentWord.charAt(i) == '_'){

        return;
      }
    }
    winScreen(primaryStage, words+1);
  }


  protected static void submitScore(Stage primaryStage, String words, String name){
    words = name + " " +words;
    SendData SendData = new SendData("127.0.0.1", 5000, "hang " + words);

  }

}
