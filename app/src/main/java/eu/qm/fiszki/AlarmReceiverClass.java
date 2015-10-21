package eu.qm.fiszki;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiverClass extends BroadcastReceiver {

    public Context context;
    public DBAdapter myDB;
    public NotificationsClass notification;
    public PendingIntent pendingIntent;
    public String notificationMessage;
    public String notificationTitle;
/*
    public void bridge(Context ctext,DBAdapter database,NotificationsClass notifi,PendingIntent pIntent,
                       String notifiMessage,String notifiTitle){
        context = ctext;
        myDB = database;
        notification = notifi;
        pendingIntent = pIntent;
        notificationMessage = notifiMessage;
        notificationTitle = notifiTitle;

    }
*/
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void start(AlarmManager manager,Context context,int sec) {
        int interval = 8000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, this.pendingIntent);
        Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                1000 * sec, this.pendingIntent);
    }

    public void close(AlarmManager manager,PendingIntent pendingIntent,Context context){
        manager.cancel(pendingIntent);
        Toast.makeText(context, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

}