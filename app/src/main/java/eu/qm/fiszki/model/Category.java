package eu.qm.fiszki.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Category implements Serializable {

    public static final String columnCategoryId =  "id";
    public static final String columnCategoryCategory =  "category";
    public static final String columnCategoryEntryByUsers =  "entryByUser";
    public static final String columnCategoryChosen =  "chosen";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
    private int id;
    @DatabaseField
    private String category;
    @DatabaseField
    private boolean entryByUser;
    @DatabaseField(defaultValue="false")
    private boolean chosen;

    public Category() {
    }

    public Category(int id, String category, boolean entryByUser,boolean chosen) {
        this.id = id;
        this.category = category;
        this.entryByUser = entryByUser;
        this.chosen = chosen;
    }
    public Category(String category, boolean entryByUser, boolean chosen) {
        this.category = category;
        this.entryByUser = entryByUser;
        this.chosen = chosen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
