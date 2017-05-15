package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.AccountController;
import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ChangeActivityManager;

/**
 * Created by mBoiler on 15.05.2017.
 */

public class Logout extends PrimaryDrawerItem {
    public Logout(final Activity activity) {
        this.withName(R.string.drawer_logout_name);
        this.withIcon(R.drawable.ic_account_key);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                new AccountController(activity).clear();
                new ChangeActivityManager(activity).resetMain();
                return false;
            }
        });
    }
}
