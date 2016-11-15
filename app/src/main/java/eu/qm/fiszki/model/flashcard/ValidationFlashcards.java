package eu.qm.fiszki.model.flashcard;

import android.content.Context;
import android.widget.Toast;

import eu.qm.fiszki.R;

/**
 * Created by mBoiler on 12.11.2016.
 */

public class ValidationFlashcards {

    private Context mContext;
    private FlashcardRepository mFlashcardRepository;

    public ValidationFlashcards(Context context) {
        this.mContext = context;
        this.mFlashcardRepository = new FlashcardRepository(context);
    }

    public boolean validate(Flashcard flashcard){
        if (flashcard.getWordDB().isEmpty()) {
            Toast.makeText(mContext, R.string.validation_flashcard_empty, Toast.LENGTH_LONG).show();
            return false;
        }
        if (flashcard.getTranslationDB().isEmpty()) {
            Toast.makeText(mContext, R.string.validation_flashcard_empty, Toast.LENGTH_LONG).show();
            return false;
        }
        if(mFlashcardRepository.getFlashcardByName(flashcard.getWordDB())!=null){
            if (mFlashcardRepository.getFlashcardByName(flashcard.getWordDB()).getCategoryID()==flashcard.getCategoryID()){
                Toast.makeText(mContext,R.string.validation_flashcard_same,Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }
}
