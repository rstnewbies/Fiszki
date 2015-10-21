package eu.qm.fiszki.ActivityContainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBStatus;
import eu.qm.fiszki.R;
import eu.qm.fiszki.TimerClass;


public class MainActivity extends AppCompatActivity {

    TimerClass timer = new TimerClass();
    DBAdapter myDb = new DBAdapter(this);
    DBStatus openDataBase = new DBStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer.start(this, 60000, getString(R.string.notification_message), getString(R.string.notification_title));
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
    public void onDestroy()
    {
        super.onDestroy();
        openDataBase.closeDB(myDb);
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
}
