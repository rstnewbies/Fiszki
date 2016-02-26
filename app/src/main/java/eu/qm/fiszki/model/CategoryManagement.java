package eu.qm.fiszki.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import eu.qm.fiszki.database.DBHelper;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class CategoryManagement {

    private DBHelper dbHelper;
    private RuntimeExceptionDao<Category, Integer> categoryDao;
    private List<Category> categoryList;

    public CategoryManagement(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        categoryDao = dbHelper.getCategoryDao();
    }
}
