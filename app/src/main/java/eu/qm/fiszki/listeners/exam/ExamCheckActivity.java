package eu.qm.fiszki.listeners.exam;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ChangeActivityManager;

public class ExamCheckActivity extends AppCompatActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_check);

        init();
        buildToolbar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new ChangeActivityManager(mActivity).exitExamCheck();
    }

    private void init() {
        this.mActivity = this;
    }

    private void buildToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

}
