package eu.qm.fiszki.myWords.Flashcards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.myWords.NoSwipeView;

/**
 * Created by tm on 15.07.16.
 */
public class FlashcardShowAdapter extends RecyclerView.Adapter<FlashcardShowAdapter.ViewHolder> {

    private ArrayList<Category> mArrayList;
    private NoSwipeView mView;

    public FlashcardShowAdapter(ArrayList<Category> arrayList, NoSwipeView view) {
        mArrayList = arrayList;
        mView = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_add_button, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_show_adapter, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(position!=0) {
            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout main;

        public ViewHolder(View itemView) {
            super(itemView);
                main = (RelativeLayout) itemView.findViewById(R.id.mainCard);
        }
    }
}