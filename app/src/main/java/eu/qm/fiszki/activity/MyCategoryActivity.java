package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import eu.qm.fiszki.R;
import eu.qm.fiszki.dialogs.AddCategoryDialog;

public class MyCategoryActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_category);
        init();
        buildToolbar();
    }

    private void init() {
        activity = this;
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMainActivity = new Intent(activity, MainActivity.class);
                activity.startActivity(goMainActivity);
                activity.finish();
            }
        });
    }

    public void addCategoryClick(View view) {
        new AddCategoryDialog(activity).show();

    }
}
