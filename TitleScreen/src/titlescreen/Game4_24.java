/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.pkg4_24;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author micha
 * game objective : create a combination of 24 as fast you can, using 4 randomly generated number 1..12, using +,-,*,/
 * score between 100 and 1000 based on time
 * max score of 1000 if solved between 0 and 5 seconds, down to 100 by the time the 20 second mark is hit
 * not all sets of 4 numbers can create 24
 * each number can only be used once
 * 
 * 
 * Buttons:
 * 4 number buttons - 4 buttons with the current rotation of numbers
 * 4 
 */
public class Game4_24 extends Application{
    private boolean gameStarted = false; 
    private boolean winStatus = false;
    private Button b00;
    private Button b01;
    private Button b10;
    private Button b11;
    private Label score,data;
    private Game game;
    private Button reset,next,plus,minus,times,divide;
    private Scene resultScene;

    @Override
    public void start(Stage primaryStage) {
        int operationSelected=-1;//addition = 0, subtraction = 1, multiplication = 2, division = 3
        game = new Game();
        
        //create number buttons and create an array of references to them
        b00 = new Button("PLEASE");
        b01 = new Button("TO START");
        b10 = new Button("PRESS Q");
        b11 = new Button("GAME!!!");
        
        Button[] buttons = {b00,b01,b10,b11};
        Button[] otherButtons= {reset,next,plus,minus,times,divide};
        //primary button setup
        for (Button button : buttons){
            button.setMinSize(100, 100);//sizing
            button.setMaxSize(100, 100);
            button.setDisable(true);//initially disable buttons
            
            button.setOnAction(e->{//add click function
                data.setText("");

                if (gameStarted==true){
                    
                    act(game,button);
                    score.setText("points: "+game.getScore());
                    if (game.getTurn()==false)
                        setInvis(buttons);
                    
                    if (winStatus==false && game.getMove()==3)
                        data.setText("not 24 :(");  
                    if (winStatus==true){
                        score.setText("points: "+game.getScore()+"\n q for next\n z to end");
                        data.setText("yay! 24 :)");
                    }
                }
            });
        }
        
        //reset button config
        reset = new Button("cls");
        reset.setMinSize(30, 30);
        reset.setOnAction(e->{
            if (gameStarted==true && winStatus==false){
                data.setText("");
                game.resetMove();
                game.setOp(-1);
                game.setTurn(false);
                for (int i = 0 ; i < 4 ; i++){
                    buttons[i].setDisable(false);//turn all buttons back on
                    buttons[i].setText(game.getVals()[i]+"");//set all button values back to original values
                    buttons[i].setVisible(true);
                }
            }
        });
        
        
        //plus button config
        plus = new Button("+");
        plus.setOnAction(e->{
            if (game.getTurn()==true){
                game.setOp(0);
            }
        });
        plus.setMinSize(30, 30);
        
        //minus button config
        minus = new Button("-");
        minus.setOnAction(e->{
            if (game.getTurn()==true){
                game.setOp(1);
            }
        });
        minus.setMinSize(30, 30);
        
        //times button config
        times = new Button("*");
        times.setOnAction(e->{
            if (game.getTurn()==true){
                game.setOp(2);
            }
        });
        times.setMinSize(30, 30);
        
        //div button config
        divide = new Button("÷");
        divide.setOnAction(e->{
            if (game.getTurn()==true){
                game.setOp(3);
            }
        });
        divide.setMinSize(30, 30);

        
        //skip button config 
        next = new Button(">>>");
        next.setOnAction(e->{
            if (winStatus==false && gameStarted==true){
                game.resetMove();
                game.setOp(-1);
                game.setTurn(false);
                for (Button button : buttons){
                    button.setDisable(false);
                    button.setVisible(true);
                }
                createBoard(game, buttons);
            }
        });
        next.setMinSize(30, 30);
        
        HBox funcRow = new HBox();
        funcRow.getChildren().addAll(reset,plus,minus,times,divide,next);
        funcRow.setMargin(reset,new Insets(0,10,0,0));
        funcRow.setMargin(next, new Insets(0,0,0,10));
        
        GridPane numPane = new GridPane();
        numPane.add(b00,0,0);
        numPane.add(b01,0,1);
        numPane.add(b10,1,0);
        numPane.add(b11,1,1);
        numPane.setHgap(30);
        numPane.setVgap(30);
        
        VBox allButtons = new VBox(10);
        allButtons.getChildren().addAll(numPane,funcRow);//adds the number buttons, function button, esc, skip buttons

        data = new Label("Press q to start \n Press z at any time\n to stop and record score  ");
        score = new Label("Score: "+ (int)game.getScore());
        VBox labels = new VBox(15);
        labels.setMargin(data,new Insets(90,0,0,0));
        labels.getChildren().addAll(data,score);

        HBox finalLayout = new HBox(10);
        finalLayout.getChildren().addAll(allButtons,labels);
        Scene scene =  new Scene (finalLayout, 425,275);

        
        scene.setOnKeyPressed(e->{
            if (e.getCode()==KeyCode.Q && gameStarted == false){//START GAME, activates if game isnt active
                data.setText("");
                game.startTime();
                gameStarted=true;
                for (Button button : buttons){
                    button.setDisable(false);
                }
                createBoard(game, buttons);
            }
            else if (e.getCode()==KeyCode.Q && winStatus==true){//creates next question after user has solved current
                winStatus=false;
                game.resetMove();
                for (Button button : buttons){
                    button.setDisable(false);
                    button.setVisible(true);
                }
                data.setText("");
                createBoard(game, buttons);
                game.startTime();
                
            }
            else if (e.getCode()==KeyCode.Z){
                VBox n = new VBox(5);
                gameStarted=false;
                if (game.getSolve()>=1){
                    String line;
                    line = "# solved: "+game.getSolve()+", time taken: "+game.getTotalTime() + ", "+game.getScore()/game.getSolve()+" avg points/question";
                    //display stats
                    Label solveResult=new Label(game.getSolve()+" solution(s) found");
                    Label timeResult = new Label(game.getTotalTime()+" seconds taken");
                    Label pointResult = new Label(game.getScore()+" points obtained");
                    Label pointPerQ = new Label(game.getScore()/game.getSolve() +" points avg / question solved");
                    n.getChildren().addAll(solveResult,timeResult,pointResult,pointPerQ);
                    //SEAN USE BELOW CLIENT CODE
                    //Client client = new Client("127.0.0.1", 5000, "four "+line);
                    resultScene = new Scene(n,300,100);

                }
                else{
                    Label out = new Label("Game ended with 0 solutions completed");
                    n.getChildren().add(out);
                    resultScene = new Scene(n,275,20);
                }
                primaryStage.setScene(resultScene);
            }
        });
        //ISAIAH ADD close -> main menu / exit
        primaryStage.setTitle("24");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);   
    }
    //perform operation based on selected buttons
    public void act(Game game,Button button) {
        //for setting up first term for operation
        if (game.getTurn()==false){//fresh
            game.setOp(-1);
            double val = Double.parseDouble(button.getText());
            game.setTerm1(val);
            button.setDisable(true);
            game.setTurn(true);
        }
        //for when first term exists, operation has been selected, and second term has been selected
        else if (game.getTurn()==true && game.getOp()!=-1){
            double val = Double.parseDouble(button.getText());
            game.setTerm2(val);
            int op=game.getOp();
            double result=0;
            double i = game.getTerm1(),j=game.getTerm2();
            
            //opperation ifs
            if (op==0) result = i+j;
            else if (op==1) result = i-j;
            else if (op==2) result = i*j;
            else if (op==3) result = i/j;
            //set new button to result of operation, reset 'turn'
            button.setText(result+"");
            game.setTurn(false);
            game.nextMove();
            
            //if 3 moves have been made and the result is 24, a solution has been found
            if (game.getMove()==3 && result==24){
                winStatus=true;
                game.stopTime();
                game.raise();
                game.setSolve(game.getSolve()+1);
            }
            
           
        }
    }
    //generates a board of random numbers between 1 and 12
    public void createBoard(Game game, Button[] buttons){
        for (int i = 0 ; i<4 ; i++){
            game.setVal((double)(1+(int)(Math.random() * ((12 - 1) + 1))), i);
            buttons[i].setText(game.getVals()[i]+"");
        }
    }
    //sets disabled button (from turn selection) to invisible as well
    public void setInvis(Button[] buttons){
        for (Button button : buttons){
            if (button.isDisabled()){
                button.setVisible(false);
            }
        }
    }
}
