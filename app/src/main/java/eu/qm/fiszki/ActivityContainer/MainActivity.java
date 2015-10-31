package eu.qm.fiszki.ActivityContainer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import eu.qm.fiszki.AlarmReceiverClass;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBStatus;
import eu.qm.fiszki.R;


public class MainActivity extends AppCompatActivity {

    DBAdapter myDb = new DBAdapter(this);
    DBStatus openDataBase = new DBStatus();
    PendingIntent pendingIntent;
    AlarmReceiverClass alarm;
    AlarmManager manager;
    Intent alarmIntent;
    TextView backgroundLayoutText;
    ImageView emptyDBImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyDBImage = (ImageView) findViewById(R.id.emptyDBImage);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        openDataBase.openDB(myDb);
        checkListComponents();

        alarmIntent = new Intent(MainActivity.this, AlarmReceiverClass.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm = new AlarmReceiverClass();
        alarm.start(manager, this, pendingIntent, 10);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        checkListComponents();
        if(myDb.getAllRows().getCount()>0) {
            emptyDBImage.setAlpha(0);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void addNewWord(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
        startActivity(myIntent);
    }

    public void populateListView()
    {
        ListView listViewItems = (ListView) findViewById(R.id.listView);
        ItemAdapter flashCardList = new ItemAdapter(this, myDb.getAllRows(), myDb, this);
        listViewItems.setAdapter(flashCardList);
    }

    public void checkListComponents()
    {
        if(myDb.getAllRows().getCount()>0){
            populateListView();
        }
        else {
            Toast.makeText(getApplicationContext(), "Lista jest pusta", Toast.LENGTH_LONG).show();
        }
    }

    public void notificationOff(View view) {
        alarm.close(manager,this,pendingIntent);
    }

}
