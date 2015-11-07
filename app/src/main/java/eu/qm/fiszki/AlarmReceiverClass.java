package eu.qm.fiszki;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.Serializable;

import eu.qm.fiszki.ActivityContainer.CheckActivity;

public class AlarmReceiverClass extends BroadcastReceiver implements Serializable {

    @Override
    public void onReceive(Context context, Intent intent) {

        long[] vibrate = {0,200,100,200};
        Bitmap icon =  BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        PendingIntent pi = PendingIntent.getActivity(context, 100, new Intent(context,
                CheckActivity.class), 0);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setLargeIcon(icon);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setSound(sound);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_message)));
        mBuilder.setContentTitle(context.getString(R.string.notification_title));
        mBuilder.setContentText(context.getString(R.string.notification_message));
        mBuilder.setContentIntent(pi);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(vibrate);
        NotificationManager nm =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(100,mBuilder.build());

    }

    public void start(AlarmManager manager,Context context,PendingIntent pendingIntent, int sec) {
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 *60* sec, pendingIntent);
        Toast.makeText(context, context.getString(R.string.alarm_on), Toast.LENGTH_SHORT).show();
    }

    public void close(AlarmManager manager,Context context, PendingIntent pendingIntent){
        manager.cancel(pendingIntent);
        Toast.makeText(context, context.getString(R.string.alarm_off), Toast.LENGTH_SHORT).show();
    }

}