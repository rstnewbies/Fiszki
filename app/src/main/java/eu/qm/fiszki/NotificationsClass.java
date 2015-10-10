package eu.qm.fiszki;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Siusiacz on 2015-10-10.
 */
public class NotificationsClass {
    public void CreateNotification(Context window) {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent intent = PendingIntent.getActivity(window, 100, new Intent(window, CheckActivity.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(window);
        mBuilder.setSmallIcon(R.drawable.ic_add_black_18dp);
        mBuilder.setSound(sound);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        mBuilder.setContentIntent(intent);
        NotificationManager nm = (NotificationManager) window.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(100,mBuilder.build());

    }

}
