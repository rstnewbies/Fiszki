package eu.qm.fiszki;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TimerClass Timer = new TimerClass();
    TextView small;
    DBAdapter myDb = new DBAdapter(this);
    OpenDataBaseClass openDataBase = new OpenDataBaseClass();

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
        if(myDb.rowCount()>0) {
            small = (TextView) findViewById(R.id.textView4);
            small.setTextColor(getResources().getColor(android.R.color.transparent));
        }
    }

    public void dodajNoweSlowko(View view) {

            Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivity(myIntent);

    }

    private void populateListView()
    {
        Cursor cursor = myDb.getAllRows();
        String[] fromFieldNames = new String[] {DBAdapter.KEY_WORD, DBAdapter.KEY_TRANSLATION};
        int[] toViewIDs = new int[] {R.id.word, R.id.translation};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.item_layout, cursor, fromFieldNames, toViewIDs, 0);
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(myCursorAdapter);
    }


}
