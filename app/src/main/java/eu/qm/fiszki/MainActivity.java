package eu.qm.fiszki;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import eu.qm.fiszki.ActivityContainer.AddWordActivity;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;
import eu.qm.fiszki.DataBaseContainer.DBStatus;


public class MainActivity extends AppCompatActivity {
    TimerClass Timer = new TimerClass();

    DBAdapter myDb = new DBAdapter(this);
    DBStatus openDataBase = new DBStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDataBase.openDB(myDb);
        populateListView();
        Timer.start(this, 30000, getString(R.string.notification_message),
                getString(R.string.notification_title), myDb);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        populateListView();
    }

    public void addNewWord(View view) {

            Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivity(myIntent);

    }

    private void populateListView()
    {
        Cursor cursor = myDb.getAllRows();
        String[] fromFieldNames = new String[] {DBModel.KEY_WORD, DBModel.KEY_TRANSLATION};
        int[] toViewIDs = new int[] {R.id.word, R.id.translation};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.item_layout, cursor, fromFieldNames, toViewIDs, 0);
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(myCursorAdapter);
    }


}
