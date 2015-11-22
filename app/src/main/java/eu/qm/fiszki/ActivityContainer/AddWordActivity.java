package eu.qm.fiszki.ActivityContainer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import eu.qm.fiszki.AlarmReceiverClass;
import eu.qm.fiszki.AlertClass;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;
import eu.qm.fiszki.DataBaseContainer.DBStatus;
import eu.qm.fiszki.R;
import eu.qm.fiszki.SettingsActivity;


public class AddWordActivity extends AppCompatActivity {

    EditText inputWord, inputTranslation;
    DBAdapter myDb = new DBAdapter(this);
    DBStatus OpenDataBase = new DBStatus();
    SettingsActivity settings = new SettingsActivity();
    AlertClass alert = new AlertClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        inputWord = (EditText) findViewById(R.id.inputWord);
        inputWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        inputWord.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        inputTranslation = (EditText) findViewById(R.id.inputTranslation);
        inputTranslation.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        OpenDataBase.openDB(myDb);
        settings.alarmIntent = new Intent(this, AlarmReceiverClass.class);
        settings.pendingIntent = PendingIntent.getBroadcast(this, 0, settings.alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        settings.manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        settings.alarm = new AlarmReceiverClass();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addword, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_new_word) {
            if (inputWord.getText().toString().isEmpty() || inputTranslation.getText().toString().isEmpty()){
                alert.buildAlert(getString(R.string.alert_title), getString(R.string.alert_message_onEmptyFields), getString(R.string.action_OK), AddWordActivity.this);
            }
            else if (myDb.getRowValue(DBModel.KEY_WORD, inputWord.getText().toString()) == true){
                alert.buildAlert(getString(R.string.alert_title), getString(R.string.alert_message_onRecordExist), getString(R.string.action_OK), AddWordActivity.this);
                inputWord.setText(null);
                inputTranslation.setText(null);

            } else if (!TextUtils.isEmpty(inputWord.getText().toString()) && !TextUtils.isEmpty(inputTranslation.getText().toString())) {
                myDb.insertRow(inputWord.getText().toString(), inputTranslation.getText().toString());
                inputWord.setText(null);
                inputTranslation.setText(null);
                if (myDb.getAllRows().getCount() == 1) {
                    myDb.updateRow("notification", 1);
                    settings.alarm.start(settings.manager, this, settings.pendingIntent, settings.time);
                }
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}