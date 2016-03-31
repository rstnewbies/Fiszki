package eu.qm.fiszki.algorithm;

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
    PriorityCount priorityCount;
    MultiplierPoints multiplierPoints;
    int[] calculatedPriority;
    Drawer drawer;

    public Algorithm(Context context) {
        flashcardRepository = new FlashcardRepository(context);
    }

    public Flashcard drawCardAlgorithm(ArrayList<Flashcard> flashcardsList) {
        Flashcard flashcard = null;
        drawer = new Drawer();
        priorityCount = new PriorityCount(flashcardsList);
        multiplierPoints = new MultiplierPoints(priorityCount.priorityCount());
        calculatedPriority = multiplierPoints.multipler();

        int draw = drawer.drawInteger(calculatedPriority[4]);

        if(0<=draw && draw<=calculatedPriority[0]){
            flashcard = flashcardRepository.getRandomFlashcardByPririty(1);
        }
        if(calculatedPriority[0]<=draw && draw<=calculatedPriority[1]){
            flashcard = flashcardRepository.getRandomFlashcardByPririty(2);
        }
        if(calculatedPriority[1]<=draw && draw<=calculatedPriority[2]){
            flashcard = flashcardRepository.getRandomFlashcardByPririty(3);
        }
        if(calculatedPriority[2]<=draw && draw<=calculatedPriority[3]){
            flashcard = flashcardRepository.getRandomFlashcardByPririty(4);
        }
        if(calculatedPriority[3]<=draw && draw<=calculatedPriority[4]){
            flashcard = flashcardRepository.getRandomFlashcardByPririty(5);
        }
        return flashcard;
    }
}
