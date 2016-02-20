package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryManagement;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardManagement;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class ListManagement {

    private Activity activity;
    private ListView listView;
    private ExpandableListView expandableListView;
    private FlashcardManagement flashcardManagement;
    private CategoryManagement categoryManagement;
    private MyExpandableListViewAdapter adapterExp;


    public ListManagement(ListView listView,ExpandableListView expandableListView,Activity activity) {
        this.activity = activity;
        this.listView = listView;
        this.expandableListView = expandableListView;
        flashcardManagement = new FlashcardManagement(activity.getBaseContext());
        categoryManagement = new CategoryManagement(activity.getBaseContext());
    }

    public void populate() {
        //Uncategory
        Category uncategory = categoryManagement.getCategoryByID(0);
        ArrayList<Flashcard> arrayList = flashcardManagement.getFlashcardsByCategory(uncategory);
        ItemAdapter adapter = new ItemAdapter(activity.getBaseContext(), R.layout.item_layout, arrayList);
        listView.setAdapter(adapter);

        //Categories
        ArrayList<Category> categories = categoryManagement.getUserCategory();
        ArrayList<ArrayList<Flashcard>> sortedFlashcard = new ArrayList<>();
        for (int x = 0; x<categories.size(); x++){
            Category category = categoryManagement.getCategoryByID(3);
            ArrayList<Flashcard> flashcards = flashcardManagement.getFlashcardsByCategory(category);
            sortedFlashcard.add(flashcards);
        }
        adapterExp = new MyExpandableListViewAdapter(sortedFlashcard,categories);
        adapterExp.setInflater((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE),activity);
        expandableListView.setDividerHeight(2);
        expandableListView.setAdapter(adapterExp);

    }



}
