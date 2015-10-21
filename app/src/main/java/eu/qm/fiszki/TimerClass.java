package eu.qm.fiszki;

import android.content.Context;
import android.os.CountDownTimer;

public class TimerClass {

    NotificationsClass notification = new NotificationsClass();

    public void start(final Context window, int time, final String notificationMessage, final String notificationTitle, final DBAdapter myDB) {

        CountDownTimer Count = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if(myDB.rowCount()>0) {
                    notification.CreateNotification(window, notificationMessage, notificationTitle);
                }
                start();
            }


        };
        Count.start();
    }
}
