package eu.qm.fiszki.drawer;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;

import eu.qm.fiszki.drawer.drawerItem.ClearDataBase;
import eu.qm.fiszki.drawer.drawerItem.Contact;
import eu.qm.fiszki.drawer.drawerItem.Exit;
import eu.qm.fiszki.drawer.drawerItem.Frequenc;
import eu.qm.fiszki.drawer.drawerItem.SelectCategory;
import eu.qm.fiszki.drawer.drawerItem.SwitchNotyfication;
import eu.qm.fiszki.drawer.drawerItem.Tutorial;
import eu.qm.fiszki.drawer.drawerItem.Version;

public class DrawerMain extends DrawerBuilder {

    public DrawerMain(Activity activity, Toolbar toolbar) {
        this.withActivity(activity);
        this.withToolbar(toolbar);
        this.withAccountHeader(new DrawerHeader(activity).build());
        this.withSelectedItem(-1);
        this.addDrawerItems(new Frequenc(activity));
        this.addDrawerItems(new SelectCategory());
        this.addDrawerItems(new ClearDataBase(activity));
        this.addDrawerItems(new DividerDrawerItem());
        this.addDrawerItems(new Contact(activity));
        this.addDrawerItems(new Tutorial(activity));
        this.addDrawerItems(new Exit(activity));
        this.addDrawerItems(new Version(activity));
        this.addStickyDrawerItems(new SwitchNotyfication(activity));
    }
}
