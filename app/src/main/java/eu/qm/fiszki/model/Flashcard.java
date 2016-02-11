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
    @DatabaseField(foreign = true)
    private Category category;

    public Flashcard() {
    }

    public Flashcard (int id, String word, String translation, int priority) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.priority = priority;
    }

    public Flashcard (String word, String translation, int priority) {
        this.word = word;
        this.translation = translation;
        this.priority = priority;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}



