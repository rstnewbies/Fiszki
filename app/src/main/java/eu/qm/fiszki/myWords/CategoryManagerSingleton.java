package eu.qm.fiszki.myWords;

/**
 * Created by tm on 28.07.16.
 */
public class CategoryManagerSingleton {

    private static CategoryManagerSingleton ourInstance = new CategoryManagerSingleton();

    public static CategoryManagerSingleton getInstance() {
        return ourInstance;
    }

    public static int clickedCategoryId;

    private CategoryManagerSingleton() {
    }

    public static int getClickedCategoryId() {
        return clickedCategoryId;
    }

    public static void setClickedCategoryId(int clickedCategoryId) {
        CategoryManagerSingleton.clickedCategoryId = clickedCategoryId;
    }
}
