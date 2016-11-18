package eu.qm.fiszki.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.category.CategoryRepository;
import eu.qm.fiszki.model.category.ValidationCategory;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;

/**
 * Created by mBoiler on 12.11.2016.
 */

public class EditAndDeleteCategoryDialog extends MaterialDialog.Builder {

    private MaterialEditText mCategoryNameET;
    private ValidationCategory mValidationCategory;
    private CategoryRepository mCategoryRepository;
    private FlashcardRepository mFlashcardRepository;
    private Category mCategory;
    private Activity mActivity;
    private ArrayList<Flashcard> mFlashcards;

    public EditAndDeleteCategoryDialog(@NonNull Activity activity, Category category) {
        super(activity);
        this.mCategory = category;
        this.mActivity = activity;
        this.title(R.string.edit_category_title);
        this.icon(activity.getResources().getDrawable(R.drawable.ic_pencil_black));
        this.customView(R.layout.category_add_dialog, false);
        this.autoDismiss(false);
        this.positiveText(R.string.edit_category_done);
        this.positiveColor(activity.getResources().getColor(R.color.ColorPrimaryDark));
        this.neutralText(R.string.edit_category_delete);
        this.neutralColor(activity.getResources().getColor(R.color.md_red_A700));

        this.onPositive(editCategory());
        this.onNeutral(deleteCategory());

        init();

        mCategoryNameET.setText(mCategory.getCategory());
    }

    public void init() {
        mCategoryNameET = (MaterialEditText) customView.findViewById(R.id.add_category_dialog_et_name);
        mValidationCategory = new ValidationCategory(context);
        mCategoryRepository = new CategoryRepository(context);
        mFlashcardRepository = new FlashcardRepository(mActivity);
    }

    private MaterialDialog.SingleButtonCallback editCategory() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Category category = mCategory;
                category.setCategory(mCategoryNameET.getText().toString().trim());

                if (mValidationCategory.validate(category)) {
                    mCategoryRepository.updateCategory(category);
                    Toast.makeText(context, R.string.edit_category_toast, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        };
    }

    private MaterialDialog.SingleButtonCallback deleteCategory() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final MaterialDialog editDialog = dialog;

                builder.setMessage(R.string.edit_category_delete_message)
                        .setPositiveButton(R.string.button_action_yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteCategoryWithFlashcards();
                                mindfulSnackbar();
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

    private void deleteCategoryWithFlashcards() {
        mFlashcards = mFlashcardRepository.getFlashcardsByCategoryID(mCategory.getId());
        if (!mFlashcards.isEmpty()) {
            mFlashcardRepository.deleteFlashcards(mFlashcards);
        }
        mCategoryRepository.deleteCategory(mCategory);
    }


    private void mindfulSnackbar() {
        Snackbar snackbar = Snackbar
                .make(mActivity.getCurrentFocus(), R.string.snackbar_return_category_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_return_word_button, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCategoryRepository.addCategory(mCategory);

                        if (mFlashcards!=null) {
                            mFlashcardRepository.addFlashcards(mFlashcards);
                        }
                        
                        mActivity.onWindowFocusChanged(true);
                    }
                });

        snackbar.show();
    }
}
