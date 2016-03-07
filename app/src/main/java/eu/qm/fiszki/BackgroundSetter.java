package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import eu.qm.fiszki.activity.SettingsActivity;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 06.03.2016.
 */
public class BackgroundSetter {

    public ListPopulate listPopulate;
    CategoryRepository categoryRepository;
    FlashcardRepository flashcardRepository;
    ImageView emptyDBImage;
    TextView emptyDBText;
    ExpandableListView expandableListView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlarmReceiver alarm;
    Activity activity;

    public BackgroundSetter(Activity activity) {
        this.activity = activity;
        categoryRepository = new CategoryRepository(activity.getBaseContext());
        flashcardRepository = new FlashcardRepository(activity.getBaseContext());
        emptyDBImage = (ImageView) activity.findViewById(R.id.emptyDBImage);
        emptyDBText = (TextView) activity.findViewById(R.id.emptyDBText);
        emptyDBImage.setImageResource(R.drawable.emptydb);
        expandableListView = (ExpandableListView) activity.findViewById(R.id.categoryList);
        listPopulate = new ListPopulate(expandableListView, activity);
        sharedPreferences = activity.getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        alarm = new AlarmReceiver();
    }

    public void set() {
        if (categoryRepository.countCategory() > 2 || flashcardRepository.countFlashcards() > 0) {
            emptyDBImage.setVisibility(View.INVISIBLE);
            emptyDBText.setVisibility(View.INVISIBLE);
            expandableListView.setVisibility(View.VISIBLE);
            listPopulate.populate();
        } else {
            emptyDBImage.setVisibility(View.VISIBLE);
            emptyDBText.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.INVISIBLE);
            editor.clear();
            editor.putInt(SettingsActivity.notificationPosition, 0);
            editor.commit();
            alarm.close(activity.getBaseContext());
        }
    }

}
