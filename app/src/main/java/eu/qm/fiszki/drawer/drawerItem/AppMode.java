package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ExamModeActivity;
import eu.qm.fiszki.activity.LearningModeActivity;
import eu.qm.fiszki.activity.SettingsActivity;

/**
 * Created by tm on 08.07.16.
 */
public class AppMode extends PrimaryDrawerItem {

    public AppMode(Activity activity) {
        this.withName(R.string.drawer_mode_name);
        this.withIcon(R.drawable.ic_mode);
        this.withSubItems(
                new LearningMode(activity),
                new ExamMode(activity)
        );
    }
}

class LearningMode extends PrimaryDrawerItem {
    public LearningMode(final Activity activity) {
        this.withName(R.string.learning_mode_title);
        this.withLevel(2);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent goSettings = new Intent(activity, LearningModeActivity.class);
                activity.startActivity(goSettings);
                activity.finish();
                return false;
            }
        });
    }
}

class ExamMode extends PrimaryDrawerItem {
    public ExamMode(final Activity activity) {
        this.withName(R.string.exam_mode_title);
        this.withLevel(2);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Intent goSettings = new Intent(activity, ExamModeActivity.class);
                activity.startActivity(goSettings);
                activity.finish();
                return false;
            }
        });
    }
}