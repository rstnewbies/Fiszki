package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.view.MaterialListView;

import java.util.ArrayList;

import eu.qm.fiszki.CategoryShowAdapter;
import eu.qm.fiszki.R;
import eu.qm.fiszki.dialogs.AddCategoryDialog;
import eu.qm.fiszki.model.Category;

public class MyCategoryActivity extends AppCompatActivity {

    private Activity mActivity;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_category);
        init();
        buildToolbar();
        populateListView();
    }

    private void init() {
        mActivity = this;
        mListView = (ListView) findViewById(R.id.category_listview);
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMainActivity = new Intent(mActivity, MainActivity.class);
                mActivity.startActivity(goMainActivity);
                mActivity.finish();
            }
        });
    }

    public void addCategoryClick(View view) {
        new AddCategoryDialog(mActivity).show();
    }

    public void populateListView() {
        ArrayList<Category> arrayList = new ArrayList<>();
        Category one = new Category("", false, false);

        arrayList.add(one);
        arrayList.add(one);
        arrayList.add(one);
        arrayList.add(one);
        arrayList.add(one);
        arrayList.add(one);
        arrayList.add(one);
        arrayList.add(one);
        arrayList.add(one);

        CategoryShowAdapter adapter = new CategoryShowAdapter(this,R.layout.category_show_adapter,arrayList);
        mListView.setAdapter(adapter);
    }
}
