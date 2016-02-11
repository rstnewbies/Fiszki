package eu.qm.fiszki;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;

import eu.qm.fiszki.model.Flashcard;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class ListViewManagement {

    ListView listView;

    public ListViewManagement(ListView listView){
        this.listView = listView;
    }

    public void populate(Context context,ArrayList<Flashcard> arrayList){
        ItemAdapter adapter = new ItemAdapter(context,R.layout.item_layout,arrayList);
        listView.setAdapter(adapter);
    }



}
