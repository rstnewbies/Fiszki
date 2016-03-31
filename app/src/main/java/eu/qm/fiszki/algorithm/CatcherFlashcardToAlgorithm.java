package eu.qm.fiszki.algorithm;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 31.03.2016.
 */
public class CatcherFlashcardToAlgorithm {

    CategoryRepository categoryRepository;
    Drawer draw;
    FlashcardRepository flashcardRepository;

    public CatcherFlashcardToAlgorithm(Context context) {
        categoryRepository = new CategoryRepository(context);
        flashcardRepository = new FlashcardRepository(context);
        draw = new Drawer();
    }

    public Flashcard getFlashcardToAlgoritm(int priority){
        ArrayList<Flashcard> flashcardsPiorited = new ArrayList<Flashcard>();
        ArrayList<Flashcard> categoricalFlashcards = new ArrayList<Flashcard>();
        ArrayList<Category> categories = categoryRepository.getChosenCategory();
        if(categories.isEmpty()){
            return flashcardRepository.getRandomFlashcardByPririty(priority);
        }
        for (Category category:categories) {
            ArrayList<Flashcard> flashcards = flashcardRepository.getFlashcardsByCategoryID(category.getId());
            categoricalFlashcards.addAll(flashcards);
        }
        for (Flashcard flashcard:categoricalFlashcards) {
            if(priority==flashcard.getPriority()){
                flashcardsPiorited.add(flashcard);
            }
        }
        return flashcardsPiorited.get(draw.drawInteger(flashcardsPiorited.size()));
    }
}
