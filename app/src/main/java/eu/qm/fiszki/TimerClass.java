package eu.qm.fiszki;

import android.content.Context;
import android.os.CountDownTimer;

import eu.qm.fiszki.DataBaseContainer.DBAdapter;

public class TimerClass {
    NotificationsClass notification = new NotificationsClass();
    DBAdapter dba;

    public TimerClass(DBAdapter myDB)
    {
        super();
        this.dba = myDB;
    }
    public void start(final Context window, int time, final String notificationMessage, final String notificationTitle, final DBAdapter myDB) {
        CountDownTimer Count = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished){
            }

            public void onFinish()
            {
                if(dba.getAllRows().getCount()>0) {
                    notification.CreateNotification(window, notificationMessage, notificationTitle);
                    start();
                }
            }
        };
        Count.start();
    }
}
