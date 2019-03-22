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

public class exitWindow {
    static int answer;
    public static int callExit(){
        
        
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Exit Menu");
        window.setMinWidth(500);
        
        Label label = new Label();
        label.setText("Do you want to return back to the main menu screen?");
        
        
        
        Button btnYes = new Button("Yes");
        Button btnNo = new Button("No");
        btnYes.setOnAction(e -> {
            answer = 1;
            window.close();
        });
        
        btnNo.setOnAction(e -> {
            answer = 0;
            window.close();
        });
        
        window.setOnCloseRequest(e -> {
            answer = 2;
            window.close();
        });
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,btnYes,btnNo);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
    
    }
}
