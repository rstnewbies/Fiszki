package eu.qm.fiszki.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Category implements Serializable {

    public static final String columnCategoryId =  "id";
    public static final String columnCategoryCategory =  "category";
    public static final String columnCategoryEntryByUsers =  "entryByUser";
    public static final String columnCategoryChoose =  "choose";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
    private int id;
    @DatabaseField
    private String category;
    @DatabaseField
    private boolean entryByUser;
    @DatabaseField
    private boolean choose;

    public Category() {
    }

    public Category(int id, String category, boolean entryByUser) {
        this.id = id;
        this.category = category;
        this.entryByUser = entryByUser;
    }
    public Category(String category, boolean entryByUser) {
        this.category = category;
        this.entryByUser = entryByUser;
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


}
