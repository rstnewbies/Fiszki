package eu.qm.fiszki.myWords.Flashcards;

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

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.myWords.NoSwipeView;

/**
 * Created by tm on 27.07.16.
 */
public class FlashcardFragment extends Fragment {

    private RecyclerView mRecycleView;
    public NoSwipeView viewPager;

    public FlashcardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_words, container, false);
        mRecycleView = (RecyclerView) rootView.findViewById(R.id.listview);
        viewPager = (NoSwipeView) container;
        populateListView();
        return rootView;
    }

    private void populateListView() {

        ArrayList<Category> arrayList = new ArrayList<>();
        Category button = new Category("", false, false);

        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);
        arrayList.add(button);

        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mStaggeredLayoutManager);

        FlashcardShowAdapter adapter = new FlashcardShowAdapter(arrayList,viewPager);
        mRecycleView.setAdapter(adapter);
    }
}
