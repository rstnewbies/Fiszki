package eu.qm.fiszki;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;

/**
 * Created by mBoiler on 19.02.2016.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<Flashcard>> sortedFlashcards;
    private ArrayList<Flashcard> flashcards;
    private ArrayList<Category> categories;
    private Flashcard flashcard;
    private Activity activity;
    private LayoutInflater inflater;

    public MyExpandableListViewAdapter(ArrayList<ArrayList<Flashcard>> sortedFlashcards, ArrayList<Category> categories) {
        this.sortedFlashcards = sortedFlashcards;
        this.categories = categories;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {

        flashcards = sortedFlashcards.get(childPosition);
        convertView = inflater.inflate(R.layout.empty_group, null);
        convertView.setClickable(true);
        convertView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
        return convertView;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }
        ((CheckedTextView) convertView).setText(categories.get(groupPosition).getCategory());
        ((CheckedTextView) convertView).setChecked(isExpanded);
        convertView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
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

}
