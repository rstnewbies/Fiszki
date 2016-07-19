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
import eu.qm.fiszki.activity.myWords.category.CategoryActivity;
import eu.qm.fiszki.activity.myWords.flashcards.FlashcardsActivity;
import eu.qm.fiszki.database.DBTransform;
import eu.qm.fiszki.database.SQL.DBAdapter;
import eu.qm.fiszki.database.SQL.DBStatus;
import eu.qm.fiszki.drawer.DrawerMain;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.FlashcardRepository;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    static private DBAdapter mMyDb;
    static private DBStatus mOpenDataBase;

    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private Activity mActivity;
    private CategoryRepository mCategoryRepository;
    private FlashcardRepository mFlashcardRepository;
    private Drawer mDrawer;
    private int mCountBackPress;

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
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
            mCountBackPress = 0;
        } else {
            if (mCountBackPress == 0) {
                Toast.makeText(mActivity, R.string.back_press_toast, Toast.LENGTH_SHORT).show();
                mCountBackPress++;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Apptentive.onStart(this);
        mCategoryRepository.addSystemCategory();
        new DBTransform(mMyDb, mActivity);
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
        mActivity = this;
        mCategoryRepository = new CategoryRepository(mActivity);
        mFlashcardRepository = new FlashcardRepository(mActivity);

        mOpenDataBase = new DBStatus();
        mMyDb = new DBAdapter(mActivity);
        mOpenDataBase.openDB(mMyDb);

        mCountBackPress = 0;
    }

    private void buildDrawer() {
        mDrawer = new DrawerMain(mActivity, mToolbar).build();
        mDrawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                mDrawer.setSelection(-1);
                return false;
            }
        });
    }

    private void buildFAB() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    private void buildToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_36px);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer();
            }
        });
    }

    public void myWordsCardClick(View view) {
        startActivity(new Intent(this, CategoryActivity.class));
        finish();
    }

    public void learningCardClick(View view) {
        if (mFlashcardRepository.countFlashcards() == 0) {
            new Alert().addFiszkiToFeature(mActivity).show();
        } else {
            startActivity(new Intent(this, LearningModeActivity.class));
            finish();
        }
    }

    public void examCardClick(View view) {
        if (mFlashcardRepository.countFlashcards() == 0) {
            new Alert().addFiszkiToFeature(mActivity).show();
        } else {
            startActivity(new Intent(this, ExamModeActivity.class));
            finish();
        }
    }
}

