package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

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

                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.frequenc_choose);
                RadioButton rb1 = (RadioButton) dialog.findViewById(R.id.freq_choose_1);
                RadioButton rb2 = (RadioButton) dialog.findViewById(R.id.freq_choose_2);
                RadioButton rb3 = (RadioButton) dialog.findViewById(R.id.freq_choose_3);
                RadioButton rb4 = (RadioButton) dialog.findViewById(R.id.freq_choose_4);
                Button btn = (Button) dialog.findViewById(R.id.freq_choose_btn);

                System.out.println(localSharedPreferences.getNotificationPosition());

                if (localSharedPreferences.getNotificationPosition() == 1) {
                    rb1.setChecked(true);
                } else if (localSharedPreferences.getNotificationPosition() == 2) {
                    rb2.setChecked(true);
                } else if (localSharedPreferences.getNotificationPosition() == 3) {
                    rb3.setChecked(true);
                } else if (localSharedPreferences.getNotificationPosition() == 4) {
                    rb4.setChecked(true);
                }

                rb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RBClick(1, dialog, activity);
                    }
                });

                rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RBClick(2, dialog, activity);
                    }
                });

                rb3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RBClick(3, dialog, activity);
                    }
                });

                rb4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RBClick(4, dialog, activity);
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

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
