package eu.qm.fiszki.activity;

/**
 * Created by bgood on 2016-04-14.
 */
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import eu.qm.fiszki.R;
import eu.qm.fiszki.tutorial.ColorPage;

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        findViewById(R.id.bRetry).setOnClickListener(this);

        if (savedInstanceState == null) {
            replaceTutorialFragment();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRetry:
                replaceTutorialFragment();
                break;
        }
    }

    public void replaceTutorialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new ColorPage());
        fragmentTransaction.commit();
    }
}
