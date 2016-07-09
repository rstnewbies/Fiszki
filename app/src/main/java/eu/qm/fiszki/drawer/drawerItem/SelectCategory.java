package eu.qm.fiszki.drawer.drawerItem;

import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.R;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class SelectCategory extends PrimaryDrawerItem{

    public SelectCategory() {
        this.withName(R.string.drawer_select_name);
        this.withDescription(R.string.drawer_select_sub);
        this.withIcon(R.drawable.checkbox_multiple_marked_outline);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                return false;
            }
        });
    }
}
