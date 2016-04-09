package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Category;
import eu.qm.fiszki.model.CategoryRepository;
import eu.qm.fiszki.model.FlashcardRepository;

/**
 * Created by mBoiler on 08.04.2016.
 */
public class ShowCategoryAdapter extends ArrayAdapter<Category> {

    ArrayList arrayList;
    Context context;
    int rLayout;
    CheckBox checkBox;
    ArrayList<Category> choosenCategory;
    FlashcardRepository flashcardRepository;

    public ShowCategoryAdapter(Context context, int resource, ArrayList<Category> arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
        this.context = context;
        this.rLayout = resource;
        choosenCategory = new ArrayList<>();
        flashcardRepository = new FlashcardRepository(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Category category = (Category) arrayList.get(position);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(rLayout, parent, false);
        checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
        if(category.getId()==1){
            checkBox.setText(R.string.add_new_word_no_category);
        }else {
            checkBox.setText(category.getCategory());
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    choosenCategory.add(category);
                } else {
                   choosenCategory.remove(category);
                }
            }
        });
        return convertView;
    }

    public ArrayList<Category> getChoosenCategory(){
        return choosenCategory;
    }

}
