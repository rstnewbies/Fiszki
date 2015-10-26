package eu.qm.fiszki;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity  {

    public  DBAdapter myDb;
    OpenDataBaseClass openDataBase = new OpenDataBaseClass();
    NotificationsClass notifications = new NotificationsClass();
    PendingIntent pendingIntent;
    AlarmReceiverClass alarm;
    AlarmManager manager;
    Intent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DBAdapter(this);
        openDataBase.openDB(myDb);
        populateListView();


        alarmIntent = new Intent(MainActivity.this, AlarmReceiverClass.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("db",myDb);
        alarmIntent.putExtra("bundle", bundle);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm = new AlarmReceiverClass();
        alarm.start(manager,this,pendingIntent,10);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateListView();
    }

    public void addNewWord(View view) {

            Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
            startActivity(myIntent);
    }

    private void populateListView()
    {
        Cursor cursor = myDb.getAllRows();
        String[] fromFieldNames = new String[] {DBAdapter.KEY_WORD, DBAdapter.KEY_TRANSLATION};
        int[] toViewIDs = new int[] {R.id.word, R.id.translation};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.item_layout, cursor, fromFieldNames, toViewIDs, 0);
        ListView myList = (ListView) findViewById(R.id.listView);
        myList.setAdapter(myCursorAdapter);
    }

    public void notificationOff(View view) {
        alarm.close(manager,this,pendingIntent);
    }


}
