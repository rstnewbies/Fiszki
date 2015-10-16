package eu.qm.fiszki.DataBaseContainer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Random;

public class DBOperations {

    private SQLiteDatabase db;

    public long insertRow(String word, String translate) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBModel.KEY_WORD, word);
        initialValues.put(DBModel.KEY_TRANSLATION, translate);

        return db.insert(DBModel.DATABASE_TABLE, null, initialValues);
    }

    public Cursor getAllRows() {
        String where = null;
        Cursor c = db.query(true, DBModel.DATABASE_TABLE, DBModel.ALL_KEYS, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    //TODO: to sie sypnie jak usune jakis wiersz
    public int rowCount() {
        String countQuery = "SELECT  * FROM " + DBModel.DATABASE_TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public Cursor getRandomRow() {
        Random rand = new Random();
        int rowId = rand.nextInt(rowCount()) + 1;
        String where = DBModel.KEY_ROWID + "=" + rowId;
        Cursor c = db.query(true, DBModel.DATABASE_TABLE, DBModel.ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}
