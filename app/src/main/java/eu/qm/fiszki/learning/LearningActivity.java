package eu.qm.fiszki.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ChangeActivityManager;
import eu.qm.fiszki.dialogs.learning.ByCategoryLearningDialog;
import eu.qm.fiszki.dialogs.learning.ByLanguageLearningDialog;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;
import eu.qm.fiszki.myWords.flashcards.FlashcardsActivity;

public class LearningActivity extends AppCompatActivity {

    private FlashcardRepository mFlashcardRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        buildToolbar();
        mFlashcardRepository = new FlashcardRepository(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.learning_toolbar_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onAllClick(View view) {
        new ChangeActivityManager(this).goToLearningCheck(mFlashcardRepository.getAllFlashcards());
    }

    public void onCategoryClick(View view) {
        new ByCategoryLearningDialog(this).show();
    }

    public void onLangClick(View view) {
        new ByLanguageLearningDialog(this).show();
    }
}
