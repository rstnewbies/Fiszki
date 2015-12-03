package eu.qm.fiszki.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;


public class MainActivity extends AppCompatActivity {

    public DBAdapter myDb;
    public DBStatus openDataBase;
    public Alert alert;
    public ItemAdapter flashCardList;
    public ImageView emptyDBImage;
    public TextView emptyDBText;
    public Context context;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert = new Alert();
        openDataBase = new DBStatus();
        myDb = new DBAdapter(this);
        context = this;
        listView = (ListView) findViewById(R.id.listView);
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) findViewById(R.id.emptyDBText);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        openDataBase.openDB(myDb);
        listViewPopulate();
        listViewSelect();
        toolbarSettings();
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
    public void onResume() {
        super.onResume();
        listViewPopulate();
        if (myDb.getAllRows().getCount() > 0) {
            emptyDBImage.setAlpha(0);
            emptyDBText.setAlpha(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void listViewPopulate() {
        if (myDb.getAllRows().getCount() > 0) {
            flashCardList = new ItemAdapter(this, myDb.getAllRows(), myDb, this);
            listView.setAdapter(flashCardList);
        }
    }

    public void listViewSelect() {
        final LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                footer.setVisibility(View.VISIBLE);

            }
        });
    }

    public void toolbarSettings() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_settings);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.settings) {
                            Intent goSettings = new Intent(MainActivity.this, SettingsActivity.class);
                            startActivity(goSettings);
                        } else if (id == R.id.learningMode) {
                            if (myDb.getAllRows().getCount() > 0) {
                                Intent goLearningMode = new Intent(MainActivity.this, LearningModeActivity.class);
                                startActivity(goLearningMode);
                            } else {
                                alert.buildAlert(getString(R.string.alert_title_fail), getString(R.string.learningmode_emptybase), getString(R.string.alert_nameButton_OK), MainActivity.this);
                            }
                        }
                        return true;
                    }
                });

    }
}