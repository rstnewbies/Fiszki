package eu.qm.fiszki.activity.exam;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.StreamHandler;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ChangeActivityManager;
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.flashcard.Flashcard;

public class ExamBadAnswerActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static ArrayList mBadAnswer;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_bad_answer);
        this.mActivity = this;
        this.mBadAnswer = (ArrayList)
                getIntent().getSerializableExtra(ChangeActivityManager.EXAM_BAD_ANSWER_KEY_INTENT);
        buildToolbar();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new ChangeActivityManager(mActivity).exitExamBadAnswer();
    }

    public void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.exam_bad_answer_toolbar_title);
        toolbar.setNavigationIcon(R.drawable.md_nav_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String POSITION = "POSITION";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(POSITION, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_exam_bad_answer, container, false);
            TextView cunt = (TextView) rootView.findViewById(R.id.exam_bad_answer_cunt);
            TextView word = (TextView) rootView.findViewById(R.id.exam_bad_answer_word);
            TextView answer = (TextView) rootView.findViewById(R.id.exam_bad_answer_uncorrect);
            TextView correct = (TextView) rootView.findViewById(R.id.exam_bad_answer_correct);

            cunt.setText((getArguments().getInt(POSITION)+1)+
                    " "+ getString(R.string.exam_bad_answer_cunt)+" "+mBadAnswer.size());
            word.setText(((Flashcard)(((ArrayList)mBadAnswer.get(getArguments().getInt(POSITION)))
                    .get(0))).getWord());
            answer.setText((String)((ArrayList)mBadAnswer.get(getArguments().getInt(POSITION)))
                    .get(1));
            correct.setText(((Flashcard)(((ArrayList)mBadAnswer.get(getArguments().getInt(POSITION)))
                    .get(0))).getTranslation());
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mBadAnswer.size();
        }
    }
}
