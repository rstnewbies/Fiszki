package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.content.Context;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.R;

/**
 * Created by tm on 08.07.16.
 */
public class SwitchNotyfication extends SwitchDrawerItem {

    public SwitchNotyfication(final Context context) {
        this.withName(R.string.drawer_notyfication_switch_name);
        this.withIcon(R.drawable.ic_notifications_black_24dp);
        this.withOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context, context.getString(R.string.drawer_notyfication_switch_toast_on), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.drawer_notyfication_switch_toast_off), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
