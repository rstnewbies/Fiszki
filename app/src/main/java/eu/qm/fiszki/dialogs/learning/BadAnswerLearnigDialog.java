package eu.qm.fiszki.dialogs.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import eu.qm.fiszki.FirebaseManager;
import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.learning.LearningCheckActivity;
import eu.qm.fiszki.model.flashcard.Flashcard;

/**
 * Created by mBoiler on 24.11.2016.
 */

public class BadAnswerLearnigDialog extends MaterialDialog.Builder {

    public BadAnswerLearnigDialog(@NonNull Context context,
                                  @NonNull Flashcard flashcard,
                                  @NonNull LearningCheckActivity lca) {
        super(context);
        this.title(R.string.alert_title_fail);
        this.content(Html.fromHtml(
                context.getResources().getString(R.string.learning_check_dialog_bad_answer_1) +
                        " " + "<b>" + flashcard.getTranslation() + "</b>" + "<br>" +
                        context.getResources().getString(R.string.learning_check_dialog_bad_answer_2)));

        this.positiveText(R.string.button_action_ok);
        this.positiveColor(context.getResources().getColor(R.color.ColorPrimaryDark));

        this.neutralText(R.string.learning_check_dialog_skip_btn);
        this.neutralColor(context.getResources().getColor(R.color.ColorPrimaryDark));

        this.autoDismiss(false);
        this.onPositive(okClick());
        this.onNeutral(skipClick(lca));

    }

    private MaterialDialog.SingleButtonCallback okClick() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        };
    }

    private MaterialDialog.SingleButtonCallback skipClick(final LearningCheckActivity lca) {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                lca.drawFlashcard();
                dialog.dismiss();
                new FirebaseManager(context).sendEvent(FirebaseManager.Params.LEARNING_DIALOG_SKIP);
            }
        };
    }
}
