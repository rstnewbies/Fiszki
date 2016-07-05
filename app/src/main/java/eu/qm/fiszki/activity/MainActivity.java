package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.apptentive.android.sdk.Apptentive;
import com.crashlytics.android.Crashlytics;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.shamanland.fab.ShowHideOnScroll;

import eu.qm.fiszki.ListPopulate;
import eu.qm.fiszki.R;
import eu.qm.fiszki.database.DBTransform;
import eu.qm.fiszki.database.SQL.DBAdapter;
import eu.qm.fiszki.database.SQL.DBStatus;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardRepository;
import eu.qm.fiszki.toolbar.ToolbarAfterClick;
import eu.qm.fiszki.toolbar.ToolbarMainActivity;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    static final public String typeCategory = "TYPECATEGORY";
    static final public String typeFlashcard = "TYPEFLASHCARD";
    static public Category expandedGroup;
    static public DBAdapter myDb;
    static public DBStatus openDataBase;
    static public Context context;
    static public ExpandableListView expandableListView;
    static public FloatingActionButton fab;
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
        Fabric.with(this, new Crashlytics());

        init();

        expandableListView = (ExpandableListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);


        openDataBase.openDB(myDb);

        sharedPreferences = getSharedPreferences("eu.qm.fiszki.activity", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        listPopulate = new ListPopulate(activity);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddWordActivity.class);
                startActivity(myIntent);
            }
        });
        toolbarMainActivity.set();
        selectionFlashcard();

        expandableListView.setOnTouchListener(new ShowHideOnScroll(fab) {
            @Override
            public void onScrollUp() {
                if (expandableListView.canScrollVertically(1)) {
                    super.onScrollUp();
                }
            }
        });

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.ic_arrow_back_white_24dp)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("Kutong.com").withIcon(getResources().getDrawable(R.drawable.check))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.app_name).withLevel(1)
                                .withSubItems(
                                        new PrimaryDrawerItem().withName(R.string.add_new_word_title).withLevel(2),
                                        new PrimaryDrawerItem().withName(R.string.title_activity_learning_mode).withLevel(2)
                                ),
                        new PrimaryDrawerItem().withName(R.string.app_name).withLevel(1))
                .build();

    }

    private void init() {
        activity = this;
        context = this;
        openDataBase = new DBStatus();
        myDb = new DBAdapter(context);
        toolbarAfterClick = new ToolbarAfterClick(activity);
        toolbarMainActivity = new ToolbarMainActivity(activity);
        flashcardRepository = new FlashcardRepository(context);
        categoryRepository = new CategoryRepository(context);
    }

    @Override
    public void onBackPressed() {
        if (selectedFlashcard != null || selectedCategory != null) {
            toolbarMainActivity.set();
            fab.show();
            listPopulate.populate(null, null,0);
            selectedFlashcard = null;
            selectedCategory = null;
        } else {
            this.finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Apptentive.onStart(this);
        categoryRepository.addSystemCategory();
        transform = new DBTransform(myDb, context);
        boolean shownOnlyOnce = Apptentive.engage(this, "changelog");
        boolean shown = Apptentive.engage(this, "notes");
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarMainActivity.set();
        listPopulate.populate(null, null,0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void selectionFlashcard() {
        selectedFlashcard = null;
        expandableListView.setLongClickable(true);
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    if (selectedFlashcard != null && selectedFlashcard.getId() == (listPopulate.adapterExp.getFlashcard(groupPosition, childPosition)).getId()) {
                        toolbarMainActivity.set();
                        fab.show();
                        listPopulate.populate(null, null, 0);
                        selectedFlashcard = null;
                        selectedCategory = null;
                        expandedGroup = null;
                    } else {
                        selectedFlashcard =
                                listPopulate.adapterExp.getFlashcard(groupPosition, childPosition);
                        selectedCategory = null;
                        expandedGroup = listPopulate.adapterExp.getCategory(groupPosition);
                        selectedType = typeFlashcard;
                        toolbarAfterClick.set(selectedCategory, selectedFlashcard, selectedType, listPopulate,position);
                        fab.hide();
                        listPopulate.populate(selectedFlashcard, selectedCategory,position);
                    }
                }
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    if (selectedCategory != null && selectedCategory.getId() == (listPopulate.adapterExp.getCategory(groupPosition).getId())) {
                        toolbarMainActivity.set();
                        fab.show();
                        listPopulate.populate(null, null,0);
                        selectedCategory = null;
                        selectedFlashcard = null;
                        selectedType = null;
                    } else {
                        selectedCategory = listPopulate.adapterExp.getCategory(groupPosition);
                        selectedFlashcard = null;
                        selectedType = typeCategory;
                        toolbarAfterClick.set(selectedCategory, selectedFlashcard, selectedType, listPopulate,position);
                        fab.hide();
                        listPopulate.populate(selectedFlashcard, selectedCategory,position);
                    }
                }
                return true;
            }
        });
    }
}

