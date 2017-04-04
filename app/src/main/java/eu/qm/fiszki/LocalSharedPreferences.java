package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class LocalSharedPreferences {

    private static final String NOTIFICATION_POSITION = "notification_time";
    private static final String NOTIFICATION_STATUS = "notification_status";
    private SharedPreferences mNotificationPositionPreferences;
    private SharedPreferences.Editor mNotificationPositionEditor;
    private SharedPreferences mNotificationStatusPreferences;
    private SharedPreferences.Editor mNotificationStatusEditor;

    public LocalSharedPreferences(Activity activity) {
        mNotificationStatusPreferences = activity.getSharedPreferences(NOTIFICATION_POSITION, Context.MODE_PRIVATE);
        mNotificationPositionPreferences = activity.getSharedPreferences(NOTIFICATION_STATUS, Context.MODE_PRIVATE);
        mNotificationPositionEditor = mNotificationStatusPreferences.edit();
        mNotificationStatusEditor = mNotificationPositionPreferences.edit();
    }

    public int getNotificationPosition() {
        return mNotificationStatusPreferences.getInt(NOTIFICATION_POSITION, 0);
    }

    public void setNotificationPosition(int volume) {
        mNotificationPositionEditor.clear();
        mNotificationPositionEditor.putInt(NOTIFICATION_POSITION, volume);
        mNotificationPositionEditor.commit();
    }

    /*
     *status 1 - notyfication on
     *status 0 - notyfication off
     */
    public int getNotificationStatus() {
        return mNotificationPositionPreferences.getInt(NOTIFICATION_STATUS, 0);
    }

    public void setNotificationStatus(int volume) {
        mNotificationStatusEditor.clear();
        mNotificationStatusEditor.putInt(NOTIFICATION_STATUS, volume);
        mNotificationStatusEditor.commit();
    }
}
