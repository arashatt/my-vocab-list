package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.ChooseFile;
import sample.ListOfDics;
import sample.LoadingFile;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.TreeSet;

public class List {

ListOfDics listOfDics = new ListOfDics();
    ListView<String> listOfWords = new ListView<>();
    TreeSet<String> strings = new TreeSet<>();
    TextField input = new TextField();
    VBox root = new VBox();
    Stage stage = new Stage();
    Scene scene;
    LoadingFile loader = new LoadingFile();
    ChooseFile choose = new ChooseFile(stage);
    File file;
    TextField search = new TextField();
    Button chooseFile = new Button("Choose File");
    Button save1 = new Button("Save the changes");
    HBox hb = new HBox(chooseFile,save1);
    String undo = "";

    public List() throws Exception, NullPointerException {
        hb.setSpacing(5);
        search.setPromptText("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                listOfWords.scrollTo(search.getText());
                listOfWords.getSelectionModel().select(search.getText());
                listOfWords.getFocusModel().focus(listOfWords.getItems().indexOf(search.getText()));
             //   System.out.println("yeey"+      listOfWords.getFocusModel().isFocused(listOfWords.getItems().indexOf(search.getText()) ));                search.clear();
            }

        });
        save1.setOnAction(e->{
            SaveTheCurrent(file);
        });
        chooseFile.setOnAction(e -> {

            try {
                strings.addAll(loader.load(choose.getFile()));
                listOfWords.getItems().clear();
                listOfWords.getItems().addAll(strings);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        try {
            file = choose.getFile();
            strings = loader.load(file);
            listOfWords.getItems().addAll(strings);
        }catch (NullPointerException e){

        }

        input.setPromptText("Enter a word");
        input.setOnAction(e -> {
            listOfWords.getItems().clear();
            listOfWords.getItems().addAll(loader.addOneWord(input.getText()));
            input.clear();


        });
        listOfWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        try {
                            Desktop desktop = Desktop.getDesktop();
                            URI oURL = new URI(listOfDics.getComboBox().getSelectionModel().getSelectedItem().site + listOfWords.getSelectionModel().getSelectedItem());
                            desktop.browse(oURL);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {

                }
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                event.consume();
                exit();
            }
        });

        listOfWords.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {





                    try {
                        Desktop desktop = Desktop.getDesktop();

                        String encodeURL= URLEncoder.encode( listOfDics.getComboBox().getSelectionModel().getSelectedItem().site + listOfWords.getSelectionModel().getSelectedItem(), "UTF-8" );
                        desktop.browse(new URI(encodeURL));
                        ;
                    } catch (UnsupportedEncodingException | URISyntaxException e) {
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                if (event.getCode().equals(KeyCode.DELETE)) {
                    try {


                        undo = listOfWords.getSelectionModel().getSelectedItem();
                        listOfWords.getFocusModel().focusNext();
                        listOfWords.scrollTo(listOfWords.getSelectionModel().getSelectedIndex() + 1);
                        strings.remove(undo);
                        listOfWords.getItems().clear();
                        listOfWords.getItems().addAll(strings);
                    } catch (NullPointerException nu) {
                        System.out.println("nothing to delete");
                    }
                }
                if (event.getCode().equals(KeyCode.MINUS)) {


                    if (!undo.isEmpty()) {
                        listOfWords.getItems().clear();
                        listOfWords.getItems().addAll(loader.addOneWord(undo));
                    }
                }

            }
        });

        root = new VBox(input, search, listOfWords, listOfDics.getComboBox(), hb);
        root.setSpacing(15);
        root.setPadding((new Insets(10, 50, 50, 50)));
        scene = new Scene(root);
        stage.setTitle("LIST Of WORDS");
        stage.setScene(scene);
        stage.show();
    }

    public void exit() {
        Stage newStage = new Stage();
//
//newStage.setOnCloseRequest(event -> {
//    event.consume();
//});
        Button btn1 = new Button("Choose New File"), btn2 = new Button("Current File"), btn3 = new Button("Leave Without Saving");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                File file1 = null;

                if (event.getSource().equals(btn1)) {
                 FileChooser dchooser = new FileChooser();
                    FileChooser.ExtensionFilter extFilter =
                            new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
                    dchooser.getExtensionFilters().add(extFilter);
                 file1 = dchooser.showSaveDialog(null);

                } else {
                    file1 = file;
                }

SaveTheCurrent(file1);
                newStage.close();
                stage.close();
            }
        };
        btn1.setOnAction(event);
        btn2.setOnAction(event);
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(1);
        tilePane.getChildren().addAll(btn1, btn2, btn3);
        VBox vBox = new VBox(tilePane);
        btn3.setOnAction(e -> {
            newStage.close();
            stage.close();
        });

        Scene newScene = new Scene(vBox);
        newStage.setTitle("Close");
        newStage.setScene(newScene);
        newStage.showAndWait();


    }
public  void SaveTheCurrent(File file1){




    try {
        FileWriter fileWriter = new FileWriter(file1);
        for (String s : strings) {
            fileWriter.write(s);
            fileWriter.write(13);
            fileWriter.write(10);


        }
        fileWriter.flush();
        fileWriter.close();
    } catch (NullPointerException | IOException n) {
        System.out.println("nullfile 999");
    }

}
}
