package eu.qm.fiszki.optionsAfterSelection;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import eu.qm.fiszki.BackgroundSetter;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class DeleteFlashcard {

    ToolbarMainActivity toolbarMainActivity;
    Flashcard deletedFlashcard;
    BackgroundSetter backgroundSetter;
    FlashcardRepository flashcardRepository;

    public DeleteFlashcard(Flashcard selectedFlashcard, Activity activity) {
        backgroundSetter = new BackgroundSetter(activity);
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        toolbarMainActivity = new ToolbarMainActivity(activity);
        deletedFlashcard = selectedFlashcard;
        flashcardRepository.deleteFlashcard(deletedFlashcard);
        backgroundSetter.set();
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content),
                        activity.getString(R.string.snackbar_return_word_message),
                        Snackbar.LENGTH_LONG)
                .setAction(activity.getString(R.string.snackbar_return_word_button),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                flashcardRepository.addFlashcard(deletedFlashcard);
                                backgroundSetter.set();
                                toolbarMainActivity.set();
                            }
                        });
        snackbar.show();
    }
}
