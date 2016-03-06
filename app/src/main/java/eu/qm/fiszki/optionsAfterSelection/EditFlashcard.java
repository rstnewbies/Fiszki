package eu.qm.fiszki.optionsAfterSelection;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import eu.qm.fiszki.BackgroundSetter;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;
import eu.qm.fiszki.toolbar.ToolbarSelected;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class EditFlashcard {

    ToolbarMainActivity toolbarMainActivity;
    EditText editOrginal;
    EditText editTranslate;
    Button dialogButton;
    Dialog dialog;
    FlashcardRepository flashcardRepository;
    BackgroundSetter backgroundSetter;
    ToolbarSelected toolbarSelected;

    public EditFlashcard(final Activity activity, final Flashcard selectedFlashcard) {

        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.layout_dialog_edit_flashcard);
        dialog.setTitle(R.string.main_activity_dialog_edit_item);
        backgroundSetter = new BackgroundSetter(activity);
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        dialogButton = (Button) dialog.findViewById(R.id.editButton);
        editOrginal = (EditText) dialog.findViewById(R.id.editOrginal);
        editTranslate =(EditText) dialog.findViewById(R.id.editTranslate);
        toolbarSelected = new ToolbarSelected(activity);
        toolbarMainActivity = new ToolbarMainActivity(activity);
        editOrginal.setText(selectedFlashcard.getWord());
        editTranslate.setText(selectedFlashcard.getTranslation());

        editOrginal.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard =
                        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(editOrginal, 0);
            }
        }, 50);
        editOrginal.setSelection(editOrginal.getText().length());

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFlashcard.setWord(editOrginal.getText().toString());
                selectedFlashcard.setTranslation(editOrginal.getText().toString());
                flashcardRepository.updateFlashcard(selectedFlashcard);
                backgroundSetter.set();
                toolbarMainActivity.set();
                dialog.dismiss();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

}
