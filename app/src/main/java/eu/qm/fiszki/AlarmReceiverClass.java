package eu.qm.fiszki;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

public class AlarmReceiverClass extends BroadcastReceiver{
    DBAdapter newMyDb;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra("bundle");
        newMyDb = (DBAdapter) bundle.getSerializable("db");

        if(newMyDb.rowCount()>0){

            Toast.makeText(context, "Wypierdol tosta", Toast.LENGTH_SHORT).show();
        }



    }

    public void start(AlarmManager manager,Context context,PendingIntent pendingIntent, int sec) {
        int interval = 8000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval,pendingIntent);
        Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();

        manager.setRepeating( AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                1000 * sec, pendingIntent);
    }

    public void close(AlarmManager manager,Context context, PendingIntent pendingIntent){
        manager.cancel(pendingIntent);
        Toast.makeText(context, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

}