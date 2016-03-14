package eu.qm.fiszki;

import android.app.Activity;
import android.widget.EditText;

import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class Rules {

    Alert alert;
    FlashcardRepository flashcardRepository;
    CategoryRepository categoryRepository;

    public boolean addNewWordRule(EditText orginalWord, EditText translateWord, Activity activity) {
        alert = new Alert();
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        if (orginalWord.getText().toString().isEmpty() || translateWord.getText().toString().isEmpty()) {
            alert.buildAlert(activity.getString(R.string.alert_title),
                    activity.getString(R.string.alert_message_onEmptyFields),
                    activity.getString(R.string.button_action_ok), activity);
            return false;
        }
        if (!flashcardRepository.existence(orginalWord.getText().toString())) {
            alert.buildAlert(activity.getString(R.string.alert_title),
                    activity.getString(R.string.alert_message_onRecordExist),
                    activity.getString(R.string.button_action_ok), activity);
            orginalWord.setText(null);
            translateWord.setText(null);
            orginalWord.requestFocus();
            return false;
        }
        return true;
    }

    public boolean addNewCategoryRule( Activity activity,EditText newCategory ){
        alert = new Alert();
        categoryRepository = new CategoryRepository(activity.getBaseContext());
        if(newCategory.getText().toString().isEmpty()){
            alert.buildAlert(activity.getString(R.string.alert_title),
                    activity.getString(R.string.alert_message_onEmptyFields),
                    activity.getString(R.string.button_action_ok), activity);
            return false;
        }
        if(categoryRepository.getCategoryByName(newCategory.getText().toString())==null){
            alert.buildAlert(activity.getString(R.string.alert_title),
                    activity.getString(R.string.alert_message_onRecordExist),
                    activity.getString(R.string.button_action_ok), activity);
            newCategory.setText(null);
            newCategory.requestFocus();
            return false;
        }
        return true;
    }
}
