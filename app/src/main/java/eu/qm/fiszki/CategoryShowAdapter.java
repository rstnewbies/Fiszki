package eu.qm.fiszki;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import eu.qm.fiszki.model.Category;

/**
 * Created by tm on 15.07.16.
 */
public class CategoryShowAdapter extends ArrayAdapter<Category> {

    private final ArrayList<Category> arrayList;
    private final Context context;
    private final int rLayout;

    public CategoryShowAdapter(Context context, int resource, ArrayList<Category> arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
        this.context = context;
        this.rLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(rLayout, parent, false);
        return convertView;
    }
}
