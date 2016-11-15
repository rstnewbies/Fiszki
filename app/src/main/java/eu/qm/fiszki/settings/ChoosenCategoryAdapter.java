package eu.qm.fiszki.settings;

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
import eu.qm.fiszki.model.category.Category;
import eu.qm.fiszki.model.category.CategoryRepository;

/**
 * Created by bgood on 2016-04-03.
 */



public class ChoosenCategoryAdapter extends ArrayAdapter<Category> {

    ArrayList arrayList;
    LayoutInflater inflater;
    Context context;
    int rLayout;
    CheckBox checkBox;
    CategoryRepository categoryRepository;

    public ChoosenCategoryAdapter(Context context, int resource, ArrayList<Category> arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
        this.context = context;
        this.rLayout = resource;
        categoryRepository = new CategoryRepository(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Category category = (Category) arrayList.get(position);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(rLayout, parent, false);
        checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
        if(category.getId()==1){
            checkBox.setText(R.string.add_new_flashcard_no_category);
        }else {
            checkBox.setText(category.getCategory());
        }
        if (category.isChosen()){
            checkBox.setChecked(true);
        }
        else {
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    category.setChosen(true);
                    categoryRepository.updateCategory(category);
                }else {
                    category.setChosen(false);
                    categoryRepository.updateCategory(category);
                }
            }
        });
        return convertView;
    }

}
