package eu.qm.fiszki.myWords.flashcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.dialogs.AddFlashcardDialog;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.category.CategoryRepository;
import eu.qm.fiszki.model.flashcard.Flashcard;
import eu.qm.fiszki.model.flashcard.FlashcardRepository;
import eu.qm.fiszki.myWords.CategoryManager;
import eu.qm.fiszki.myWords.category.CategoryActivity;

public class FlashcardsActivity extends AppCompatActivity {

    private Activity mActivity;
    private RecyclerView mRecycleView;
    private Category mCurrentCategory;
    private CategoryRepository mCategoryRepository;
    private FlashcardRepository mFlashcardRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcards_activity);
        init();
        buildToolbar();
        buildFAB();
        buildListView();

    }

    private void init() {
        this.mActivity = this;
        mCategoryRepository = new CategoryRepository(mActivity);
        mFlashcardRepository = new FlashcardRepository(mActivity);
        mCurrentCategory = mCategoryRepository.getCategoryByID(CategoryManager.getClickedCategoryId());
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
                new AddFlashcardDialog(mActivity,mCurrentCategory.getId()).show();
            }
        });
    }

    private void buildToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.md_nav_back);
        toolbar.setTitle(mCurrentCategory.getCategory());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void buildListView() {
        mRecycleView = (RecyclerView) findViewById(R.id.listview_category);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);
    }

    private void updateListView() {
        ArrayList<Flashcard> flashcards = mFlashcardRepository.getFlashcardsByCategoryID(mCurrentCategory.getId());
        FlashcardShowAdapter adapter = new FlashcardShowAdapter(mActivity,flashcards);
        mRecycleView.swapAdapter(adapter,false);
    }
}
