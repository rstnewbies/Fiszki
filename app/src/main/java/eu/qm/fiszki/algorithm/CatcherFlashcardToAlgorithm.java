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

    public Flashcard getFlashcardToAlgoritmByPriority(int priority, ArrayList<Flashcard> flashcards){
        return flashcards.get(draw.drawInteger(flashcards.size()));
    }

    public ArrayList<Flashcard> getFlashcardsFromChosenCategoryToNotification(){
        ArrayList<Flashcard> flashcards = new ArrayList<Flashcard>();
        ArrayList<Category> categories = categoryRepository.getChosenCategory();
        for (Category category:categories) {
            flashcards.addAll(flashcardRepository.getFlashcardsByCategoryID(category.getId()));
        }
        return flashcards;
    }

    public ArrayList<Flashcard> getFlashcardsFromChosenCategory(ArrayList<Category> chosenCategory){
        ArrayList<Flashcard> flashcards = new ArrayList<Flashcard>();
        for (Category category:chosenCategory) {
            flashcards.addAll(flashcardRepository.getFlashcardsByCategoryID(category.getId()));
        }
        return flashcards;
    }
}
