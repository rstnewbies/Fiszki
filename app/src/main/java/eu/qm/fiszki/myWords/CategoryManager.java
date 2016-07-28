package eu.qm.fiszki.myWords;

/**
 * Created by tm on 28.07.16.
 */
public class CategoryManager {

    private static CategoryManager ourInstance = new CategoryManager();

    public static CategoryManager getInstance() {
        return ourInstance;
    }

    public static int clickedCategoryId;

    private CategoryManager() {
    }

    public static int getClickedCategoryId() {
        return clickedCategoryId;
    }

    public static void setClickedCategoryId(int clickedCategoryId) {
        CategoryManager.clickedCategoryId = clickedCategoryId;
    }
}
