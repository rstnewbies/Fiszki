package eu.qm.fiszki;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import eu.qm.fiszki.ActivityContainer.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    private  Switch notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        notificationSwitch.setChecked(true);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                 // MainActivity alarm = new MainActivity();
                  //alarm.notOff();
                    Toast.makeText(getApplicationContext(), "Wylaczony switch", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Wylaczony switch", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //domyślnie po uruchomieniu
        if(notificationSwitch.isChecked()){
           // MainActivity alarm = new MainActivity();
           // alarm.notOff();
            Toast.makeText(getApplicationContext(), "Wylaczony switch", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Wylaczony switch", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent goHome = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(goHome);
        }
        return super.onOptionsItemSelected(item);
    }

}
