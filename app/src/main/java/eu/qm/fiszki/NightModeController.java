package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mBoiler on 04.04.2017.
 */

public class NightModeController {

    private static final String NIGHTMODE_STATUS = "nightmode_status";
    private SharedPreferences mNightModePreferences;
    private SharedPreferences.Editor mNightModeEditor;

    public NightModeController(Activity activity) {
        mNightModePreferences = activity.getSharedPreferences(NIGHTMODE_STATUS, Context.MODE_PRIVATE);
        mNightModeEditor = mNightModePreferences.edit();
    }

    public void on(){
        mNightModeEditor.clear();
        mNightModeEditor.putInt(NIGHTMODE_STATUS, 1);
        mNightModeEditor.commit();
    }

    public void off(){
        mNightModeEditor.clear();
        mNightModeEditor.putInt(NIGHTMODE_STATUS, 0);
        mNightModeEditor.commit();
    }

    public int getStatus(){
        return  mNightModePreferences.getInt(NIGHTMODE_STATUS, 0);
    }
}
