package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.LocalSharedPreferences;
import eu.qm.fiszki.R;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class NightMode extends SwitchDrawerItem {

    private LocalSharedPreferences mLocalSharedPreferences;

    public NightMode(final Activity activity) {
        mLocalSharedPreferences = new LocalSharedPreferences(activity);

        this.withName(R.string.drawer_nightmode);
        this.withIcon(R.drawable.ic_weather_night);
        this.withCheckable(false);
        this.withSelectable(false);

        //Sync switch position
        if (mLocalSharedPreferences.getNightModeStatus() == 0) {
            this.withChecked(false);
        } else {
            this.withChecked(true);
        }

        this.withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(activity,activity.getString(R.string.drawer_nightmode_toast_on),Toast.LENGTH_SHORT).show();
                    mLocalSharedPreferences.setNightModeStatus(1);
                }else{
                    Toast.makeText(activity,activity.getString(R.string.drawer_nightmode_toast_off),Toast.LENGTH_SHORT).show();
                    mLocalSharedPreferences.setNightModeStatus(0);
                }
            }
        });
    }
}
