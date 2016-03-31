package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.qm.fiszki.activity.SettingsActivity;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class ListPopulate {

    public static final String emptyFlashcard = "IT_IS_EMPTY_CATEGORY";
    public static final String lastCategoryName = "IT_IS_LAST_CATEGORY";
    private final ImageView emptyDBImage;
    private final TextView emptyDBText;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final AlarmReceiver alarm;
    public MyExpandableListViewAdapter adapterExp;
    Category lastCategory;
    ArrayList<Flashcard> unCategoryFlashcard;
    ArrayList<Category> categories;
    ArrayList<ArrayList<Flashcard>> sortedFlashcard;
    private Activity activity;
    private ExpandableListView expandableListView;
    private FlashcardRepository flashcardRepository;
    private CategoryRepository categoryRepository;


    public ListPopulate(Activity activity) {
        this.activity = activity;
        expandableListView = (ExpandableListView) activity.findViewById(R.id.list);
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        categoryRepository = new CategoryRepository(activity.getBaseContext());
        emptyDBImage = (ImageView) activity.findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) activity.findViewById(R.id.emptyDBText);
        sharedPreferences = activity.getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        alarm = new AlarmReceiver();
    }

    public void populate() {

        if (flashcardRepository.countFlashcards() > 0 || categoryRepository.countCategory() > 2) {
            emptyDBImage.setVisibility(View.INVISIBLE);
            emptyDBText.setVisibility(View.INVISIBLE);
            expandableListView.setVisibility(View.VISIBLE);
            //Uncategory
            lastCategory = new Category(lastCategoryName, false, false);
            unCategoryFlashcard = flashcardRepository.getFlashcardsByCategoryID(1);
            //Categories
            categories = categoryRepository.getUserCategory();
            sortedFlashcard = new ArrayList<>();
            for (int x = 0; x < categories.size(); x++) {
                ArrayList<Flashcard> flashcards = flashcardRepository.getFlashcardsByCategoryID(categories.get(x).getId());
                if (flashcards.size() <= 0) {
                    flashcards = new ArrayList<Flashcard>();
                    Flashcard emptyflashcard = new Flashcard(emptyFlashcard, null, 0, 0);
                    flashcards.add(emptyflashcard);
                }
                sortedFlashcard.add(flashcards);
            }

            categories.add(lastCategory);
            sortedFlashcard.add(unCategoryFlashcard);

            adapterExp = new MyExpandableListViewAdapter(sortedFlashcard, categories);
            adapterExp.setInflater((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE), activity);
            expandableListView.setDividerHeight(1);
            expandableListView.setAdapter(adapterExp);
            expandableListView.expandGroup(adapterExp.lastGroup);
        }else
        {
            expandableListView.setVisibility(View.INVISIBLE);
            emptyDBImage.setVisibility(View.VISIBLE);
            emptyDBText.setVisibility(View.VISIBLE);
            editor.clear();
            editor.putInt(SettingsActivity.notificationPosition, 0);
            editor.commit();
            alarm.close(activity.getBaseContext());
        }
    }
}
