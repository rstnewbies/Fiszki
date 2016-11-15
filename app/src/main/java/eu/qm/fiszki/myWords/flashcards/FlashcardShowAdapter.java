package eu.qm.fiszki.myWords.flashcards;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.dialogs.EditAndDeleteFlashcardDialog;
import eu.qm.fiszki.model.flashcard.Flashcard;

/**
 * Created by tm on 15.07.16.
 */
public class FlashcardShowAdapter extends RecyclerView.Adapter<FlashcardShowAdapter.ViewHolder> {

    private ArrayList<Flashcard> mArrayList;
    private Activity mActivity;

    public FlashcardShowAdapter(Activity activity, ArrayList<Flashcard> arrayList) {
        mArrayList = arrayList;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcards_show_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Flashcard flashcard = mArrayList.get(position);

        holder.mWord.setText(flashcard.getWord());
        holder.mTranslation.setText(flashcard.getTranslation());

        holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditAndDeleteFlashcardDialog(mActivity,flashcard).show();
            }
        });

        holder.mMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        private RelativeLayout mMain;
        private TextView mTranslation;
        private TextView mWord;
        private ImageButton mEditBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mMain = (RelativeLayout) itemView.findViewById(R.id.mainCard);
            mTranslation = (TextView) itemView.findViewById(R.id.flashcard_translate);
            mWord = (TextView) itemView.findViewById(R.id.flashcard_word);
            mEditBtn = (ImageButton) itemView.findViewById(R.id.editBtn);
        }
    }
}