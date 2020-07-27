package sample;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class ChooseFile {
    static PreferenceClass preferenceClass = new PreferenceClass();
    FileChooser fileChooser = new FileChooser();

    public ChooseFile() {
        fileChooser.setTitle("Loading file");
        // fileChooser.showOpenDialog(primaryStage);
        if (!preferenceClass.getDirectoryPreferences().isEmpty()) {
            File initdir = new File(preferenceClass.getDirectoryPreferences());
            fileChooser.setInitialDirectory(initdir);

        }
    }

    public File getFile() {
        //updating initial directory from user preferencs
        if (!preferenceClass.getDirectoryPreferences().isEmpty()) {
            File initdir = new File(preferenceClass.getDirectoryPreferences());
            fileChooser.setInitialDirectory(initdir);

        }
        File file = null;
        try {
            file = fileChooser.showOpenDialog(null);

        } catch (NullPointerException nu) {
            System.out.println("the user did't chose any file");
        }
        return file;
    }


}
