package eu.qm.fiszki.toolbar;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ExamModeActivity;
import eu.qm.fiszki.activity.LearningModeActivity;
import eu.qm.fiszki.activity.SettingsActivity;
import eu.qm.fiszki.activity.TutorialActivity;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class ToolbarMainActivity {

    Activity activity;
    Toolbar toolbar;
    FlashcardRepository flashcardRepository;
    Alert alert;
    FloatingActionButton fab;

    public ToolbarMainActivity(Activity activity) {
        this.activity = activity;
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        alert = new Alert();
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
    }

    public void set() {
        fab.show();
        toolbar.getMenu().clear();
        toolbar.setTitle(activity.getString(R.string.app_name));
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setBackgroundResource(R.color.ColorPrimary);
        toolbar.setNavigationIcon(null);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.settings) {
                            Intent goSettings = new Intent(activity, SettingsActivity.class);
                            activity.startActivity(goSettings);
                            activity.finish();
                        } else if (id == R.id.examMode) {
                            if (flashcardRepository.countFlashcards() > 0) {
                                Intent goLearningMode = new Intent(activity, ExamModeActivity.class);
                                activity.startActivity(goLearningMode);
                            } else {
                                alert.buildAlert(activity.getString(R.string.alert_title_fail),
                                        activity.getString(R.string.alert_learningmode_emptybase),
                                        activity.getString(R.string.button_action_ok),
                                        activity);
                            }
                        } else if (id == R.id.learningMode) {
                            if (flashcardRepository.countFlashcards() > 0) {
                                Intent goLearningMode = new Intent(activity, LearningModeActivity.class);
                                activity.startActivity(goLearningMode);
                            } else {
                                alert.buildAlert(activity.getString(R.string.alert_title_fail),
                                        activity.getString(R.string.alert_learningmode_emptybase),
                                        activity.getString(R.string.button_action_ok),
                                        activity);
                            }
                        }
                        return true;
                    }
                });
        toolbar.dismissPopupMenus();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
