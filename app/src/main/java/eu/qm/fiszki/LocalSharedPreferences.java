package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class LocalSharedPreferences {

    private static String notificationPosition = "notification_time";
    private static String notificationStatus = "notification_status";

    private SharedPreferences notificationPositionPreferences;
    private SharedPreferences notificationStatusPreferences;
    private SharedPreferences.Editor notificationPositionEditor;
    private SharedPreferences.Editor notificationStatusEditor;

    public LocalSharedPreferences(Activity activity) {
        notificationStatusPreferences = activity.getSharedPreferences(notificationPosition, Context.MODE_PRIVATE);
        notificationPositionPreferences = activity.getSharedPreferences(notificationStatus, Context.MODE_PRIVATE);
        notificationPositionEditor = notificationStatusPreferences.edit();
        notificationStatusEditor = notificationPositionPreferences.edit();
    }

    public void setNotificationPosition(int volume) {
        notificationPositionEditor.clear();
        notificationPositionEditor.putInt(notificationPosition, volume);
        notificationPositionEditor.commit();
    }

    public int getNotificationPosition(){
        return notificationStatusPreferences.getInt(notificationPosition,0);
    }

    /*
     *status 1 - notyfication on
     *status 0 - notyfication off
     */
    public int getNotificationStatus(){
        return notificationPositionPreferences.getInt(notificationStatus,0);
    }

    public void setNotificationStatus(int volume){
        notificationStatusEditor.clear();
        notificationStatusEditor.putInt(notificationStatus, volume);
        notificationStatusEditor.commit();
    }
}
