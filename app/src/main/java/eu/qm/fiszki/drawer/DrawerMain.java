package eu.qm.fiszki.drawer;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;

import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.ContainerDrawerItem;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.MiniDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.ToggleDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.xmlpull.v1.sax2.Driver;

import eu.qm.fiszki.R;

public class DrawerMain extends DrawerBuilder {

    public DrawerMain(Activity activity, Toolbar toolbar) {
        this.withActivity(activity);
        this.withToolbar(toolbar);
        this.withAccountHeader(new DrawerHeader(activity).build());
        this.withSelectedItem(-1);
        this.addDrawerItems(
                new ExpandableDrawerItem().withName("Categorie").withLevel(1).withSubItems(
                        new PrimaryDrawerItem().withName("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM").withLevel(2).withBadge("4").withBadgeStyle(new BadgeStyle().withTextColor(Color.BLUE)),
                        new PrimaryDrawerItem().withName("Francisz").withLevel(2)
                ),
                new SectionDrawerItem().withName("Version 1.6"),
                new PrimaryDrawerItem().withName("janusz").withIcon(R.drawable.ic_notification).withSubItems(
                        new PrimaryDrawerItem().withName("janusz").withLevel(1),
                        new PrimaryDrawerItem().withName("janusz").withIcon(R.drawable.ic_notification).withLevel(1)
                ),
                new DividerDrawerItem().withSubItems(
                        new PrimaryDrawerItem().withName("janusz").withLevel(1),
                        new PrimaryDrawerItem().withName("janusz").withIcon(R.drawable.ic_notification).withLevel(1)
                ),
                new ToggleDrawerItem().withName("janusz").withIcon(R.drawable.ic_notification).withSubItems(
                        new PrimaryDrawerItem().withName("janusz").withLevel(1),
                        new PrimaryDrawerItem().withName("janusz").withIcon(R.drawable.ic_notification).withLevel(1)
                ),
                new MiniDrawerItem().withName("janusz").withIcon(R.drawable.ic_notification).withSubItems(
                        new PrimaryDrawerItem().withName("janusz").withLevel(1),
                        new PrimaryDrawerItem().withName("janusz").withIcon(R.drawable.ic_notification).withLevel(1)
                )
        )
    }
}
