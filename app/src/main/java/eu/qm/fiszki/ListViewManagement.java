package eu.qm.fiszki;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

    public void select() {

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {



                return true;
            }
        });
    }
}
