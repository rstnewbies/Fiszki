package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.view.View;

import com.apptentive.android.sdk.Apptentive;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.R;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class Contact extends PrimaryDrawerItem {

    public Contact(final Activity activity) {
        this.withName(R.string.drawer_contact_name);
        this.withIcon(R.drawable.send);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Apptentive.showMessageCenter(activity);
                return false;
            }
        });
    }
}
