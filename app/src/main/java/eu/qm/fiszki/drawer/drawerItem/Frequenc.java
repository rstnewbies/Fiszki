package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.LocalSharedPreferences;
import eu.qm.fiszki.R;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class Frequenc extends PrimaryDrawerItem {

    private LocalSharedPreferences localSharedPreferences;
    private AlarmReceiver alarmReceiver;

    public Frequenc(final Activity activity) {
        localSharedPreferences = new LocalSharedPreferences(activity);
        alarmReceiver = new AlarmReceiver();

        this.withName(R.string.drawer_freqenc_name);
        this.withIcon(R.drawable.clock_alert);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                new MaterialDialog.Builder(activity)
                        .title(R.string.settings_choose_category)
                        .items(R.array.notification_frequency)
                        .itemsCallbackSingleChoice(getSelectedFreq(), new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                RBClick(which+1, activity);
                                return false;
                            }
                        })
                        .positiveText(R.string.button_action_ok)
                        .cancelable(false)
                        .show();
                return false;
            }
        });
    }

    private void RBClick(int id, Activity activity) {
        alarmReceiver.close(activity);
        localSharedPreferences.setNotificationPosition(id);
        if (localSharedPreferences.getNotificationStatus() == 1) {
            alarmReceiver.start(activity);
        }
    }

    private int getSelectedFreq() {
        switch (localSharedPreferences.getNotificationPosition()) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            default:
                return -1;
        }
    }
}
