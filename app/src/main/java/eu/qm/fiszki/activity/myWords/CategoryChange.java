package eu.qm.fiszki.activity.myWords;

/**
 * Created by tm on 19.07.16.
 */
public class CategoryChange {

    private static CategoryChange ourInstance = new CategoryChange();

    public static CategoryChange getInstance() {
        return ourInstance;
    }

    public static int clickedCategoryID;

    private CategoryChange() {

    }
}
