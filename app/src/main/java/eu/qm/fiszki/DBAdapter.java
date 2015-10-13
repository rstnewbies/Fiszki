package eu.qm.fiszki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

public class DBAdapter {

    private static final String TAG = "DBAdapter";

    // Fields:
    public static final String KEY_ROWID = "_id";
    public static final String KEY_WORD = "word";
    public static final String KEY_TRANSLATION = "translation";

    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_WORD, KEY_TRANSLATION};

    // Column numbers:
    public static final int COL_ROWID = 0;
    public static final int COL_WORD = 1;
    public static final int COL_TRANSLATE = 2;

    // DataBase info:
    public static final String DATABASE_NAME = "dbQM";
    public static final String DATABASE_TABLE = "mainFiszki";
    public static final int DATABASE_VERSION = 1; // wersja bazy ++.

    //SQL statement to create database
    private static final String DATABASE_CREATE_SQL =
            "CREATE TABLE " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_WORD + " TEXT NOT NULL, "
                    + KEY_TRANSLATION + " TEXT NOT NULL"
                    + ");";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter open()
    {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        myDBHelper.close();
    }

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    public long insertRow(String word, String translate)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_WORD, word);
        initialValues.put(KEY_TRANSLATION, translate);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public Cursor getAllRows()
    {
        String where = null;
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if (c != null)
        {
            c.moveToFirst();
        }
        return c;
    }

    //TODO: to sie sypnie jak usune jakis wiersz
    public int rowCount()
    {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public Cursor getRandomRow()
    {
        Random rand = new Random();
        int rowId = rand.nextInt(rowCount())+1;
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db)
        {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            onCreate(_db);
        }
    }
}

