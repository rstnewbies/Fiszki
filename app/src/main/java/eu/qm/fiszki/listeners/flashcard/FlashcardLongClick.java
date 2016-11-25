package eu.qm.fiszki.listeners.flashcard;

import android.app.Activity;
import android.view.View;

import eu.qm.fiszki.R;
import eu.qm.fiszki.myWords.flashcards.SelectedFlashcardsSingleton;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.myWords.flashcards.SelectedFabManager;

/**
 * Created by mBoiler on 18.11.2016.
 */

public class FlashcardLongClick implements View.OnLongClickListener {

    private Activity mActivity;
    private Flashcard mFlashcard;
    private SelectedFabManager mFabManager;


    public FlashcardLongClick(Activity activity, Flashcard flashcard) {
        this.mActivity = activity;
        this.mFlashcard = flashcard;
        this.mFabManager = new SelectedFabManager(activity);
    }

    @Override
    public boolean onLongClick(View view) {
        if (SelectedFlashcardsSingleton.findFlashcard(mFlashcard)) {
            SelectedFlashcardsSingleton.removeFlashcard(mFlashcard);
            view.setBackgroundColor(mActivity.getResources().getColor(R.color.White));
        } else {
            SelectedFlashcardsSingleton.addFlashcards(mFlashcard);
            view.setBackgroundColor(mActivity.getResources().getColor(R.color.SelecteddColor));
        }
        checkStatus();
        return true;
    }

    private void checkStatus() {
        if (SelectedFlashcardsSingleton.getStatus() == SelectedFlashcardsSingleton.STATUS_ON) {
            if (SelectedFlashcardsSingleton.getFlashcards().isEmpty()) {
                SelectedFlashcardsSingleton.setStatuOff();
                mFabManager.hideAll();
            }
        } else {
            if (SelectedFlashcardsSingleton.getFlashcards().size() == 1) {
                SelectedFlashcardsSingleton.setStatuOn();
                mFabManager.showAll();
            }
        }
    }
}

