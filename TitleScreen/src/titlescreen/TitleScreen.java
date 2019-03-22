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
public class TitleScreen extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        final Menu menu1 = new Menu("File");
        MenuBar menuBar = new MenuBar();
        MenuItem menuNew = new MenuItem("New");
        MenuItem menuOpen = new MenuItem("Open");
        MenuItem menuSave = new MenuItem("Save");
        MenuItem menuSaveAS = new MenuItem("Save As");
        MenuItem menuExit = new MenuItem("Exit");
        menu1.getItems().addAll(menuNew, menuOpen, menuSave, menuSaveAS, menuExit);
        menuBar.getMenus().add(menu1);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        
        Button btnBlackJack = new Button("Black Jack");
        
        btnBlackJack.setOnAction(e -> {
            primaryStage.hide();
            blackJackGame.display(menuBar);
            primaryStage.show();
        });
        
        Button btnGame2 = new Button("Game2");
        Button btnGame3 = new Button("Game3");

        btnBlackJack.setLayoutX(20);
        btnBlackJack.setLayoutY(30);
        Pane root = new Pane();
        root.getChildren().addAll(menuBar, btnBlackJack);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
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
