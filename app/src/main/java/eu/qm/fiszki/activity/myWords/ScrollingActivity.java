package eu.qm.fiszki.activity.myWords;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.myWords.Category.CategoryShowAdapter;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



 ArrayList<Category> arrayList = new ArrayList<>();
        Category one = new Category(1,"", false, false);
        Category two = new Category(2,"", false, false);
        Category three = new Category(3,"", false, false);
        arrayList.add(one);
        arrayList.add(two);
        arrayList.add(three);
        arrayList.add(one);
        arrayList.add(two);
        arrayList.add(three);
        arrayList.add(one);
        arrayList.add(two);
        arrayList.add(three);

        RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.listview);

        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);

        CategoryShowAdapter adapter = new CategoryShowAdapter(arrayList);
        mRecycleView.setAdapter(adapter);
    }
}
