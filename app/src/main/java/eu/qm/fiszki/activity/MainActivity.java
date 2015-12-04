package eu.qm.fiszki.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

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
    public FloatingActionButton fab;
    public LinearLayout footer;
    public View[] selectedItem;
    public boolean[] clickedItem;
    public int earlierPosition;
    public Cursor editedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earlierPosition = -1;
        alert = new Alert();
        openDataBase = new DBStatus();
        myDb = new DBAdapter(this);
        context = this;
        listView = (ListView) findViewById(R.id.listView);
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) findViewById(R.id.emptyDBText);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        openDataBase.openDB(myDb);

        sync();
        listViewPopulate();
        listViewSelect();
        toolbarSettings();
        fab = (FloatingActionButton) findViewById(R.id.fab);
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
        footer = (LinearLayout) findViewById(R.id.footer);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                editedItem = null;
                editedItem = (Cursor) parent.getAdapter().getItem(position);

                if (!clickedItem[position] && earlierPosition == -1) {
                    selectedItem[position] = view;
                    clickedItem[position] = true;
                    selectedItem[position].setBackgroundColor(getResources().getColor(R.color.pressed_color));
                    fab.setVisibility(View.INVISIBLE);
                    footer.setVisibility(View.VISIBLE);
                    earlierPosition = position;
                } else if (!clickedItem[position]) {
                    selectedItem[earlierPosition].setBackgroundColor(getResources().getColor(R.color.default_color));
                    clickedItem[earlierPosition] = false;
                    selectedItem[position] = view;
                    clickedItem[position] = true;
                    selectedItem[position].setBackgroundColor(getResources().getColor(R.color.pressed_color));
                    fab.setVisibility(View.INVISIBLE);
                    footer.setVisibility(View.VISIBLE);
                    earlierPosition = position;
                } else {
                    selectedItem[earlierPosition].setBackgroundColor(getResources().getColor(R.color.default_color));
                    view.setSelected(false);
                    fab.setVisibility(View.VISIBLE);
                    footer.setVisibility(View.INVISIBLE);
                    clickedItem[position] = false;
                }


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


    public void listViewEdit(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_edit);
        dialog.setTitle("Title...");
        EditText editOrginal = (EditText) dialog.findViewById(R.id.editOrginal);
        editOrginal.setText(editedItem.getString(1));
        EditText editTranslate = (EditText) dialog.findViewById(R.id.editTranslate);
        editTranslate.setText(editedItem.getString(2));
        Button dialogButton = (Button) dialog.findViewById(R.id.editButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void listViewDelete(View view) {
    }

    public void sync() {
        int x = myDb.getAllRows().getCount();
        selectedItem = new View[x + 1];
        clickedItem = new boolean[x + 1];
        Arrays.fill(clickedItem, Boolean.FALSE);

    }
}