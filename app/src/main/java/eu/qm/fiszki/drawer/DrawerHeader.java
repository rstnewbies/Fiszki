package eu.qm.fiszki.drawer;

import android.app.Activity;

import com.mikepenz.materialdrawer.AccountHeaderBuilder;

import eu.qm.fiszki.R;

/**
 * Created by Siusiacz on 06.07.2016.
 */
public class DrawerHeader extends AccountHeaderBuilder {

    public DrawerHeader(Activity activity) {
        this.withActivity(activity);
        this.withHeaderBackground(R.drawable.header_background);
    }
}
