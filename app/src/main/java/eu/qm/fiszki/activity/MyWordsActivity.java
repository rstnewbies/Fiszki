package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import eu.qm.fiszki.myWords.Category.CategoryFragment;
import eu.qm.fiszki.myWords.Flashcards.FlashcardFragment;
import eu.qm.fiszki.myWords.NoSwipeView;
import eu.qm.fiszki.R;

public class MyWordsActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static NoSwipeView mViewPager;
    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_words);

        mActivity = this;

        buildToolbar();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (NoSwipeView) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }



    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem()==0) {
            startActivity(new Intent(mActivity, MainActivity.class));
            finish();
        }else{
            mViewPager.setCurrentItem(0);
        }
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new CategoryFragment();
                case 1:
                    return new FlashcardFragment();
                default:
                    throw new IllegalArgumentException("Wrong position");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Category";
                case 1:
                    return "Flashcards";
            }
            return null;
        }
    }
}
