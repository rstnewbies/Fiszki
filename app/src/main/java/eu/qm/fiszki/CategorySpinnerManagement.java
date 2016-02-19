package eu.qm.fiszki;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by mBoiler on 19.02.2016.
 */
public class CategorySpinnerManagement {

    Spinner spinner;

    public CategorySpinnerManagement(Spinner spinner) {
        this.spinner = spinner;
    }

    public void selectedSpinner(final Context context){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.layout_dialog_add_category);
                    dialog.setTitle(R.string.main_activity_dialog_edit_item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
