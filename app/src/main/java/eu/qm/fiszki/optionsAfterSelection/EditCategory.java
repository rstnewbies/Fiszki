package eu.qm.fiszki.optionsAfterSelection;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import eu.qm.fiszki.ListPopulate;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class EditCategory {

    EditText editCategory;
    Button dialogButton;
    Dialog dialog;
    CategoryRepository categoryRepository;
    ToolbarMainActivity toolbarMainActivity;

    public EditCategory(final Activity activity, final Category selectedCategory,
                        final ListPopulate listPopulate) {

        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.layout_dialog_edit_category);
        dialog.setTitle(R.string.main_activity_dialog_edit_category);
        categoryRepository = new CategoryRepository(activity.getBaseContext());
        dialogButton = (Button) dialog.findViewById(R.id.editButton);
        editCategory = (EditText) dialog.findViewById(R.id.editCategory);
        toolbarMainActivity = new ToolbarMainActivity(activity);

        editCategory.setText(selectedCategory.getCategory());
        editCategory.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard =
                        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(editCategory, 0);
            }
        }, 50);
        editCategory.setSelection(editCategory.getText().length());

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarMainActivity.set();
                selectedCategory.setCategory(editCategory.getText().toString());
                categoryRepository.updateCategory(selectedCategory);
                listPopulate.populate(null,null);
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
