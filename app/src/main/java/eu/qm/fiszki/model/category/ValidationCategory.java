package eu.qm.fiszki.model.category;

import android.content.Context;
import android.widget.Toast;

import eu.qm.fiszki.R;

/**
 * Created by mBoiler on 12.11.2016.
 */

public class ValidationCategory {

    private Context mContext;
    //private CategoryRepository mCategoryRepository;

    public ValidationCategory(Context context) {
        this.mContext = context;
        //this.mCategoryRepository = new CategoryRepository(mContext);
    }

    public boolean validate(Category category){
        if(category.getCategoryDB().isEmpty()){
            Toast.makeText(mContext, R.string.validation_category_empty,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
