package eu.qm.fiszki.database;

import android.content.Context;
import android.database.Cursor;

import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryManagement;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardManagement;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class DBTransform {

    Category category;
    Flashcard flashcard;
    FlashcardManagement flashcardManagement;
    CategoryManagement categoryManagement;

    public DBTransform(DBAdapter myDb, Context context) {
            if (myDb.getAllRows().getCount() > 0) {
                categoryManagement = new CategoryManagement(context);
                flashcardManagement = new FlashcardManagement(context);
                category = categoryManagement.getCategoryByName(DBHelper.uncategory);
                Cursor c = myDb.getAllRows();
                do {
                    flashcard = new Flashcard(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3),category);
                    flashcardManagement.addFlashcards(flashcard);
                } while (c.moveToNext());
                myDb.deleteAll(DBModel.DATABASE_TABLE);
            }

    }
}
