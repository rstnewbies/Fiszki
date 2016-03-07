package eu.qm.fiszki.toolbar;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.optionsAfterSelection.DeleteCategory;
import eu.qm.fiszki.optionsAfterSelection.DeleteFlashcard;
import eu.qm.fiszki.optionsAfterSelection.EditCategory;
import eu.qm.fiszki.optionsAfterSelection.EditFlashcard;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class ToolbarSelected extends ActionBarActivity {

    Activity activity;
    Toolbar toolbar;
    ToolbarMainActivity toolbarMainActivity;
    FloatingActionButton fab;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public ToolbarSelected(Activity activity) {
        this.activity = activity;
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbarMainActivity = new ToolbarMainActivity(activity);
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);

    }

    public void set(final Category selectedCategory, final Flashcard selectedFlashcard,
                    final String selectedType, final View selectedView) {
        fab.hide();
        toolbar.getMenu().clear();
        toolbar.setTitle(activity.getString(R.string.main_activity_title_seleced_record));
        toolbar.setBackgroundResource(R.color.seleced_Adapter);
        toolbar.inflateMenu(R.menu.menu_selected_mainactivity);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.show();
                toolbarMainActivity.set();
                selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
            }
        });
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.editRecord) {
                            if (selectedType.equals(MainActivity.typeFlashcard)) {
                                EditFlashcard editFlashcard =
                                        new EditFlashcard(activity, selectedFlashcard);
                            } else {
                                EditCategory editCategory =
                                        new EditCategory(activity, selectedCategory);
                            }
                        } else if (id == R.id.deleteRecord) {
                            if (selectedType.equals(MainActivity.typeFlashcard)) {
                                DeleteFlashcard deleteFlashcard =
                                        new DeleteFlashcard(selectedFlashcard, activity);
                                toolbarMainActivity.set();
                            } else {
                                DeleteCategory deleteCategory =
                                        new DeleteCategory(selectedCategory, activity);
                                toolbarMainActivity.set();
                            }
                        }
                        return true;
                    }
                });
        toolbar.dismissPopupMenus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ToolbarSelected Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://eu.qm.fiszki.toolbar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ToolbarSelected Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://eu.qm.fiszki.toolbar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
