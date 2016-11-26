package eu.qm.fiszki.activity.myWords;

/**
 * Created by tm on 28.07.16.
 */
public class CategoryManagerSingleton {

    private static CategoryManagerSingleton ourInstance = new CategoryManagerSingleton();

    public static CategoryManagerSingleton getInstance() {
        return ourInstance;
    }

    private static int currentCategoryId;

    private CategoryManagerSingleton() {
    }

    public static int getCurrentCategoryId() {
        return currentCategoryId;
    }

    public static void setCurrentCategoryId(int currentCategoryId) {
        CategoryManagerSingleton.currentCategoryId = currentCategoryId;
    }
}
