package eu.qm.fiszki.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import eu.qm.fiszki.database.DBHelper;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class FlashcardRepository {

    public DBHelper dbHelper;
    public RuntimeExceptionDao<Flashcard, Integer> flashcardDao;
    public ArrayList<Flashcard> flashcardList;
    QueryBuilder<Flashcard, Integer> qb;

    public FlashcardRepository(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        flashcardDao = dbHelper.getFlashcardDao();
        qb = flashcardDao.queryBuilder();
    }

    public ArrayList<Flashcard> getAllFlashcards() {
        return flashcardList = (ArrayList<Flashcard>) flashcardDao.queryForAll();
    }

    public int countFlashcards() {
        return (int) flashcardDao.countOf();
    }

    public void addFlashcard(Flashcard flashcard) {
        flashcardDao.create(flashcard);
    }

    public void addFlashcards(ArrayList<Flashcard> arrayListFlashcards) {
        for (Flashcard flashcard : arrayListFlashcards) {
            flashcardDao.create(flashcard);
        }
    }

    public Flashcard getFlashcardByName(String name) {
        flashcardList =
                (ArrayList<Flashcard>) flashcardDao.queryForEq(Flashcard.columnFlashcardWord, name);
        if (!flashcardList.isEmpty()) {
            return flashcardList.get(0);
        } else {
            return null;
        }
    }

    public boolean existFlashcardInCategory(String name) {
        if (getFlashcardByName(name) != null) {
            return true;
        } else {
            return false;
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

    public void deleteFlashcards(ArrayList<Flashcard> flashcards) {
        for (Flashcard flashcard:flashcards) {
            flashcardDao.delete(flashcard);
        }
    }

    public void updateFlashcard(Flashcard flashcard) {
        flashcardDao.update(flashcard);
    }

    public ArrayList<Flashcard> getFlashcardsByPriority(int priority) {

        ArrayList<Flashcard> flashcardListByPriority =
                (ArrayList<Flashcard>) flashcardDao.queryForEq(Flashcard.columnFlashcardPriority, priority);
        return flashcardListByPriority;
    }

    public Flashcard getRandomFlashcardByPririty(int priority) {
        Random generator = new Random();
        ArrayList<Flashcard> flashcards = getFlashcardsByPriority(priority);
        Flashcard flashcard = flashcards.get(generator.nextInt(flashcards.size()));
        return flashcard;
    }

    public ArrayList<Flashcard> getFlashcardsByCategoryID(int CategoryID) {
        return (ArrayList<Flashcard>) flashcardDao.queryForEq(Flashcard.columnFlashcardCategoryID, CategoryID);
    }

}
