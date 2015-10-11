package eu.qm.fiszki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    TimerClass timer = new TimerClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer.start(this,5000,getString(R.string.notification_message),getString(R.string.notification_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, CheckActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void dodajNoweSlowko(View view) {
            Intent myIntent = new Intent(MainActivity.this, Add_Word.class);
            startActivity(myIntent);

    }
}
