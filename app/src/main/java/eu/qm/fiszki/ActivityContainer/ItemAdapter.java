package eu.qm.fiszki.ActivityContainer;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;

import eu.qm.fiszki.Main2Activity;
import eu.qm.fiszki.R;

public class ItemAdapter extends CursorAdapter
{
    DBAdapter newAdapter;
    Main2Activity newActivity;

    public ItemAdapter(Context context ,Cursor c, DBAdapter myDB, Main2Activity mainActivity)
    {
        super(context, c);
        this.newAdapter = myDB;
        this.newActivity = mainActivity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView word = (TextView) view.findViewById(R.id.word);
        TextView translation = (TextView) view.findViewById(R.id.translation);

        String wordString = cursor.getString(cursor.getColumnIndexOrThrow(DBModel.KEY_WORD));
        String translationString = cursor.getString(cursor.getColumnIndexOrThrow(DBModel.KEY_TRANSLATION));

        word.setText(wordString);
        translation.setText(translationString);
    }

    public void refreshList()
    {
        newActivity.populateListView();
    }
}
