package eu.qm.fiszki.dialogs;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import eu.qm.fiszki.R;

/**
 * Created by tm on 11.07.16.
 */
public class AddCategoryDialog extends MaterialDialog.Builder{

    private MaterialEditText mCategoryNameET;
    private MaterialAutoCompleteTextView mCategoryLangACET;

    public AddCategoryDialog(@NonNull Activity activity) {
        super(activity);
        this.title(R.string.add_category_dialog_title);
        this.icon(context.getResources().getDrawable(R.drawable.ic_category_add));
        this.customView(R.layout.category_add_dialog, false);
        this.positiveText(R.string.add_category_positive_btn_text);
        init();
        setAdapterToLang();
        this.onPositive(addCategory());

        mCategoryNameET.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mCategoryNameET, InputMethodManager.SHOW_IMPLICIT);
    }

    private void init(){
        mCategoryNameET = (MaterialEditText) customView.findViewById(R.id.add_category_dialog_et_name);
        mCategoryLangACET = (MaterialAutoCompleteTextView) customView.findViewById(R.id.add_category_dialog_lang);
    }

    private void setAdapterToLang(){
        String[] countries = context.getResources().getStringArray(R.array.notification_frequency);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(context, android.R.layout.simple_selectable_list_item, countries);
        mCategoryLangACET.setAdapter(adapter);
    }

    private MaterialDialog.SingleButtonCallback addCategory() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                //todo save to DataBase
                Toast.makeText(context,R.string.add_category_toast,Toast.LENGTH_SHORT).show();
            }
        };
    }
}
