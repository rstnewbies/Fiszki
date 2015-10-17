package eu.qm.fiszki;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//// TODO: 2015-10-03 Dodanie akceptacji słowa Enterem 
public class CheckActivity extends AppCompatActivity {

    DBAdapter myDb;
    TextView word;
    EditText enteredWord;
    String wordFromData;
    String expectedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        openDB();
        Cursor c = myDb.getRandomRow();
            wordFromData = c.getString(c.getColumnIndex(myDb.KEY_WORD));
            expectedWord = c.getString(c.getColumnIndex(myDb.KEY_TRANSLATION));
            enteredWord = (EditText) findViewById(R.id.EnteredWord);
            word = (TextView) findViewById(R.id.textView3);
            word.append(wordFromData);
        enteredWord.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
    }

    private void openDB()
    {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        AlertClass message = new AlertClass();
        CheckerClass check = new CheckerClass();
        if (id == R.id.action_OK)
        {
           if(check.Check(expectedWord, enteredWord.getText().toString()))
           {
                message.Pass(this,getString(R.string.alert_message_pass),getString(R.string.alert_title_pass),getString(R.string.alert_nameButton_OK));
               finish();
           }
           else
           {
                message.Fail(this,expectedWord,getString(R.string.alert_message_fail),getString(R.string.alert_title_fail),getString(R.string.alert_nameButton_OK));
           }
        }
        return super.onOptionsItemSelected(item);
    }
}
