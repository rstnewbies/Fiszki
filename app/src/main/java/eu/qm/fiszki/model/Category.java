package eu.qm.fiszki.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Category implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String category;

    public Category() {
    }

    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }
    public Category(String category) {
        this.category = category;
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
