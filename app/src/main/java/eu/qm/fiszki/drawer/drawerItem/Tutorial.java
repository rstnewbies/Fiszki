package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.TutorialActivity;

/**
 * Created by Siusiacz on 09.07.2016.
 */
public class Tutorial extends PrimaryDrawerItem{

    public Tutorial(final Activity activity) {
        this.withName(R.string.drawer_tutorial_name);
        this.withIcon(R.drawable.help_circle);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent goTutorial = new Intent(activity, TutorialActivity.class);
                activity.startActivity(goTutorial);
                activity.finish();
                return false;
            }
        });
    }
}
