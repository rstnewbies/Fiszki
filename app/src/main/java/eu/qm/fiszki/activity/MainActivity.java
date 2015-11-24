package eu.qm.fiszki.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;

import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.R;

import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    public DBAdapter myDb = new DBAdapter(this);
    DBStatus openDataBase = new DBStatus();
    ImageView emptyDBImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_settings);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent goSettings = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(goSettings);
                        return true;
                    }
                });
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        openDataBase.openDB(myDb);
        checkListComponents();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(myIntent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       int id = item.getItemId();
        if (id == R.id.settings) {
            Intent goSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(goSettings);
        } else if(id == R.id.learningMode){
            Intent goLearningMode = new Intent(MainActivity.this, LearningModeActivity.class);
            startActivity(goLearningMode);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkListComponents();
        if (myDb.getAllRows().getCount() > 0) {
            emptyDBImage.setAlpha(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void populateListView() {
        ListView listViewItems = (ListView) findViewById(R.id.listView);
        ItemAdapter flashCardList = new ItemAdapter(this, myDb.getAllRows(), myDb, this);
        listViewItems.setAdapter(flashCardList);
    }

    public void checkListComponents() {
        if (myDb.getAllRows().getCount() > 0) {
            populateListView();
        }
    }
}
