package eu.qm.fiszki.ActivityContainer;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import eu.qm.fiszki.AlertClass;
import eu.qm.fiszki.CheckerClass;
import eu.qm.fiszki.DataBaseContainer.DBAdapter;
import eu.qm.fiszki.DataBaseContainer.DBModel;
import eu.qm.fiszki.DataBaseContainer.DBStatus;
import eu.qm.fiszki.R;

//// TODO: 2015-10-03 Dodanie akceptacji słowa Enterem 
public class CheckActivity extends AppCompatActivity {

    TextView word;
    EditText enteredWord;
    DBAdapter myDb = new DBAdapter(this);
    DBStatus OpenDataBase = new DBStatus();

    String wordFromData;
    String expectedWord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        OpenDataBase.openDB(myDb);
        Cursor c = myDb.getRandomRow();
            wordFromData = c.getString(c.getColumnIndex(DBModel.KEY_WORD));
            expectedWord = c.getString(c.getColumnIndex(DBModel.KEY_TRANSLATION));
            enteredWord = (EditText) findViewById(R.id.EnteredWord);
            word = (TextView) findViewById(R.id.textView3);
            word.append(wordFromData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_check, menu);
        return true;
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
                message.Pass(this, getString(R.string.alert_message_pass), getString(R.string.alert_title_pass), getString(R.string.alert_nameButton_OK));
           }
           else
           {
                message.Fail(this,expectedWord,getString(R.string.alert_message_fail),getString(R.string.alert_title_fail),getString(R.string.alert_nameButton_OK));
           }
        }
        return super.onOptionsItemSelected(item);
    }
}
