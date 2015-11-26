package eu.qm.fiszki.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;

import eu.qm.fiszki.Alert;
import eu.qm.fiszki.Checker;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBModel;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.R;

//// TODO: 2015-10-03 Dodanie akceptacji słowa Enterem 
public class CheckActivity extends AppCompatActivity {

    TextView word;
    EditText enteredWord;
    DBAdapter myDb = new DBAdapter(this);
    DBStatus OpenDataBase = new DBStatus();

    String wordFromData;
    String expectedWord;
    int rowId;
    int rowPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        OpenDataBase.openDB(myDb);

        Cursor c = drawCardAlgorithm();
        
        int cCount = c.getCount();
        int cPosition = myDb.intRowValue(DBModel.SETTINGS_NAME, "cursorPosition");
        if(cPosition < cCount) {
            c.move(cPosition);
            cPosition++;
            myDb.updateRow("cursorPosition", cPosition);
        } else {
            cPosition = 1;
            myDb.updateRow("cursorPosition", cPosition);
        }

            wordFromData = c.getString(c.getColumnIndex(DBModel.KEY_WORD));
            expectedWord = c.getString(c.getColumnIndex(DBModel.KEY_TRANSLATION));
            rowId = c.getInt(c.getColumnIndex(DBModel.KEY_ROWID));
            rowPriority = c.getInt(c.getColumnIndex(DBModel.KEY_PRIORITY));
            enteredWord = (EditText) findViewById(R.id.EnteredWord);
            enteredWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        Alert message = new Alert();
        Checker check = new Checker();
        if (id == R.id.action_OK)
        {
           if(check.Check(expectedWord, enteredWord.getText().toString()))
           {
               message.pass(this, getString(R.string.alert_message_pass), getString(R.string.alert_title_pass), getString(R.string.alert_nameButton_OK));

               if(rowPriority<5) {
                   myDb.updateFlashcardPriority(rowId, rowPriority + 1);
               }
           }
           else
           {
            message.fail(this, expectedWord, getString(R.string.alert_message_fail),
                    getString(R.string.alert_message_tryagain), getString(R.string.alert_title_fail), getString(R.string.alert_nameButton_OK));

             myDb.updateFlashcardPriority(rowId, 1);

           }
        }
        return super.onOptionsItemSelected(item);
    }

    private Cursor drawCardAlgorithm() {
        final int[] points = {25, 20, 15, 10, 5};
        int[] totalPoints = new int[5];
        int[] section = new int[5];

        Cursor cursorPriority = null;

        for(int i=0; i<5; i++) {
            Cursor cardsPriority = myDb.getAllRowsPriority(i+1);
            int count = cardsPriority.getCount();
            totalPoints[i] = count * points[i];
            if(i <= 0) {
                section[i] = totalPoints[i];
            }else {
                section[i] = totalPoints[i] + section[i-1];
            }
        }
        Random rand = new Random(section[4]+1);
        int drawn = rand.nextInt();

        if(drawn <= section[0]) {
            cursorPriority = myDb.getRandomRowWithpriority(1);
        } else if(drawn <= section[1]) {
            cursorPriority = myDb.getRandomRowWithpriority(2);
        } else if(drawn <= section[2]) {
            cursorPriority = myDb.getRandomRowWithpriority(3);
        } else if(drawn <= section[3]) {
            cursorPriority = myDb.getRandomRowWithpriority(4);
        } else if(drawn <= section[4]) {
            cursorPriority = myDb.getRandomRowWithpriority(5);
        }

        return cursorPriority;
    }
}
