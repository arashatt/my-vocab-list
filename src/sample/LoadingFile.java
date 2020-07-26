package sample;

import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class LoadingFile {
    FileReader save  = null;
    TreeSet<String> strings = null;

    public FileReader getSave() {
        return save;
    }

    public void setSave(FileReader save) {
        this.save = save;
    }

    public TreeSet<String> getStrings() {
        return strings;
    }

    public void setStrings(TreeSet<String> strings) {
        this.strings = strings;
    }

    public TreeSet<String> load(File file) throws IOException {
        ListView<String> listOfWords = new ListView<>();
        try {


            save = new FileReader(file);
        } catch (IOException ex) {
            System.out.println("error fixed");
            File saved = new File("list.txt");
            //   saved.createNewFile();
            this.save = new FileReader(saved);
        }catch (NullPointerException nu){

        }

        Scanner sc = new Scanner(new BufferedReader(save));

        strings = new TreeSet<>();
        String temp;
        while (sc.hasNextLine()) {
            temp = sc.nextLine();
            if (ifAlfabet(temp))
                strings.add(temp);
        }
        return strings;
    }
    public TreeSet<String> addOneWord(String s ){
        System.out.println("one word invoked");
        if(ifAlfabet(s)) {
            System.out.println("checked and added");
            strings.add(s);
        }

        return strings;
    }
    public static boolean ifAlfabet(String s){
        char[]chars = s.toLowerCase().toCharArray();
        for(char c : chars){
            if(!(c >='a' && c <='z' || c ==' ' || c == '-')){
                return false;
            }
        }
        if(s.isEmpty())return false;
        if(s.equals(" "))return false;

        return true;
    }
}
