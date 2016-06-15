package eu.qm.fiszki.optionsAfterSelection;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import eu.qm.fiszki.addWord.CategorySpinnerRepository;
import eu.qm.fiszki.ListPopulate;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;

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
    CategoryRepository categoryRepository;
    Spinner editFlashcardsCategory;
    CategorySpinnerRepository categorySpinnerRepository;

    public EditFlashcard(final Activity activity, final Flashcard selectedFlashcard,
                         final ListPopulate listPopulate) {

        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.layout_dialog_edit_flashcard);
        dialog.setTitle(R.string.main_activity_dialog_edit_item);
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        categoryRepository = new CategoryRepository(activity.getBaseContext());
        dialogButton = (Button) dialog.findViewById(R.id.editButton);
        editOrginal = (EditText) dialog.findViewById(R.id.editOrginal);
        editTranslate = (EditText) dialog.findViewById(R.id.editTranslate);
        editFlashcardsCategory = (Spinner) dialog.findViewById(R.id.editFlashcardSpinner);
        categorySpinnerRepository = new CategorySpinnerRepository(editFlashcardsCategory,
                activity.getBaseContext());
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

        categorySpinnerRepository.populate(false);
        editFlashcardsCategory.setSelection(categorySpinnerRepository.dataAdapter
                .getPosition(categoryRepository.getCategoryByID(selectedFlashcard.getCategoryId()).getCategory()));

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFlashcard.setWord(editOrginal.getText().toString());
                selectedFlashcard.setTranslation(editTranslate.getText().toString());
                selectedFlashcard.setCategoryId(categorySpinnerRepository.getSelectedCategoryID());
                flashcardRepository.updateFlashcard(selectedFlashcard);
                listPopulate.populate(null,null,0);
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
