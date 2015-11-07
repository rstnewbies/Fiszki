package eu.qm.fiszki;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import eu.qm.fiszki.ActivityContainer.MainActivity;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;
import eu.qm.fiszki.DataBaseContainer.DBStatus;

public class SettingsActivity extends AppCompatActivity {
    public Switch notificationSwitch;
    public PendingIntent pendingIntent;
    public AlarmReceiverClass alarm;
    public AlarmManager manager;
    public Intent alarmIntent;
    public Context context;
    public DBAdapter myDb = new DBAdapter(this);
    public DBStatus openDataBase = new DBStatus();
    public int time;
    public TextView sbt;
    SeekBar sb;
    int progress;

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
        sb = (SeekBar) findViewById(R.id.seekBar);
        sbt = (TextView) findViewById(R.id.SeekBarText);


        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        if (myDb.intRowValue(DBModel.SETTINGS_NAME, "notification") == 1) {
            notificationSwitch.setChecked(true);
        }
        if (myDb.intRowValue(DBModel.SETTINGS_NAME, "notification") == 0) {
            notificationSwitch.setChecked(false);
        }
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myDb.updateRow("notification", 1);
                    alarm.start(manager, context, pendingIntent, time);
                    sb.setEnabled(false);
                } else {
                    myDb.updateRow("notification", 0);
                    alarm.close(manager, context, pendingIntent);
                    sb.setEnabled(true);
                }
            }
        });

        progress = myDb.intRowValue(DBModel.SETTINGS_NAME, "notification_time");
        sb.setProgress(progress);
        SeekBarTextSegregation(progress);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBarTextSegregation(progress);
                Toast.makeText(getApplicationContext(),R.string.notification_frequency_set, Toast.LENGTH_SHORT).show();
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

    public void SeekBarTextSegregation(int progres){
        switch (progres) {
            case 0:
                sbt.setText(getString(R.string.notification_frequency_text) + " 1 " + getString(R.string.notification_frequency_min));
                time = 1;
                myDb.updateRow("notification_time", 0);
                break;
            case 1:
                sbt.setText(getString(R.string.notification_frequency_text) + " 5 " + getString(R.string.notification_frequency_mins));
                time = 5;
                myDb.updateRow("notification_time", 1);
                break;
            case 2:
                sbt.setText(getString(R.string.notification_frequency_text) + " 15 " + getString(R.string.notification_frequency_mins));
                time = 15;
                myDb.updateRow("notification_time", 2);
                break;
            case 3:
                sbt.setText(getString(R.string.notification_frequency_text) + " 30 " + getString(R.string.notification_frequency_mins));
                time = 30;
                myDb.updateRow("notification_time", 3);
                break;

            }
        }
}
