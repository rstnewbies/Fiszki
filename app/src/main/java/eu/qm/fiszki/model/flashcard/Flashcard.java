package eu.qm.fiszki.model.flashcard;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class Flashcard implements Serializable {

    public static final String columnFlashcardId = "id";
    public static final String columnFlashcardWord = "word";
    public static final String columnFlashcardTranslation = "translation";
    public static final String columnFlashcardPriority = "priority";
    public static final String columnFlashcardCategoryID = "categoryID";

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;
    @DatabaseField
    private String word;
    @DatabaseField
    private String translation;
    @DatabaseField
    private int priority;
    @DatabaseField
    private int categoryID;
    @DatabaseField
    private int staticFail;
    @DatabaseField
    private int staticPass;


    public Flashcard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordDB(){
        return word;
    }

    public String getWord() {
        return word.replace("%sq%","'");
    }

    public void setWord(String word) {
        this.word = word.replace("'","%sq%");
    }

    public String getTranslationDB(){
        return translation;
    }

    public String getTranslation() {
        return translation.replace("%sq%","'");
    }

    public void setTranslation(String translation) {
        this.translation = translation.replace("'","%sq%");
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getStaticFail() {
        return staticFail;
    }

    public void upStaticFail() {
        this.staticFail++;
    }

    public int getStaticPass() {
        return staticPass;
    }

    public void upStaticPass() {
        this.staticPass++;
    }

    public void upPriority(){
        if(this.priority<=5) {
            this.priority++;
        }else{
            this.priority = 5;
        }
    }

    public void downPriority(){
        if(this.priority>0) {
            this.priority--;
        }else{
            this.priority = 0;
        }
    }

    public void resetStatictic(){
        this.staticFail = 0;
        this.staticPass = 0;
    }
}



