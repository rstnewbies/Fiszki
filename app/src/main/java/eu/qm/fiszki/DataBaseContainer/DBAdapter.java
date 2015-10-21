package eu.qm.fiszki.DataBaseContainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

import eu.qm.fiszki.ActivityContainer.ItemAdapter;

public class DBAdapter {

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open()
    {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        myDBHelper.close();
    }

    public long insertRow(String word, String translate)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBModel.KEY_WORD, word);
        initialValues.put(DBModel.KEY_TRANSLATION, translate);
        return db.insert(DBModel.DATABASE_TABLE, null, initialValues);
    }

    public long deleteRow(String id)
    {
        return db.delete(DBModel.DATABASE_TABLE, DBModel.KEY_ROWID +"="+ id ,null);
    }

    public Cursor getAllRows()
    {
        String where = null;
        Cursor c = db.query(true, DBModel.DATABASE_TABLE, DBModel.ALL_KEYS, where, null, null, null, null, null);
        if (c != null)
        {
            c.moveToFirst();
        }
        return c;
    }

    //TODO: to sie sypnie jak usune jakis wiersz
    /*public int rowCount()
    {
        String countQuery = "SELECT  * FROM " + DBModel.DATABASE_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }*/

    public Cursor getRandomRow()
    {
        //Random rand = new Random();
        //int rowId = rand.nextInt(rowCount()) + 1;
        //String where = DBModel.KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, DBModel.DATABASE_TABLE, DBModel.ALL_KEYS,
                null, null,null, null, "RANDOM()", "1");
        if (c != null)
        {
            c.moveToFirst();
        }
        return c;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DBModel.DATABASE_NAME, null, DBModel.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db)
        {
            _db.execSQL(DBModel.DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion)
        {
            Log.w(DBModel.TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            _db.execSQL("DROP TABLE IF EXISTS " + DBModel.DATABASE_TABLE);

            onCreate(_db);
        }
    }
}

