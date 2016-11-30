package eu.qm.fiszki.model.flashcard;

import android.content.Context;
import android.widget.Toast;

import eu.qm.fiszki.R;

/**
 * Created by mBoiler on 12.11.2016.
 */

public class ValidationFlashcards {

    private Context mContext;

    public ValidationFlashcards(Context context) {
        this.mContext = context;
    }

    public boolean validateAdd(Flashcard flashcard) {
        if (flashcard.getWordDB().isEmpty() || flashcard.getTranslationDB().isEmpty()) {
            Toast.makeText(mContext, R.string.validation_flashcard_empty, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
