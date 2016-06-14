package eu.qm.fiszki.algorithm;

import java.util.ArrayList;

import eu.qm.fiszki.model.Flashcard;

/**
 * Created by mBoiler on 31.03.2016.
 */
public class PriorityCount {

    private ArrayList<Flashcard> flashcards;
    private int[] priority;

    public PriorityCount(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
        priority = new int[5];
    }

    public int[] priorityCount() {
        if (!flashcards.isEmpty()) {
            for (Flashcard flashcard : flashcards) {
                int flashcardPriority = flashcard.getPriority();
                switch (flashcardPriority) {
                    case 1:
                        priority[0]++;
                        break;
                    case 2:
                        priority[1]++;
                        break;
                    case 3:
                        priority[2]++;
                        break;
                    case 4:
                        priority[3]++;
                        break;
                    case 5:
                        priority[4]++;
                        break;
                }
            }
            return priority;
        }
        return null;
    }
}
