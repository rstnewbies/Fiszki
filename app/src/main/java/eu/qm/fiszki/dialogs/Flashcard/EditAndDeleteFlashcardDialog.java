package eu.qm.fiszki.dialogs.flashcard;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;
import eu.qm.fiszki.model.flashcard.ValidationFlashcards;

/**
 * Created by mBoiler on 15.11.2016.
 */

public class EditAndDeleteFlashcardDialog extends MaterialDialog.Builder {

    private Activity mActivity;
    private Flashcard mFlashcard;
    private MaterialEditText mWordET;
    private MaterialEditText mTranslateET;
    private FlashcardRepository mFlashcardRepository;
    private ValidationFlashcards mValidationFlashcards;

    public EditAndDeleteFlashcardDialog(@NonNull Activity activity, Flashcard flashcard) {
        super(activity);
        this.mActivity = activity;
        this.mFlashcard = flashcard;
        this.title(R.string.flashcard_edit_title);
        this.customView(R.layout.flashcard_edit_dialog, false);
        this.icon(context.getResources().getDrawable(R.drawable.ic_pencil_black));
        this.autoDismiss(false);
        this.neutralText(R.string.flashcard_delete_btn);
        this.neutralColor(context.getResources().getColor(R.color.md_red_A700));
        this.positiveText(R.string.flashcard_edit_done);
        this.positiveColor(context.getResources().getColor(R.color.ColorPrimaryDark));

        this.onPositive(editFlashcard());
        this.onNeutral(deleteFlashcard());

        init();

        mWordET.setText(mFlashcard.getWord());
        mTranslateET.setText(mFlashcard.getTranslation());
    }

    private void init() {
        mTranslateET = (MaterialEditText) customView.findViewById(R.id.edit_flashcard_et_translation);
        mWordET = (MaterialEditText) customView.findViewById(R.id.edit_flashcard_et_word);
        mValidationFlashcards = new ValidationFlashcards(context);
        mFlashcardRepository = new FlashcardRepository(context);
    }

    private MaterialDialog.SingleButtonCallback editFlashcard() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Flashcard flashcard = mFlashcard;
                flashcard.setWord(mWordET.getText().toString().trim());
                flashcard.setTranslation(mTranslateET.getText().toString().trim());

                if (mValidationFlashcards.validateAdd(flashcard)) {
                    mFlashcardRepository.updateFlashcard(flashcard);
                    Toast.makeText(context, R.string.flashcard_edit_toast, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        };
    }

    private MaterialDialog.SingleButtonCallback deleteFlashcard() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final MaterialDialog editDialog = dialog;

                builder.setMessage(R.string.flashcard_delete_message)
                .setPositiveButton(R.string.button_action_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mindfulSnackbar();
                        mFlashcardRepository.deleteFlashcard(mFlashcard);
                        editDialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.button_action_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).show();
    }
};
    }

    private void mindfulSnackbar() {
        Snackbar snackbar = Snackbar
                .make(mActivity.getCurrentFocus(), R.string.snackbar_return_category_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_return_word_button, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mFlashcardRepository.addFlashcard(mFlashcard);
                        mActivity.onWindowFocusChanged(true);
                    }
                });

        snackbar.show();
    }
}
