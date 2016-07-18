package eu.qm.fiszki.activity.myWords.myCategory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.dialogs.AddCategoryDialog;
import eu.qm.fiszki.model.Category;

public class MyCategoryActivity extends AppCompatActivity {

    private Activity mActivity;
    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_category);
        init();
        buildToolbar();
        populateListView();
    }

    @Override
    public void onBackPressed() {
        mActivity.startActivity(new Intent(this, MainActivity.class));
        mActivity.finish();
    }

    private void init() {
        mActivity = this;
        mRecycleView = (RecyclerView) findViewById(R.id.category_listview);
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                mActivity.finish();
            }
        });
    }

    public void addCategoryClick(View view) {
        new AddCategoryDialog(mActivity).show();
    }

    public void populateListView() {

        ArrayList<Category> arrayList = new ArrayList<>();
        Category button = new Category("", false, false);

        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);

        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);

        CategoryShowAdapter adapter = new CategoryShowAdapter(this,arrayList);

        mRecycleView.setAdapter(adapter);
    }
}
