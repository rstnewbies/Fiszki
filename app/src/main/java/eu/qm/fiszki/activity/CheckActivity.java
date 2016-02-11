package eu.qm.fiszki.activity;

import android.content.Context;
import android.app.ActionBar;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;
import eu.qm.fiszki.Alert;
import eu.qm.fiszki.Algorithm;
import eu.qm.fiszki.Checker;
import eu.qm.fiszki.database.DBAdapter;
import eu.qm.fiszki.database.DBModel;
import eu.qm.fiszki.database.DBStatus;
import eu.qm.fiszki.R;
import eu.qm.fiszki.model.Flashcard;
import eu.qm.fiszki.model.FlashcardManagement;

public class CheckActivity extends AppCompatActivity {

    Algorithm algorithm = new Algorithm();
    TextView word;
    EditText enteredWord;
    DBAdapter myDb = new DBAdapter(this);
    DBStatus OpenDataBase = new DBStatus();
    Alert alert;
    Context context;
    FlashcardManagement flashcardManagement;
    String wordFromData;
    String expectedWord;
    int rowId;
    int rowPriority;
    boolean firstTry = true;
    Flashcard flashcard;

    MenuItem mi;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        OpenDataBase.openDB(myDb);
        Cursor c = myDb.getAllRows();
        alert = new Alert();
        context = this;
        flashcardManagement = new FlashcardManagement(context);

        if (flashcardManagement.getAllFlashcards().size()<1) {
            alert.emptyBase(context, getString(R.string.main_activity_empty_base_main_layout),
                    getString(R.string.alert_title_fail), getString(R.string.button_action_ok));

        } else {

            flashcard = algorithm.drawCardAlgorithm();

                wordFromData = flashcard.getWord();
                expectedWord = flashcard.getTranslation();
                rowId = flashcard.getId();
                rowPriority = flashcard.getPriority();
                enteredWord = (EditText) findViewById(R.id.EnteredWord);
                enteredWord.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                enteredWord.setText("");
                word = (TextView) findViewById(R.id.textView3);
                word.append(wordFromData);
                enteredWord.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                keyboardAction();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(myDb.getAllRows().getCount()>0) {
            enteredWord.setText("");
        }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OpenDataBase.closeDB(myDb);
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
        mi = item;
        id = R.id.action_OK;
        Alert message = new Alert();
        Checker check = new Checker();
        if (id == R.id.action_OK)
        {
           if(check.Check(expectedWord, enteredWord.getText().toString()))
           {
               message.pass(this, getString(R.string.alert_message_pass), getString(R.string.alert_title_pass), getString(R.string.button_action_ok));

               if(rowPriority<5 && firstTry) {
                   flashcard.setPriority(flashcard.getPriority()+1);
                   flashcardManagement.updateFlashcard(flashcard);
               }
           }
           else
           {
               enteredWord.setText("");
               enteredWord.requestFocus();
            message.fail(this, expectedWord, getString(R.string.alert_message_fail),
                    getString(R.string.alert_message_tryagain), getString(R.string.alert_title_fail), getString(R.string.button_action_ok));
               firstTry = false;

               flashcard.setPriority(1);
               flashcardManagement.updateFlashcard(flashcard);
           }
        }
        else if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void keyboardAction(){
        enteredWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                   id = R.id.action_OK;
                    onOptionsItemSelected(mi);
                    enteredWord.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            keyboard.showSoftInput(enteredWord, 0);
                        }
                    },50);

                }

                return false;
            }
        });
    }

}
