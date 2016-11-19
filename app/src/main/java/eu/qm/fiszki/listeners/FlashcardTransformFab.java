package eu.qm.fiszki.listeners;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import eu.qm.fiszki.R;
import eu.qm.fiszki.dialogs.TransformFlashcardDialog;
import eu.qm.fiszki.model.category.CategoryRepository;

/**
 * Created by mBoiler on 19.11.2016.
 */

public class FlashcardTransformFab implements View.OnClickListener {

    private Activity mActivity;
    private CategoryRepository mCategoryRepository;

    public FlashcardTransformFab(Activity activity) {
        this.mActivity = activity;
        this.mCategoryRepository = new CategoryRepository(activity);
    }

    @Override
    public void onClick(View view) {
        if (mCategoryRepository.getUserCategory().isEmpty()) {
            new AlertDialog.Builder(mActivity)
                    .setMessage(R.string.flashcard_transform_button_empty_message)
                    .setPositiveButton(R.string.button_action_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
            .show();
        } else {
            new TransformFlashcardDialog(mActivity).show();
        }
    }
}
