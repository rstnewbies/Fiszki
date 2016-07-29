package eu.qm.fiszki.myWords.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.MainActivity;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.myWords.flashcards.FlashcardsActivity;

public class CategoryActivity extends AppCompatActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        mActivity = this;
        buildToolbar();
        buildFAB();

        ArrayList<Category> arrayList = new ArrayList<>();
        Category one = new Category(1, "", false, false);
        Category two = new Category(2, "", false, false);
        Category three = new Category(3, "", false, false);
        arrayList.add(one);
        arrayList.add(two);

        RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.listview);

        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);

        CategoryShowAdapter adapter = new CategoryShowAdapter(mActivity,arrayList);
        mRecycleView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        mActivity.startActivity(new Intent(mActivity, FlashcardsActivity.class));
        mActivity.finish();
    }

    private void buildFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void buildToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.md_nav_back);
        toolbar.setTitle("Asdasd");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,MainActivity.class));
                mActivity.finish();
            }
        });
    }
}
