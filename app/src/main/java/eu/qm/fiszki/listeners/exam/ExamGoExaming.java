package eu.qm.fiszki.listeners.exam;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ChangeActivityManager;
import eu.qm.fiszki.learning.LearningCheckActivity;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.category.CategoryRepository;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;

/**
 * Created by mBoiler on 25.11.2016.
 */

public class ExamGoExaming implements View.OnClickListener {

    private int mRepeat;
    private String mRangeText;
    private String mRepeatText;
    private Activity mActivity;
    private ArrayList<Flashcard> mChosenFlashcard;

    public ExamGoExaming(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onClick(View view) {
        getValueFromCards();
        if (checkChosen()) {
            this.mRepeat = Integer.parseInt(mRepeatText);
            this.mChosenFlashcard = new FlashcardRepository(mActivity)
                    .getFlashcardsByCategoryID(new CategoryRepository(mActivity)
                            .getCategoryByName(mRangeText).getId());
            if(mChosenFlashcard.isEmpty()){
                Toast.makeText(mActivity,R.string.exam_range_empty_toast,Toast.LENGTH_LONG).show();
            }else{
                new ChangeActivityManager(mActivity).goToExamCheck(mChosenFlashcard,mRepeat);
            }
        }
    }

    private void getValueFromCards() {
        this.mRangeText = ((TextView) mActivity
                .findViewById(R.id.exam_range_text)).getText().toString();
        this.mRepeatText = ((TextView) mActivity
                .findViewById(R.id.exam_repeat_text)).getText().toString();
    }

    private boolean checkChosen() {
        if (mRangeText.equals(mActivity.getResources().getString(R.string.exam_card_range_title))) {
            Toast.makeText(mActivity, "siusiak", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mRepeatText.equals(mActivity.getResources().getString(R.string.exam_card_repeat_title))) {
            Toast.makeText(mActivity, "cipuszka", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
