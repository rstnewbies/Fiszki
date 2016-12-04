package eu.qm.fiszki.dialogs.flashcard;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import eu.qm.fiszki.FirebaseManager;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;
import eu.qm.fiszki.model.flashcard.ValidationFlashcards;

/**
 * Created by mBoiler on 15.11.2016.
 */

public class AddFlashcardDialog extends MaterialDialog.Builder {

    private int mCategoryId;
    private Activity mActivity;
    private MaterialEditText mWordEt;
    private MaterialEditText mTranslateEt;
    private FlashcardRepository mFlashcardRepository;
    private ValidationFlashcards mValidationFlashcards;

    public AddFlashcardDialog(@NonNull Activity activity, int mCategoryId) {
        super(activity);
        this.mActivity = activity;
        this.mCategoryId = mCategoryId;
        this.title(R.string.flashcard_add_title);
        this.customView(R.layout.flashcard_add_dialog, false);
        this.positiveText(R.string.add_new_flashcard_btn);
        this.positiveColor(activity.getResources().getColor(R.color.ColorPrimaryDark));
        this.autoDismiss(false);
        this.onPositive(addFlashcardBtn());

        init();
    }

    private void init() {
        mWordEt = (MaterialEditText) customView.findViewById(R.id.add_flashcard_et_word);
        mTranslateEt = (MaterialEditText) customView.findViewById(R.id.add_flashcard_et_translation);
        mValidationFlashcards = new ValidationFlashcards(context);
        mFlashcardRepository = new FlashcardRepository(context);
    }

    private MaterialDialog.SingleButtonCallback addFlashcardBtn() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Flashcard flashcard = new Flashcard();
                flashcard.setWord(mWordEt.getText().toString().trim());
                flashcard.setTranslation(mTranslateEt.getText().toString().trim());
                flashcard.setCategoryID(mCategoryId);
                flashcard.setPriority(1);

                if (mValidationFlashcards.validateAdd(flashcard)) {
                    mFlashcardRepository.addFlashcard(flashcard);
                    new FirebaseManager(mActivity).sendEvent(FirebaseManager.Params.ADD_FLASHCARD);
                    Toast.makeText(context, R.string.add_new_flashcard_toast, Toast.LENGTH_LONG).show();
                    mTranslateEt.setText(null);
                    mWordEt.setText(null);
                    mWordEt.requestFocus();
                }
            }
        };
    }
}
