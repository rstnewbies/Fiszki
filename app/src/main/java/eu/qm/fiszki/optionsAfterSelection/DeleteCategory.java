package eu.qm.fiszki.optionsAfterSelection;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.ListPopulate;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class DeleteCategory {

    Category deletedCategory;
    CategoryRepository categoryRepository;
    ArrayList<Flashcard> deletedFlashcards;
    FlashcardRepository flashcardRepository;
    ToolbarMainActivity toolbarMainActivity;

    public DeleteCategory(Category selectedCategory, Activity activity, final ListPopulate listPopulate) {

        categoryRepository = new CategoryRepository(activity.getBaseContext());
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        toolbarMainActivity = new ToolbarMainActivity(activity);
        deletedCategory = selectedCategory;
        categoryRepository.deleteCategory(deletedCategory);
        deletedFlashcards = flashcardRepository.getFlashcardsByCategoryID(deletedCategory.getId());
        flashcardRepository.deleteFlashcards(deletedFlashcards);
        listPopulate.populate(null,null);
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(R.id.fab),
                        activity.getString(R.string.snackbar_return_category_message),
                        Snackbar.LENGTH_LONG)
                .setAction(activity.getString(R.string.snackbar_return_word_button),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                categoryRepository.addCategory(deletedCategory);
                                flashcardRepository.addFlashcards(deletedFlashcards);
                                listPopulate.populate(null,null);
                                toolbarMainActivity.set();
                            }
                        });
        snackbar.show();
    }
}
