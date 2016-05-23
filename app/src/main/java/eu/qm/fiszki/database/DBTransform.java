package eu.qm.fiszki.database;

import android.content.Context;
import android.database.Cursor;

import eu.qm.fiszki.database.SQL.DBAdapter;
import eu.qm.fiszki.database.SQL.DBModel;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class DBTransform {

    Category category;
    Flashcard flashcard;
    FlashcardRepository flashcardRepository;
    CategoryRepository categoryRepository;

    public DBTransform(DBAdapter myDb, Context context) {
        if (myDb.getAllRows().getCount() > 0) {
            categoryRepository = new CategoryRepository(context);
            flashcardRepository = new FlashcardRepository(context);
            Cursor c = myDb.getAllRows();
            do {
                flashcard = new Flashcard(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), 1);
                flashcardRepository.addFlashcard(flashcard);
            } while (c.moveToNext());
            myDb.deleteAll(DBModel.DATABASE_TABLE);
        }

    }
}
