package eu.qm.fiszki.myWords.flashcards;

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
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.myWords.category.CategoryActivity;

public class FlashcardsActivity extends AppCompatActivity {

    private Activity mActivity;
    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcards_activity);
        mActivity = this;
        buildToolbar();
        buildFAB();
        buildListView();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            updateListView();
        }
    }

    @Override
    public void onBackPressed() {
        mActivity.startActivity(new Intent(mActivity,CategoryActivity.class));
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.right_out,R.anim.left_in);
    }

    private void buildFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plusbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                onBackPressed();
            }
        });
    }

    private void buildListView() {
        mRecycleView = (RecyclerView) findViewById(R.id.listview);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);
    }

    private void updateListView() {
        ArrayList<Flashcard> arrayList = new ArrayList<>();
        Flashcard one = new Flashcard();
        Flashcard two = new Flashcard();
        arrayList.add(one);
        arrayList.add(two);
        FlashcardShowAdapter adapter = new FlashcardShowAdapter(mActivity,arrayList);
        mRecycleView.swapAdapter(adapter,false);
    }
}
