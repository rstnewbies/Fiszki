package eu.qm.fiszki.dialogs.flashcard;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;
import eu.qm.fiszki.model.flashcard.ValidationFlashcards;

/**
 * Created by mBoiler on 12.11.2016.
 */

//todo pokazywanie klawiatury po fokusie

public class QuicklyAddFlashcardDialog extends MaterialDialog.Builder {

    private ValidationFlashcards mValidationFlashcards;
    private MaterialEditText mFlashcardWordET;
    private MaterialEditText mFlashcardTranslationET;
    private FlashcardRepository mFlashcardRepository;
    private Activity mActivity;

    public QuicklyAddFlashcardDialog(@NonNull Activity activity) {
        super(activity);
        this.mActivity = activity;
        this.title(R.string.add_fast_new_flashcard);
        this.customView(R.layout.flashcard_add_dialog, false);
        this.positiveText(R.string.add_new_flashcard_btn);
        this.positiveColor(mActivity.getResources().getColor(R.color.ColorPrimaryDark));
        this.onPositive(addQuicklyFlashcard());
        init();
        autoDismiss(false);
    }

    private void init() {
        mFlashcardWordET = (MaterialEditText) customView.findViewById(R.id.add_flashcard_et_word);
        mFlashcardTranslationET = (MaterialEditText) customView.findViewById(R.id.add_flashcard_et_translation);
        mFlashcardRepository = new FlashcardRepository(context);
        mValidationFlashcards = new ValidationFlashcards(context);
    }

    private MaterialDialog.SingleButtonCallback addQuicklyFlashcard() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Flashcard flashcard = new Flashcard();
                flashcard.setWord(mFlashcardWordET.getText().toString().trim());
                flashcard.setTranslation(mFlashcardTranslationET.getText().toString().trim());
                flashcard.setPriority(1);
                flashcard.setCategoryID(1);

                if (mValidationFlashcards.validateAdd(flashcard)) {
                    mFlashcardRepository.addFlashcard(flashcard);
                    Toast.makeText(context, R.string.add_new_flashcard_toast, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        };
    }
}
