package eu.qm.fiszki.optionsAfterSelection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.view.View;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.ListPopulate;
import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.SettingsActivity;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class DeleteFlashcard {

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    ToolbarMainActivity toolbarMainActivity;
    Flashcard deletedFlashcard;

    FlashcardRepository flashcardRepository;
    AlarmReceiver alarm;

    public DeleteFlashcard(Flashcard selectedFlashcard, final Activity activity,
                           final ListPopulate listPopulate) {
        sharedPreferences = activity.getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        alarm = new AlarmReceiver();
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        toolbarMainActivity = new ToolbarMainActivity(activity);
        deletedFlashcard = selectedFlashcard;
        flashcardRepository.deleteFlashcard(deletedFlashcard);
        listPopulate.populate(null,null);
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(R.id.fab),
                        activity.getString(R.string.snackbar_return_word_message),
                        Snackbar.LENGTH_LONG)
                .setAction(activity.getString(R.string.snackbar_return_word_button),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                flashcardRepository.addFlashcard(deletedFlashcard);
                                listPopulate.populate(null,null);
                                toolbarMainActivity.set();
                                if (flashcardRepository.isFirst()) {
                                    alarm.start(activity, 15);
                                    editor.clear();
                                    editor.putInt(SettingsActivity.notificationPosition, 3);
                                    editor.commit();
                                }
                            }
                        });
        snackbar.show();
    }
}
