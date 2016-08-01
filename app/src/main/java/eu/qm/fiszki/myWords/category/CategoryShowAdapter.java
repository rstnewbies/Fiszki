package eu.qm.fiszki.myWords.category;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.myWords.CategoryManager;
import eu.qm.fiszki.myWords.flashcards.FlashcardsActivity;

/**
 * Created by tm on 15.07.16.
 */
public class CategoryShowAdapter extends RecyclerView.Adapter<CategoryShowAdapter.ViewHolder> {

    private ArrayList<Category> mArrayList;
    private Activity mActivity;
    private FloatingActionButton fab;

    public CategoryShowAdapter(Activity activity, ArrayList<Category> arrayList) {
        mArrayList = arrayList;
        mActivity = activity;
        fab = (FloatingActionButton) activity.findViewById(R.id.deleteFAB);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_show_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryManager.setClickedCategoryId(mArrayList.get(position).getId());
                mActivity.startActivity(new Intent(mActivity, FlashcardsActivity.class));
                mActivity.finish();
                mActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        holder.main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fab.show();
                return true;
            }
        });
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

        private CardView main;

        public ViewHolder(View itemView) {
            super(itemView);
            main = (CardView) itemView.findViewById(R.id.placeCard);
        }
    }
}