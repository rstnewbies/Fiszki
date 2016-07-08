package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.R;

/**
 * Created by tm on 08.07.16.
 */
public class Exit extends PrimaryDrawerItem {

    public Exit(final Activity activity) {
        this.withName(R.string.drawer_exit_name);
        this.withIcon(R.drawable.ic_exit_to_app_black_24px);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                activity.finish();
                return false;
            }
        });
    }
}
