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
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import eu.qm.fiszki.ActivityContainer.MainActivity;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;
import eu.qm.fiszki.DataBaseContainer.DBStatus;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public Switch notificationSwitch;
    public PendingIntent pendingIntent;
    public AlarmReceiverClass alarm;
    public AlarmManager manager;
    public Intent alarmIntent;
    public Context context;
    public DBAdapter myDb = new DBAdapter(this);
    public DBStatus openDataBase = new DBStatus();
    public int time = 15 ;
    public Spinner spinnerFrequency;
    String spinnerPosition = "notification_time";

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
        spinnerFrequency = (Spinner) findViewById(R.id.spinner);
        spinnerFrequency.setOnItemSelectedListener(this);
        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        sync(myDb.intRowValue(DBModel.SETTINGS_NAME,spinnerPosition),
                myDb.intRowValue(DBModel.SETTINGS_NAME, "notification"));
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myDb.updateRow("notification", 1);
                    alarm.start(manager, context, pendingIntent, time);
                    spinnerFrequency.setClickable(false);
                } else {
                    myDb.updateRow("notification", 0);
                    alarm.close(manager, context, pendingIntent);
                    spinnerFrequency.setClickable(true);
                }
            }
        });

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

        switch (position){
            case 0:
                time = 1;
                myDb.updateRow(spinnerPosition, 0);
                break;
            case 1:
                time = 5;
                myDb.updateRow(spinnerPosition, 1);
                break;
            case 2:
                time = 15;
                myDb.updateRow(spinnerPosition, 2);
                break;
            case 3:
                time = 30;
                myDb.updateRow(spinnerPosition, 3);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sync(int poss,int notifiStatus){
        switch (poss){
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
        }
        switch (notifiStatus){
            case 0:
                notificationSwitch.setChecked(false);
                spinnerFrequency.setClickable(true);
                break;
            case 1:
                notificationSwitch.setChecked(true);
                spinnerFrequency.setClickable(false);
                break;
        }
    }
}
