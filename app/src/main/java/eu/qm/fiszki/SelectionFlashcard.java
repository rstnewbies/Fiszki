package eu.qm.fiszki;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;
import eu.qm.fiszki.toolbar.ToolbarSelected;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class SelectionFlashcard {

    ExpandableListView expandableListView;
    BackgroundSetter backgroundSetter;
    ToolbarMainActivity toolbarMainActivity;
    FloatingActionButton fab;
    Activity activity;
    ToolbarSelected toolbarSelected;

    public SelectionFlashcard(ExpandableListView expandableListView, Activity activity) {

        this.activity = activity;
        this.expandableListView = expandableListView;
        backgroundSetter = new BackgroundSetter(activity);
        toolbarMainActivity = new ToolbarMainActivity(activity);
        toolbarSelected = new ToolbarSelected(activity);
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
    }

    public void set() {
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    MainActivity.selectedFlashcard =
                            backgroundSetter.listPopulate.adapterExp.getFlashcard(groupPosition, childPosition);
                    MainActivity.selectedType = MainActivity.typeFlashcard;

                    if (MainActivity.selectedView == view) {
                        toolbarMainActivity.set();
                        fab.show();
                        MainActivity.selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
                        MainActivity.selectedView = null;

                    } else {
                        if (MainActivity.selectedView != null) {
                            MainActivity.selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
                        }
                        MainActivity.selectedView = view;
                        MainActivity.selectedView.setBackgroundColor(activity.getResources().getColor(R.color.pressed_color));
                        toolbarSelected.set(MainActivity.selectedCategory, MainActivity.selectedFlashcard);
                        fab.hide();
                    }

                }
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    MainActivity.selectedCategory =
                            backgroundSetter.listPopulate.adapterExp.getCategory(groupPosition);
                    MainActivity.selectedType = MainActivity.typeCategory;
                }
                return true;
            }
        });

    }
}
