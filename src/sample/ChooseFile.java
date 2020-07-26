package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class ChooseFile {
    Desktop desktop = Desktop.getDesktop();
    FileChooser fileChooser = new FileChooser();
    Stage primaryStage;

    public ChooseFile(Stage stage) {

        primaryStage = stage;


        fileChooser.setTitle("Loading file");
        // fileChooser.showOpenDialog(primaryStage);


    }

    public File getFile() {
        File file = null;
        try {
            file = fileChooser.showOpenDialog(primaryStage);

        } catch (NullPointerException nu) {
            System.out.println("the user did't chose any file");
        }
        return file;
    }


}
