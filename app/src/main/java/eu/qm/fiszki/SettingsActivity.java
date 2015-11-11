package eu.qm.fiszki;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import eu.qm.fiszki.ActivityContainer.MainActivity;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;
import eu.qm.fiszki.DataBaseContainer.DBStatus;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public PendingIntent pendingIntent;
    public AlarmReceiverClass alarm;
    public AlarmManager manager;
    public Intent alarmIntent;
    public Context context;
    public DBAdapter myDb = new DBAdapter(this);
    public DBStatus openDataBase = new DBStatus();
    public int time = 15;
    public Spinner spinnerFrequency;
    public AlertClass alert;
    public String spinnerPosition = "notification_time";
    public String notificationStatus = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        openDataBase.openDB(myDb);
        context = this;
        alarmIntent = new Intent(this, AlarmReceiverClass.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm = new AlarmReceiverClass();
        alert = new AlertClass();
        spinnerFrequency = (Spinner) findViewById(R.id.spinner);
        spinnerFrequency.setOnItemSelectedListener(this);
        sync(myDb.intRowValue(DBModel.SETTINGS_NAME, spinnerPosition));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent goHome = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(goHome);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                    if (myDb.intRowValue(DBModel.SETTINGS_NAME, notificationStatus) == 1 ||
                            myDb.intRowValue(DBModel.SETTINGS_NAME, spinnerPosition) != 0) {
                        alarm.close(manager, context, pendingIntent);
                        myDb.updateRow(notificationStatus, 0);
                        myDb.updateRow(spinnerPosition, 0);
                        time=0;}
                break;
            case 1:
                if (myDb.getAllRows().getCount() > 0) {
                    if (myDb.intRowValue(DBModel.SETTINGS_NAME, notificationStatus) == 0 ||
                            myDb.intRowValue(DBModel.SETTINGS_NAME, spinnerPosition) != 1) {
                        alarm.close(manager, context, pendingIntent);
                        time = 1;
                        myDb.updateRow(spinnerPosition, 1);
                        alarm.start(manager, context, pendingIntent, time);
                        myDb.updateRow(notificationStatus, 1);
                    }
                } else {
                    alert.buildAlert(
                            context.getString(R.string.notification_change_title),
                            context.getString(R.string.notification_change_message),
                            context.getString(R.string.action_OK),
                            SettingsActivity.this);
                    spinnerFrequency.setSelection(0);
                }
                break;
            case 2:
                if (myDb.getAllRows().getCount() > 0) {
                    if (myDb.intRowValue(DBModel.SETTINGS_NAME, notificationStatus) == 0 ||
                            myDb.intRowValue(DBModel.SETTINGS_NAME, spinnerPosition) != 2) {
                        alarm.close(manager, context, pendingIntent);
                        time = 5;
                        myDb.updateRow(spinnerPosition, 2);
                        alarm.start(manager, context, pendingIntent, time);
                        myDb.updateRow(notificationStatus, 1);
                    }
                } else {
                    alert.buildAlert(
                            context.getString(R.string.notification_change_title),
                            context.getString(R.string.notification_change_message),
                            context.getString(R.string.action_OK),
                            SettingsActivity.this);
                    spinnerFrequency.setSelection(0);
                }
                break;
            case 3:
                if (myDb.getAllRows().getCount() > 0) {
                    if (myDb.intRowValue(DBModel.SETTINGS_NAME, notificationStatus) == 0 ||
                            myDb.intRowValue(DBModel.SETTINGS_NAME, spinnerPosition) != 3) {
                        alarm.close(manager, context, pendingIntent);
                        time = 15;
                        myDb.updateRow(spinnerPosition, 3);
                        alarm.start(manager, context, pendingIntent, time);
                        myDb.updateRow(notificationStatus, 1);
                    }
                } else {
                    alert.buildAlert(
                            context.getString(R.string.notification_change_title),
                            context.getString(R.string.notification_change_message),
                            context.getString(R.string.action_OK),
                            SettingsActivity.this);
                    spinnerFrequency.setSelection(0);
                }
                break;
            case 4:
                if (myDb.getAllRows().getCount() > 0) {
                    if (myDb.intRowValue(DBModel.SETTINGS_NAME, notificationStatus) == 0
                            || myDb.intRowValue(DBModel.SETTINGS_NAME, spinnerPosition) != 4) {
                        alarm.close(manager, context, pendingIntent);
                        time = 30;
                        myDb.updateRow(spinnerPosition, 4);
                        alarm.start(manager, context, pendingIntent, time);
                        myDb.updateRow(notificationStatus, 1);
                    }
                } else {
                    alert.buildAlert(
                            context.getString(R.string.notification_change_title),
                            context.getString(R.string.notification_change_message),
                            context.getString(R.string.action_OK),
                            SettingsActivity.this);
                    spinnerFrequency.setSelection(0);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void sync(int poss) {
        switch (poss) {
            case 0:
                spinnerFrequency.setSelection(0);
                break;
            case 1:
                spinnerFrequency.setSelection(1);
                break;
            case 2:
                spinnerFrequency.setSelection(2);
                break;
            case 3:
                spinnerFrequency.setSelection(3);
                break;
            case 4:
                spinnerFrequency.setSelection(4);
                break;
        }
    }
}
