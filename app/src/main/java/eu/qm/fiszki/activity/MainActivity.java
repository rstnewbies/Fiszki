package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import eu.qm.fiszki.ListPopulate;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.database.DBTransform;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.toolbar.ToolbarAfterClick;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;


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
    static public Flashcard selectedFlashcard;
    static public Category selectedCategory;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    ToolbarAfterClick toolbarAfterClick;
    ToolbarMainActivity toolbarMainActivity;
    FlashcardRepository flashcardRepository;
    DBTransform transform;
    CategoryRepository categoryRepository;
    Activity activity;
    ListPopulate listPopulate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        openDataBase = new DBStatus();
        myDb = new DBAdapter(this);
        context = this;
        expandableListView = (ExpandableListView) findViewById(R.id.list);
        openDataBase.openDB(myDb);
        flashcardRepository = new FlashcardRepository(context);
        categoryRepository = new CategoryRepository(context);
        sharedPreferences = getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        toolbarAfterClick = new ToolbarAfterClick(activity);
        toolbarMainActivity = new ToolbarMainActivity(activity);
        listPopulate = new ListPopulate(activity);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(myIntent);
            }
        });

        toolbarMainActivity.set();
        selectionFlashcard();
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
        listPopulate.populate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void selectionFlashcard() {
        expandableListView.setLongClickable(true);
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    selectedFlashcard =
                            listPopulate.adapterExp.getFlashcard(groupPosition, childPosition);

                    selectedType = typeFlashcard;

                }
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    selectedCategory =
                            listPopulate.adapterExp.getCategory(groupPosition);
                    selectedType = typeCategory;

                }

                if (selectedView == view) {
                    toolbarMainActivity.set();
                    fab.show();
                    selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
                    selectedView = null;

                } else {
                    if (selectedView != null) {
                        selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
                    }
                    selectedView = view;
                    selectedView.setBackgroundColor(activity.getResources().getColor(R.color.pressed_color));
                    toolbarAfterClick.set(selectedCategory, selectedFlashcard, selectedType, selectedView, listPopulate);
                    fab.hide();
                }
                return true;
            }
        });

        expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (selectedView != null) {
                    selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
                    selectedView = null;
                    toolbarMainActivity.set();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (selectedView != null) {
                    selectedView.setBackgroundColor(activity.getResources().getColor(R.color.default_color));
                    selectedView = null;
                    toolbarMainActivity.set();
                }
                return false;
            }
        });

    }
}

