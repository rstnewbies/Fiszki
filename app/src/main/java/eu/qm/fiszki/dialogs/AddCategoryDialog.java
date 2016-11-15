package eu.qm.fiszki.dialogs;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.category.CategoryRepository;
import eu.qm.fiszki.model.category.ValidationCategory;

/**
 * Created by tm on 11.07.16.
 */
public class AddCategoryDialog extends MaterialDialog.Builder {

    private CategoryRepository mCategoryRepository;
    private MaterialEditText mCategoryNameET;
    private MaterialAutoCompleteTextView mCategoryLangACET;
    private Activity mActivity;
    private ValidationCategory mValidationCategory;

    public AddCategoryDialog(@NonNull Activity activity) {
        super(activity);
        this.mActivity = activity;
        this.title(R.string.category_dialog_title);
        this.icon(context.getResources().getDrawable(R.drawable.ic_category_add));
        this.customView(R.layout.category_add_dialog, false);
        this.positiveText(R.string.category_positive_btn_text);
        this.positiveColor(mActivity.getResources().getColor(R.color.ColorPrimaryDark));
        this.onPositive(addCategory());
        init();
        autoDismiss(false);
    }

    private void init() {
        mCategoryNameET = (MaterialEditText) customView.findViewById(R.id.add_category_dialog_et_name);
        //mCategoryLangACET = (MaterialAutoCompleteTextView) customView.findViewById(R.id.add_category_dialog_lang);
        mCategoryRepository = new CategoryRepository(mActivity);
        mValidationCategory = new ValidationCategory(mActivity);
    }

    private void setAdapterToLang() {
        String[] countries = context.getResources().getStringArray(R.array.notification_frequency);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(context, android.R.layout.simple_selectable_list_item, countries);
        mCategoryLangACET.setAdapter(adapter);
    }

    private MaterialDialog.SingleButtonCallback addCategory() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Category category = new Category();
                category.setCategory(mCategoryNameET.getText().toString().trim());
                category.setEntryByUser(true);

                if (mValidationCategory.validate(category)) {
                    mCategoryRepository.addCategory(category);
                    Toast.makeText(context, R.string.category_toast, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        };
    }
}
