package eu.qm.fiszki.database.SQL;

import eu.qm.fiszki.database.SQL.DBAdapter;

public class DBStatus {

    public void openDB(DBAdapter dataAdapter) {
        dataAdapter.open();
    }

    public void closeDB(DBAdapter dataAdapter) {
        dataAdapter.close();
    }
}
