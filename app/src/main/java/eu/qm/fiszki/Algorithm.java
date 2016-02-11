package eu.qm.fiszki;

import java.util.ArrayList;

import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardManagement;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class Algorithm {


    FlashcardManagement flashcardManagement;

    public Flashcard simple(int x){
        ArrayList<Flashcard> flashcardsList = flashcardManagement.getAllFlashcards();
        Flashcard flashcard = flashcardsList.get(x);
        return flashcard;
    }
}
