package eu.qm.fiszki.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import eu.qm.fiszki.AlarmReceiver;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.BackgroundSetter;
import eu.qm.fiszki.ListPopulate;
import eu.qm.fiszki.R;
import eu.qm.fiszki.Rules;
import eu.qm.fiszki.SelectionFlashcard;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.database.DBTransform;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.optionsAfterSelection.DeleteCategory;
import eu.qm.fiszki.optionsAfterSelection.DeleteFlashcard;
import eu.qm.fiszki.optionsAfterSelection.EditCategory;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;
import eu.qm.fiszki.toolbar.ToolbarSelected;


public class MainActivity extends AppCompatActivity {

    static final public String typeCategory = "TYPECATEGORY";
    static final public String typeFlashcard = "TYPEFLASHCARD";
    static public DBAdapter myDb;
    static public DBStatus openDataBase;
    static public Context context;
    static public ExpandableListView expandableListView;
    static public FloatingActionButton fab;
    static public View selectedView;
    static public String selectedType;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    static public Flashcard selectedFlashcard;
    static public Category selectedCategory;
    BackgroundSetter backgroundSetter;
    ToolbarSelected toolbarSelected;
    ToolbarMainActivity toolbarMainActivity;
    FlashcardRepository flashcardRepository;
    ListPopulate listPopulate;
    private CategoryRepository categoryRepository;
    private Activity activity;
    DBTransform transform;
    SelectionFlashcard selectionFlashcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        openDataBase = new DBStatus();
        myDb = new DBAdapter(this);
        context = this;
        expandableListView = (ExpandableListView) findViewById(R.id.categoryList);
        openDataBase.openDB(myDb);
        listPopulate = new ListPopulate(expandableListView, this);
        flashcardRepository = new FlashcardRepository(context);
        categoryRepository = new CategoryRepository(context);
        backgroundSetter = new BackgroundSetter(activity);
        sharedPreferences = getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbarSelected = new ToolbarSelected(activity);
        toolbarMainActivity = new ToolbarMainActivity(activity);
        selectionFlashcard = new SelectionFlashcard(expandableListView,activity);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(myIntent);
            }
        });

        toolbarMainActivity.set();
        selectionFlashcard.set();
    }


    @Override
    protected void onStart() {
        super.onStart();
        categoryRepository.addSystemCategory();
        transform = new DBTransform(myDb, context);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarMainActivity.set();
        backgroundSetter.set();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

