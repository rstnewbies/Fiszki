package eu.qm.fiszki;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class Algorithm {

    FlashcardRepository flashcardRepository;

    public Algorithm(Context context) {
        flashcardRepository = new FlashcardRepository(context);
    }

    public Flashcard drawCardAlgorithm() {
        final int[] points = {25, 20, 15, 10, 5};
        int[] totalPoints = new int[5];
        int[] section = new int[5];
        int drawn = 0;

        Flashcard flashcard = null;

        for(int i=0; i<5; i++) {
            ArrayList<Flashcard> flashcardsList = flashcardRepository.getFlashcardsByPriority(i+1);
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
            flashcard = flashcardRepository.getRandomFlashcardByPririty(1);
        } else if(drawn <= section[1]) {
            flashcard = flashcardRepository.getRandomFlashcardByPririty(2);
        } else if(drawn <= section[2]) {
            flashcard = flashcardRepository.getRandomFlashcardByPririty(3);
        } else if(drawn <= section[3]) {
            flashcard = flashcardRepository.getRandomFlashcardByPririty(4);
        } else if(drawn <= section[4]+1) {
            flashcard = flashcardRepository.getRandomFlashcardByPririty(5);
        }

        return flashcard;
    }
}
