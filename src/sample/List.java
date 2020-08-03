package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    TextField searchAndDiscard = new TextField();
    Tab tab = new Tab();
    ListOfDics listOfDics = new ListOfDics();
    ListView<String> listOfWords = new ListView<>();
    TreeSet<String> strings = new TreeSet<>();
    TextField input = new TextField();
    VBox root = new VBox();
    LoadingFile loader = new LoadingFile();
    File file;
    TextField search = new TextField();
    Button chooseFile = new Button("Choose File");
    Button save1 = new Button("Save the changes");
    HBox hb = new HBox(chooseFile, save1);
    String undo = "";
    boolean isOpen;

    public List(File file) {
        this.file = file;
        isOpen = true;
    }

    public Tab list() throws Exception, NullPointerException {

        hb.setSpacing(5);
        search.setPromptText("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (strings.contains(search.getText())) {
                    listOfWords.scrollTo(search.getText());
                    listOfWords.getSelectionModel().select(search.getText());
                    listOfWords.getFocusModel().focus(listOfWords.getItems().indexOf(search.getText()));
                    search.clear();

                } else {
                    for (String s : strings) {
                        if (search.getText().length() <= s.length())
                            if (s.substring(0, search.getText().length()).equals(search.getText())) {
                                listOfWords.scrollTo(s);
                                listOfWords.getSelectionModel().select(s);
                                listOfWords.getFocusModel().focus(listOfWords.getItems().indexOf(s));
                                search.clear();
                                break;
                            }
                    }
                }

                //   System.out.println("yeey"+      listOfWords.getFocusModel().isFocused(listOfWords.getItems().indexOf(search.getText()) ));                search.clear();
                listOfWords.requestFocus();


            }

        });
        save1.setOnAction(e -> {
            SaveTheCurrent(file);
        });
        chooseFile.setOnAction(e -> {

            try {
                ChooseFile choose = new ChooseFile();

                strings.addAll(loader.load(choose.getFile()));
                listOfWords.getItems().clear();
                listOfWords.getItems().addAll(strings);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        try {

            strings = loader.load(file);
            listOfWords.getItems().addAll(strings);
        } catch (NullPointerException e) {

        }

        input.setPromptText("Enter a word");
        input.setOnAction(e -> {
            listOfWords.getItems().clear();
            listOfWords.getItems().addAll(loader.addOneWord(input.getText()));
int index =listOfWords.getItems().indexOf(input.getText());
            listOfWords.scrollTo(index);
            listOfWords.getSelectionModel().select(index);
            listOfWords.getFocusModel().focus(index);
            listOfWords.requestFocus();
            input.clear();


        });
        searchAndDiscard.setPromptText("Search And Discard");
        searchAndDiscard.setOnAction(e->{
            openInBrowser(searchAndDiscard.getText());
            searchAndDiscard.clear();
        });
        listOfWords.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        openInBrowser( listOfWords.getSelectionModel().getSelectedItem());
                    }
                } else if (event.getButton().equals(MouseButton.SECONDARY)) {

                }
            }
        });

        listOfWords.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {


                    openInBrowser( listOfWords.getSelectionModel().getSelectedItem());

                }
                if (event.getCode().equals(KeyCode.DELETE)) {
                    try {


                        undo = listOfWords.getSelectionModel().getSelectedItem();
                        listOfWords.getFocusModel().focusNext();
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
                if(event.getCode().compareTo(KeyCode.A) >= 0 && event.getCode().compareTo(KeyCode.Z) <= 0  )
                if (event.getText().toLowerCase().charAt(0) >= 'a' || event.getText().toLowerCase().charAt(0) <= 'z') {
                    for (String s : strings) {

                        if (s.charAt(0) == event.getText().toLowerCase().charAt(0)) {
                            listOfWords.scrollTo(s);
                            listOfWords.getSelectionModel().select(s);
                            listOfWords.getFocusModel().focus(listOfWords.getItems().indexOf(s));
                            search.clear();
                            break;
                        }
                    }
                }
            }
        });

        root = new VBox(input, search, listOfWords, listOfDics.getComboBox(), searchAndDiscard,hb);
        root.setSpacing(15);
        //  root.setPadding((new Insets(10, 50, 50, 50)));
        tab.setContent(root);
        tab.setText(file.getName().substring(0, file.getName().length() - 4));
        tab.setOnCloseRequest(e -> {
            PreferenceClass.prefs.putInt(PreferenceClass.ID2,listOfDics.comboBox.getSelectionModel().getSelectedIndex());
            isOpen = false;
            try {
                if (strings.equals(loader.load(file))) {
                    //for getting insure that we don't lose any data I trigger save1 button which is not neccessary
                    //but releves me from the fact that users might use data
                    save1.fire();
                    tab.setDisable(true);

                } else {
                    exit();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return tab;
    }

    public void exit() {
        PreferenceClass.prefs.putInt(PreferenceClass.ID2,listOfDics.comboBox.getSelectionModel().getSelectedIndex());

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
                tab.setDisable(true);
            }
        };
        btn1.setOnAction(event);
        btn2.setOnAction(event);
        GridPane vBox1 = new GridPane();
        Label labaleName = new Label("Close: " + file.getName().substring(0, file.getName().length() - 4));
        vBox1.setAlignment(Pos.CENTER);
        vBox1.add(labaleName, 0, 0);
        vBox1.add(btn1, 0, 1);
        vBox1.add(btn2, 0, 2);
        vBox1.add(btn3, 0, 3);
        vBox1.setVgap(5);
        //full extending buttons
        labaleName.setPrefHeight(Integer.MAX_VALUE);
        labaleName.setPrefWidth(Integer.MAX_VALUE);
        labaleName.setFont(new Font(Integer.SIZE));
        btn1.setPrefHeight(Integer.MAX_VALUE);
        btn1.setPrefWidth(Integer.MAX_VALUE);
        btn2.setPrefHeight(Integer.MAX_VALUE);
        btn2.setPrefWidth(Integer.MAX_VALUE);
        btn3.setPrefHeight(Integer.MAX_VALUE);
        btn3.setPrefWidth(Integer.MAX_VALUE);
        // vBox1.getChildren().addAll(btn1, btn2, btn3);
        btn3.setOnAction(e -> {
            newStage.close();
            tab.setDisable(true);
        });

        vBox1.setPadding(new Insets(5, 5, 5, 5));
        Scene newScene = new Scene(vBox1, 277, 220);

        newStage.setTitle("Close: " + file.getName().substring(0, file.getName().length() - 4));
        newStage.setScene(newScene);
        newStage.showAndWait();


    }

    public void SaveTheCurrent(File file1) {


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

    public void openInBrowser(String word) {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI oURL = new URI(listOfDics.getComboBox().getSelectionModel().getSelectedItem().site +word);
            desktop.browse(oURL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
