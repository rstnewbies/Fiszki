package eu.qm.fiszki;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import eu.qm.fiszki.ActivityContainer.CheckActivity;

//// TODO: 2015-10-03 Dadanie cofnięcia do MainActivity po zamknięciu allertDialog. 
public class AlertClass {

    public void Pass(Context activActivity, String message, String title,
                     String nameButton) {
        final AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(activActivity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(nameButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                CheckActivity.getInstance().finish();
            }
        });
        alertDialog.show();
    }

    public void Fail(Context activActivity,String orginalWord,String message, String title,
                     String nameButton) {
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(activActivity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message + " " + orginalWord);
        alertDialog.setButton(nameButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
