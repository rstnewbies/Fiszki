package eu.qm.fiszki.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

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

    public void addFlashcards(Flashcard flashcard) {
        flashcardDao.create(flashcard);
    }

    public void addFlashcard(ArrayList<Flashcard> arrayListFlashcards) {
        for (Flashcard flashcard : arrayListFlashcards) {
            flashcardDao.create(flashcard);
        }
    }

    public Flashcard getFlashcardById(int id) {
        Flashcard flashcard;
        flashcard = flashcardDao.queryForId(id);
        return flashcard;
    }

    public Flashcard getFlashcardByName(String name) {

        flashcardList = (ArrayList<Flashcard>) flashcardDao.queryForEq("word", name);
        return flashcardList.get(0);
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

    public Flashcard getRandomFlashacrd() {
        Random generator = new Random();
        ArrayList<Flashcard> flashcards = getAllFlashcards();
        return flashcards.get(generator.nextInt(flashcards.size()));
    }

    public ArrayList<Flashcard> getFlashcardsByCategoryID(int CategoryID) {
        ArrayList<Flashcard> flashcardListByCategory = new ArrayList<Flashcard>();
        ArrayList<Flashcard> flashcardList = getAllFlashcards();

        for (Flashcard flashcard : flashcardList) {
            if (flashcard.getCategory() == CategoryID) {
                flashcardListByCategory.add(flashcard);
            }
        }
        return flashcardListByCategory;
    }

    public void deleteFlashcardByCategory(int categoryId) {
        flashcardList = getFlashcardsByPriority(categoryId);
        flashcardDao.delete(flashcardList);
    }
}
