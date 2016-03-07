package eu.qm.fiszki.toolbar;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.optionsAfterSelection.DeleteCategory;
import eu.qm.fiszki.optionsAfterSelection.DeleteFlashcard;
import eu.qm.fiszki.optionsAfterSelection.EditCategory;
import eu.qm.fiszki.optionsAfterSelection.EditFlashcard;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class ToolbarSelected {

    Activity activity;
    Toolbar toolbar;
    ToolbarMainActivity toolbarMainActivity;
    FloatingActionButton fab;

    public ToolbarSelected(Activity activity) {
        this.activity = activity;
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbarMainActivity = new ToolbarMainActivity(activity);
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);

    }

    public void set(final Category selectedCategory, final Flashcard selectedFlashcard,
                    final String selectedType, final View selectedView) {
        fab.hide();
        toolbar.getMenu().clear();
        toolbar.setTitle(activity.getString(R.string.main_activity_title_seleced_record));
        toolbar.setBackgroundResource(R.color.seleced_Adapter);
        toolbar.inflateMenu(R.menu.menu_selected_mainactivity);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.show();
                toolbarMainActivity.set();
                selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
            }
        });
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.editRecord) {
                            if (selectedType.equals(MainActivity.typeFlashcard)) {
                                EditFlashcard editFlashcard =
                                        new EditFlashcard(activity, selectedFlashcard);
                            } else {
                                EditCategory editCategory =
                                        new EditCategory(activity, selectedCategory);
                            }
                        } else if (id == R.id.deleteRecord) {
                            if (selectedType.equals(MainActivity.typeFlashcard)) {
                                DeleteFlashcard deleteFlashcard =
                                        new DeleteFlashcard(selectedFlashcard, activity);
                                toolbarMainActivity.set();
                            } else {
                                DeleteCategory deleteCategory =
                                        new DeleteCategory(selectedCategory, activity);
                                toolbarMainActivity.set();
                            }
                        }
                        return true;
                    }
                });
        toolbar.dismissPopupMenus();
    }

}
