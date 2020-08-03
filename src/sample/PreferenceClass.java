package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.prefs.Preferences;
/*
A node in a hierarchical collection of preference data. This class allows applications to store and retrieve user and system preference and configuration data.
 This data is stored persistently in an implementation-dependent backing store. Typical implementations include flat files, OS-specific registries, directory servers and SQL databases.
 The user of this class needn't be concerned with details of the backing store.*/
public class PreferenceClass {// all fields are static because everywhere we should have access to user preferences
   static Preferences prefs;
  static  String ID1 = "user directory";
  static String ID2 = "user last dictionary select";
    //this class also have a button control for GUI purposes
   static  Button prefdir = new Button("Choose a directory");

    public PreferenceClass() {
        prefs = Preferences.userRoot().node(this.getClass().getName());

prefdir.setOnAction(event);
    }

    public void setDirectoryPreferences(String directory) {
        prefs.put(ID1, directory);
    }

    public String getDirectoryPreferences() {
        return prefs.get(ID1, "");
    }

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            DirectoryChooser fc = new DirectoryChooser();
            try {
                if(!prefs.get(ID1,"").isEmpty())
fc.setInitialDirectory(new File(prefs.get(ID1,"")));
                File f = fc.showDialog(null);
                ChooseFile.preferenceClass.setDirectoryPreferences(f.getAbsolutePath());
                fc = null;
                f = null;

            } catch (NullPointerException e) {// the user may close the file chooser dialog without saving a directory

            }
        }

    };
}