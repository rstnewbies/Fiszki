package eu.qm.fiszki.activity.myWords.flashcards;

import java.util.ArrayList;

import eu.qm.fiszki.model.flashcard.Flashcard;

/**
 * Created by mBoiler on 18.11.2016.
 */
public class SelectedFlashcardsSingleton {
    private static SelectedFlashcardsSingleton ourInstance = new SelectedFlashcardsSingleton();

    public static SelectedFlashcardsSingleton getInstance() {
        return ourInstance;
    }

    public static final int STATUS_ON = 1;
    public static final int STATUS_OFF = 0;


    private static ArrayList<Flashcard> mFlashcards = new ArrayList<>();
    private static int status;

    private SelectedFlashcardsSingleton() {
    }

    public static ArrayList<Flashcard> getFlashcards() {
        return mFlashcards;
    }

    public static void addFlashcards(Flashcard flashcard){
        getFlashcards().add(flashcard);
    }

    public static void clearFlashcards(){
        getFlashcards().clear();
        setStatuOff();
    }

    public static boolean findFlashcard(Flashcard flashcard){
        for (Flashcard card:mFlashcards) {
            if(card.equals(flashcard)){
                return true;
            }
        }
        return false;
    }

    public static void removeFlashcard(Flashcard flashcard){
        getFlashcards().remove(flashcard);
    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        SelectedFlashcardsSingleton.status = status;
    }

    public static void setStatuOn(){
        setStatus(STATUS_ON);
    }

    public static void setStatuOff(){
        setStatus(STATUS_OFF);
    }

    public static boolean isFlashcard(Flashcard flashcard) {
        for (Flashcard card : mFlashcards) {
            if (card.getId() == flashcard.getId()) {
                return true;
            }
        }
        return false;
    }
}
