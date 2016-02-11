package eu.qm.fiszki;

import java.util.ArrayList;
import java.util.Random;

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

    public Flashcard drawCardAlgorithm() {
        final int[] points = {25, 20, 15, 10, 5};
        int[] totalPoints = new int[5];
        int[] section = new int[5];
        int drawn = 0;

        Flashcard flashcard = null;

        for(int i=0; i<5; i++) {
            ArrayList<Flashcard> flashcardsList = flashcardManagement.getFlashcardsByPriority(i);
            int count = flashcardsList.size();
            totalPoints[i] = count * points[i];
            if(i <= 0) {
                section[i] = totalPoints[i];
            }else {
                section[i] = totalPoints[i] + section[i-1];
            }
        }
        Random rand = new Random();
        drawn = rand.nextInt(section[4]);
        drawn += 1;

        if(drawn <= section[0]) {
            flashcard = flashcardManagement.getRandomFlashcardByPririty(1);
        } else if(drawn <= section[1]) {
            flashcard = flashcardManagement.getRandomFlashcardByPririty(2);
        } else if(drawn <= section[2]) {
            flashcard = flashcardManagement.getRandomFlashcardByPririty(3);
        } else if(drawn <= section[3]) {
            flashcard = flashcardManagement.getRandomFlashcardByPririty(4);
        } else if(drawn <= section[4]+1) {
            flashcard = flashcardManagement.getRandomFlashcardByPririty(5);
        }

        return flashcard;
    }
}
