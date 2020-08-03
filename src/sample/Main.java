package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    TabPane tabPane = new TabPane();
    ChooseFile chooseFile = new ChooseFile();
    private Scene mainScene;
    VBox vBox = new VBox();

    Button newList = new Button("New List");

    public void start(Stage primaryStage) throws Exception {

        vBox.getChildren().addAll(newList, PreferenceClass.prefdir, tabPane);
        ArrayList<List> lists = new ArrayList<>();
        newList.setOnAction(e -> {
            File file = chooseFile.getFile();
            if (file != null) {
                lists.add(new List(file));

                try {
                    tabPane.getTabs().add(lists.get(lists.size() - 1).list());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        vBox.setPadding(new Insets(10, 50, 10, 50));
        mainScene = new Scene(vBox, 350, 700);
primaryStage.setOnCloseRequest(e->{
    for(List li: lists){
        if(li.isOpen){
            li.exit();
        }
    }
});
        primaryStage.setHeight(700);
        primaryStage.setTitle("Welcome \u2660\u2665\u2663\u2666");
        primaryStage.setScene(mainScene);

        primaryStage.show();

    }
}
