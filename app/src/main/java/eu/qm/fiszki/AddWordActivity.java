package eu.qm.fiszki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddWordActivity extends AppCompatActivity {

    EditText inputWord, inputTranslation;
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        inputWord = (EditText) findViewById(R.id.inputWord);
        inputTranslation = (EditText) findViewById(R.id.inputTranslation);
        openDB();
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
            if(!TextUtils.isEmpty(inputWord.getText()) || !TextUtils.isEmpty(inputTranslation.getText()))
            {
                myDb.insertRow(inputWord.getText().toString(), inputTranslation.getText().toString());
            }
            inputWord.setText(null);
            inputTranslation.setText(null);
            //populateListView();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDB()
    {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB()
    {
        myDb = new DBAdapter(this);
        myDb.close();
    }
}
