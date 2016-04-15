package eu.qm.fiszki.activity;

/**
 * Created by bgood on 2016-04-14.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import eu.qm.fiszki.R;
import eu.qm.fiszki.tutorial.ColorPage;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tutorial);

        if (savedInstanceState == null) {
            replaceTutorialFragment();
        }
    }

    public void replaceTutorialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new ColorPage());
        fragmentTransaction.commit();
    }
}
