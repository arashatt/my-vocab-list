package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
public class ListOfDics {



    Dictionary learners = new Dictionary("http:/www.learnersdictionary.com/definition/", "learner\'s");
    Dictionary longman = new Dictionary("https://www.ldoceonline.com/dictionary/", "longman");

    Dictionary collins = new Dictionary("https://www.collinsdictionary.com/us/dictionary/english/", "collins");
    Dictionary cambridge = new Dictionary("https://dictionary.cambridge.org/us/dictionary/english/","cambridge" );
    Dictionary freeCollocation= new Dictionary("https://www.freecollocation.com/search?word=", "free collocation");
    Dictionary powerThreasure = new Dictionary("https://www.powerthesaurus.org/","power Thesaurus" );

    ObservableList<Dictionary> listOfDic = FXCollections.observableArrayList();
    ComboBox<Dictionary> comboBox;

    public ListOfDics(){
        listOfDic.addAll( longman,freeCollocation,powerThreasure,cambridge,collins,powerThreasure,learners);
        comboBox = new ComboBox();
        comboBox.setItems(listOfDic);
        comboBox.getSelectionModel().select(longman);
    }


    public ObservableList<Dictionary> getListOfDic() {
        return listOfDic;
    }

    public ComboBox<Dictionary> getComboBox() {
        return comboBox;
    }
}
