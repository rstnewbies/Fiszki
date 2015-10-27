package eu.qm.fiszki.DataBaseContainer;

import eu.qm.fiszki.DataBaseContainer.DBAdapter;

public class DBStatus {

    public void openDB(DBAdapter dataAdapter)
    {
        dataAdapter.open();
    }

    public void closeDB(DBAdapter dataAdapter)
    {
        dataAdapter.close();
    }
}
