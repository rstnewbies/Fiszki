package eu.qm.fiszki.model.category;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.database.ORM.DBHelper;

/**
 * Created by mBoiler on 11.02.2016.
 */
public class CategoryRepository {

    public static final String addCategoryName = "ADDNEWCATEGORY";

    private Context mContext;
    private DBHelper dbHelper;
    private RuntimeExceptionDao<Category, Integer> categoryDao;

    public CategoryRepository(Context context) {
        this.mContext = context;
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        categoryDao = dbHelper.getCategoryDao();
    }

    public ArrayList<Category> getAllCategory() {
        return (ArrayList<Category>) categoryDao.queryForAll();
    }

    public void addCategory(Category category) {
        categoryDao.create(category);
    }

    public int countCategory() {
        return (int) categoryDao.countOf();
    }

    public void addSystemCategory() {
        Category firstCategory = new Category();
        firstCategory.setId(1);
        firstCategory.setCategory(mContext.getResources().getString(R.string.uncategory));
        firstCategory.setEntryByUser(false);
        categoryDao.createIfNotExists(firstCategory);
        if(!getCategoryByID(1).getCategoryDB().equals(firstCategory.getCategoryDB())){
            updateCategory(firstCategory);
        }
        //delete addCategory from version<1.7
        Category addCategory = categoryDao.queryForId(2);
        if(addCategory!=null && addCategory.getCategory().equals(addCategoryName) && !addCategory.isEntryByUser()){
            categoryDao.delete(addCategory);
        }
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

    public void deleteCategories(ArrayList<Category> categories) {
        for (Category category : categories) {
            categoryDao.delete(category);
        }
    }

    public ArrayList<Category> getChosenCategory(){
        return (ArrayList<Category>) categoryDao.queryForEq(Category.columnCategoryChosen, true);
    }

    public ArrayList<Category> getCategoryByLang(String langFrom,String langOn){
        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<Category> categoryFrom = (ArrayList<Category>) categoryDao.queryForEq(Category.columnCategoryLangFrom,langFrom);
        for (Category cat:categoryFrom){
            if(cat.getLangOn()!=null && cat.getLangOn().equals(langOn)){
                categories.add(cat);
            }
        }
        return categories;
    }

    public ArrayList<Category> getCategoryByLangFrom(String langFrom){
        return (ArrayList<Category>) categoryDao.queryForEq(Category.columnCategoryLangFrom, langFrom);
    }

    public ArrayList<Category> getCategoryByLangOn(String langOn){
        return (ArrayList<Category>) categoryDao.queryForEq(Category.columnCategoryLangOn, langOn);
    }
}
