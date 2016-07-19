package eu.qm.fiszki.activity.myWords;

/**
 * Created by tm on 19.07.16.
 */
public class CategoryChange {

    public static int clickedCategoryID;

    private static CategoryChange ourInstance = new CategoryChange();

    private CategoryChange() {

    }

    public static CategoryChange getInstance() {
        return ourInstance;
    }

    public static int get() {
        return clickedCategoryID;
    }

    public static void set(int clickedCategoryID) {
        CategoryChange.clickedCategoryID = clickedCategoryID;
    }


}
