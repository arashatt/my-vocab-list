package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {


    private Scene mainScene;
    VBox vBox= new VBox();
Button newList = new Button("New List");
    public void start(Stage primaryStage) throws Exception {
        vBox.getChildren().add(newList );
        ArrayList<List> lists = new ArrayList<>();
        newList.setOnAction(e ->{
            try {
                lists.add(new List());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        vBox.setPadding(new Insets(10,50,10,50));
        mainScene = new Scene(vBox);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
}
