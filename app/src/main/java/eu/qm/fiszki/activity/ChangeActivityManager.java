package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.learning.LearningActivity;
import eu.qm.fiszki.learning.LearningCheckActivity;
import eu.qm.fiszki.listeners.exam.ExamCheckActivity;
import eu.qm.fiszki.model.flashcard.Flashcard;

/**
 * Created by mBoiler on 26.11.2016.
 */

public class ChangeActivityManager {

    public final static String FLASHCARDS_KEY_INTENT= "FLASHCARDS";
    public final static String EXAM_REPEAT_KEY_INTENT= "FLASHCARDS";

    private Activity mActivity;

    public ChangeActivityManager(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    public void goToLearningCheck(@NonNull ArrayList<Flashcard> flashcards){
        Intent goLearning = new Intent(mActivity,new LearningCheckActivity().getClass());
        goLearning.putExtra(FLASHCARDS_KEY_INTENT,flashcards);
        mActivity.startActivity(goLearning);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void exitLearningCheck(){
        mActivity.startActivity(new Intent(mActivity, LearningActivity.class));
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }

    public void goToExamCheck(@NonNull ArrayList<Flashcard> flashcards, @NonNull int repeat){
        Intent goLearning = new Intent(mActivity,new ExamCheckActivity().getClass());
        goLearning.putExtra("flashcards",flashcards);
        goLearning.putExtra("repeat",repeat);
        mActivity.startActivity(goLearning);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void exitExamCheck(){
        mActivity.startActivity(new Intent(mActivity, LearningActivity.class));
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }
}
