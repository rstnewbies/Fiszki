package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.SettingsActivity;

/**
 * Created by tm on 08.07.16.
 */
public class Options extends PrimaryDrawerItem {

    public Options(final Activity activity) {
        this.withName(R.string.drawer_options_name);
        this.withIcon(R.drawable.ic_settings_black_24px);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent goSettings = new Intent(activity, SettingsActivity.class);
                activity.startActivity(goSettings);
                activity.finish();
                return false;
            }
        });
    }
}
