package eu.qm.fiszki.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.R;
import eu.qm.fiszki.SettingsActivity;


public class MainActivity extends AppCompatActivity {

    public DBAdapter myDb = new DBAdapter(this);
    DBStatus openDataBase = new DBStatus();
    TextView backgroundLayoutText;
    ImageView emptyDBImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        openDataBase.openDB(myDb);
        checkListComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       int id = item.getItemId();
        if (id == R.id.settings) {
            Intent goSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(goSettings);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        checkListComponents();
        if(myDb.getAllRows().getCount()>0) {
            emptyDBImage.setAlpha(0);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void addNewWord(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
        startActivity(myIntent);
    }

    public void populateListView()
    {
        ListView listViewItems = (ListView) findViewById(R.id.listView);
        ItemAdapter flashCardList = new ItemAdapter(this, myDb.getAllRows(), myDb, this);
        listViewItems.setAdapter(flashCardList);
    }

    public void checkListComponents()
    {
        if(myDb.getAllRows().getCount()>0){
            populateListView();
        }
    }
}
