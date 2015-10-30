package eu.qm.fiszki;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import eu.qm.fiszki.ActivityContainer.MainActivity;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBStatus;

public class SettingsActivity extends AppCompatActivity {
    public Switch notificationSwitch;
    public PendingIntent pendingIntent;
    public AlarmReceiverClass alarm;
    public AlarmManager manager;
    public Intent alarmIntent;
    int time = 30;
    public Context context;
    public DBAdapter myDb = new DBAdapter(this);
    public DBStatus openDataBase = new DBStatus();

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
        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (myDb.getAllRows().getCount() > 0) {
                        alarm.start(manager, context, pendingIntent, time);
                    } else {
                        Toast.makeText(getApplicationContext(), "Baza jest pusta", Toast.LENGTH_LONG).show();
                    }
                } else {
                    alarm.close(manager, context, pendingIntent);
                }
            }
        });
        if (notificationSwitch.isChecked()) {

        } else {

        }
    }

}
