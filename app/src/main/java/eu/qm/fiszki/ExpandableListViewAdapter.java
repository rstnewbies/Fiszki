package eu.qm.fiszki;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mBoiler on 19.02.2016.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<Object> childtems1, childtems2;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child1, child2;

    public ExpandableListViewAdapter(ArrayList<String> parents, ArrayList<Object> childern1, ArrayList<Object> childern2) {
        this.parentItems = parents;
        this.childtems1 = childern1;
        this.childtems2 = childern2;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, View convertView, final ViewGroup parent) {

        child1 = (ArrayList<String>) childtems1.get(groupPosition);
        child2 = (ArrayList<String>) childtems2.get(groupPosition);
        TextView textWord = null;
        TextView textTranslation = null;
        if (child1.get(childPosition).equals("")) {
            convertView = inflater.inflate(R.layout.empty_group, null);
            convertView.setClickable(true);
        } else {
            convertView = inflater.inflate(R.layout.group, null);
            textTranslation = (TextView) convertView.findViewById(R.id.translation);
            textWord = (TextView) convertView.findViewById(R.id.word);
            textTranslation.setText(child2.get(childPosition));
            textWord.setText(child1.get(childPosition));
            convertView.setClickable(false);
        }
        convertView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
        return convertView;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }
        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
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
        return ((ArrayList<String>) childtems1.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
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

    public String getChildWord(int groupPosition, int childPosition){
        child1 = (ArrayList<String>) childtems1.get(groupPosition);
        return child1.get(childPosition);
    }

    public String getChildTranslate(int groupPosition, int childPosition){
        child2 = (ArrayList<String>) childtems2.get(groupPosition);
        return child2.get(childPosition);
    }

    public String getGroupName(int groupPosition){
        return parentItems.get(groupPosition);
    }
}
