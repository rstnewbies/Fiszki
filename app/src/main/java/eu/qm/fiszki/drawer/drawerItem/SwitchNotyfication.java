package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.FirebaseManager;
import eu.qm.fiszki.LocalSharedPreferences;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;

/**
 * Created by tm on 08.07.16.
 */
public class SwitchNotyfication extends SwitchDrawerItem {

    private Activity mActivity;
    private AlarmReceiver mAlarmReceiver;
    private LocalSharedPreferences mLocalSharedPreferences;
    private FlashcardRepository mFlashcardRepository;

    public SwitchNotyfication(final Activity activity) {
        this.mActivity = activity;
        this.mAlarmReceiver = new AlarmReceiver();
        this.mLocalSharedPreferences = new LocalSharedPreferences(activity);
        this.mFlashcardRepository = new FlashcardRepository(activity);

        this.withName(R.string.drawer_notyfication_switch_name);
        this.withIcon(R.drawable.ic_notifications_black_24dp);
        this.withCheckable(false);
        this.withSelectable(false);

        if (!mFlashcardRepository.getAllFlashcards().isEmpty()) {
            this.withSwitchEnabled(true);
        } else {
            this.withSwitchEnabled(false);
        }

        //Sync switch position
        if (mLocalSharedPreferences.getNotificationStatus() == 0) {
            this.withChecked(false);
        } else {
            this.withChecked(true);
        }

        //Add event if move switch
        this.withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAlarmReceiver.close(activity);
                    mAlarmReceiver.start(activity);
                    mLocalSharedPreferences.setNotificationStatus(1);
                    if(mLocalSharedPreferences.getNotificationPosition()==0){
                        mLocalSharedPreferences.setNotificationPosition(3);
                    }

                    Toast.makeText(activity.getBaseContext(), activity.getString(R.string.drawer_notyfication_switch_toast_on), Toast.LENGTH_SHORT).show();
                    new FirebaseManager(mActivity).sendEvent(FirebaseManager.Params.NOTYFI_ON);
                } else {
                    mAlarmReceiver.close(activity);
                    mLocalSharedPreferences.setNotificationStatus(0);
                    Toast.makeText(activity.getBaseContext(), activity.getString(R.string.drawer_notyfication_switch_toast_off), Toast.LENGTH_SHORT).show();
                    new FirebaseManager(mActivity).sendEvent(FirebaseManager.Params.NOTYFI_OFF);
                }
            }
        });
    }
}
