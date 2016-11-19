package eu.qm.fiszki.listeners;

import android.app.Activity;
import android.view.View;

/**
 * Created by mBoiler on 19.11.2016.
 */

public class FlashcardCancelFab implements View.OnClickListener {

    private Activity mActivity;

    public FlashcardCancelFab(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onClick(View view) {
        mActivity.onWindowFocusChanged(true);
    }
}
