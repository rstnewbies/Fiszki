package eu.qm.fiszki;

import android.content.Context;
import android.os.CountDownTimer;

/**
 * Created by Siusiacz on 2015-10-10.
 */
public class TimerClass {
    NotificationsClass nope = new NotificationsClass();

    public void start(final Context window, int time, final String notificationMessage, final String notificationTitle) {
        CountDownTimer Count = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                nope.CreateNotification(window,notificationMessage,notificationTitle);
                start();
            }

        };
        Count.start();
    }
}