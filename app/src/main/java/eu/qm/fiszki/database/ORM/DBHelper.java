package eu.qm.fiszki.database.ORM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String uncategory = "UNCATEGORY";
    public static final String addCategoryName = "ADDNEWCATEGORY";
    private static final String DATABASE_NAME = "Flashcards.db";
    private static final int DATABASE_VERSION = 3;
    private RuntimeExceptionDao<Flashcard, Integer> flashcardDao = null;
    private RuntimeExceptionDao<Category, Integer> categoryDao = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Flashcard.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        onCreate(database, connectionSource);
        getCategoryDao().executeRaw("ALTER TABLE `category` ADD COLUMN chosen BOOLEAN;");
    }

    public RuntimeExceptionDao<Flashcard, Integer> getFlashcardDao() {
        if (flashcardDao == null) {
            flashcardDao = getRuntimeExceptionDao(Flashcard.class);
        }
        return flashcardDao;
    }

    public RuntimeExceptionDao<Category, Integer> getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = getRuntimeExceptionDao(Category.class);
        }
        return categoryDao;
    }


}

