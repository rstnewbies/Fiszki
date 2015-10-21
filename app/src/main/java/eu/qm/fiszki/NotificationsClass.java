package eu.qm.fiszki;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import eu.qm.fiszki.ActivityContainer.CheckActivity;

public class NotificationsClass {
    public void CreateNotification(Context window,String notificationMessage,
                                   String notificationTitle) {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        PendingIntent intent = PendingIntent.getActivity(window, 100, new Intent(window,
                                                         CheckActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(window);
        mBuilder.setSmallIcon(R.drawable.ic_add_black_18dp);
        mBuilder.setSound(sound);
        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationMessage);
        mBuilder.setContentIntent(intent);

        NotificationManager nm =
                (NotificationManager) window.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(100,mBuilder.build());
    }
    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(100);
    }
}
