package eu.qm.fiszki.listeners;

import android.app.Activity;
import android.os.Handler;
import android.view.View;

import eu.qm.fiszki.dialogs.EditAndDeleteFlashcardDialog;
import eu.qm.fiszki.model.flashcard.Flashcard;

/**
 * Created by mBoiler on 18.11.2016.
 */

public class FlashcardClick implements View.OnClickListener {

    private int mClickCount;
    private Activity mActivity;
    private Flashcard mFlashcard;


    public FlashcardClick(Activity activity, Flashcard flashcard) {
        this.mClickCount = 0;
        this.mActivity = activity;
        this.mFlashcard = flashcard;
    }

    @Override
    public void onClick(View view) {
        mClickCount++;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                mClickCount = 0;
            }
        };

        if (mClickCount == 1) {
            //Single click
            handler.postDelayed(r, 250);
        } else if (mClickCount == 2) {
            //Double click
            mClickCount = 0;
            new EditAndDeleteFlashcardDialog(mActivity, mFlashcard).show();
        }
    }
}

