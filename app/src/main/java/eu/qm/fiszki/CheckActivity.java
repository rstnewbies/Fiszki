package eu.qm.fiszki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

//// TODO: 2015-10-03 Dodanie akceptacji słowa Enterem 
public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
    }
    //Do "WordFromData" wprowadzi sie dane z bazy danych.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String wordFromData = "Gall";
        EditText enteredWord = (EditText)findViewById(R.id.EnteredWord);
        CheckerClass investigator = new CheckerClass();
        AlertClass message = new AlertClass();

        if (id == R.id.action_OK) {
           if(investigator.Check(wordFromData,enteredWord.getText().toString())){
                message.Pass(this,getString(R.string.alert_message_pass),getString(R.string.alert_title_pass),getString(R.string.alert_nameButton_OK));
           }
           else{
                message.Fail(this,wordFromData,getString(R.string.alert_message_fail),getString(R.string.alert_title_fail),getString(R.string.alert_nameButton_OK));
           }
        }
        return super.onOptionsItemSelected(item);
    }
}
