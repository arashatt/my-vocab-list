package sample;

public class Dictionary {
    String site;
    String name;

    public Dictionary(String site, String name) {
        this.site = site;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
