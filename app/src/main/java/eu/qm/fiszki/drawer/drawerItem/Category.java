package eu.qm.fiszki.drawer.drawerItem;

import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import eu.qm.fiszki.R;

/**
 * Created by tm on 08.07.16.
 */
public class Category extends ExpandableDrawerItem {

    public Category() {
        this.withName(R.string.drawer_category_name);
        this.withIcon(R.drawable.ic_category);
        this.withSubItems(addNewCategoryItem());
    }

    private ArrayList<IDrawerItem> addNewCategoryItem() {

        ArrayList<IDrawerItem> categories = new ArrayList<IDrawerItem>();
        categories.add(new PrimaryDrawerItem().withName("Brytyjski - Afrykański").withLevel(3).withIcon(R.drawable.ic_exit_to_app_black_24px).withBadge("4").withDescription("B1 Dom"));
        categories.add(new PrimaryDrawerItem().withName("Polski - Niemiecki").withLevel(3).withIcon(R.drawable.ic_exit_to_app_black_24px).withBadge("4").withDescription("Słowa które nie wiem."));
        return categories;
    }


}
