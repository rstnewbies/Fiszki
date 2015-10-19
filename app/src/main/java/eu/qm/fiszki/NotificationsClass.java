package eu.qm.fiszki;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
public class NotificationsClass {
    public void CreateNotification(Context window,String notificationMessage,
                                   String notificationTitle) {
        long[] vibrate = {0,200,100,200};
        PendingIntent intent = PendingIntent.getActivity(window, 100, new Intent(window,
                                                         CheckActivity.class), 0);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(window);
        mBuilder.setSmallIcon(R.drawable.ic_add_black_18dp);
        mBuilder.setSound(sound);
        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationMessage);
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(vibrate);
        NotificationManager nm =
                (NotificationManager) window.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(100,mBuilder.build());
    }
}
