package eu.qm.fiszki.dialogs.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;

import com.afollestad.materialdialogs.MaterialDialog;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.flashcard.Flashcard;

/**
 * Created by mBoiler on 24.11.2016.
 */

public class BadAnswerLearnigDialog extends MaterialDialog.Builder {

    public BadAnswerLearnigDialog(@NonNull Context context, Flashcard flashcard) {
        super(context);
        this.title(R.string.alert_title_fail);
        this.positiveText(R.string.button_action_ok);
        this.positiveColor(context.getResources().getColor(R.color.ColorPrimaryDark));
        this.content(Html.fromHtml(
                context.getResources().getString(R.string.learning_check_dialog_bad_answer_1) +
                " " + "<b>" + flashcard.getTranslation() + "</b>" + "<br>" +
                context.getResources().getString(R.string.learning_check_dialog_bad_answer_2)));
    }
}
