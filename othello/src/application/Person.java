package application;

import javafx.beans.property.SimpleStringProperty;

public abstract class Person {
    private final SimpleStringProperty name;
    private final SimpleStringProperty score;

    public Person(String nm, String sc) {
        this.name = new SimpleStringProperty(nm);
        this.score = new SimpleStringProperty(sc);
    }
    
    // we use simpleStringProperty to use the .get()
    public String getName() {
        return name.get();
    }

    public String getScore() {
        return score.get();
    }
    public void setScore(String sc) {
        score.set(sc);
    }
}