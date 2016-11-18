package eu.qm.fiszki.listeners;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.SelectedFlashcardsSingleton;
import eu.qm.fiszki.model.flashcard.Flashcard;

/**
 * Created by mBoiler on 18.11.2016.
 */

public class FlashcardLongClick implements View.OnLongClickListener {

    private Activity mActivity;
    private Flashcard mFlashcard;
    private FloatingActionButton mFabAdd;
    private FloatingActionButton mFabDelete;

    public FlashcardLongClick(Activity activity,Flashcard flashcard) {
        this.mActivity = activity;
        this.mFlashcard = flashcard;
        this.mFabAdd = (FloatingActionButton) mActivity.findViewById(R.id.fab_add);
        this.mFabDelete = (FloatingActionButton) mActivity.findViewById(R.id.fab_delete);
    }

    @Override
    public boolean onLongClick(View view) {
        if(SelectedFlashcardsSingleton.findFlashcard(mFlashcard)){
            SelectedFlashcardsSingleton.removeFlashcard(mFlashcard);
            view.setBackgroundColor(mActivity.getResources().getColor(R.color.White));
        }else{
            SelectedFlashcardsSingleton.addFlashcards(mFlashcard);
            view.setBackgroundColor(mActivity.getResources().getColor(R.color.SelecteddColor));
        }
        checkStatus();
        return true;
    }

    public void checkStatus(){
        if(SelectedFlashcardsSingleton.getStatus()==SelectedFlashcardsSingleton.STATUS_ON){
            if(SelectedFlashcardsSingleton.getFlashcards().isEmpty()){
                SelectedFlashcardsSingleton.setStatuOff();
                mFabAdd.show();
                mFabDelete.hide();
            }
        }else{
            if(SelectedFlashcardsSingleton.getFlashcards().size()==1){
                SelectedFlashcardsSingleton.setStatuOn();
                mFabAdd.hide();
                mFabDelete.show();
            }
        }
    }
}

