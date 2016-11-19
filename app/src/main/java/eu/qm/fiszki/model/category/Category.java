package eu.qm.fiszki.model.category;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Category implements Serializable {

    public static final String columnCategoryId = "id";
    public static final String columnCategoryLangOn = "langOn";
    public static final String columnCategoryChosen = "chosen";
    public static final String columnCategoryLangFrom = "langFrom";
    public static final String columnCategoryCategory = "category";
    public static final String columnCategoryEntryByUsers = "entryByUser";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;
    @DatabaseField
    private String category;
    @DatabaseField(defaultValue = "true")
    private boolean entryByUser;
    @DatabaseField(defaultValue = "false")
    private boolean chosen;
    @DatabaseField
    private String langOn;
    @DatabaseField
    private String langFrom;


    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryDB() {
        return category;
    }

    public String getCategory() {
        return category.replace("%sq%", "'");
    }

    public void setCategory(String category) {
        this.category = category.replace("'", "%sq%");
    }

    public boolean isEntryByUser() {
        return entryByUser;
    }

    public void setEntryByUser(boolean entryByUser) {
        this.entryByUser = entryByUser;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public String getLangOn() {
        return langOn;
    }

    public void setLangOn(String langOn) {
        this.langOn = langOn;
    }

    public String getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(String langFrom) {
        this.langFrom = langFrom;
    }
}
