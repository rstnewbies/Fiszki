package eu.qm.fiszki.ActivityContainer;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;

import eu.qm.fiszki.R;

public class ItemAdapter extends CursorAdapter
{
    DBAdapter newAdapter;
    MainActivity newActivity;

    public ItemAdapter(Context context ,Cursor c, DBAdapter myDB, MainActivity mainActivity)
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
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView word = (TextView) view.findViewById(R.id.word);
        TextView translation = (TextView) view.findViewById(R.id.translation);
        Button deleteRowButton = (Button) view.findViewById(R.id.deleteRowButton);

        final String idString = cursor.getString(cursor.getColumnIndexOrThrow(DBModel.KEY_ROWID));
        String wordString = cursor.getString(cursor.getColumnIndexOrThrow(DBModel.KEY_WORD));
        String translationString = cursor.getString(cursor.getColumnIndexOrThrow(DBModel.KEY_TRANSLATION));

        deleteRowButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                newAdapter.deleteRow(idString);
                refreshList();
            }
        });
        word.setText(wordString);
        translation.setText(translationString);
    }

    public void refreshList()
    {
        newActivity.populateListView();
    }
}
