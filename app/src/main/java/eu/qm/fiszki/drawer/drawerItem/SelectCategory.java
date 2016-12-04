package eu.qm.fiszki.drawer.drawerItem;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.dialogs.category.SelectCategoryDialog;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.category.CategoryRepository;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class SelectCategory extends PrimaryDrawerItem {

    CategoryRepository categoryRepository;
    FlashcardRepository flashcardRepository;

    public SelectCategory(final Activity activity) {
        categoryRepository = new CategoryRepository(activity);
        flashcardRepository = new FlashcardRepository(activity);

        final ArrayList<Category> categoryToPopulate = new ArrayList<Category>();
        categoryToPopulate.add(categoryRepository.getCategoryByID(1));
        categoryToPopulate.addAll(categoryRepository.getUserCategory());

        this.withName(R.string.drawer_select_name);
        this.withDescription(R.string.drawer_select_sub);
        this.withIcon(R.drawable.ic_category_select);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (flashcardRepository.getAllFlashcards().isEmpty()) {
                    new Alert().addFiszkiToFeature(activity).show();
                } else {
                    new SelectCategoryDialog(activity,categoryToPopulate).show();
                }
                return false;
            }
        });
    }
}
