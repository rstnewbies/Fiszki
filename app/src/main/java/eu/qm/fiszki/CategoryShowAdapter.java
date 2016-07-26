package eu.qm.fiszki;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import eu.qm.fiszki.model.Category;

/**
 * Created by tm on 15.07.16.
 */
public class CategoryShowAdapter extends RecyclerView.Adapter<CategoryShowAdapter.ViewHolder> {

    private final ArrayList<Category> arrayList;
    private final Activity activity;
    private final NoSwipeView mViewPager;

    public CategoryShowAdapter(Activity activity, ArrayList<Category> arrayList, NoSwipeView viewPager) {
        this.arrayList = arrayList;
        this.activity = activity;
        mViewPager = viewPager;
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
                    mViewPager.setCurrentItem(2);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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