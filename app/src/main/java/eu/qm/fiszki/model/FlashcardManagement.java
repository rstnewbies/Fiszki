package eu.qm.fiszki.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;

import eu.qm.fiszki.database.DBHelper;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class FlashcardManagement {

    public DBHelper dbHelper;
    public RuntimeExceptionDao<Flashcard, Integer> flashcardDao;
    public ArrayList<Flashcard> flashcardList;

    public FlashcardManagement(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        flashcardDao = dbHelper.getFlashcardDao();
    }

    public ArrayList<Flashcard> getAllFlashcards() {
        return flashcardList = (ArrayList<Flashcard>) flashcardDao.queryForAll();
    }

    public void addFlashcards(Flashcard flashcard) {
        flashcardDao.create(flashcard);
    }

    public Flashcard getFlashcardById(int id) {
        Flashcard flashcard;
        flashcard = flashcardDao.queryForId(id);
        return flashcard;
    }

    public Flashcard getFlashcardByName(String name) {
        flashcardList = (ArrayList<Flashcard>) flashcardDao.queryForAll();
        for (Flashcard flashcard : flashcardList) {
            if (flashcard.getWord().equals(name)) {
                return flashcard;
            }

        }
        return null;
    }

    public boolean existence(String name) {
        if (getFlashcardByName(name) != null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isFirst() {
        if (getAllFlashcards().size() == 1) {
            return true;
        }
        return false;
    }

    public void deleteFlashcard(Flashcard flashcard) {
        flashcardDao.delete(flashcard);
    }

    public void deleteAllFlashcards(ArrayList<Flashcard> flashcards){
        flashcardDao.delete(flashcards);
    }

    public void updateFlashcard(Flashcard flashcard) {
        flashcardDao.update(flashcard);
    }
}
