package eu.qm.fiszki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    /*
    EditText slowo, tlumaczenie;
    DBAdapter myDb;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        slowo = (EditText) findViewById(R.id.addWord);
        tlumaczenie = (EditText) findViewById(R.id.addTranslation);
        openDB();
        populateListView();
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    private void openDB()
    {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB()
    {
        myDb = new DBAdapter(this);
        myDb.close();
    }

    public void onClick_Add(View v)
    {
        if(!TextUtils.isEmpty(slowo.getText()) || !TextUtils.isEmpty(tlumaczenie.getText()))
        {
            myDb.insertRow(slowo.getText().toString(), tlumaczenie.getText().toString());
        }
        slowo.setText(null);
        tlumaczenie.setText(null);
        populateListView();
    }

    private void populateListView()
    {
        Cursor cursor = myDb.getAllRows();
        String[] fromFieldNames = new String[] {DBAdapter.KEY_ROWID,DBAdapter.KEY_WORD, DBAdapter.KEY_TRANSLATION};
        int[] toViewIDs = new int[] {R.id.number, R.id.word, R.id.translation};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor, fromFieldNames, toViewIDs, 0);
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(myCursorAdapter);
    }
     */
}
