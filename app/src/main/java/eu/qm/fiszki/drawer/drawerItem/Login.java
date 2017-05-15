package eu.qm.fiszki.drawer.drawerItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.rengwuxian.materialedittext.MaterialEditText;

import eu.qm.fiszki.ConnectController;
import eu.qm.fiszki.R;
import eu.qm.fiszki.api.ApiCore;
import eu.qm.fiszki.model.category.CategoryRepository;

/**
 * Created by mBoiler on 11.05.2017.
 */

public class Login extends PrimaryDrawerItem {

    private Activity mActivity;

    public Login(final Activity activity) {
        mActivity = activity;

        this.withName(R.string.drawer_login_name);
        this.withIcon(R.drawable.ic_account_key);
        this.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (new ConnectController().isOnline()) {
                    new MaterialDialog.Builder(activity)
                            .title(R.string.drawer_login_name)
                            .customView(R.layout.content_login_dialog, false)
                            .positiveText(R.string.button_action_login)
                            .onPositive(login())
                            .autoDismiss(false)
                            .cancelable(true)
                            .show();
                } else {
                    new ConnectController().connectionErrorDialog(activity);
                }
                return false;
            }
        });
    }

    private MaterialDialog.SingleButtonCallback login() {
        return new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                MaterialEditText username =
                        (MaterialEditText) dialog.findViewById(R.id.login_username);
                MaterialEditText password =
                        (MaterialEditText) dialog.findViewById(R.id.login_password);

                if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    new ApiCore(mActivity).login(username.getText().toString(), password.getText().toString());
                    Toast.makeText(mActivity,R.string.logining,Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        };
    }
}
