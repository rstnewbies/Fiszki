package eu.qm.fiszki.DataBaseContainer;

/**
 * Created by Mateusz on 2015-10-15.
 */
public class DBModel {

    //(private)
    public static final String TAG = "DBAdapter";

    // Fields:
    //FLASHCARD
    public static final String KEY_ROWID = "_id";
    public static final String KEY_WORD = "word";
    public static final String KEY_TRANSLATION = "translation";
    //SYSTEM SETTINGS
    public static final String SETTINGS_ROWID = "_id";
    public static final String SETTINGS_NAME = "name";
    public static final String SETTINGS_STATUS = "status";

    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_WORD, KEY_TRANSLATION};
    public static final String[] ALL_KEYS_SETTINGS = new String[]{SETTINGS_ROWID, SETTINGS_NAME, SETTINGS_STATUS};

    // Column numbers:
    public static final int COL_ROWID = 0;
    public static final int COL_WORD = 1;
    public static final int COL_TRANSLATE = 2;

    // DataBase info:
    public static final String DATABASE_NAME = "dbQM";
    public static final String DATABASE_TABLE = "mainFiszki";
    public static final String SETTINGS_TABLE = "applicationSettings";
    public static final int DATABASE_VERSION = 1; // wersja bazy ++.

    //SQL statement to create database (private)
    public static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_WORD + " TEXT NOT NULL, "
                    + KEY_TRANSLATION + " TEXT NOT NULL"
                    + ");";
    public static final String SETTINGS_CREATE_SQL =
            "CREATE TABLE " + SETTINGS_TABLE
                    + " (" + SETTINGS_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SETTINGS_NAME + " TEXT NOT NULL, "
                    + SETTINGS_STATUS + " INTEGER NOT NULL"
                    + ");";
    public static final String FILL_SETTINGS_SQL =
            "INSERT INTO "+ SETTINGS_TABLE +" VALUES(1, 'notification', 0)";
    public static final String SECOND_FILL_SETTINGS_SQL =
            "INSERT INTO "+ SETTINGS_TABLE +" VALUES(2, 'notification_time', 2)";
}
