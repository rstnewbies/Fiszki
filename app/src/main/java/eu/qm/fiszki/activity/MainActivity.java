package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.apptentive.android.sdk.Apptentive;
import com.crashlytics.android.Crashlytics;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBTransform;
import eu.qm.fiszki.database.SQL.DBAdapter;
import eu.qm.fiszki.database.SQL.DBStatus;
import eu.qm.fiszki.drawer.DrawerMain;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.FlashcardRepository;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    static private DBAdapter myDb;
    static private DBStatus openDataBase;

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private Activity activity;
    private CategoryRepository categoryRepository;
    private Drawer drawer;
    private int countBackPress;
    private FlashcardRepository flashcardRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        init();
        buildDrawer();
        buildFAB();
        buildToolbar();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            countBackPress = 0;
        } else {
            if (countBackPress == 0) {
                Toast.makeText(activity,R.string.back_press_toast,Toast.LENGTH_SHORT).show();
                countBackPress++;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Apptentive.onStart(this);
        categoryRepository.addSystemCategory();
        new DBTransform(myDb, activity);
        Apptentive.engage(this, "changelog");
        Apptentive.engage(this, "notes");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        activity = this;
        categoryRepository = new CategoryRepository(activity);
        flashcardRepository = new FlashcardRepository(activity);

        openDataBase = new DBStatus();
        myDb = new DBAdapter(activity);
        openDataBase.openDB(myDb);

        countBackPress = 0;
    }

    private void buildDrawer() {
        drawer = new DrawerMain(activity, toolbar).build();
        drawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                drawer.setSelection(-1);
                return false;
            }
        });
    }

    private void buildFAB() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void buildToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_36px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer();
            }
        });
    }

    public void myWordsCardClick(View view) {
        Intent goMyWordActivity = new Intent(this, MyCategoryActivity.class);
        startActivity(goMyWordActivity);
        finish();
    }

    public void learningCardClick(View view) {
        if (flashcardRepository.getAllFlashcards().isEmpty()) {
            Alert alert = new Alert();
            alert.buildAlert(
                    activity.getResources().getString(R.string.alert_no_category_to_choose_title),
                    activity.getResources().getString(R.string.alert_no_category_to_choose_messege),
                    activity.getResources().getString(R.string.button_action_ok),
                    activity);
        }else {
            Intent goLearningMode = new Intent(this, LearningModeActivity.class);
            startActivity(goLearningMode);
            finish();
        }
    }

    public void examCardClick(View view) {
        if (flashcardRepository.getAllFlashcards().isEmpty()) {
            Alert alert = new Alert();
            alert.buildAlert(
                    activity.getResources().getString(R.string.alert_no_category_to_choose_title),
                    activity.getResources().getString(R.string.alert_no_category_to_choose_messege),
                    activity.getResources().getString(R.string.button_action_ok),
                    activity);
        }else {
            Intent goExamMode = new Intent(this, ExamModeActivity.class);
            startActivity(goExamMode);
            finish();
        }
    }
}

