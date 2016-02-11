package eu.qm.fiszki;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.model.Flashcard;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class ListViewManagement {

    ListView listView;
    View pastView;
    Flashcard selectedFlashcard;
    MainActivity mainActivity;


    public ListViewManagement(ListView listView) {
        this.listView = listView;
        mainActivity = new MainActivity();
    }

    public void populate(Context context, ArrayList<Flashcard> arrayList) {
        ItemAdapter adapter = new ItemAdapter(context, R.layout.item_layout, arrayList);
        listView.setAdapter(adapter);
    }

    public void select(final Context context) {


    }


}
