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

    private SharedPreferences sharedPreferences2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor2;

    public LocalSharedPreferences(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(notificationPosition, Context.MODE_PRIVATE);
        sharedPreferences2 = activity.getSharedPreferences(notificationStatus, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor2 = sharedPreferences2.edit();
    }

    public void setNotificationPosition(int volume) {
        editor.clear();
        editor.putInt(notificationPosition, volume);
        editor.commit();
    }

    public int getNotificationPosition(){
        return sharedPreferences.getInt(notificationPosition,0);
    }

    /*
     * status 1 - notyfication on
     *status 0 - notyfication off
     */
    public int getNotificationStatus(){
        return sharedPreferences2.getInt(notificationStatus,0);
    }

    public void setNotificationStatus(int volume){
        editor2.clear();
        editor2.putInt(notificationStatus, volume);
        editor2.commit();
    }
}
