package eu.qm.fiszki;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;

/**
 * Created by mBoiler on 19.02.2016.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    public ArrayList<Category> categories;
    public int lastGroup;
    private ArrayList<ArrayList<Flashcard>> sortedFlashcards;
    private ArrayList<Flashcard> flashcards;
    private Activity activity;
    private LayoutInflater inflater;
    private Flashcard selectedFlashcard;
    private Category selectedCategory;

    public MyExpandableListViewAdapter(ArrayList<ArrayList<Flashcard>> sortedFlashcards,
                                       ArrayList<Category> categories, Flashcard selectedFlashcard,
                                       Category selectedCategory) {
        this.sortedFlashcards = sortedFlashcards;
        this.categories = categories;
        this.lastGroup = categories.size() - 1;
        this.selectedFlashcard = selectedFlashcard;
        this.selectedCategory = selectedCategory;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {

        flashcards = (ArrayList<Flashcard>) sortedFlashcards.get(groupPosition);
        TextView textWord = null;
        TextView textTranslation = null;
        if (flashcards.get(0).getWord().equals(ListPopulate.emptyFlashcard)) {
            convertView = inflater.inflate(R.layout.empty_group, null);
            convertView.setClickable(true);
        } else {
            if (groupPosition == lastGroup) {
                convertView = inflater.inflate(R.layout.item_layout, null);
                textTranslation = (TextView) convertView.findViewById(R.id.translation);
                textWord = (TextView) convertView.findViewById(R.id.word);
            } else {
                convertView = inflater.inflate(R.layout.group, null);
                textTranslation = (TextView) convertView.findViewById(R.id.translation);
                textWord = (TextView) convertView.findViewById(R.id.word);
            }
            textTranslation.setText(flashcards.get(childPosition).getTranslation());
            textWord.setText(flashcards.get(childPosition).getWord());
            convertView.setClickable(false);

        }
        if (selectedFlashcard != null && selectedFlashcard.getId() == (flashcards.get(childPosition).getId())) {
            convertView.setBackgroundColor(activity.getResources().getColor(R.color.pressed_color));
        } else {
            convertView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
        }
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (groupPosition == lastGroup) {
            convertView = inflater.inflate(R.layout.layout_last_category, null);
        } else {
            convertView = inflater.inflate(R.layout.row, null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.group_icon);
            CheckedTextView categoryName = (CheckedTextView) convertView.findViewById(R.id.categoryName);
            if (isExpanded) {
                icon.setImageResource(R.drawable.ic_arrow_drop_up_black_36dp);
            } else {
                icon.setImageResource(R.drawable.ic_arrow_drop_down_black_36dp);
            }
            categoryName.setText(categories.get(groupPosition).getCategory());
            categoryName.setChecked(isExpanded);
            categoryName.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
            if (selectedCategory != null && selectedCategory.getId() == (categories.get(groupPosition).getId())) {
                convertView.setBackgroundColor(activity.getResources().getColor(R.color.pressed_color));
            } else {
                convertView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
            }
        }
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sortedFlashcards.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Flashcard getFlashcard(int groupPosition, int childPosition) {
        ArrayList<Flashcard> flashcards = (ArrayList<Flashcard>) sortedFlashcards.get(groupPosition);
        return flashcards.get(childPosition);
    }

    public Category getCategory(int groupPosition) {
        return categories.get(groupPosition);
    }

    public int getPositionGroupByCategory(Category category){
        int x = 0;
        for (Category cat:categories) {
            if(category.getId()==cat.getId()){
                return x;
            }else{
                x++;
            }
        }
        return 0;
    }

}
