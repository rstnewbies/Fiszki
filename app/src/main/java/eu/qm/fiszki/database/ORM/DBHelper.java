package eu.qm.fiszki.database.ORM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.flashcard.Flashcard;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "Flashcards.db";
    private static final int DATABASE_VERSION = 5;
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
        if(newVersion>=3 && oldVersion<3) {
            getCategoryDao().executeRaw("ALTER TABLE `category` ADD COLUMN chosen BOOLEAN;");
        }
        if(newVersion>=4 && oldVersion<4){
            getCategoryDao().executeRaw("ALTER TABLE `category` ADD COLUMN langFrom VARCHAR(255);");
            getCategoryDao().executeRaw("ALTER TABLE `category` ADD COLUMN langOn VARCHAR(255);");
        }
        if(newVersion>=5 && oldVersion<5){
            getFlashcardDao()
                    .executeRaw("ALTER TABLE `flashcard` ADD COLUMN staticFail INT(255) DEFAULT '0';");
            getFlashcardDao()
                    .executeRaw("ALTER TABLE `flashcard` ADD COLUMN staticPass INT(255) DEFAULT '0';");
        }
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

