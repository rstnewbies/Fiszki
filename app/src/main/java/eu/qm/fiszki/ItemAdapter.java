package eu.qm.fiszki;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Flashcard;

/**
 * Created by mBoiler on 30.01.2016.
 */
public class ItemAdapter extends ArrayAdapter<Flashcard> {


    public ItemAdapter(Context context, int resource, ArrayList<Flashcard> fiszka) {
        super(context, resource, fiszka);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        Flashcard p = getItem(position);

        TextView tt1 = (TextView) convertView.findViewById(R.id.word);
        TextView tt2 = (TextView) convertView.findViewById(R.id.translation);

        tt1.setText(p.getWord());
        tt2.setText(p.getTranslation());

        return convertView;
    }


}