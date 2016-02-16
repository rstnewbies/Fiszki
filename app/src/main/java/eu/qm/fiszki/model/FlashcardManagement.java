package eu.qm.fiszki.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.Random;

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

    public void deleteAllFlashcards(ArrayList<Flashcard> flashcards) {
        flashcardDao.delete(flashcards);
    }

    public void updateFlashcard(Flashcard flashcard) {
        flashcardDao.update(flashcard);
    }

    public ArrayList<Flashcard> getFlashcardsByPriority(int priority) {

        ArrayList<Flashcard> flashcardListByPriority = new ArrayList<Flashcard>();
        ArrayList<Flashcard> flashcardList = getAllFlashcards();

        for (Flashcard flashcard : flashcardList) {
            if (flashcard.getPriority() == priority) {
                flashcardListByPriority.add(flashcard);
            }

        }
        return flashcardListByPriority;
    }

    public Flashcard getRandomFlashcardByPririty(int priority) {
        Random generator = new Random();
        ArrayList<Flashcard> flashcards = getFlashcardsByPriority(priority);
        Flashcard flashcard = flashcards.get(generator.nextInt(flashcards.size()));
        return flashcard;
    }

    public Flashcard getRandomFlashacrd(){
        Random generator = new Random();
        ArrayList<Flashcard> flashcards = getAllFlashcards();
        return flashcards.get(generator.nextInt(flashcards.size()));
    }
}
