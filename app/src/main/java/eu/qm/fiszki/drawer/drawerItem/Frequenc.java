package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

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
                        .title(R.string.)
                        .items(R.array.notification_frequency)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                /**
                                 * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected radio button to actually be selected.
                                 **/
                                return true;
                            }
                        })
                        .positiveText(R.string.button_action_ok)
                        .show();
            return false;
            }
        });
    }

    private void RBClick(int id, Dialog dialog, Activity activity) {
        alarmReceiver.close(activity);
        localSharedPreferences.setNotificationPosition(id);
        if (localSharedPreferences.getNotificationStatus() == 1) {
            alarmReceiver.start(activity);
        }
        dialog.dismiss();
    }
}
