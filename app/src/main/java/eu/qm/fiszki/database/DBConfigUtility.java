package eu.qm.fiszki.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;

public class DBConfigUtility extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[]{Category.class, Flashcard.class};

    public static void main(String[] args) throws SQLException, IOException {
    writeConfigFile("ormlite_config.txt", classes);
    }
}
