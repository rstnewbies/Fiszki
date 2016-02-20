package eu.qm.fiszki.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Flashcard implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String word;
    @DatabaseField
    private String translation;
    @DatabaseField
    private int priority;
    @DatabaseField
    private int categoryID;

    public Flashcard() {
    }

    public Flashcard (int id, String word, String translation, int priority, int categoryID) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.priority = priority;
        this.categoryID = categoryID;
    }

    public Flashcard (String word, String translation, int priority, int categoryID) {
        this.word = word;
        this.translation = translation;
        this.priority = priority;
        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int getCategory() {
        return categoryID;
    }

    public void setCategory(int categoryID) {
        this.categoryID = categoryID;
    }
}



