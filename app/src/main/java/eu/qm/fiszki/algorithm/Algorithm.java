package eu.qm.fiszki.algorithm;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class Algorithm {

    FlashcardRepository flashcardRepository;
    CatcherFlashcardToAlgorithm catcherFlashcardToAlgorithm;
    PriorityCount priorityCount;
    MultiplierPoints multiplierPoints;
    int[] calculatedPriority;
    Drawer drawer;

    public Algorithm(Context context) {
        flashcardRepository = new FlashcardRepository(context);
        catcherFlashcardToAlgorithm = new CatcherFlashcardToAlgorithm(context);
    }

    public Flashcard drawCardAlgorithm(ArrayList<Flashcard> flashcardPool){
        Flashcard flashcard = null;
        drawer = new Drawer();
        priorityCount = new PriorityCount(flashcardPool);
        multiplierPoints = new MultiplierPoints(priorityCount.priorityCount());
        calculatedPriority = multiplierPoints.multipler();

        int draw = drawer.drawInteger(calculatedPriority[4]);

        if(0<=draw && draw<=calculatedPriority[0]){
            flashcard = catcherFlashcardToAlgorithm.getFlashcardToAlgoritmByPriority(1, flashcardPool);
        }
        if(calculatedPriority[0]<=draw && draw<=calculatedPriority[1]){
            flashcard = catcherFlashcardToAlgorithm.getFlashcardToAlgoritmByPriority(2, flashcardPool);
        }
        if(calculatedPriority[1]<=draw && draw<=calculatedPriority[2]){
            flashcard = catcherFlashcardToAlgorithm.getFlashcardToAlgoritmByPriority(3, flashcardPool);
        }
        if(calculatedPriority[2]<=draw && draw<=calculatedPriority[3]){
            flashcard = catcherFlashcardToAlgorithm.getFlashcardToAlgoritmByPriority(4, flashcardPool);
        }
        if(calculatedPriority[3]<=draw && draw<=calculatedPriority[4]){
            flashcard = catcherFlashcardToAlgorithm.getFlashcardToAlgoritmByPriority(5, flashcardPool);
        }
        return flashcard;
    }


}
