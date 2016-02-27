package eu.qm.fiszki;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import eu.qm.fiszki.model.CategoryManagement;
import eu.qm.fiszki.model.FlashcardManagement;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class Rules {

    Alert alert = new Alert();
    FlashcardManagement flashcardManagement;

    public boolean addNewWordRule(EditText orginalWord, EditText translateWord, Activity activity) {
        flashcardManagement = new FlashcardManagement(activity.getBaseContext());
        if (orginalWord.getText().toString().isEmpty() || translateWord.getText().toString().isEmpty()) {
            alert.buildAlert(activity.getString(R.string.alert_title),
                    activity.getString(R.string.alert_message_onEmptyFields),
                    activity.getString(R.string.button_action_ok), activity);
            return false;
        }
        if (!flashcardManagement.existence(orginalWord.getText().toString())) {
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

    public boolean check(String originalWord, String enteredWord) {
        if (originalWord.compareTo(enteredWord) == 0) return true;
        else return false;
    }

}
