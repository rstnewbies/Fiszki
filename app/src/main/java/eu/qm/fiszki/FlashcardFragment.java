package eu.qm.fiszki;

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

import eu.qm.fiszki.model.Category;

/**
 * Created by tm on 27.07.16.
 */
public class FlashcardFragment extends Fragment {

    private RecyclerView mRecycleView;
    private Activity mActivity;
    private NoSwipeView mViewPager;

    public FlashcardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_words, container, false);
        mRecycleView = (RecyclerView) rootView.findViewById(R.id.listview);
        mActivity = (Activity) inflater.getContext();
        mViewPager = (NoSwipeView) container;

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

        CategoryShowAdapter adapter = new CategoryShowAdapter(mActivity, arrayList, mViewPager);
        mRecycleView.setAdapter(adapter);
    }
}
