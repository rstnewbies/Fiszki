package eu.qm.fiszki;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.activity.SettingsActivity;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 04.03.2016.
 */
public class DeleteCategory {

    private ImageView emptyDBImage;
    private TextView emptyDBText;
    private FlashcardRepository flashcardRepository;
    private CategoryRepository categoryRepository;
    private Category deletedCategory;
    private ArrayList<Flashcard> deletedFlashcards;
    private MainActivity mainActivity;
    private ListPopulate listPopulate;


    public DeleteCategory(Category selectedCategory, Activity activity, FloatingActionButton fab,
                          ExpandableListView expandableListView) {

        mainActivity = new MainActivity();
        categoryRepository = new CategoryRepository(activity.getBaseContext());
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        emptyDBImage = (ImageView) activity.findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) activity.findViewById(R.id.emptyDBText);
        expandableListView = (ExpandableListView) activity.findViewById(R.id.categoryList);
        listPopulate= new ListPopulate(expandableListView, activity);

        deletedCategory = selectedCategory;
        deletedFlashcards = flashcardRepository.getFlashcardsByPriority(deletedCategory.getId());
        categoryRepository.deleteCategory(deletedCategory);
        flashcardRepository.deleteFlashcardByCategory(deletedCategory.getId());


        if (flashcardRepository.countFlashcards() > 0) {
            listPopulate.populate();
            Snackbar snackbar = Snackbar
                    .make(activity.findViewById(android.R.id.content),
                            activity.getString(R.string.snackbar_return_category_message),
                            Snackbar.LENGTH_LONG)
                    .setAction(activity.getString(R.string.snackbar_return_word_button),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    categoryRepository.addCategory(deletedCategory);
                                    flashcardRepository.addFlashcard(deletedFlashcards);
                                    listPopulate.populate();
                                }
                            });
            snackbar.show();
            fab.show();
        } else {
            emptyDBImage.setVisibility(View.VISIBLE);
            emptyDBText.setVisibility(View.VISIBLE);
            fab.show();
            mainActivity.editor.clear();
            mainActivity.editor.putInt(SettingsActivity.notificationPosition, 0);
            mainActivity.editor.commit();
            mainActivity.alarm.close(activity.getBaseContext());
            Snackbar snackbar = Snackbar
                    .make(activity.findViewById(android.R.id.content),
                            activity.getString(R.string.snackbar_return_word_message),
                            Snackbar.LENGTH_LONG)
                    .setAction(activity.getString(R.string.snackbar_return_word_button),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    categoryRepository.addCategory(deletedCategory);
                                    flashcardRepository.addFlashcard(deletedFlashcards);
                                    emptyDBImage.setVisibility(View.INVISIBLE);
                                    emptyDBText.setVisibility(View.INVISIBLE);
                                    listPopulate.populate();
                                }
                            });
            snackbar.show();
            fab.show();
        }

    }
}
