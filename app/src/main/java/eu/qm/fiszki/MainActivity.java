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


public class MainActivity extends AppCompatActivity {
    TimerClass timer = new TimerClass();

    DBAdapter myDb = new DBAdapter(this);
    OpenDataBaseClass openDataBase = new OpenDataBaseClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer.start(this,900000,getString(R.string.notification_message),getString(R.string.notification_title));
        openDataBase.openDB(myDb);
        populateListView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        populateListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, CheckActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
