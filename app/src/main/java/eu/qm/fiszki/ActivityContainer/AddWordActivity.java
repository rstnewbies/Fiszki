package eu.qm.fiszki.ActivityContainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBOperations;
import eu.qm.fiszki.DataBaseContainer.DBStatus;
import eu.qm.fiszki.R;

public class AddWordActivity extends AppCompatActivity {

    EditText inputWord, inputTranslation;
    DBAdapter myDb = new DBAdapter(this);
    DBOperations myDbo;
    DBStatus OpenDataBase = new DBStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        inputWord = (EditText) findViewById(R.id.inputWord);
        inputTranslation = (EditText) findViewById(R.id.inputTranslation);
        OpenDataBase.openDB(myDb);
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
            if (!TextUtils.isEmpty(inputWord.getText()) || !TextUtils.isEmpty(inputTranslation.getText())) {
                myDbo.insertRow(inputWord.getText().toString(), inputTranslation.getText().toString());
            }
            inputWord.setText(null);
            inputTranslation.setText(null);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}