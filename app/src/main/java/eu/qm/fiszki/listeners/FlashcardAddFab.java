package eu.qm.fiszki.listeners;

import android.app.Activity;
import android.view.View;

import eu.qm.fiszki.dialogs.AddFlashcardDialog;
import eu.qm.fiszki.myWords.CategoryManagerSingleton;

/**
 * Created by mBoiler on 19.11.2016.
 */

public class FlashcardAddFab implements View.OnClickListener {

    private Activity mActivity;

    public FlashcardAddFab(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onClick(View view) {
        new AddFlashcardDialog(mActivity, CategoryManagerSingleton.getCurrentCategoryId()).show();
    }
}
