package eu.qm.fiszki.activity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;


public class MainActivity extends AppCompatActivity {

    static public SettingsActivity settings;
    static public DBAdapter myDb;
    static public DBStatus openDataBase;
    static public Alert alert;
    static public ItemAdapter flashCardList;
    static public ImageView emptyDBImage;
    static public TextView emptyDBText;
    static public Context context;
    static public ListView listView;
    static public FloatingActionButton fab;
    static public LinearLayout footer;
    static public View[] selectedItem;
    public boolean[] clickedItem;
    static public int earlierPosition, selectPosition;
    static public ItemAdapter editedItem;
    static public Dialog dialog;
    static public EditText editOriginal;
    static public EditText editTranslate;
    static public Button dialogButton;
    public int rowId;
    public AlarmReceiver alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = new SettingsActivity();
        alarm = new AlarmReceiver();
        alert = new Alert();
        openDataBase = new DBStatus();
        myDb = new DBAdapter(this);
        context = this;
        listView = (ListView) findViewById(R.id.listView);
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) findViewById(R.id.emptyDBText);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        settings.context=this;
        settings.alarmIntent = new Intent(this, AlarmReceiver.class);
        settings.pendingIntent = PendingIntent.getBroadcast(this, 0, settings.alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        settings.manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        openDataBase.openDB(myDb);
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
        listViewSelect();
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
        sync();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_edit);
        dialog.setTitle(R.string.dialog_edit_item);
        editOriginal = (EditText) dialog.findViewById(R.id.editOrginal);
        editTranslate = (EditText) dialog.findViewById(R.id.editTranslate);
        dialogButton = (Button) dialog.findViewById(R.id.editButton);

        footer = (LinearLayout) findViewById(R.id.footer);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                rowId = (int) id;
                selectPosition = position;
                editedItem = (ItemAdapter) parent.getAdapter();
                editOriginal.setText(editedItem.getCursor().getString(1));
                editTranslate.setText(editedItem.getCursor().getString(2));

                if (!clickedItem[position] && earlierPosition == -1) {
                    selectedItem[position] = view;
                    clickedItem[position] = true;
                    selectedItem[position].setBackgroundColor(getResources().getColor(R.color.pressed_color));
                    selectedItem[position].setSelected(true);
                    fab.setVisibility(View.INVISIBLE);
                    footer.setVisibility(View.VISIBLE);
                    earlierPosition = position;
                } else if (!clickedItem[position]) {
                    selectedItem[earlierPosition].setBackgroundColor(getResources().getColor(R.color.default_color));
                    clickedItem[earlierPosition] = false;
                    selectedItem[earlierPosition].setSelected(false);
                    selectedItem[position] = view;
                    clickedItem[position] = true;
                    selectedItem[position].setBackgroundColor(getResources().getColor(R.color.pressed_color));
                    selectedItem[position].setSelected(true);
                    fab.setVisibility(View.INVISIBLE);
                    footer.setVisibility(View.VISIBLE);
                    earlierPosition = position;
                } else {
                    selectedItem[earlierPosition].setBackgroundColor(getResources().getColor(R.color.default_color));
                    fab.setVisibility(View.VISIBLE);
                    footer.setVisibility(View.INVISIBLE);
                    clickedItem[position] = false;
                    selectedItem[position].setSelected(false);
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

    public void listViewEdit(View v) {

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.updateAdapter(rowId,editOriginal.getText().toString(),
                        editTranslate.getText().toString());
                selectedItem[earlierPosition].setBackgroundColor(getResources().getColor(R.color.default_color));
                fab.setVisibility(View.VISIBLE);
                footer.setVisibility(View.INVISIBLE);
                clickedItem[selectPosition] = false;
                selectedItem[selectPosition].setSelected(false);
                listViewPopulate();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void listViewDelete(View view) {
        myDb.deleteRecord(rowId);
        if (myDb.getAllRows().getCount() > 0) {
            listViewPopulate();
        } else {
            finish();
            startActivity(getIntent());
            alarm.close(settings.manager, settings.context, settings.pendingIntent);
            myDb.updateRow(settings.notificationStatus, 0);
            myDb.updateRow(settings.spinnerPosition,0);
        }
    }

    public void sync() {
        earlierPosition = -1;
        int x = myDb.getAllRows().getCount();
        selectedItem = new View[x + 1];
        clickedItem = new boolean[x + 1];
        Arrays.fill(clickedItem, Boolean.FALSE);
    }
}