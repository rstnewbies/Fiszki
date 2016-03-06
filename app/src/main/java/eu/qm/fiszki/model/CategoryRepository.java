package eu.qm.fiszki.model;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.util.ArrayList;

import eu.qm.fiszki.database.DBHelper;
import eu.qm.fiszki.database.DBTransform;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class CategoryRepository {

    private DBHelper dbHelper;
    private RuntimeExceptionDao<Category, Integer> categoryDao;
    private ArrayList<Category> categoryList;

    public CategoryRepository(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        categoryDao = dbHelper.getCategoryDao();
    }

    public ArrayList<Category> getAllCategory() {
        return (ArrayList<Category>) categoryDao.queryForAll();
    }

    public void addCategory(Category category) {
        categoryDao.create(category);
    }

    public int countCategory(){
        return (int) categoryDao.countOf();
    }

    public void addSystemCategory(){
        Category addCategory = new Category(2, DBHelper.addCategoryName,false);
        categoryDao.createIfNotExists(addCategory);
        Category firstCategory = new Category(1, DBHelper.uncategory,false);
        categoryDao.createIfNotExists(firstCategory);
    }

    public Category getCategoryByName(String name) {
        ArrayList<Category> arrayList =
                (ArrayList<Category>) categoryDao.queryForEq(Category.columnCategoryCategory, name);
        if (!arrayList.isEmpty()) {
            return arrayList.get(0);
        } else {
            return null;
        }
    }

    public boolean existCategory(int id) {
        if (getCategoryByID(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Category getCategoryByID(int id) {
        ArrayList<Category> arrayList =
                (ArrayList<Category>) categoryDao.queryForEq(Category.columnCategoryId, id);
        if (!arrayList.isEmpty()) {
            return arrayList.get(0);
        } else {
            return null;
        }
    }

    public ArrayList<Category> getUserCategory() {
        return (ArrayList<Category>) categoryDao.queryForEq(Category.columnCategoryEntryByUsers, true);
    }

    public void updateCategory(Category category) {
        categoryDao.update(category);
    }

    public void deleteCategory(Category category) {
        categoryDao.delete(category);
    }
}
