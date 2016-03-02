package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class ListPopulate {

    public static final String emptyFlashcard = "ITISEMPTYCATEGORY";
    private Activity activity;
    private ListView listView;
    private ExpandableListView expandableListView;
    private FlashcardRepository flashcardRepository;
    private CategoryRepository categoryRepository;
    public MyExpandableListViewAdapter adapterExp;


    public ListPopulate(ExpandableListView expandableListView, Activity activity) {
        this.activity = activity;
        this.expandableListView = expandableListView;
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        categoryRepository = new CategoryRepository(activity.getBaseContext());
    }

    public void populate() {
        //Categories
        ArrayList<Category> categories = categoryRepository.getUserCategory();
        ArrayList<ArrayList<Flashcard>> sortedFlashcard = new ArrayList<>();
        for (int x = 0; x<categories.size(); x++){
            ArrayList<Flashcard> flashcards = flashcardRepository.getFlashcardsByCategoryID(categories.get(x).getId());
            if(flashcards.size()<=0){
                flashcards = new ArrayList<Flashcard>();
                Flashcard emptyflashcard = new Flashcard(emptyFlashcard,null,0,0);
                flashcards.add(emptyflashcard);
            }
            sortedFlashcard.add(flashcards);
        }
        adapterExp = new MyExpandableListViewAdapter(sortedFlashcard,categories);
        adapterExp.setInflater((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE),activity);
        expandableListView.setDividerHeight(2);
        expandableListView.setAdapter(adapterExp);

        expandableListView.expandGroup(MyExpandableListViewAdapter.lastGroup);
    }



}
