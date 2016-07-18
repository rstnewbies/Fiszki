package eu.qm.fiszki.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.myCategory.MyCategoryActivity;

public class FlashcardActivity extends AppCompatActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        init();
        buildToolbar();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MyCategoryActivity.class));
        finish();
    }

    private void init() {
        mActivity = this;
    }

    private void buildToolbar() {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, MyCategoryActivity.class));
                    mActivity.finish();
                }
            });
        }
    }
}
