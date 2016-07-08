package eu.qm.fiszki.drawer;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.ContainerDrawerItem;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.MiniDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.xmlpull.v1.sax2.Driver;

import eu.qm.fiszki.R;
import eu.qm.fiszki.drawer.drawerItem.AppMode;
import eu.qm.fiszki.drawer.drawerItem.Category;
import eu.qm.fiszki.drawer.drawerItem.Exit;
import eu.qm.fiszki.drawer.drawerItem.Options;
import eu.qm.fiszki.drawer.drawerItem.SwitchNotyfication;
import eu.qm.fiszki.drawer.drawerItem.Version;

public class DrawerMain extends DrawerBuilder {


    public DrawerMain(Activity activity, Toolbar toolbar) {
        this.withActivity(activity);
        this.withToolbar(toolbar);
        this.withAccountHeader(new DrawerHeader(activity).build());
        this.withSelectedItem(-1);
        this.addDrawerItems(new Category());
        this.addDrawerItems(new AppMode(activity));
        this.addDrawerItems(new DividerDrawerItem());
        this.addDrawerItems(new Options(activity));
        this.addDrawerItems(new Exit(activity));
        this.addDrawerItems(new Version(activity));
        this.addStickyDrawerItems(new SwitchNotyfication(activity.getBaseContext()));
    }

    private void addCategoryItems() {

    }

}
