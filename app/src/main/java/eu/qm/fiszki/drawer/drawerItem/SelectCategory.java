package eu.qm.fiszki.drawer.drawerItem;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.settings.ChoosenCategoryAdapter;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class SelectCategory extends PrimaryDrawerItem {

    CategoryRepository categoryRepository;
    FlashcardRepository flashcardRepository;

    public SelectCategory(final Activity activity) {
        categoryRepository = new CategoryRepository(activity);
        flashcardRepository = new FlashcardRepository(activity);

        this.withName(R.string.drawer_select_name);
        this.withDescription(R.string.drawer_select_sub);
        this.withIcon(R.drawable.checkbox_multiple_marked_outline);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (flashcardRepository.getAllFlashcards().isEmpty()) {
                    new Alert().addFiszkiToFeature(activity).show();
                } else {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.category_choose);

                    ArrayList<Category> categoryToPopulate = new ArrayList<Category>();
                    categoryToPopulate.add(categoryRepository.getCategoryByID(1));
                    categoryToPopulate.addAll(categoryRepository.getUserCategory());

                    //populate listview
                    ListView listView = (ListView) dialog.findViewById(R.id.choose_category_lv);
                    ChoosenCategoryAdapter choosenCategoryAdapter = new ChoosenCategoryAdapter(activity,
                            R.layout.layout_choose_category_adapter, categoryToPopulate);
                    listView.setAdapter(choosenCategoryAdapter);

                    //limit of height
                    if (listView.getAdapter().getCount() >= 6) {
                        RelativeLayout.LayoutParams lp =
                                (RelativeLayout.LayoutParams) listView.getLayoutParams();
                        lp.height = 1000;
                        listView.setLayoutParams(lp);
                    }

                    //click on Button
                    Button btn = (Button) dialog.findViewById(R.id.choose_category_btn);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                return false;
            }
        });
    }
}
