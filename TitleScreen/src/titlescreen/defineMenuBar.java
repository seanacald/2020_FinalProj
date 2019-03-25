/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package titlescreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
/**
 *
 * @author ivonu
 */
public class defineMenuBar {
    public static MenuBar menuCreation(MenuBar theMenuBar, Stage theStage) {

        final Menu theMenu = new Menu("File");
        MenuItem menuNew = new MenuItem("New");
        MenuItem menuOpen = new MenuItem("Open");
        MenuItem menuSave = new MenuItem("Save");

        menuOpen.setOnAction(e -> {
            
            Stage theFileOpener = new Stage();
            FileChooser fc = new FileChooser();
            fc.setTitle("Set your save");
            File file = fc.showOpenDialog(theFileOpener);
            String name = "";
            String theirCurrentScore = "";
            try (BufferedReader theReader = new BufferedReader(new FileReader (file))){
               
                String allText;
                String[] nameAndScore = new String[2];
                while((allText = theReader.readLine()) != null){
                    nameAndScore = allText.split(" ");
                    name = nameAndScore[0];
                    theirCurrentScore = nameAndScore[1];
                }
            }
            catch (Exception ex) {
                Logger.getLogger(defineMenuBar.class.getName()).log(Level.SEVERE, null, ex);
            }
         System.out.println(name);
         System.out.println(theirCurrentScore);

        });
        
        theMenu.getItems().addAll(menuNew, menuOpen, menuSave);
        theMenuBar.getMenus().add(theMenu);
        theMenuBar.prefWidthProperty().bind(theStage.widthProperty());
        
        return theMenuBar;
        
        //
    }
    /*
    public static String getTheData(){
        Stage theFileOpener = new Stage();
            FileChooser fc = new FileChooser();
            fc.setTitle("Set your save");
            File file = fc.showOpenDialog(theFileOpener);
            String name = "";
            String theirCurrentScore = "";
            try (BufferedReader theReader = new BufferedReader(new FileReader (file))){
               
                String allText;
                String[] nameAndScore = new String[2];
                while((allText = theReader.readLine()) != null){
                    nameAndScore = allText.split(" ");
                    name = nameAndScore[0];
                    theirCurrentScore = nameAndScore[1];
                }
            }
            catch (Exception ex) {
                Logger.getLogger(defineMenuBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return name;
    }
    */
}
