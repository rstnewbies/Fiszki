package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mBoiler on 04.04.2017.
 */

public class AccountController {

    private static final String ACCOUNT= "account";
    private SharedPreferences mAccountPreferences;
    private SharedPreferences.Editor mAccountEditor;
    private Activity mActivity;

    public AccountController(Activity activity) {
        mActivity = activity;
        mAccountPreferences = activity.getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        mAccountEditor = mAccountPreferences.edit();
    }

    public void set(String pass){
        mAccountEditor.clear();
        mAccountEditor.putString(ACCOUNT, pass);
        mAccountEditor.commit();
    }

    public void clear(){
        mAccountEditor.clear();
        mAccountEditor.putString(ACCOUNT, null);
        mAccountEditor.commit();
    }

    public String getStatus(){
        return  mAccountPreferences.getString(ACCOUNT, null);
    }
}
