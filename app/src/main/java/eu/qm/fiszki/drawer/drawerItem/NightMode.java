package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.LocalSharedPreferences;
import eu.qm.fiszki.NightModeController;
import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ChangeActivityManager;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class NightMode extends SwitchDrawerItem {

    private NightModeController nightModeController;

    public NightMode(final Activity activity) {
        nightModeController = new NightModeController(activity);

        this.withName(R.string.drawer_nightmode);
        this.withIcon(R.drawable.ic_weather_night);
        this.withCheckable(false);
        this.withSelectable(false);

        //Sync switch position
        if (nightModeController.getStatus() == 0) {
            this.withChecked(false);
        } else {
            this.withChecked(true);
        }

        this.withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(activity,activity.getString(R.string.drawer_nightmode_toast_on),Toast.LENGTH_SHORT).show();
                    nightModeController.on();
                    new ChangeActivityManager(activity).resetMain();
                }else{
                    Toast.makeText(activity,activity.getString(R.string.drawer_nightmode_toast_off),Toast.LENGTH_SHORT).show();
                    nightModeController.off();
                    new ChangeActivityManager(activity).resetMain();
                }
            }
        });
    }
}
