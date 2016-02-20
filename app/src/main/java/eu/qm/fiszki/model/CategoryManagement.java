package eu.qm.fiszki.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;

import eu.qm.fiszki.database.DBHelper;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class CategoryManagement {

    private DBHelper dbHelper;
    private RuntimeExceptionDao<Category, Integer> categoryDao;
    private ArrayList<Category> categoryList;

    public CategoryManagement(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        categoryDao = dbHelper.getCategoryDao();
    }

    public ArrayList<Category> getAllCategory() {
        return (ArrayList<Category>) categoryDao.queryForAll();
    }

    public void addCategory(Category category) {
        categoryDao.createIfNotExists(category);
    }

    public Category getCategoryByName(String name) {
        categoryList = (ArrayList<Category>) categoryDao.queryForAll();
        for (Category categories : categoryList) {
            if (categories.getCategory().equals(name)) {
                return categories;
            }
        }
        return null;
    }

    public boolean existCategory(int id) {
        if (getCategoryByID(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Category getCategoryByID(int id) {
        categoryList = (ArrayList<Category>) categoryDao.queryForAll();
        for (Category categories : categoryList) {
            if (categories.getId() == id) {
                return categories;
            }
        }
        return null;
    }

    public ArrayList<Category> getUserCategory(){
        ArrayList<Category> categories = new ArrayList<Category>();
        categoryList = (ArrayList<Category>) categoryDao.queryForAll();
        for (Category category:categoryList) {
            if(category.getId()!=1){
                if(category.getId()!=2) {
                    categories.add(category);
                }
            }
        }
        return categories;
    }
}
