package eu.qm.fiszki.myWords.Category;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import eu.qm.fiszki.myWords.Flashcards.FlashcardFragment;
import eu.qm.fiszki.myWords.Flashcards.FlashcardShowAdapter;
import eu.qm.fiszki.myWords.NoSwipeView;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;

/**
 * Created by tm on 27.07.16.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView mRecycleView;
    private Activity mActivity;
    private NoSwipeView viewPager;

    public CategoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_words, container, false);
        mRecycleView = (RecyclerView) rootView.findViewById(R.id.listview);
        mActivity = (Activity) inflater.getContext();
        viewPager = (NoSwipeView) container;
        populateListView();
        return rootView;
    }

    private void populateListView() {

        ArrayList<Category> arrayList = new ArrayList<>();
        Category one = new Category(1,"", false, false);
        Category two = new Category(2,"", false, false);
        Category three = new Category(3,"", false, false);
        arrayList.add(one);
        arrayList.add(two);
        arrayList.add(three);

        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);

        CategoryShowAdapter adapter = new CategoryShowAdapter(arrayList);
        mRecycleView.setAdapter(adapter);
    }
}
