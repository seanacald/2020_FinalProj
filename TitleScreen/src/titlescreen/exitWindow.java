/*
* Isaiah von Uders
* March 25th, 2019
* Final Project
* Black Jack - exitWindow
* This program is an additional file used to figure out what exactly the user
* wants to do when closing a window.
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
        
        //Create new stage
        Stage window = new Stage();
        
        //Prevent the user from clicking off this menu
        window.initModality(Modality.APPLICATION_MODAL);
        //Set the title of this stage to be Exit Menu, and set minimum width to be 500
        window.setTitle("Exit Menu");
        window.setMinWidth(500);
        
        //Create a new Label with text asking them if they want to return to menu
        Label label = new Label();
        label.setText("Do you want to return back to the main menu screen?");
        
        
        //Create a Yes Button and a No Button
        Button btnYes = new Button("Yes");
        Button btnNo = new Button("No");
        btnYes.setOnAction(e -> {
            //If the user clicks the Yes Button
            answer = 1;
            window.close();
        });
        
        btnNo.setOnAction(e -> {
            //If the user clicks the No Button
            answer = 0;
            window.close();
        });
        
        window.setOnCloseRequest(e -> {
            //If the user clicks the X button at the top left, do not close the game
            answer = 2;
            window.close();
        });
        
        //Create a new VBox, add all nodes to it, and set the alignment to be the center
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,btnYes,btnNo);
        layout.setAlignment(Pos.CENTER);
        
        //Add the VBox to the scene, and add the scene to the window
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        //Return the answer (which indicates proper closure protocal).
        return answer;
    
    }
}
