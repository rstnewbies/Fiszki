package eu.qm.fiszki.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormatSymbols;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;




public class MainActivity extends AppCompatActivity {

    public DBAdapter myDb = new DBAdapter(this);
    DBStatus openDataBase = new DBStatus();
    ImageView emptyDBImage;
    TextView emptyDBText;
    Alert alert = new Alert();
    Context context;
    ListView listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewItems = (ListView) findViewById(R.id.listView);

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
                            CharSequence[] items = {"10", "20", "50", DecimalFormatSymbols.getInstance().getInfinity()};
                            new AlertDialog.Builder(MainActivity.this)
                                    .setSingleChoiceItems(items, 0, null)
                                    .setTitle(R.string.repeat_number)
                                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialog.dismiss();
                                            int selected = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                            if (selected == 0 && myDb.getAllRows().getCount() > 0){ //10

                                            }
                                            if (selected == 1 && myDb.getAllRows().getCount() > 0){ //20

                                            }
                                            if (selected == 2 && myDb.getAllRows().getCount() > 0){ //50

                                            }
                                            if (selected == 3 && myDb.getAllRows().getCount() > 0){ // infinity
                                                Intent myIntent = new Intent(MainActivity.this, LearningModeActivity.class);
                                                startActivity(myIntent);
                                            }
                                            else if (myDb.getAllRows().getCount() == 0){
                                                alert.buildAlert(getString(R.string.alert_title_fail), getString(R.string.learningmode_emptybase), getString(R.string.alert_nameButton_OK), MainActivity.this);
                                            }
                                        }
                                    })
                                    .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                        return true;
                    }
                });
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) findViewById(R.id.emptyDBText);
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
    public void onResume() {
        super.onResume();
        checkListComponents();
        if (myDb.getAllRows().getCount() > 0) {
            emptyDBImage.setAlpha(0);
            emptyDBText.setAlpha(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void populateListView() {
        ItemAdapter flashCardList = new ItemAdapter(this, myDb.getAllRows(), myDb, this);
        listViewItems.setAdapter(flashCardList);
    }

    public void checkListComponents() {
        if (myDb.getAllRows().getCount() > 0) {
            populateListView();
        }
    }
}
