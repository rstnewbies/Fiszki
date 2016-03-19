package eu.qm.fiszki.database;

public class DBStatus {

    public void openDB(DBAdapter dataAdapter) {
        dataAdapter.open();
    }

    public void closeDB(DBAdapter dataAdapter) {
        dataAdapter.close();
    }
}
