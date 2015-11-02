package eu.qm.fiszki;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import eu.qm.fiszki.ActivityContainer.AddWordActivity;
import eu.qm.fiszki.ActivityContainer.ItemAdapter;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBStatus;
import eu.qm.fiszki.R;
import eu.qm.fiszki.SettingsActivity;
import android.content.Intent;

public class Main2Activity extends AppCompatActivity {

    public DBAdapter myDb = new DBAdapter(this);
    DBStatus openDataBase = new DBStatus();
    ImageView emptyDBImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        openDataBase.openDB(myDb);
        checkListComponents();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main2Activity.this, AddWordActivity.class);
                startActivity(myIntent);
            }
        });
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
