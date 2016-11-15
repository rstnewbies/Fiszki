package eu.qm.fiszki.myWords.category;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.dialogs.AddCategoryDialog;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.category.CategoryRepository;

public class CategoryActivity extends AppCompatActivity {

    private Activity mActivity;
    private RecyclerView mRecycleView;
    private CategoryRepository mCategoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        init();
        buildToolbar();
        buildFAB();
        buildList();

    }

    private void init() {
        mActivity = this;
        mCategoryRepository = new CategoryRepository(mActivity);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            updateList();
        }
    }

    @Override
    public void onBackPressed() {
        mActivity.finish();
    }

    private void buildFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddCategoryDialog(mActivity).show();
            }
        });
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.md_nav_back);
        toolbar.setTitle(R.string.category_activity_title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void buildList() {
        mRecycleView = (RecyclerView) findViewById(R.id.listview_category);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);
    }

    private void updateList() {
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(mCategoryRepository.getCategoryByID(1));
        categories.addAll(mCategoryRepository.getUserCategory());

        CategoryShowAdapter adapter = new CategoryShowAdapter(mActivity, categories);
        mRecycleView.swapAdapter(adapter, false);
    }
}
