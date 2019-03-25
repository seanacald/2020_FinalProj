/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlescreen;

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
public class Game4_24{
    static boolean gameStarted = false;
    static boolean winStatus = false;
    static Button b00;
    static Button b01;
    static Button b10;
    static Button b11;
    static Label score,data;
    static Game game;
    static Button reset,next,plus,minus,times,divide;
    static Stage primaryStage = new Stage();
    public static void display() {
        int operationSelected=-1;//addition = 0, subtraction = 1, multiplication = 2, division = 3
        game = new Game();

        //create number buttons and create an array of references to them
        b00 = new Button("PLEASE");
        b01 = new Button("PRESS Q");
        b10 = new Button("TO START");
        b11 = new Button("GAME!!!");

        Button[] buttons = {b00,b01,b10,b11};
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
                        score.setText("points: "+game.getScore()+"\n q for next");
                        data.setText("yay! 24 :)");
                    }
                }
            });
        }

        //reset button config
        reset = new Button("cls");
        reset.setMinSize(30, 30);
        reset.setOnAction(e->{
            if (gameStarted==true){
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
            game.resetMove();
                for (Button button : buttons){
                    button.setDisable(false);
                    button.setVisible(true);
                }
                createBoard(game, b00,b01,b10,b11);
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

        data = new Label("Press q to start");
        score = new Label("Score: "+ (int)game.getScore());
        VBox labels = new VBox(15);
        labels.setMargin(data,new Insets(90,0,0,0));
        labels.getChildren().addAll(data,score);

        HBox finalLayout = new HBox(10);
        finalLayout.getChildren().addAll(allButtons,labels);
        Scene scene =  new Scene (finalLayout, 350,275);

        scene.setOnKeyPressed(e->{
            if (e.getCode()==KeyCode.Q && gameStarted == false){//START GAME, activates if game isnt active
                data.setText("");
                game.startTime();
                gameStarted=true;
                for (Button button : buttons){
                    button.setDisable(false);
                }
                createBoard(game, b00,b01,b10,b11);
            }
            else if (e.getCode()==KeyCode.Q && winStatus==true){
                winStatus=false;
                game.resetMove();
                for (Button button : buttons){
                    button.setDisable(false);
                    button.setVisible(true);
                }
                createBoard(game, b00,b01,b10,b11);
                game.startTime();

            }
        });
        primaryStage.setTitle("24");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void act(Game game,Button button) {
        if (game.getTurn()==false){//fresh
            
            game.setOp(-1);
            double val = Double.parseDouble(button.getText());
            game.setTerm1(val);
            button.setDisable(true);
            game.setTurn(true);
        }
        else if (game.getTurn()==true && game.getOp()!=-1){
            double val = Double.parseDouble(button.getText());
            game.setTerm2(val);
            int op=game.getOp();
            double result=0;
            double i = game.getTerm1(),j=game.getTerm2();

            if (op==0) result = i+j;
            else if (op==1) result = i-j;
            else if (op==2) result = i*j;
            else if (op==3) result = i/j;
            button.setText(result+"");
            game.setTurn(false);
            game.nextMove();

            if (game.getMove()==3 && result==24){
                winStatus=true;
                game.stopTime();
                game.raise();
            }


        }
    }
    public static void createBoard(Game game, Button a, Button b, Button c, Button d){
        Button[] buttons = {a,b,c,d};
        for (int i = 0 ; i<4 ; i++){
            game.setVal((double)(1+(int)(Math.random() * ((12 - 1) + 1))), i);
            buttons[i].setText(game.getVals()[i]+"");
        }
    }
    public static void setInvis(Button[] buttons){
        for (Button button : buttons){
            if (button.isDisabled()){
                button.setVisible(false);
            }
        }
    }
}
