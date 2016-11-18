package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.LocalSharedPreferences;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;

/**
 * Created by tm on 08.07.16.
 */
public class SwitchNotyfication extends SwitchDrawerItem {

    private AlarmReceiver alarmReceiver;
    private LocalSharedPreferences localSharedPreferences;
    private FlashcardRepository flashcardRepository;

    public SwitchNotyfication(final Activity activity) {
        alarmReceiver = new AlarmReceiver();
        localSharedPreferences = new LocalSharedPreferences(activity);
        flashcardRepository = new FlashcardRepository(activity);

        this.withName(R.string.drawer_notyfication_switch_name);
        this.withIcon(R.drawable.ic_notifications_black_24dp);
        this.withCheckable(false);
        this.withSelectable(false);

        if (!flashcardRepository.getAllFlashcards().isEmpty()) {
            this.withSwitchEnabled(true);
        } else {
            this.withSwitchEnabled(false);
        }

        //Sync switch position
        if (localSharedPreferences.getNotificationStatus() == 0) {
            this.withChecked(false);
        } else {
            this.withChecked(true);
        }

        //Add event if move switch
        this.withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmReceiver.close(activity);
                    alarmReceiver.start(activity);
                    localSharedPreferences.setNotificationStatus(1);
                    if(localSharedPreferences.getNotificationPosition()==0){
                        localSharedPreferences.setNotificationPosition(3);
                    }

                    Toast.makeText(activity.getBaseContext(), activity.getString(R.string.drawer_notyfication_switch_toast_on), Toast.LENGTH_SHORT).show();
                } else {
                    alarmReceiver.close(activity);
                    localSharedPreferences.setNotificationStatus(0);
                    Toast.makeText(activity.getBaseContext(), activity.getString(R.string.drawer_notyfication_switch_toast_off), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
